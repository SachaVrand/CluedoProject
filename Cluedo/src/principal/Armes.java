package principal;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Enumération des differentes cartes de type Arme présentes dans le jeu cluedo.
 * @author Sacha et Clement
 *
 */
public enum Armes {

	/**
	 * Représente la carte Candlestick
	 */
	Candlestick(new ImageIcon("Images/Candlestick.jpg")),
	
	/**
	 * Représente la carte Dagger
	 */
	Dagger(new ImageIcon("Images/Dagger.jpg")),
	
	/**
	 * Représente la carte Lead_pipe
	 */
	LeadPipe(new ImageIcon("Images/LeadPipe.jpg")),
	
	/**
	 * Représente la carte Revolver
	 */
	Revolver(new ImageIcon("Images/Revolver.jpg")),
	
	/**
	 * Représente la carte Rope
	 */
	Rope(new ImageIcon("Images/Rope.jpg")),
	
	/**
	 * Représente la carte Spanner
	 */
	Spanner(new ImageIcon("Images/Spanner.jpg"));
	
	/**
	 * Image de type Icon représentant la carte.
	 */
	private Icon img;
	
	/**
	 * Constructeur de l'énumération.
	 * @param img Image de type Icon représentant la carte.
	 */
	Armes(Icon img)
	{
		this.img = img;
	}
	
	/**
	 * Méthode qui retourne l'image associée à la carte sous la forme d'un Icon.
	 * @return Icon représentant l'image associée à la carte.
	 */
	public Icon getImage()
	{
		return this.img;
	}
}
