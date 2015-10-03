package principal;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Enumération des differentes cartes de type Suspect présentes dans le jeu cluedo.
 * @author Sacha et Clement
 *
 */
public enum Suspects {

	/**
	 * Représente la carte Miss_Scarlett
	 */
	Scarlett(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Colonel_Mustard
	 */
	Mustard(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Mrs_White
	 */
	White(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Professor_Plum
	 */
	Plum(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Mrs_Peacock
	 */
	Peacock(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Reverend_Green
	 */
	Green(new ImageIcon("pathManquant"));
	
	/**
	 * Image associée à la carte.
	 */
	private Icon img;
	
	/**
	 * Constructeur de l'énumération.
	 * @param img Icon correspondant à l'image de la carte.
	 */
	Suspects(Icon img)
	{
		this.img = img;
	}
	
	/**
	 * Méthode qui retourne l'image associée sous la forme d'un Icon.
	 * @return Icon représentant l'image de la carte.
	 */
	public Icon getImage()
	{
		return this.img;
	}
}
