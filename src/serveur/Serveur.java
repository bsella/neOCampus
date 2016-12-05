package serveur;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import IHM.IHM;
import capteur.CapteurExterieur;
import capteur.CapteurInterieur;

public class Serveur {
	private List<CapteurInterieur> listeCapteurInter = new ArrayList<>();
	private List<CapteurExterieur> listeCapteurExter = new ArrayList<>();
	private List<IHM> listeIHM = new ArrayList<>();
	private ServerSocket sSocket;
	private Socket s;
	
	public Serveur() throws IOException{
		sSocket= new ServerSocket(8888);
		s=sSocket.accept();
	}	

	public void enregistrerCapteur() throws IOException{
		//listeCapteurInter.add(new CapteurInterieur(0, null, null));
		for(IHM ihm : listeIHM){
			//ihm.envoyerInformationsCapteur();
		}
		PrintStream p=new PrintStream(s.getOutputStream());
		p.println("ConnexionOK");
	}
	public void diffuser(int ID, int valeur){
		
	}
}
