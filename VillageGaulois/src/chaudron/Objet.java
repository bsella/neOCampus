package chaudron;
import java.util.ArrayList;
import java.util.List;

public abstract class Objet 
{
	List <Objet> list = new ArrayList<Objet>();
	String nom;
	
	public Objet (String nom)
	{
		this.nom = nom;
	}

	public String getNom () 
	{
		return nom;
	}
	
	public String toString ()
	{
		return getNom();
	}
	
	public boolean equals (Object objet)
	{
		return ((objet instanceof Objet) && ((Objet)objet).getNom() == this.getNom());
	}
	
	public void ajouterObjet (Objet objet)
	{
		boolean identique = false;
		for (Objet o : list)
		{
			if (equals(o))
			{
				identique = true;
				break;
			}
		}
		if (!identique)
			list.add(objet);
	}
	
	public void retirerObjet (Objet objet)
	{
		list.remove(objet);
	}
}
