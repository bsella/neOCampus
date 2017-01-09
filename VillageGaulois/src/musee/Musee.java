package musee;

import java.util.HashMap;
import java.util.Map;

import musee.Trophee.Type;
import village.Date;
import village.EnumGenre;
import village.EnumStatus;
import village.Gaulois;

public class Musee 
{
	Map <Gaulois, Map <Trophee, Integer>> collectionTrophee = new HashMap <> ();
	
	public void ajouterTrophee (Gaulois gaulois, Trophee trophee)
	{
		Map <Trophee, Integer> mapTropheeInteger = new HashMap <> ();
		if (!collectionTrophee.containsKey(gaulois))
		{	
			mapTropheeInteger.put(trophee, 1);
			collectionTrophee.put(gaulois, mapTropheeInteger);
		}
		else if (!collectionTrophee.get(gaulois).containsKey(trophee))
		{
			mapTropheeInteger = collectionTrophee.get(gaulois);
			mapTropheeInteger.put(trophee, 1);
		}
		else
		{
			mapTropheeInteger = collectionTrophee.get(gaulois);
			mapTropheeInteger.put(trophee, collectionTrophee.get(gaulois).get(trophee) + 1);
		}
	}
	
	public void afficherTrophee ()
	{
		for (Map.Entry <Gaulois, Map <Trophee, Integer>> outer_element : collectionTrophee.entrySet())
		{
			System.out.println(outer_element.getKey());
			for (Map.Entry <Trophee, Integer> inner_element : outer_element.getValue().entrySet())
				System.out.println("- " + inner_element.getValue() + " " + inner_element.getKey());
		}
	}
	
	public int score (String nom)
	{
		int score_inter, score = 0;
		Gaulois gaulois = new Gaulois (nom, null, null, null);
		try 
		{
			for (Map.Entry <Trophee, Integer> map : collectionTrophee.get(gaulois).entrySet())
			{
				score_inter = 0;
				Trophee trophee = map.getKey();
				switch (trophee.type)
				{
					case CASQUE :
						score_inter += 3;
						break;
					case EPEE :
						score_inter += 2;
						break;
					case BOUCLIER :
						score_inter += 1;
						break;
				}
				switch (trophee.grade)
				{
					case LEGIONNAIRE :
						score_inter *= 1;
						break;
					case DECURION :
						score_inter *= 2;
						break;
					case CENTURION :
						score_inter *= 3;
						break;
					case LEGAT :
						score_inter *= 4;
						break;
				}
				score_inter *= map.getValue();
				score += score_inter;
			}
		}
		catch (NullPointerException nullP)
		{
			System.out.println("Gaulois inconnu");
			score = -1;
		}
		return score;
	}
	
	public static void main (String[] args)
	{
		Date dateNaissance = new Date(10, 5, 1994);
		EnumStatus status = EnumStatus.BARDE;
		EnumGenre genre = EnumGenre.HOMME;
		Gaulois gaulois = new Gaulois("Obelix", dateNaissance, genre, status);
		Gaulois gaulois2 = new Gaulois("Asterix", dateNaissance, genre, status);
		Trophee trophee = new Trophee (Type.BOUCLIER, Grade.CENTURION);
		Trophee trophee2 = new Trophee (Type.CASQUE, Grade.CENTURION);
		Trophee trophee3 = new Trophee (Type.BOUCLIER, Grade.LEGIONNAIRE);
		Trophee trophee4 = new Trophee (Type.CASQUE, Grade.LEGIONNAIRE);
		Musee musee = new Musee ();
		musee.ajouterTrophee(gaulois2, trophee);
		musee.ajouterTrophee(gaulois2, trophee2);
		musee.ajouterTrophee(gaulois2, trophee3);
		musee.ajouterTrophee(gaulois2, trophee4);
		musee.ajouterTrophee(gaulois2, trophee4);
		musee.ajouterTrophee(gaulois, trophee);
		musee.ajouterTrophee(gaulois, trophee2);
		musee.ajouterTrophee(gaulois, trophee3);
		musee.ajouterTrophee(gaulois, trophee3);
		musee.ajouterTrophee(gaulois, trophee4);
		musee.afficherTrophee();
		System.out.println("score Asterix = " + musee.score("Asterix"));
		System.out.println("score asterix = " + musee.score("asterix"));

	}
}