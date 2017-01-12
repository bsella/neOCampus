package village;

public class Date implements Comparable <Date>
{
	int jour, mois, annee;
	
	public Date (int jour, int mois, int annee)
	{
		this.jour = jour;
		this.mois = mois;
		this.annee = annee;
	}
	
	public int compareTo (Date date) 
	{
		if (this.annee > date.annee)
			return 1;
		else if (this.annee < date.annee)
			return -1;
		else if (this.mois > date.mois)
			return 1;
		else if (this.mois < date.mois)
			return -1;
		else if (this.jour > date.jour)
			return 1;
		else if (this.jour < date.jour)
			return -1;
		return 0;
	}
}