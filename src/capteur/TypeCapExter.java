package capteur;

public enum TypeCapExter {
	TEMPERATURE,
	HUMIDITE,
	LUMINOSITE,
	VITESSE_VENT,
	PRESSION_ATMOSPHERIQUE;
	public static TypeCapExter getTypeExter(String t){
		switch(t){
			case "PRESSION":
				return TypeCapExter.PRESSION_ATMOSPHERIQUE;
			case "VITESSE_VENT":
				return TypeCapExter.VITESSE_VENT;
			case "TEMPERATURE":
				return TypeCapExter.TEMPERATURE;
			case "HUMIDITE":
				return TypeCapExter.HUMIDITE;
			case "LUMINOSITE":
				return TypeCapExter.LUMINOSITE;
			default: return null;
		}
	}
}
