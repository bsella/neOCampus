package capteur;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CapteurInterieur {
	private String ID;
	private Emplacement emplacement;
	private TypeCapInter type;
	private String uniteDeMesure;
	private int intervalleMin, intervalleMax;
	private double precision;
	private double margeDeConfiance;
	private int frequence;
	private String date;
	
	private Socket sToServer=new Socket("127.0.0.1",7888);
	
	public CapteurInterieur(String id ,Emplacement e, TypeCapInter t, int frequence) throws UnknownHostException, IOException{
		ID=id;
		emplacement=e;
		type=t;
		switch(t){
		case TEMPERATURE:
			this.uniteDeMesure="degre Celsius('C)";
			this.intervalleMin=-10; this.intervalleMax=50;
			this.precision=.1;
			this.margeDeConfiance=.2;
			//this.frequence=60;
			break;
		case CONSOMMATION_ECLAIRAGE:
			this.uniteDeMesure="Watt(W)";
			 this.intervalleMin=0; this.intervalleMax=3000;
			this.precision=1;
			this.margeDeConfiance=2;
			//this.frequence=30;
			break;
		case EAU_CHAUDE:
			this.uniteDeMesure="Litre(l)";
			this.intervalleMin=0; this.intervalleMax=1000;
			this.precision=.1;
			this.margeDeConfiance=0;
			//this.frequence=60;
			break;
		case EAU_FROIDE:
			this.uniteDeMesure="Litre(l)";
			this.intervalleMin=0; this.intervalleMax=1000;
			this.precision=.1;
			this.margeDeConfiance=0;
			//this.frequence=600;
			break;
		case HUMIDITE:
			this.uniteDeMesure="%";
			this.intervalleMin=0; this.intervalleMax=100;
			this.precision=1;
			this.margeDeConfiance=0;
			//this.frequence=90;
			break;
		case LUMINOSITE:
			this.uniteDeMesure="Lumen(lum)";
			this.intervalleMin=0; this.intervalleMax=1000;
			this.precision=.01;
			this.margeDeConfiance=.01;
			//this.frequence=5;
			break;
		case VOLUME_SONORE:
			this.uniteDeMesure="Decibel(dB)";
			this.intervalleMin=0; this.intervalleMax=120;
			this.precision=.1;
			this.margeDeConfiance=.1;
			//this.frequence=10;
			break;
		default:
			break;
		}
		this.frequence=frequence;
		DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date= new Date();
		this.date=dateFormat.format(date);
	}
	
	public void envoyerConnexionCapteur() throws IOException{
		PrintStream p= new PrintStream(sToServer.getOutputStream());
		p.println("ConnexionCapteur;"+ID+";"+type+";"+emplacement.getBatiment()+";"+emplacement.getEtage()+";"+emplacement.getSalle()+";"+emplacement.getDescriptif());
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(sToServer.getInputStream());
		String conf =sc.nextLine();
		if(conf.equals("ConnexionOK"))
			System.out.println("Connexion du capteur "+ ID + " reussie");
		else
			System.out.println("Connexion du capteur "+ ID + " echouee");
	}
	public void deconnecterCapteur(){
		
	}
}
