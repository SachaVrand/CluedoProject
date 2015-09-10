import java.util.ArrayList;
import java.util.List;

public abstract class Joueur {
	
	private String nom;
	private String adresse;
	private Boolean encoreEnJeu;
	private List<Carte> cartesJoueur;
	private List<String> suggestionsFaites;
	
	public Joueur(String nom, String adresse, List<Carte> cartesJoueur)
	{
		this.nom = nom;
		this.adresse = adresse;
		this.encoreEnJeu = true;
		this.cartesJoueur = cartesJoueur;
		this.suggestionsFaites = new ArrayList<String>();
	}
	
	public List<Carte> getCartesJoueur()
	{
		return this.cartesJoueur;
	}
	
	public List<String> getSuggestionsFaites()
	{
		return this.suggestionsFaites;
	}
	
	public void ajouterSuggestion(String uneSuggestionFaite)
	{
		this.suggestionsFaites.add(uneSuggestionFaite);
	}
	
	public boolean getEncoreEnJeu()
	{
		return this.encoreEnJeu;
	}

}
