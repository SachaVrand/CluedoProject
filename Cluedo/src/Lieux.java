import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Enumération des differentes cartes de type Lieu présentes dans le jeu cluedo.
 * @author Sacha et Clement
 *
 */
public enum Lieux {
	
	/**
	 * Représente la carte Kitchen
	 */
	Kitchen(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Ballroom
	 */
	Ballroom(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Conservatory
	 */
	Conservatory(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Dining_room
	 */
	Dining_room(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Billard_room
	 */
	Billard_room(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Library
	 */
	Library(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Lounge
	 */
	Lounge(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Hall
	 */
	Hall(new ImageIcon("pathManquant")),
	
	/**
	 * Représente la carte Study
	 */
	Study(new ImageIcon("pathManquant"));
	
	/**
	 * Représente l'image associée à la carte.
	 */
	private Icon img;
	
	/**
	 * Constructeur d l'énumération Lieux
	 * @param img Icon représentant l'iamage de la carte.
	 */
	Lieux(Icon img)
	{
		this.img = img;
	}
	
	/**
	 * Méthode qui retourne l'image associée à la carte sous la forme d'un Icon
	 * @return Icon représentant l'image associée à la carte.
	 */
	public Icon getImage()
	{
		return this.img;
	}

}
