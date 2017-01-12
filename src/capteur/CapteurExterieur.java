package capteur;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CapteurExterieur extends Capteur {
	private GPSCoord emplacement;
	private TypeCapExter type;

	public CapteurExterieur(String ID,GPSCoord gps, TypeCapExter t){
		super(ID);
		emplacement=gps;
		type=t;
		if(t!=null)switch(t){
			case HUMIDITE:
				setUDM("%");
				setIMin(0); setIMax(100);
				setPrec(1);
				setMarge(0.0);
				setFrec(90);
				break;
			case LUMINOSITE:
				setUDM("Lumen(lum)");
				setIMax(0.0); setIMax(1000.0);
				setPrec(.01);
				setMarge(.01);
				setFrec(5);
				break;
			case PRESSION_ATMOSPHERIQUE:
				setUDM("Hectopascal(hPa)");
				setIMax(1000); setIMax(1100);
				setPrec(.1);
				setMarge(0);
				setFrec(5);
				break;
			case TEMPERATURE:
				setUDM("degre Celsius('C)");
				setIMax(-10); setIMax(50);
				setPrec(.1);
				setMarge(.2);
				setFrec(60);
				break;
			case VITESSE_VENT:
				setUDM("kilometre a l'heure(km/h)");
				setIMax(0); setIMax(30);
				setPrec(.1);
				setMarge(.3);
				setFrec(20);
				break;
			default:break;
		}
		DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date= new Date();
		setDate(dateFormat.format(date));
	}
	
	public TypeCapExter getType(){
		return this.type;
	}
	public GPSCoord getGPS(){
		return this.emplacement;
	}
	
	public int compareTo(CapteurExterieur c){
		return this.emplacement.compareTo(c.emplacement);
	}
	public String toString(){
		return this.emplacement.toString()+" "+ this.getID();
	}
}
