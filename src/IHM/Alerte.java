package IHM;

public class Alerte {

	String IDCapteur;
	int valAlerte;
	boolean sens;
	
	public Alerte (String ID, int valAlerte, boolean sens ) {
		this.IDCapteur = ID;
		this.valAlerte = valAlerte;
		this.sens = sens;
	}

	public boolean isSens() {
		return sens;
	}
	
	public String getIDCapteur() {
		return IDCapteur;
	}

	public int getValAlerte() {
		return valAlerte;
	}
}
