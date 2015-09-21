import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

/**
 * Classe repr�sentant une Carte du jeu cluedo.
 * @author Sacha et Clement
 *
 */
public class Carte {
	
	/**
	 * Image correspondant � la carte.
	 */
	private Icon image;
	
	/**
	 * Nom repr�sentant la carte.
	 */
	private String nom;
	
	/**
	 * Instancie une nouvelle Carte avec le nom et l'image pass�s en param�tres.
	 * @param nom Nom correspondant � la Carte.
	 * @param img Image correspondant � la Carte.
	 */
	public Carte(String nom, Icon img)
	{
		this.nom = nom;
		this.image = img;
	}
	
	/**
	 * M�thode retournant le nom associ� � la carte sous la forme d'une chaine de caract�res.
	 * @return Nom de la carte sous la forme d'un String
	 */
	public String getNom()
	{
		return this.nom;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
			return true;
		
		if(o instanceof Carte)
		{
			Carte tmp = (Carte)o;
			if(nom.equals(tmp.getNom()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	/**
	 * M�thode qui retourne si une carte, repr�sent�e par un String, est pr�sente dans la Collection de cartes pass�e en param�tre.
	 * @param cartes Collection de cartes dans laquelle vous souhaitez chercher.
	 * @param nom Chaine de caract�res repr�sentant la carte recherch�e.
	 * @return true si la carte est pr�sente au moins une fois dans la collection, sinon false.
	 */
	public static boolean contientCarte(List<Carte> cartes, String nom)
	{
		for(Carte c : cartes)
		{
			if(c.getNom().equals(nom))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * M�thode qui retourne les cartes en commun dans la collection d'objet et le tableau de String en les comparant les cartes avec leur nom.
	 * @param cartes Collection de cartes dans laquelle vous souhaitez chercher.
	 * @param noms Tableau de noms repr�sentant des cartes.
	 * @return Liste des cartes contenu dans la collection de cartes et le tableau de String, liste vide si aucune carte en commun.
	 */
	public static List<String> cartesContenuDans(List<Carte> cartes, String[] noms)
	{
		List<String> res = new ArrayList<String>();
		for(Carte c : cartes)
		{
			for(String n : noms)
			{
				if(c.getNom().equals(n))
				{
					res.add(n) ;
				}
			}
		}
		return res;
	}

}
