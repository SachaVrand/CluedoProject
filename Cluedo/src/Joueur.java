import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite répresentant un joueur de la partie de cluedo.
 * @author Sacha
 *
 */
public abstract class Joueur {
	
	/**
	 * Représente le nom du joueur.
	 */
	protected String nom;
	
	/**
	 * Représente l'adresse ip du joueur.
	 */
	protected String adresse;
	
	/**
	 * Booléen représentant si le joueur est encore en jeu ou non. true si oui, sinon false.
	 */
	protected boolean encoreEnJeu;
	
	/**
	 * Collection de carte que le joueur a durant une partie.
	 */
	protected List<Carte> cartesJoueur;
	
	/**
	 * Instancie un nouveau joueur avec le nom et l'adresse passés en paramètres, l'attribut encoreEnJeu à true et instancie une nouvelle collections de carte vide.
	 * @param nom Nom du joueur sous la forme d'une chaine de caractère.
	 * @param adresse Adresse ip du joueur sous la forme d'une chaine de caractères.
	 */
	public Joueur(String nom, String adresse)
	{
		this.nom = nom;
		this.adresse = adresse;
		this.encoreEnJeu = true;
		this.cartesJoueur = new ArrayList<Carte>();
	}
	
	/**
	 * Retourne la collection de Carte que possède le joueur.
	 * @return List<Carte> collections de cartes du joueur.
	 */
	public List<Carte> getCartesJoueur()
	{
		return this.cartesJoueur;
	}
	
	/**
	 * Méthode qui ajoute la carte passée en paramètre à la collection de carte du joueur.
	 * @param c Carte à ajouter
	 */
	public void ajouterCarte(Carte c)
	{
		this.cartesJoueur.add(c);
	}
	
	/**
	 * Méthode qui retourne si joueur est encore jeu ou non.
	 * @return true si encore en jeu, sinon false.
	 */
	public boolean getEncoreEnJeu()
	{
		return this.encoreEnJeu;
	}
	
	/**
	 * Méthode qui permet de set si joueur est encore en jeu.
	 * @param b true si encore en jeu, sinon false.
	 */
	public void setEncoreEnJeu(boolean b)
	{
		this.encoreEnJeu = b;
	}
	
	/**
	 * Méthode qui retourne le nom du joueur sous forme de chaine de caractères.
	 * @return
	 */
	public String getNom()
	{
		return this.nom;
	}
	
	/**
	 * Méthode abstraite qui doit faire jouer (suggestion ou accusation) le joueur actuel sur la partie de cluedo.
	 * @return Un tableau de String représentant les commandes taper par le joueur.
	 */
	public abstract String[] jouerCoup();
	
	/**
	 * Méthode qui doit permettre au joueur de refuter ou non, une suggestion.
	 * @param cartes Cartes sous la forme de String que l'autre joueur à suggérer.
	 * @param nomJoueur Nom du joueur ayant suggérer.
	 * @return true si joueur a pu réfuter.
	 */
	public abstract boolean refuter(String[] cartes, String nomJoueur);
	
	
	

}
