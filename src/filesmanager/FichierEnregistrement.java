package filesmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import capteur.Capteur;
import capteur.CapteurExterieur;
import capteur.GPSCoord;
import capteur.TypeCapExter;
import capteur.TypeCapInter;
import capteur.emplacement.Batiment;
import capteur.emplacement.CapteurInterieur;
import capteur.emplacement.Etage;
import capteur.emplacement.Salle;

public class FichierEnregistrement implements Serializable {
	
	private static final long serialVersionUID = -2908695004069533256L;
	
	private String identifiant;
	
	private boolean isInterieur;
	protected TypeCapExter typeE;
	protected TypeCapInter typeI;
	
	private String batiment;
	private int etage;
	private String salle;
	private String positionR;
	
	protected String latitude;
	protected String longitude;
	
	private Date dateActuelle;
	
	private List<Date> datesEnregistrements = new ArrayList<Date>();
	private Map< Date, List<Float> > donnees = new HashMap< Date, List<Float> >();
	
	public FichierEnregistrement( Object capteur ){
		if( capteur instanceof CapteurInterieur ){
			
			CapteurInterieur capteurinterieur = (CapteurInterieur)capteur;
			this.identifiant = capteurinterieur.getID();
			this.isInterieur = true;
			this.typeI = capteurinterieur.getType();
			this.batiment = capteurinterieur.getBatiment().getNom();
			this.etage = capteurinterieur.getEtage().get();
			this.salle = capteurinterieur.getSalle().toString();
			
		}else if( capteur instanceof CapteurExterieur ){
			
			CapteurExterieur capteurexterieur = (CapteurExterieur)capteur;
			this.identifiant = capteurexterieur.getID();
			this.isInterieur = false;
			this.typeE = capteurexterieur.getType();
			this.latitude = "0";
			this.longitude = "0";
			
		}
	}
	
	public String getIdentifiant(){
		return this.identifiant;
	}
	
	public boolean getType(){
		return this.isInterieur;
	}
	
	public void setDateActuelle(Date date){
		this.dateActuelle = date;
		datesEnregistrements.add(date);
	}
	
	public Date getDateActuelle(){
		return this.dateActuelle;
	}
	
	public void addDonnee( Date date, float valeur ){
		if( !donnees.containsKey(date) ){
			donnees.put(date, new ArrayList<Float>());
		}
		donnees.get(date).add(valeur);
	}
	
	public List<Date> getDates(){
		return datesEnregistrements;
	}
	
	public List<Float> getData( Date date ){
		return donnees.get(date);
	}
	
	//TODO PAS FINI
	public Capteur getCapteur(){
		if( this.isInterieur ){
			return new CapteurInterieur(identifiant, 
					new Salle(new Etage(this.etage, new Batiment(this.batiment, 0, 0)), this.salle),
					this.positionR,
					typeI);
		}else{
			return new CapteurExterieur(identifiant, new GPSCoord( 0 , 0), typeE);
		}
	}


}
