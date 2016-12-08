package capteur;

import java.io.PrintStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class CapteurInterieur {
	private String ID;
	private Emplacement emplacement;
	private TypeCapInter type;
	protected String uniteDeMesure;
	private int intervalleMin, intervalleMax;
	private double precision;
	protected double margeDeConfiance;
	private int frequence;
	private String date;
	private Socket sToServer;
	
	
	public CapteurInterieur(String id ,Emplacement e, TypeCapInter t, String adr, int port) throws Exception{
		sToServer=new Socket(adr,port);
		ID=id;
		emplacement=e;
		type=t;
		switch(t){
		case TEMPERATURE:
			this.uniteDeMesure="degre Celsius('C)";
			this.intervalleMin=-10; this.intervalleMax=50;
			this.precision=.1;
			this.margeDeConfiance=.2;
			this.frequence=60;
			break;
		case CONSOMMATION_ECLAIRAGE:
			this.uniteDeMesure="Watt(W)";
			 this.intervalleMin=0; this.intervalleMax=3000;
			this.precision=1;
			this.margeDeConfiance=2;
			this.frequence=30;
			break;
		case EAU_CHAUDE:
			this.uniteDeMesure="Litre(l)";
			this.intervalleMin=0; this.intervalleMax=1000;
			this.precision=.1;
			this.margeDeConfiance=0;
			this.frequence=60;
			break;
		case EAU_FROIDE:
			this.uniteDeMesure="Litre(l)";
			this.intervalleMin=0; this.intervalleMax=1000;
			this.precision=.1;
			this.margeDeConfiance=0;
			this.frequence=600;
			break;
		case HUMIDITE:
			this.uniteDeMesure="%";
			this.intervalleMin=0; this.intervalleMax=100;
			this.precision=1;
			this.margeDeConfiance=0;
			this.frequence=90;
			break;
		case LUMINOSITE:
			this.uniteDeMesure="Lumen(lum)";
			this.intervalleMin=0; this.intervalleMax=1000;
			this.precision=.01;
			this.margeDeConfiance=.01;
			this.frequence=5;
			break;
		case VOLUME_SONORE:
			this.uniteDeMesure="Decibel(dB)";
			this.intervalleMin=0; this.intervalleMax=120;
			this.precision=.1;
			this.margeDeConfiance=.1;
			this.frequence=10;
			break;
		default:
			break;
		}
		DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date= new Date();
		this.date=dateFormat.format(date);
	}
	
	public boolean envoyerConnexionCapteur() throws Exception{
		PrintStream p= new PrintStream(sToServer.getOutputStream());
		p.println("ConnexionCapteur;"+ID+";"+type+";"+emplacement.getBatiment()+";"+emplacement.getEtage()+";"+emplacement.getSalle()+";"+emplacement.getDescriptif());
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(sToServer.getInputStream());
		String conf =sc.nextLine();
		if(conf.equals("ConnexionOK")){
			System.out.println("Connexion du capteur "+ ID + " reussie");
			return true;
		}
		else{
			System.out.println("Connexion du capteur "+ ID + " echouee");
			return false;
		}
	}
	
	public boolean deconnecterCapteur() throws Exception{
		PrintStream p= new PrintStream(sToServer.getOutputStream());
		p.println("DeconnexionCapteur;"+ID);
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(sToServer.getInputStream());
		String conf =sc.nextLine();
		if(conf.equals("DeconnexionOK")){
			System.out.println("Deconnexion du capteur "+ ID + " reussie");
			return true;
		}else{
			System.out.println("Deconnexion du capteur "+ ID + " echouee");
			return false;
		}		
	}
	public String getDate(){
		return date;
	}
	public int getFrequence(){
		return frequence;
	}
	public double simule(){
		Random r= new Random();
		double d=(intervalleMin+(intervalleMax-intervalleMin)*r.nextDouble())/precision;
		return (int)d*precision;
	}
	public void envoyerValeurCapteur(double val) throws Exception{
		PrintStream p=new PrintStream(sToServer.getOutputStream());
		p.println("ValeurCapteur;"+val);
	}
}
