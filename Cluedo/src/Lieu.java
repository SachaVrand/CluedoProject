import javax.swing.Icon;

/**
 * Classe représentant une carte de type Lieu.
 * @author Sacha
 *
 */
public class Lieu extends Carte{

	/**
	 * Instancie un nouveau Lieu avec le nom et l'image passés en paramètres.
	 * @see Carte#Carte(String, Icon)
	 * @param nom Nom de la carte. Ex: Kitchen.
	 * @param img Image correspondant à la carte.
	 */
	public Lieu(String nom, Icon img) {
		super(nom, img);
	}

}
