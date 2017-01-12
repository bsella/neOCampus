package IHM;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.swing.DefaultListModel;

import capteur.Capteur;

public class ServeurIHM{
	private Socket sock;
	private static Semaphore mutex= new Semaphore(0);
	private String buffer;
	private EcouteThread et= new EcouteThread(this);
	private DefaultListModel<String> lm;
	private TableauCapteurModel tableauModel;
	private CapteurTreeModel treeModel;
	public ServeurIHM(String adr, int port, DefaultListModel<String> lm, TableauCapteurModel tcm) throws Exception{
		this.lm=lm;
		this.tableauModel=tcm;
		sock=new Socket(adr,port);
		et=new EcouteThread(this);
		et.start();
	}
	public void updateBuffer(String s){
		buffer=s;
		mutex.release();
	}
	public String read(){
		try{
			@SuppressWarnings("resource")
			Scanner sc=new Scanner(sock.getInputStream());			
			if(sc.hasNextLine()){
				return sc.nextLine();
			}
		}catch(Exception e){
			System.out.println("erreur de connexion au serveur");
		}
		return null;
	}
	
	public String readThread(){
		try{
			mutex.acquire();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		return buffer;
	}
	public void send(String message){
		PrintStream p;
		try{
			p = new PrintStream(sock.getOutputStream());
			p.println(message);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public boolean envoyerConnexionIHM(String id){
		send("ConnexionVisu;"+id);
		String conf =readThread();
		if(conf.equals("ConnexionOK")){
			System.out.println("Connexion de l'IHM "+ id + " réussie");
			return true;
		}
		else{
			System.out.println("Connexion de l'IHM "+ id + " échouée");
			return false;
		}
	}
	public boolean deconnecterIHM(String id){
		send("DeconnexionVisu");
		String answer=readThread();
		if(answer.equals("DeconnexionOK")){
			System.out.println("Déconnexion de l'IHM "+id+" réussie");
			return true;
		}
		System.out.println("Déconnexion de l'IHM "+id+" échouée");
		return false;
	}
	public String[] inscrire(String ID, String[] listeCapteurs){
		String buff="InscriptionCapteur";
		for(String s : listeCapteurs)
			buff+=";"+s;
		send(buff);
		buff=readThread();
		String[] parts=buff.split(";");
		if(parts[0].equals("InscriptionCapteurKO")){
			String[] capteursValides= new String[listeCapteurs.length-(parts.length-1)];
			int index=0;
			for(int j=0;j<listeCapteurs.length;j++){
				boolean contain=false;
				for(int i=1;i<parts.length;i++){
					if(listeCapteurs[j].equals(parts[i]))
						contain=true;
				}
				if(!contain){
					capteursValides[index]=listeCapteurs[j];
					index++;
				}
			}
			return capteursValides;
		}
		return listeCapteurs;
	}
	public void terminateThread(){
		et.terminate();
	}
	public void addToList(String[] parts){
		String buff=parts[1];
		for(int i=2;i<parts.length; i++)
			buff=buff+";"+parts[i];
		lm.addElement(buff);
	}
	public void addToTable(Capteur c){
		tableauModel.add(c);
	}
	public void changeVal(String id, double val){
		tableauModel.changeVal(tableauModel.stringToCapteur(id), val);
	}
	public void remove(String id){
		//TODO REMOVE
		treeModel.remove(tableauModel.stringToCapteur(id));
		tableauModel.remove(tableauModel.stringToCapteur(id));
	}
}
