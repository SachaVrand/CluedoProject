package principal;
import javax.swing.Icon;

/**
 * Classe représentant une carte de type Arme.
 * @author Sacha et Clement
 *
 */
public class Arme extends Carte{

	/**
	 * Instancie une nouvelle Arme avec le nom et l'image passés en paramètres.
	 * @see Carte#Carte(String, Icon)
	 * @param nom Nom de la carte. Ex: Dagger.
	 * @param img Image correspondant à la carte.
	 */
	public Arme(String nom, Icon img) {
		super(nom, img);
	}

}
