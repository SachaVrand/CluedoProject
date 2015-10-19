package principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe représentant un joueur de type Ordinateur.
 * @author Sacha et Clement
 *
 */
public class Ordi extends Joueur
{
	private class ProbabiliteCarte
	{
		Carte carte;
		String nomJoueurPossedant;
		int indiceProbabilite;
		
		public ProbabiliteCarte(Carte carte, String nomJoueurPossedant)
		{
			this.carte = carte;
			this.nomJoueurPossedant = nomJoueurPossedant;
			indiceProbabilite = 0;
		}
		
		public ProbabiliteCarte(Carte carte)
		{
			this.carte = carte;
			this.nomJoueurPossedant = "inconnu";
			indiceProbabilite = 1;
		}
	}
	
	private List<ProbabiliteCarte> listePCartes;
	
	
	/**
	 * Représente le niveau d'intelligence de l'objet Ordi. 0 = Random
	 */
	private int IALevel;
	
	/**
	 * Représente la liste des cartes deja montrées par l'ordi à n'importe quel joueur.
	 */
	private List<String> cartesDejaMontrees;
	
	/**
	 * [i][j] i=0 noms des cartes, i=1 noms des joueurs possedant la carte.
	 */
	//String[][] cartesConnues;
	
	
	/**
	 * Instancie un nouveau joueur de type ordinateur.
	 * @see Joueur#Joueur(String, String)
	 * @param nom Nom du joueur.
	 * @param adresse Adresse ip du joueur.
	 */
	public Ordi(String nom,int IALevel)
	{
		super(nom);
		this.IALevel = IALevel;
		this.listePCartes = new ArrayList<ProbabiliteCarte>();
		this.cartesDejaMontrees = new ArrayList<String>();
		initialiserCarteConnues();
	}

	/**
	 * Méthode non implémentée
	 * @return Null
	 */
	@Override
	public String[] jouerCoup() 
	{
		String[] res = null;
		
		//Random
		if(IALevel == 0)
		{
			res = getCoupRandom();
		}
		
		return res;
	}

	/**
	 * Méthode non implémentée
	 * @return null
	 */
	@Override
	public String refuter(List<String> cartesCommun) 
	{
		String res = "";
		
		//Random
		if(IALevel == 0)
		{
			res = getRefuterRandom(cartesCommun);
		}
		//Level 1
		else if(IALevel == 1)
		{
			res = getRefuterLevelOne(cartesCommun);
		}

		return res;
	}
	
	private String[] getCoupRandom()
	{
		String[] res = new String[4];
		List<ProbabiliteCarte> tmp = this.getCartesProbables();
		if(tmp.size() > 3)
		{
			res[0] = "suggest";
			res[1] = tmp.get(0).carte.getNom();
			res[2] = tmp.get(1).carte.getNom();
			res[3] = tmp.get(2).carte.getNom();
		}
		else if(tmp.size() == 3)
		{
			res[0] = "accuse";
			res[1] = tmp.get(0).carte.getNom();
			res[2] = tmp.get(1).carte.getNom();
			res[3] = tmp.get(2).carte.getNom();
		}
		return res;
	}
	
	private String getRefuterRandom(List<String> cartesCommun)
	{
		Collections.shuffle(cartesCommun);
		return cartesCommun.get(0);
	}
	
	private String getRefuterLevelOne(List<String> cartesCommun)
	{
		String res = "";
		if(cartesDejaMontrees.size() != 0)
		{
			for(String carteDejaMontre : cartesDejaMontrees)
			{
				for(String carte : cartesCommun)
				{
					if(carteDejaMontre.equals(carte))
					{
						return carte;
					}
				}
			}
		}
		res = getRefuterRandom(cartesCommun);
		cartesDejaMontrees.add(res);
		return res;
	}
	
	private void initialiserCarteConnues()
	{
		/*List<Carte> paquet = Carte.creerPaquetDeCartes();
		cartesConnues = new String[2][paquet.size()];
		for(int i = 0; i < paquet.size(); i++)
		{
			cartesConnues[0][i] = paquet.get(i).getNom();
			cartesConnues[1][i] = "inconnu";
		}*/
		
		for(Carte c : Carte.creerPaquetDeCartes())
		{
			listePCartes.add(new ProbabiliteCarte(c));
		}
	}
	
	@Override
	public void ajouterCarte(Carte c)
	{
		super.ajouterCarte(c);
		this.ajouterCarteConnue(c.getNom(), this.getNom());
	}
	
	public void ajouterCarteConnue(String c, String nomJoueur)
	{
		/*for(int i = 0; i < cartesConnues[0].length; i++)
		{
			if(cartesConnues[0][i].equals(c))
			{
				cartesConnues[1][i] = nomJoueur;
			}
		}*/
		for(ProbabiliteCarte pc : listePCartes)
		{
			if(pc.carte.equals(c))
			{
				pc.nomJoueurPossedant = nomJoueur;
				pc.indiceProbabilite = 0;
			}
		}
	}
	private List<ProbabiliteCarte> getCartesProbables()
	{
		/*List<String> res = new ArrayList<String>();
		for(int i = 0; i < cartesConnues[1].length; i++)
		{
			if(cartesConnues[1][i].equals("inconnu"))
			{
				res.add(cartesConnues[0][i]);
			}
		}
		return res;*/
		List<ProbabiliteCarte> res = new ArrayList<>();
		for(ProbabiliteCarte pc : listePCartes)
		{
			if(pc.indiceProbabilite != 0)
			{
				res.add(pc);
			}
		}
		//Verifier ordre de tri
		res.sort(new Comparator<ProbabiliteCarte>() {

			@Override
			public int compare(ProbabiliteCarte o1, ProbabiliteCarte o2) {
				if(o1.indiceProbabilite < o2.indiceProbabilite)
				{
					return -1;
				}
				else if(o1.indiceProbabilite > o2.indiceProbabilite)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
			
		});
		return res;
	}
}
