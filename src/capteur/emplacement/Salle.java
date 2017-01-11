package capteur.emplacement;

import java.util.ArrayList;
import java.util.List;

public class Salle {
	private String salle;
	private Etage etage;
	private List<CapteurInterieur> capteurs= new ArrayList<>();
	public Salle(Etage e, String s){
		salle=s;
		etage=e;
	}
	public String getSalle(){
		return salle;
	}
	public Etage getEtage(){
		return etage;
	}
	public String toString(){
		return salle;
	}
	public void addCapteur(CapteurInterieur c){
		capteurs.add(c);
	}
	public void removeCapteur(CapteurInterieur c){
		capteurs.remove(c);
	}
	public boolean listVide(){
		return capteurs.isEmpty();
	}
	public List<CapteurInterieur> getCapteurs(){
		return capteurs;
	}
	public int compareTo(Salle s){
		return s.salle.compareTo(s.salle);
	}
	public boolean equals(Object o){
		return etage.equals(((Salle)o).etage) && salle.equals(((Salle)o).salle);
	}
}
