package IHM;

public class Alerte {

	String IDCapteur;
	int valAlerte;
	
	public Alerte (String ID, int valAlerte ) {
		this.IDCapteur = ID;
		this.valAlerte = valAlerte;
	}

	public String getIDCapteur() {
		return IDCapteur;
	}

	public int getValAlerte() {
		return valAlerte;
	}
}
