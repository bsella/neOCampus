package chaudron;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Potion
{
	List <Ingredient> list = new ArrayList<Ingredient>();
	ListIterator <Ingredient> iter = list.listIterator();
	
	public void ajouterIngredient (Ingredient ingredient)
	{
		iter = list.listIterator();
		if (!ingredient.isIndispensable())
		{
			while (iter.hasNext())
				iter.next();
		}
		iter.add(ingredient);
	}
	
	public static void main (String[] args)
	{
		Potion potion = new Potion();
		Ingredient sel = new Ingredient("sel", true);
		Ingredient poivre = new Ingredient("poivre", false);
		Ingredient poivron = new Ingredient("poivron", true);
		Ingredient poisson = new Ingredient ("poisson", false);
		potion.ajouterIngredient(sel);
		potion.ajouterIngredient(poivre);
		potion.ajouterIngredient(poivron);
		potion.ajouterIngredient(poisson);
		System.out.println(potion.list);
	}
}
