package IHM;

public class Alerte{
	private double valAlerte;
	private boolean superieur;
	
	public Alerte(double valAlerte, boolean sens){
		this.valAlerte = valAlerte;
		this.superieur = sens;
	}
	public boolean isSuperieur(){
		return superieur;
	}
	public double getValAlerte() {
		return valAlerte;
	}
	public boolean equals(Object o){
		if(o instanceof Alerte){
			Alerte a=(Alerte)o;
			return valAlerte==a.valAlerte && superieur==a.superieur;
		}
		return false;
	}
}
