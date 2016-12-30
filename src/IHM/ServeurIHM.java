package IHM;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.DefaultListModel;

public class ServeurIHM{
	private Socket sock;
	private EcouteThread et;
	private DefaultListModel<String> lm;
	PipedOutputStream out= new PipedOutputStream();
	PipedInputStream in= new PipedInputStream(out);
	public ServeurIHM(String adr, int port, DefaultListModel<String> lm) throws Exception{
		sock=new Socket(adr,port);
		this.lm=lm;
	}
	public String read() throws Exception{
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(sock.getInputStream());
		if(sc.hasNextLine()){
			return sc.nextLine();
		}
		return null;
	}
	public String readThread() throws Exception{
		String buff="";
		char c;
		do{
			c=(char)in.read();
			if(c!='\0')buff+=c;
		}while(c!='\0');
		return buff;
	}
	public void send(String message)throws Exception{
		PrintStream p= new PrintStream(sock.getOutputStream());
		p.println(message);
	}
	public boolean envoyerConnexionIHM(String id) throws Exception{
		send("ConnexionVisu;"+id);
		String conf =read();
		if(conf.equals("ConnexionOK")){
			System.out.println("Connexion de l'IHM "+ id + " réussie");
			et=new EcouteThread(this, lm, out);
			et.start();
			return true;
		}
		else{
			System.out.println("Connexion de l'IHM "+ id + " échouée");
			return false;
		}
	}
	public boolean deconnecterIHM(String id) throws Exception{
		send("DeconnexionVisu");
		String answer=readThread();
		if(answer.equals("DeconnexionOK")){
			System.out.println("Déconnexion de l'IHM "+id+" réussie");
			et.terminate();
			return true;
		}
		System.out.println("Déconnexion de l'IHM "+id+" échouée");
		return false;
	}
	public String[] inscrire(String ID, String[] listeCapteurs) throws Exception{
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
}
