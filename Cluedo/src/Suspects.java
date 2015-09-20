import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Enum�ration des differentes cartes de type Suspect pr�sentes dans le jeu cluedo.
 * @author Sacha
 *
 */
public enum Suspects {

	/**
	 * Repr�sente la carte Miss_Scarlett
	 */
	Miss_Scarlett(new ImageIcon("pathManquant")),
	
	/**
	 * Repr�sente la carte Colonel_Mustard
	 */
	Colonel_Mustard(new ImageIcon("pathManquant")),
	
	/**
	 * Repr�sente la carte Mrs_White
	 */
	Mrs_White(new ImageIcon("pathManquant")),
	
	/**
	 * Repr�sente la carte Professor_Plum
	 */
	Professor_Plum(new ImageIcon("pathManquant")),
	
	/**
	 * Repr�sente la carte Mrs_Peacock
	 */
	Mrs_Peacock(new ImageIcon("pathManquant")),
	
	/**
	 * Repr�sente la carte Reverend_Green
	 */
	Reverend_Green(new ImageIcon("pathManquant"));
	
	/**
	 * Image associ�e � la carte.
	 */
	private Icon img;
	
	/**
	 * Constructeur de l'�num�ration.
	 * @param img Icon correspondant � l'image de la carte.
	 */
	Suspects(Icon img)
	{
		this.img = img;
	}
	
	/**
	 * M�thode qui retourne l'image associ�e sous la forme d'un Icon.
	 * @return Icon repr�sentant l'image de la carte.
	 */
	public Icon getImage()
	{
		return this.img;
	}
}
