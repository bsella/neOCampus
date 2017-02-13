package IHM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.DefaultListModel;

import capteur.Capteur;
import capteur.CapteurExterieur;
import capteur.GPSCoord;
import capteur.TypeCapExter;
import capteur.TypeCapInter;
import capteur.emplacement.Batiment;
import capteur.emplacement.CapteurInterieur;
import capteur.emplacement.Etage;
import capteur.emplacement.Salle;
import interfaceSansConnexion.InterfaceSansConnexion;

public class ServeurIHM extends Thread{
	private Socket sock;
	private boolean running;
	private Semaphore sem= new Semaphore(0);
	BufferedReader br;
	private DefaultListModel<Capteur> lm;
	private TableauCapteurModel tableauModel;
	private CapteurTreeModel treeModel;
	private String buffer;
	public ServeurIHM(String adr, int port, DefaultListModel<Capteur> listmodel, TableauCapteurModel tcm, CapteurTreeModel ctm) throws Exception{
		lm=listmodel;
		tableauModel=tcm;
		treeModel=ctm;
		sock=new Socket(adr,port);
		br= new BufferedReader(new InputStreamReader(sock.getInputStream()));
		running=true;
		this.start();
	}
	private String read() throws IOException{
		return br.readLine();
	}
	private String readThread(){
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	private void send(String message){
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
	public Capteur[] inscrire(String ID, List<Capteur> listeCapteurs){
		String buff="InscriptionCapteur";
		for(Capteur cap : listeCapteurs)
			buff+=";"+cap.getID();
		send(buff);
		buff=readThread();
		String[] parts=buff.split(";");
		if(parts[0].equals("InscriptionCapteurKO")){
			Capteur[] capteursValides= new Capteur[listeCapteurs.size()-(parts.length-1)];
			int index=0;
			for(int j=0;j<listeCapteurs.size();j++){
				boolean contain=false;
				for(int i=1;i<parts.length;i++){
					if(listeCapteurs.get(j).getID().equals(parts[i]))
						contain=true;
				}
				if(!contain){
					capteursValides[index]=listeCapteurs.get(j);
					index++;
				}
			}
			return capteursValides;
		}
		Capteur[] returnCap= new Capteur[listeCapteurs.size()];
		for(int i=0; i<listeCapteurs.size();i++)
			returnCap[i]=listeCapteurs.get(i);
		return returnCap;
	}
	
	public Capteur[] desinscrire(String ID, List<Capteur> listeCapteurs){
		String buff="DesinscriptionCapteur";
		for(Capteur cap : listeCapteurs)
			buff+=";"+cap.getID();
		send(buff);
		buff=readThread();
		String[] parts=buff.split(";");
		if(parts[0].equals("DesinscriptionCapteurKO")){
			Capteur[] capteursValides= new Capteur[listeCapteurs.size()-(parts.length-1)];
			int index=0;
			for(int j=0;j<listeCapteurs.size();j++){
				boolean contain=false;
				for(int i=1;i<parts.length;i++){
					if(listeCapteurs.get(j).getID().equals(parts[i]))
						contain=true;
				}
				if(!contain){
					capteursValides[index]=listeCapteurs.get(j);
					index++;
				}
			}
			return capteursValides;
		}
		Capteur[] returnCap= new Capteur[listeCapteurs.size()];
		for(int i=0; i<listeCapteurs.size();i++)
			returnCap[i]=listeCapteurs.get(i);
		return returnCap;
	}
	public void terminate(){
		running=false;
	}
	public void addToList(Capteur cap){
		lm.addElement(cap);
	}
	public void addToTable(Capteur c){
		tableauModel.add(c);
	}
	public void changeVal(String id, double val){
		tableauModel.changeVal(tableauModel.stringToCapteur(id), val);
	}
	public void remove(String id){
		for(int i=0;i< lm.size();i++){
			if(lm.getElementAt(i).getID().equals(id))
				lm.remove(i);
		}
		treeModel.remove(id);
		tableauModel.remove(id);
	}
	public void run(){
		try{
			while(running){
				String buff =read();
				String parts[]= buff.split(";");
				System.out.println(buff);
				switch(parts[0]){
					case "CapteurPresent":
						if(parts.length==7){
							CapteurInterieur cap= new CapteurInterieur(parts[1], new Salle(new Etage(Integer.parseInt(parts[4]), new Batiment(parts[3], 0, 0)), parts[5]), parts[6], TypeCapInter.getType(parts[2]));
							addToList(cap);							
						}
						else{
							CapteurExterieur cap = new CapteurExterieur(parts[1], new GPSCoord(Double.parseDouble(parts[3]), Double.parseDouble(parts[4])), TypeCapExter.getType(parts[2]));
							addToList(cap);
						}
						break;
					case "ValeurCapteur":
						changeVal(parts[1], Double.parseDouble(parts[2]));
						InterfaceSansConnexion.recupererDonnee(parts[1], Float.parseFloat(parts[2]));
						break;
					case "CapteurDeco":
						remove(parts[1]);
						break;
					default:
						buffer=buff;
						sem.release();
				}
			}
			this.join();
		}catch(Exception e){}
	}
}
