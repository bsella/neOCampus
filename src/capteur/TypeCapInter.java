package capteur;

public enum TypeCapInter {
	TEMPERATURE("Température"),
	HUMIDITE("Humidité"),
	LUMINOSITE("Luminosité"),
	VOLUME_SONORE("Volume sonore"),
	CONSOMMATION_ECLAIRAGE("Consommation éclairage"),
	EAU_FROIDE("Eau froide"),
	EAU_CHAUDE("Eau chaude");
	private String nom;
	private TypeCapInter(String s){
		this.nom= s;
	}
	public String toString(){
		return nom;
	}
	public static TypeCapInter getType(String s){
		for(TypeCapInter inter : TypeCapInter.values())
			if(inter.nom.equals(s))
				return inter;
		return null;
	}
}

