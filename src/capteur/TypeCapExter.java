package capteur;

public enum TypeCapExter {
	TEMPERATURE("Température"),
	HUMIDITE("Humidité"),
	LUMINOSITE("Luminosité"),
	VITESSE_VENT("Vitesse vent"),
	PRESSION_ATMOSPHERIQUE("Pression atmosphérique");
	private String nom;
	private TypeCapExter(String nom){
		this.nom=nom;
	}
	public String toString(){
		return this.nom;
	}
	public static TypeCapExter getType(String s){
		for(TypeCapExter exter : TypeCapExter.values())
			if(exter.nom.equals(s))
				return exter;
		return null;
	}
}
