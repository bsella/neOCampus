package capteur;

public enum TypeCapInter {
	TEMPERATURE,
	HUMIDITE,
	LUMINOSITE,
	VOLUME_SONORE,
	CONSOMMATION_ECLAIRAGE,
	EAU_FROIDE,
	EAU_CHAUDE;
	public static TypeCapInter getTypeInter(String t){
		switch(t){
			case "TEMPERATURE":
				return TypeCapInter.TEMPERATURE;
			case "HUMIDITE":
				return TypeCapInter.HUMIDITE;
			case "LUMINOSITE":
				return TypeCapInter.LUMINOSITE;
			case "VOLUME_SONORE":
				return TypeCapInter.VOLUME_SONORE;
			case "EAU_FROIDE":
				return TypeCapInter.EAU_FROIDE;
			case "EAU_CHAUDE":
				return TypeCapInter.EAU_CHAUDE;
			case "CONSOMMATION_ECLAIRAGE":
				return TypeCapInter.CONSOMMATION_ECLAIRAGE;
			default: return null;
		}
	}
}

