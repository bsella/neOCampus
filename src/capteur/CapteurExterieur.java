package capteur;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

	public CapteurExterieur(String ID, GPSCoord gps, TypeCapExter t){
		this.ID=ID;
		emplacement=gps;
		type=t;
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
			default:break;
		}
		DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date= new Date();
		this.date=dateFormat.format(date);
	}
	
	public String getID(){
		return ID;
	}
	public TypeCapExter getType(){
		return this.type;
	}
	public GPSCoord getGPS(){
		return this.emplacement;
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
	public int compareTo(CapteurExterieur c){
		return this.emplacement.compareTo(c.emplacement);
	}
	public String toString(){
		return this.emplacement.toString()+" "+ this.ID;
	}
}
