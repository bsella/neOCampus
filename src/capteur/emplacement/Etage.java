package capteur.emplacement;

import java.util.ArrayList;
import java.util.List;

public class Etage {
	private int etage;
	private Batiment bat;
	private List<Salle> salles= new ArrayList<>();
	public Etage(int e, Batiment b){
		etage=e;
		bat=b;
	}
	public int get(){
		return etage;
	}
	public Batiment getBatiment(){
		return bat;
	}
	public String toString(){
		if(etage<0) return "étage "+etage;
		if(etage==0) return "RDC";
		if(etage==1) return "1er étage";
		return Integer.toString(etage)+ "eme étage";
	}
	public void addSalle(Salle s){
		salles.add(s);
	}
	public void removeSalle(Salle s){
		salles.remove(s);
	}
	public boolean listVide(){
		return salles.isEmpty();
	}
	public List<Salle> getSalles(){
		return salles;
	}
	public int compareTo(Etage e){
		if(this.etage>e.etage)return 1;
		if(this.etage<e.etage)return -1;
		return 0;
	}
	public boolean equals(Object o){
		if(o instanceof Etage){
			Etage e=(Etage)o;
			return bat.equals(e.getBatiment()) && etage==e.etage;
		}
		return false;
	}
}
