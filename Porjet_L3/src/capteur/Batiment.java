package capteur;

public class Batiment {
	private String nom;
	private GPSCoord gps;
	public Batiment(String nom, double lo, double la){
		this.nom=nom;
		gps= new GPSCoord(lo,la);
	}
	public String getNom(){
		return nom;
	}
	public GPSCoord getCoord(){
		return gps;
	}
	public String toString(){
		return nom;
	}
}
