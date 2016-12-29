package IHM;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServeurIHM{
	private Socket sock;
	public ServeurIHM(String adr, int port) throws Exception{
		sock=new Socket(adr,port);
	}
	public boolean envoyerConnexionIHM(String id) throws Exception{
		PrintStream p= new PrintStream(sock.getOutputStream());
		p.println("ConnexionVisu;"+id);
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(sock.getInputStream());
		String conf =sc.nextLine();
		if(conf.equals("ConnexionOK")){
			System.out.println("Connexion de l'IHM "+ id + " reussie");
			return true;
		}
		else{
			System.out.println("Connexion de l'IHM "+ id + " echouee");
			return false;
		}
	}
	public boolean deconnecterIHM(String id) throws Exception{
		PrintStream p= new PrintStream(sock.getOutputStream());
		p.println("DeconnexionVisu");
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(sock.getInputStream());
		String conf =sc.nextLine();
		if(conf.equals("DeconnexionOK")){
			System.out.println("Deconnexion de l'IHM "+id+" reussie");
			return true;
		}else{
			System.out.println("Deconnexion de l'IHM "+id+" echouee");
			return false;
		}		
	}
	public String ecouteCapteur() throws Exception{
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(sock.getInputStream());
		String buff =sc.nextLine();
		return buff;
	}
}
