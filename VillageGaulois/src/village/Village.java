package village;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Village
{
	NavigableSet <Gaulois> gauloisOrdreAlphabetique = new TreeSet <Gaulois> ();
	NavigableSet <Gaulois> gauloisOrdreAge = new TreeSet <Gaulois> (new Comparator <Gaulois> ()
			{
				public int compare (Gaulois gaulois1, Gaulois gaulois2)
				{
					Date date1 = gaulois1.getDateNaissance();
					Date date2 = gaulois2.getDateNaissance();
					int comparaison = date1.compareTo(date2);
					if (comparaison == 0)
						return gaulois1.compareTo(gaulois2);
					return comparaison;
				}
			});
	NavigableSet <Gaulois> gauloisOrdreStatus = new TreeSet <Gaulois> (new Comparator <Gaulois> ()
			{
				public int compare (Gaulois gaulois1, Gaulois gaulois2)
				{
					EnumStatus status1 = gaulois1.getStatus();
					EnumStatus status2 = gaulois2.getStatus();
					int comparaison = status1.compareTo(status2);
					if (comparaison == 0)
						return gaulois1.compareTo(gaulois2);
					return comparaison;
				}
			});
	NavigableSet <Gaulois> villageois = new TreeSet <Gaulois> ();
	NavigableSet <Gaulois> membreDuConseil = new TreeSet <Gaulois> ();
	
	public Village (Gaulois chef, Gaulois villageois)
	{
		this.addGaulois(chef);
		this.addGaulois(villageois);
		this.initMembresDuConseil();
		this.initVillageois();
	}

	public NavigableSet<Gaulois> getGauloisOrdreAlphabetique() 
	{
		return gauloisOrdreAlphabetique;
	}

	public NavigableSet<Gaulois> getGauloisOrdreAge() 
	{
		return gauloisOrdreAge;
	}

	public NavigableSet<Gaulois> getGauloisOrdreStatus() 
	{
		return gauloisOrdreStatus;
	}
	
	public NavigableSet<Gaulois> getVillageois () 
	{
		return villageois;
	}

	public NavigableSet<Gaulois> getMembreDuConseil () 
	{
		return membreDuConseil;
	}
	
	public NavigableSet<Gaulois> getMembreDuConseil (String nom, Date dateNaissance)
	{
		NavigableSet <Gaulois> membresAvantGaulois = new TreeSet <Gaulois> ();
		Gaulois gaulois = new Gaulois (nom, dateNaissance, null, null);
		for (Gaulois g : membreDuConseil)
		{
			if (gaulois.equals(g))
				return membreDuConseil.headSet(g, false);
		}
		return membresAvantGaulois;
	}
	
	public void addGaulois (Gaulois gaulois)
	{
		gauloisOrdreAlphabetique.add(gaulois);
		gauloisOrdreAge.add(gaulois);
		gauloisOrdreStatus.add(gaulois);
	}
		
	public Gaulois premierVillageois ()
	{
		for (Gaulois gaulois : gauloisOrdreStatus)
		{
			if (gaulois.getStatus() == EnumStatus.VILLAGEOIS)
				return gaulois;
		}
		return (Gaulois) null;
	}
	
	private void initMembresDuConseil ()
	{
		Gaulois gaulois = premierVillageois();
		if (gaulois != null)
			membreDuConseil = gauloisOrdreStatus.headSet(gaulois, false);
		else
			membreDuConseil = gauloisOrdreStatus;
	}
	
	private void initVillageois ()
	{
		Gaulois gaulois = premierVillageois();
		if (gaulois != null)
			villageois = gauloisOrdreStatus.tailSet(gaulois, true);
	}
	
	
	
	public static void main (String[] args)
	{
		Date dateNaissance = new Date(10, 5, 1994);
		EnumStatus status = EnumStatus.BARDE;
		EnumGenre genre = EnumGenre.HOMME;
		Date dateNaissance2 = new Date(13, 6, 1997);
		EnumStatus status2 = EnumStatus.VILLAGEOIS;
		EnumGenre genre2 = EnumGenre.HOMME;
		Date dateNaissance3 = new Date(10, 5, 1998);
		EnumStatus status3 = EnumStatus.ANCIEN;
		EnumGenre genre3 = EnumGenre.HOMME;
		Date dateNaissance4 = new Date(10, 5, 1992);
		EnumStatus status4 = EnumStatus.DRUIDE;
		EnumGenre genre4 = EnumGenre.HOMME;
		Gaulois gaulois = new Gaulois("Obelix", dateNaissance, genre, status);
		Gaulois gaulois2 = new Gaulois("Asterix", dateNaissance2, genre2, status2);
		Gaulois gaulois3 = new Gaulois("Idefix", dateNaissance3, genre3, status3);
		Gaulois gaulois4 = new Gaulois("Boule de Poils", dateNaissance4, genre4, status4);
		Village village = new Village (gaulois, gaulois2);
		System.out.println("gauloisOrdreAge = " + village.gauloisOrdreAge);
		System.out.println("gauloisOrdreStatus = " + village.gauloisOrdreStatus);
		System.out.println("gauloisOrdreAlphabetique = " + village.gauloisOrdreAlphabetique);
		System.out.println("premier villageois = " + village.premierVillageois());
		System.out.println("membreDuConseil = " + village.membreDuConseil);
		System.out.println("villageois = " + village.villageois);
		System.out.println("AJOUT DE GAULOIS");
		village.addGaulois(gaulois3);
		village.addGaulois(gaulois4);
		System.out.println("gauloisOrdreAge = " + village.gauloisOrdreAge);
		System.out.println("gauloisOrdreStatus = " + village.gauloisOrdreStatus);
		System.out.println("gauloisOrdreAlphabetique = " + village.gauloisOrdreAlphabetique);
		System.out.println("premier villageois = " + village.premierVillageois());
		System.out.println("membreDuConseil = " + village.membreDuConseil);
		System.out.println("villageois = " + village.villageois);
		System.out.println("membreDuConseil AVANT GAULOIS OBELIX = " + village.getMembreDuConseil("Obelix", dateNaissance));
	}
}
