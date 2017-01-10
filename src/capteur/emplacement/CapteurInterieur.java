package capteur.emplacement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import capteur.Capteur;
import capteur.TypeCapInter;

public class CapteurInterieur extends Capteur{
	private Salle salle;
	private String position;
	private TypeCapInter type;
	
	public CapteurInterieur(String id , Salle s, String p, TypeCapInter t){
		super(id);
		salle=s;
		position=p;
		type=t;
		if(t!=null)switch(t){
		case TEMPERATURE:
			setUDM("degre Celsius('C)");
			setIMin(-10); setIMax(50);
			setPrec(.1);
			setMarge(.2);
			setFrec(60);
			break;
		case CONSOMMATION_ECLAIRAGE:
			setUDM("Watt(W)");
			 setIMin(0); setIMax(3000);
			setPrec(1);
			setMarge(2);
			setFrec(30);
			break;
		case EAU_CHAUDE:
			setUDM("Litre(l)");
			setIMin(0); setIMax(1000);
			setPrec(.1);
			setMarge(0);
			setFrec(60);
			break;
		case EAU_FROIDE:
			setUDM("Litre(l)");
			setIMin(0); setIMax(1000);
			setPrec(.1);
			setMarge(0);
			setFrec(600);
			break;
		case HUMIDITE:
			setUDM("%");
			setIMin(0); setIMax(100);
			setPrec(1);
			setMarge(0);
			setFrec(90);
			break;
		case LUMINOSITE:
			setUDM("Lumen(lum)");
			setIMin(0); setIMax(1000);
			setPrec(.01);
			setMarge(.01);
			setFrec(5);
			break;
		case VOLUME_SONORE:
			setUDM("Decibel(dB)");
			setIMin(0); setIMax(120);
			setPrec(.1);
			setMarge(.1);
			setFrec(10);
			break;
		default:
			break;
		}
		DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date= new Date();
		setDate(dateFormat.format(date));
	}
	
	public TypeCapInter getType(){
		return this.type;
	}
	public Batiment getBatiment(){
		return salle.getEtage().getBatiment();
	}
	public Etage getEtage(){
		return salle.getEtage();
	}
	public Salle getSalle(){
		return salle;
	}
	public String getPosition(){
		return position;
	}
	public int compareTo(CapteurInterieur ci){
		int c=this.getBatiment().compareTo(ci.getBatiment());
		if(c!=0) return c;
		c= this.getEtage().compareTo(ci.getEtage());
		c=this.salle.compareTo(ci.salle);
		return c;
	}
	public String toString(){
		return getID()+" "+ getBatiment().toString()+", "+ getEtage().toString()+", salle :"+salle.toString()+", "+position;
	}
}
