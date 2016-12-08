package capteur;

import java.io.PrintStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class CapteurExterieur {
	private String ID;
	private GPSCoord emplacement;
	private TypeCapExter type;
	protected String uniteDeMesure;
	private double intervalleMin;
	private double intervalleMax;
	private double precision;
	protected double margeDeConfiance;
	private int frequence;
	private String date;
	
	private Socket sToServer;

	public CapteurExterieur(String ID, GPSCoord gps, TypeCapExter t, String adr, int port)  throws Exception{
		sToServer= new Socket(adr, port);
		this.ID=ID;
		this.emplacement=gps;
		this.type=t;
		if(t!=null)switch(t){
			case HUMIDITE:
				this.uniteDeMesure="%";
				this.intervalleMin=0; this.intervalleMax=100;
				this.precision=1;
				this.margeDeConfiance=0;
				this.frequence=90;
				break;
			case LUMINOSITE:
				this.uniteDeMesure="Lumen(lum)";
				this.intervalleMin=0.0; this.intervalleMax=1000.0;
				this.precision=.01;
				this.margeDeConfiance=.01;
				this.frequence=5;
				break;
			case PRESSION_ATMOSPHERIQUE:
				this.uniteDeMesure="Hectopascal(hPa)";
				this.intervalleMin=1000; this.intervalleMax=1100;
				this.precision=.1;
				this.margeDeConfiance=0;
				this.frequence=5;
				break;
			case TEMPERATURE:
				this.uniteDeMesure="degre Celsius('C)";
				this.intervalleMin=-10; this.intervalleMax=50;
				this.precision=.1;
				this.margeDeConfiance=.2;
				this.frequence=60;
				break;
			case VITESSE_VENT:
				this.uniteDeMesure="kilometre a l'heure(km/h)";
				this.intervalleMin=0; this.intervalleMax=30;
				this.precision=.1;
				this.margeDeConfiance=.3;
				this.frequence=20;
				break;
			default:
				this.intervalleMin=20; this.intervalleMax=30;
		}
		DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date= new Date();
		this.date=dateFormat.format(date);
	}
	public boolean envoyerConnectionCapteur() throws Exception{
		PrintStream p=new PrintStream(sToServer.getOutputStream());
		p.println("ConnexionCapteur;"+ID+";"+type+";"+emplacement.getLatitude()+";"+emplacement.getLongitude());
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
		double d= intervalleMin+r.nextDouble()*(intervalleMax-intervalleMin);
		return (double)((int)(d/precision)*precision);
	}
	public void envoyerValeurCapteur(double val) throws Exception{
		PrintStream p=new PrintStream(sToServer.getOutputStream());
		p.println("ValeurCapteur;"+val);
	}
}
