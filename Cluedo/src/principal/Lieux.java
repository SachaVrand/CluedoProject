package principal;
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
	Kitchen(new ImageIcon("Images/Kitchen.jpg")),
	
	/**
	 * Représente la carte Ballroom
	 */
	Ballroom(new ImageIcon("Images/Ballroom.jpg")),
	
	/**
	 * Représente la carte Conservatory
	 */
	Conservatory(new ImageIcon("Images/Conservatory.jpg")),
	
	/**
	 * Représente la carte Dining_room
	 */
	DiningRoom(new ImageIcon("Images/DiningRoom.jpg")),
	
	/**
	 * Représente la carte Billard_room
	 */
	BillardRoom(new ImageIcon("Images/BillardRoom.jpg")),
	
	/**
	 * Représente la carte Library
	 */
	Library(new ImageIcon("Images/Library.jpg")),
	
	/**
	 * Représente la carte Lounge
	 */
	Lounge(new ImageIcon("Images/Lounge.jpg")),
	
	/**
	 * Représente la carte Hall
	 */
	Hall(new ImageIcon("Images/Hall.jpg")),
	
	/**
	 * Représente la carte Study
	 */
	Study(new ImageIcon("Images/Study.jpg"));
	
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
