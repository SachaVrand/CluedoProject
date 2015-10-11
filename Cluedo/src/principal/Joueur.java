package principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite r�presentant un joueur de la partie de cluedo.
 * @author Sacha et Clement
 *
 */
public abstract class Joueur {
	
	/**
	 * Repr�sente le nom du joueur.
	 */
	protected String nom;
	
	/**
	 * Bool�en repr�sentant si le joueur est encore en jeu ou non. true si oui, sinon false.
	 */
	protected boolean encoreEnJeu;
	
	/**
	 * Collection de carte que le joueur a durant une partie.
	 */
	protected List<Carte> cartesJoueur;
	
	/**
	 * Instancie un nouveau joueur avec le nom et l'adresse pass�s en param�tres, l'attribut encoreEnJeu � true et instancie une nouvelle collections de carte vide.
	 * @param nom Nom du joueur sous la forme d'une chaine de caract�re.
	 * @param adresse Adresse ip du joueur sous la forme d'une chaine de caract�res.
	 */
	public Joueur(String nom)
	{
		this.nom = nom;
		this.encoreEnJeu = true;
		this.cartesJoueur = new ArrayList<Carte>();
	}
	
	/**
	 * Retourne la collection de Carte que poss�de le joueur.
	 * @return Collections des cartes du joueur.
	 */
	public List<Carte> getCartesJoueur()
	{
		return this.cartesJoueur;
	}
	
	/**
	 * M�thode qui ajoute la carte pass�e en param�tre � la collection de carte du joueur.
	 * @param c Carte � ajouter
	 */
	public void ajouterCarte(Carte c)
	{
		this.cartesJoueur.add(c);
	}
	
	/**
	 * M�thode qui retourne si joueur est encore jeu ou non.
	 * @return true si encore en jeu, sinon false.
	 */
	public boolean getEncoreEnJeu()
	{
		return this.encoreEnJeu;
	}
	
	/**
	 * M�thode qui permet de set si joueur est encore en jeu.
	 * @param b true si encore en jeu, sinon false.
	 */
	public void setEncoreEnJeu(boolean b)
	{
		this.encoreEnJeu = b;
	}
	
	/**
	 * M�thode qui retourne le nom du joueur sous forme de chaine de caract�res.
	 * @return Nom du joueur sous forme de chaine de caract�res.
	 */
	public String getNom()
	{
		return this.nom;
	}
	
	/**
	 * M�thode qui permet d'affecter la chaine pass�e en param�tre au nom du Joueur.
	 * @param nom Le nom du joueur sous forme de chaine de caract�res.
	 */
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	/**
	 * M�thode abstraite qui doit faire jouer (suggestion ou accusation) le joueur actuel sur la partie de cluedo.
	 * @return Un tableau de String repr�sentant les commandes taper par le joueur.
	 */
	public abstract String[] jouerCoup();
	
	/**
	 * M�thode qui doit permettre au joueur de refuter ou non, une suggestion.
	 * @param cartes Cartes sous la forme de String que l'autre joueur � sugg�rer.
	 * @param cartesCommun Cartes en commun dans le paquet du joueur avec les cartes suggerer sous la forme de String
	 * @return chaine correspondant � la carte montrer, exit si le joueur veut quitter.
	 */
	public abstract String refuter(String[] cartes, List<String> carteCommun);
	
	
	

}
