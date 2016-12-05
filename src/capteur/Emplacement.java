package capteur;

public class Emplacement {
	private Batiment batiment;
	private int etage;
	private String salle;
	private String descriptif;
	
	public Emplacement(Batiment b, int e, String s, String d){
		this.batiment=b;
		this.etage=e;
		this.salle=s;
		this.descriptif=d;
	}
	public String toString(){
		return batiment.toString()+", "+etage+"e etage, salle :"+salle+", "+descriptif;
	}
	public Batiment getBatiment(){
		return batiment;
	}
	public int getEtage(){
		return etage;
	}
	public String getSalle(){
		return salle;
	}
	public String getDescriptif(){
		return descriptif;
	}
}
