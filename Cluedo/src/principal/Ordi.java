package principal;
import java.util.List;

/**
 * Classe repr�sentant un joueur de type Ordinateur.
 * @author Sacha et Clement
 *
 */
public class Ordi extends Joueur
{
	/**
	 * Instancie un nouveau joueur de type ordinateur.
	 * @see Joueur#Joueur(String, String)
	 * @param nom Nom du joueur.
	 * @param adresse Adresse ip du joueur.
	 */
	public Ordi(String nom)
	{
		super(nom);
	}

	/**
	 * M�thode non impl�ment�e
	 * @return Null
	 */
	@Override
	public String[] jouerCoup() 
	{
		return null;
		
	}

	/**
	 * M�thode non impl�ment�e
	 * @return null
	 */
	@Override
	public String refuter(List<String> cartesCommun) 
	{
		return "";
	}
}
