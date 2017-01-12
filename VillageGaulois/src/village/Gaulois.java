package village;

public class Gaulois implements Comparable <Gaulois>
{
	String nom;
	Date dateNaissance;
	EnumGenre genre;
	EnumStatus status;
	
	public Gaulois (String nom, Date dateNaissance, EnumGenre genre, EnumStatus status)
	{
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.genre = genre;
		this.status = status;
	}
	
	public String getNom ()
	{
		return this.nom;
	}
	
	public String toString ()
	{
		return getNom();
	}
	
	public EnumGenre getGenre ()
	{
		return this.genre;
	}
	
	public EnumStatus getStatus ()
	{
		return this.status;
	}
	
	public Date getDateNaissance ()
	{
		return this.dateNaissance;
	}
	
	public boolean equals (Object o)
	{
		boolean bool = true;
		if (o instanceof Gaulois)
		{
			if (this.getNom() != null)
			{
				if (((Gaulois) o).getNom() != this.getNom())
					bool = false;
			}
			if (this.getDateNaissance() != null)
			{
				if (((Gaulois) o).getDateNaissance() != this.getDateNaissance())
					bool = false;
			}
			if (this.getGenre() != null)
			{
				if (((Gaulois) o).getGenre() != this.getGenre())
					bool = false;
			}
			if (this.getStatus() != null)
			{
				if (((Gaulois) o).getStatus() != this.getStatus())
					bool = false;
			}
		}
		else
			bool = false;
		return bool;
	}

	public int compareTo (Gaulois gaulois) 
	{
		return nom.compareTo(gaulois.getNom());
	}
	
	public int hashCode ()
	{
		return nom.hashCode();
	}
}
