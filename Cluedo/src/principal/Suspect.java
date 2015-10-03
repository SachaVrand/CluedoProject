package principal;
import javax.swing.Icon;

/**
 * Classe représentant une carte de type Suspect.
 * @author Sacha et Clement
 *
 */
public class Suspect extends Carte{

	/**
	 * Instancie un nouveau Suspect avec le nom et l'image passés en paramètres.
	 * @see Carte#Carte(String, Icon)
	 * @param nom Nom de la carte. Ex: Mrs_White.
	 * @param img Image correspondant à la carte.
	 */
	public Suspect(String nom, Icon img) {
		super(nom, img);
	}

}
