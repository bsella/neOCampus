package musee;

public class Trophee 
{
	public enum Type
	{
		CASQUE, EPEE, BOUCLIER;
	}
	Type type;
	Grade grade;
	
	public Trophee (Type type, Grade grade)
	{
		this.type = type;
		this.grade = grade;
	}
	
	public Type getType () 
	{
		return type;
	}

	public Grade getGrade () 
	{
		return grade;
	}
	
	public boolean equals (Object objet) 
	{
		return (this.type == ((Trophee) objet).getType() && this.grade == ((Trophee) objet).getGrade());
	}
	
	public int hashCode ()
	{
		return this.getType().hashCode() * this.getGrade().hashCode();
	}
	
	public String toString ()
	{
		return this.getType() + " d'un " + this.getGrade();
	}
}
