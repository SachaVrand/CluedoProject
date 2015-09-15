
public class Ordi extends Joueur
{
	public Ordi(String nom, String adresse)
	{
		super(nom, adresse);
	}

	@Override
	public String[] jouerCoup() 
	{
		return null;
		
	}

	@Override
	public boolean refuter(String[] cartes, String nomJoueur) 
	{
		return false;
	}
}
