import java.util.ArrayList;
import java.util.List;

public abstract class Joueur {
	
	protected String nom;
	protected String adresse;
	protected Boolean encoreEnJeu;
	protected List<Carte> cartesJoueur;
	
	public Joueur(String nom, String adresse)
	{
		this.nom = nom;
		this.adresse = adresse;
		this.encoreEnJeu = true;
		this.cartesJoueur = new ArrayList<Carte>();
	}
	
	public List<Carte> getCartesJoueur()
	{
		return this.cartesJoueur;
	}
	
	public void ajouterCarte(Carte c)
	{
		this.cartesJoueur.add(c);
	}
	
	public boolean getEncoreEnJeu()
	{
		return this.encoreEnJeu;
	}
	
	public String getNom()
	{
		return this.nom;
	}
	
	public abstract String[] jouerCoup();
	
	public abstract boolean refuter(String[] cartes);
	
	
	

}
