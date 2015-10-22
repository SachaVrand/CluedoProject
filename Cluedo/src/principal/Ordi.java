package principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
		
		public ProbabiliteCarte(Carte carte)
		{
			this.carte = carte;
			this.nomJoueurPossedant = "inconnu";
			indiceProbabilite = 100;
		}
	}
	
	private List<ProbabiliteCarte> listePSuspects;
	
	private List<ProbabiliteCarte> listePArmes;
	
	private List<ProbabiliteCarte> listePLieux;
	
	/**
	 * Représente le niveau d'intelligence de l'objet Ordi. 0 = Random
	 */
	private int niveauIA;
	
	/**
	 * Représente la liste des cartes deja montrées par l'ordi à n'importe quel joueur.
	 */
	private List<String> cartesDejaMontrees;
	
	/**
	 * Pour le niveau 2 de refuter : table de hachage ayant pour clé le nom du joueur, et valeur une liste de cartes deja montrées
	 */
	private HashMap<String, List<String>> cartesMontreesParJoueur;
	
	private String joueurActuel;
	
	private String joueurRefutant;
	
	private boolean auncuneRefutationDeMonCoup;
	
	private boolean aucuneRefutationAutre;
	
	private String[] monDernierCoupJouer;
	
	private String[] dernierCoupJouer;
	
	private int nombreTour;
	
	/**
	 * Instancie un nouveau joueur de type ordinateur.
	 * @see Joueur#Joueur(String, String)
	 * @param nom Nom du joueur.
	 * @param adresse Adresse ip du joueur.
	 */
	public Ordi(String nom,int IALevel)
	{
		super(nom);
		this.niveauIA = IALevel;
		this.listePArmes = new ArrayList<ProbabiliteCarte>();
		this.listePLieux = new ArrayList<ProbabiliteCarte>();
		this.listePSuspects = new ArrayList<ProbabiliteCarte>();
		if(niveauIA < 2)
		{
			this.cartesDejaMontrees = new ArrayList<String>();
		}
		else
		{
			this.cartesMontreesParJoueur = new HashMap<>();
		}
		nombreTour = 1;
		auncuneRefutationDeMonCoup = false;
		aucuneRefutationAutre = false;
		monDernierCoupJouer = null;
		dernierCoupJouer = null;
		initialiserProbabiliteCartes();
		joueurActuel = "";
		joueurRefutant = "";
	}
	
	public void setJoueurActuel(String nom)
	{
		this.joueurActuel = nom;
		if(niveauIA > 2 && !cartesMontreesParJoueur.containsKey(nom))
		{
			cartesMontreesParJoueur.put(nom, new ArrayList<String>());
		}
	}
	
	public void setJoueurRefutant(String nom)
	{
		this.joueurRefutant = nom;
	}
	
	public void setDernierCoupJouer(String[] dernierCoup)
	{
		this.dernierCoupJouer = dernierCoup;
	}
	
	public String[] getDernierCoupJouer()
	{
		return this.dernierCoupJouer;
	}
	
	public void setAucuneRefutationDeMonCoup(boolean b)
	{
		this.auncuneRefutationDeMonCoup = b;
	}
	
	public void setAucuneRefutationAutre(boolean b)
	{
		this.aucuneRefutationAutre = b;
	}
	
	public boolean getAucuneRefutationAutre()
	{
		return this.aucuneRefutationAutre;
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
		if(niveauIA == 0)
		{
			res = getCoupRandom();
		}
		//Level 1
		else if(niveauIA == 1)
		{
			res = getCoupLevelOne();
		}
		//Level 2
		else if(niveauIA == 2)
		{
			res = getCoupLevelTwo();
		}

		auncuneRefutationDeMonCoup = true;
		if(nombreTour == 1 && niveauIA > 1)
		{
			auncuneRefutationDeMonCoup = false;
		}
		monDernierCoupJouer = res;
		nombreTour++;
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
		if(niveauIA == 0)
		{
			res = getRefuterRandom(cartesCommun);
		}
		//Level 1
		else if(niveauIA == 1)
		{
			res = getRefuterLevelOne(cartesCommun);
		}
		//Level 2
		else if(niveauIA == 2)
		{
			res = getRefuterLevelTwo(cartesCommun);
		}

		return res;
	}
	
	private String[] getCoupRandom()
	{
		String[] res = new String[4];
		List<ProbabiliteCarte> tmpArme = this.getCartesProbables(this.listePArmes);
		List<ProbabiliteCarte> tmpLieu = this.getCartesProbables(this.listePArmes);
		List<ProbabiliteCarte> tmpSuspect = this.getCartesProbables(this.listePArmes);
		
		if(tmpArme.size() == 1 && tmpLieu.size() == 1 && tmpSuspect.size() == 1)
		{
			res[0] = "accuse";
			res[1] = tmpArme.get(0).carte.getNom();
			res[2] = tmpLieu.get(0).carte.getNom();
			res[3] = tmpSuspect.get(0).carte.getNom();
		}
		else
		{
			res[0] = "suggest";
			res[1] = tmpArme.get(0).carte.getNom();
			res[2] = tmpLieu.get(0).carte.getNom();
			res[3] = tmpSuspect.get(0).carte.getNom();
		}
		return res;
	}
	
	private String[] getCoupLevelOne()
	{
		String[] res = new String[4];
		List<ProbabiliteCarte> tmpArme = this.getCartesProbables(this.listePArmes);
		List<ProbabiliteCarte> tmpLieu = this.getCartesProbables(this.listePArmes);
		List<ProbabiliteCarte> tmpSuspect = this.getCartesProbables(this.listePArmes);
		
		
		if(auncuneRefutationDeMonCoup)
		{
			monDernierCoupJouer[0] = "accuse";
			return monDernierCoupJouer;
		}
		else
		{
			if(tmpArme.size() == 1 && tmpLieu.size() == 1 && tmpSuspect.size() == 1)
			{
				res[0] = "accuse";
				res[1] = tmpArme.get(0).carte.getNom();
				res[2] = tmpLieu.get(0).carte.getNom();
				res[3] = tmpSuspect.get(0).carte.getNom();
			}
			else
			{
				res[0] = "suggest";
				res[1] = tmpArme.get(0).carte.getNom();
				res[2] = tmpLieu.get(0).carte.getNom();
				res[3] = tmpSuspect.get(0).carte.getNom();
			}
		}
		return res;
	}
	
	private String[] getCoupLevelTwo()
	{
		String[] res = new String[4];
		List<ProbabiliteCarte> tmpArme = this.getCartesProbables(this.listePArmes);
		List<ProbabiliteCarte> tmpLieu = this.getCartesProbables(this.listePArmes);
		List<ProbabiliteCarte> tmpSuspect = this.getCartesProbables(this.listePArmes);
		
		if(nombreTour == 1)
		{
			res[0] = "suggest";
			for(Carte c : cartesJoueur)
			{
				if(c instanceof Arme && res[1] == null)
				{
					res[1] = c.getNom();
				}
				else if(c instanceof Lieu && res[2] == null)
				{
					res[2] = c.getNom();
				}
				else if(c instanceof Suspect && res[3] == null)
				{
					res[3] = c.getNom();
				}
				if(res[1] != null && res[2] != null && res[2] != null)
				{
					break;
				}
			}
			if(res[1] == null)
			{
				res[1] = getCartesProbables(listePArmes).get(0).carte.getNom();
			}
			if(res[2] == null)
			{
				res[2] = getCartesProbables(listePLieux).get(0).carte.getNom();
			}
			if(res[3] == null)
			{
				res[3] = getCartesProbables(listePSuspects).get(0).carte.getNom();
			}
		}
		else
		{
			if(auncuneRefutationDeMonCoup)
			{
				monDernierCoupJouer[0] = "accuse";
				return monDernierCoupJouer;
			}
			else
			{
				if(tmpArme.size() == 1 && tmpLieu.size() == 1 && tmpSuspect.size() == 1)
				{
					res[0] = "accuse";
					res[1] = tmpArme.get(0).carte.getNom();
					res[2] = tmpLieu.get(0).carte.getNom();
					res[3] = tmpSuspect.get(0).carte.getNom();
				}
				else
				{
					res[0] = "suggest";
					res[1] = tmpArme.get(0).carte.getNom();
					res[2] = tmpLieu.get(0).carte.getNom();
					res[3] = tmpSuspect.get(0).carte.getNom();
				}
			}
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
	
	private String getRefuterLevelTwo(List<String> cartesCommun)
	{
		//cas où nous avons déjà montrer l'une des cartes demandées au joueur.
		for (String carte : cartesCommun)
		{
			if(cartesMontreesParJoueur.get(joueurActuel).contains(carte))
			{
				return carte;
			}
		}
		//cas où nous avons deja montrée l'une des cartes demandées a un autre joueur.
		for(List<String> listeCartes : cartesMontreesParJoueur.values())
		{
			for(String carte : listeCartes)
			{
				if(cartesCommun.contains(carte))
				{
					cartesMontreesParJoueur.get(joueurActuel).add(carte);
					return carte;
				}
			}
		}
		//cas où aucune des cartes demandées n'a déjà été montrées.
		String res = getRefuterRandom(cartesCommun);
		cartesMontreesParJoueur.get(joueurActuel).add(res);
		return res;
	}
	
	private void initialiserProbabiliteCartes()
	{
		for(Carte c : Carte.creerPaquetDeCartes())
		{
			if(c instanceof Arme)
			{
				listePArmes.add(new ProbabiliteCarte(c));
			}
			else if(c instanceof Lieu)
			{
				listePLieux.add(new ProbabiliteCarte(c));
			}
			else if(c instanceof Suspect)
			{
				listePSuspects.add(new ProbabiliteCarte(c));
			}
		}
	}
	
	@Override
	public void ajouterCarte(Carte c)
	{
		super.ajouterCarte(c);
		this.ajouterCarteConnue(c, this.getNom());
	}
	
	public void ajouterCarteConnue(Carte c, String nomJoueur)
	{
		if(c instanceof Arme)
		{
			for(ProbabiliteCarte pc : listePArmes)
			{
				if(pc.carte.equals(c))
				{
					pc.nomJoueurPossedant = nomJoueur;
					pc.indiceProbabilite = 0;
				}
			}
		}
		else if(c instanceof Lieu)
		{
			for(ProbabiliteCarte pc : listePLieux)
			{
				if(pc.carte.equals(c))
				{
					pc.nomJoueurPossedant = nomJoueur;
					pc.indiceProbabilite = 0;
				}
			}
		}
		else if(c instanceof Suspect)
		{
			for(ProbabiliteCarte pc : listePSuspects)
			{
				if(pc.carte.equals(c))
				{
					pc.nomJoueurPossedant = nomJoueur;
					pc.indiceProbabilite = 0;
				}
			}
		}
	}
	
	public void changerProbDerCartes(int valeur)
	{
		boolean trouve;
		int nbCarteConnues = 0;
		ProbabiliteCarte pcInconnu = null;
		
		for(String carte : dernierCoupJouer)
		{
			trouve = false;
			for(ProbabiliteCarte pc : listePArmes)
			{
				if(pc.carte.getNom().equals(carte))
				{
					if(pc.nomJoueurPossedant.equals("inconnu"))
					{
						pc.indiceProbabilite += valeur;
						pcInconnu = pc;
					}
					else
					{
						nbCarteConnues++;
					}
					trouve = true;
					break;
				}
			}
			if(!trouve)
			{
				for(ProbabiliteCarte pc : listePLieux)
				{
					if(pc.carte.getNom().equals(carte))
					{
						if(pc.nomJoueurPossedant.equals("inconnu"))
						{
							pc.indiceProbabilite += valeur;
							pcInconnu = pc;
						}
						else
						{
							nbCarteConnues++;
						}
						trouve = true;
						break;
					}
				}
				if(!trouve)
				{
					for(ProbabiliteCarte pc : listePSuspects)
					{
						if(pc.carte.getNom().equals(carte))
						{
							if(pc.nomJoueurPossedant.equals("inconnu"))
							{
								pc.indiceProbabilite += valeur;
								pcInconnu = pc;
							}
							else
							{
								nbCarteConnues++;
							}
							break;
						}
					}
				}
			}
		}
		if(valeur == -20 && nbCarteConnues == 2)
		{
			pcInconnu.indiceProbabilite = 0;
			pcInconnu.nomJoueurPossedant = joueurRefutant;
		}
	}
	
	private List<ProbabiliteCarte> getCartesProbables(List<ProbabiliteCarte> listeATrier)
	{
		List<ProbabiliteCarte> res = new ArrayList<>();
		for(ProbabiliteCarte pc : listeATrier)
		{
			if(pc.indiceProbabilite != 0)
			{
				res.add(pc);
			}
		}
		//TODO Verifier ordre de tri et véririfer dans le cas de même probabilité, quelle carte est en premier pour ne peut etre pas toujours avoir la même
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
