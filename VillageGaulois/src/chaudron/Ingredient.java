package chaudron;

public class Ingredient extends Objet
{
	boolean indispensable;
	
	public Ingredient (String nom, boolean indispensable)
	{
		super(nom);
		this.indispensable = indispensable;
	}

	public boolean isIndispensable () 
	{
		return indispensable;
	}
}
