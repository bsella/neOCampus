package chaudron;

public class Instrument extends Objet 
{
	public enum Type { BOIS, CORDE, CUIVRE, PERCUSSION; }
	public Type type;
	
	public Instrument (String nom, Type type)
	{
		super(nom);
		this.type = type;
	}
}
