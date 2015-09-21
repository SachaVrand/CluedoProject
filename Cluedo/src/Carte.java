import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

/**
 * Classe représentant une Carte du jeu cluedo.
 * @author Sacha et Clement
 *
 */
public class Carte {
	
	/**
	 * Image correspondant à la carte.
	 */
	private Icon image;
	
	/**
	 * Nom représentant la carte.
	 */
	private String nom;
	
	/**
	 * Instancie une nouvelle Carte avec le nom et l'image passés en paramètres.
	 * @param nom Nom correspondant à la Carte.
	 * @param img Image correspondant à la Carte.
	 */
	public Carte(String nom, Icon img)
	{
		this.nom = nom;
		this.image = img;
	}
	
	/**
	 * Méthode retournant le nom associé à la carte sous la forme d'une chaine de caractères.
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
	 * Méthode qui retourne si une carte, représentée par un String, est présente dans la Collection de cartes passée en paramètre.
	 * @param cartes Collection de cartes dans laquelle vous souhaitez chercher.
	 * @param nom Chaine de caractères représentant la carte recherchée.
	 * @return true si la carte est présente au moins une fois dans la collection, sinon false.
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
	 * Méthode qui retourne les cartes en commun dans la collection d'objet et le tableau de String en les comparant les cartes avec leur nom.
	 * @param cartes Collection de cartes dans laquelle vous souhaitez chercher.
	 * @param noms Tableau de noms représentant des cartes.
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
