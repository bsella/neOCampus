package IHM;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.DefaultListModel;

import capteur.CapteurExterieur;
import capteur.CapteurInterieur;

public class ServeurIHM{
	private Socket sock;
	PipedOutputStream out= new PipedOutputStream();
	PipedInputStream in= new PipedInputStream(out);
	private TableauCapteurIntModel tcim;
	private TableauCapteurExtModel tcem;
	DefaultListModel<String> lm;
	private EcouteThread et= new EcouteThread(this,out);
	public ServeurIHM(String adr, int port, DefaultListModel<String> lm) throws Exception{
		this.lm=lm;
		sock=new Socket(adr,port);
		et=new EcouteThread(this, out);
		et.start();
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
		String buff="";
		char c;
		try{
			do{
				c=(char)in.read();
				if(c!='\0')buff+=c;
			}while(c!='\0');
		}catch(Exception e){
			System.out.println("erreur lors de la lecture du thread");
		}
		return buff;
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
	public void addToTable(CapteurInterieur ci){
		tcim.add(ci);
	}
	public void addToTable(CapteurExterieur ce){
		tcem.add(ce);
	}
	public void changeVal(CapteurInterieur ci, double val){
		tcim.changeVal(ci, val);
	}
	public void changeVal(CapteurExterieur ce, double val){
		tcem.changeVal(ce,val);
	}
	
}
