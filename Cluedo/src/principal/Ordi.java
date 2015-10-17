package principal;
import java.util.Collections;
import java.util.List;

/**
 * Classe représentant un joueur de type Ordinateur.
 * @author Sacha et Clement
 *
 */
public class Ordi extends Joueur
{
	
	/**
	 * Représente le niveau d'intelligence de l'objet Ordi. 0 = Random
	 */
	int IALevel;
	
	
	/**
	 * Instancie un nouveau joueur de type ordinateur.
	 * @see Joueur#Joueur(String, String)
	 * @param nom Nom du joueur.
	 * @param adresse Adresse ip du joueur.
	 */
	public Ordi(String nom,int IALevel)
	{
		super(nom);
		this.IALevel = IALevel;
	}

	/**
	 * Méthode non implémentée
	 * @return Null
	 */
	@Override
	public String[] jouerCoup() 
	{
		String[] res = new String[4];
		
		//Random
		if(IALevel == 0)
		{
			res = getCoupRandom();
		}
		
		return res;
	}

	/**
	 * Méthode non implémentée
	 * @return null
	 */
	@Override
	public String refuter(List<String> cartesCommun) 
	{
		String res = "";
		
		//Random
		if(IALevel == 0)
		{
			res = getRefuterRandom(cartesCommun);
		}
		
		return res;
	}
	
	public String[] getCoupRandom()
	{
		return null;
	}
	
	public String getRefuterRandom(List<String> cartesCommun)
	{
		Collections.shuffle(cartesCommun);
		return cartesCommun.get(1);
	}
}
