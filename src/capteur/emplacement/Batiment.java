package capteur.emplacement;

import java.util.ArrayList;
import java.util.List;

import capteur.GPSCoord;

public class Batiment {
	private String nom;
	private GPSCoord gps;
	List<Etage> etages= new ArrayList<>();
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
	public int compareTo(Batiment b){
		return this.nom.compareTo(b.nom);
	}
	public void addEtage(Etage e){
		etages.add(e);
	}
	public void removeEtage(Etage e){
		etages.remove(e);
	}
	public boolean listVide(){
		return etages.isEmpty();
	}
	public List<Etage> getEtages(){
		return etages;
	}
	public boolean equals(Object o){
		if(o instanceof Batiment)
			return nom.equals(((Batiment)o).getNom());
		return false;
	}
}
