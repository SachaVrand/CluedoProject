package principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Classe représentant un joueur de type Ordinateur.
 * @author Sacha et Clement
 *
 */
public class Ordi extends Joueur
{
	/**
	 * Classe interne permettant de représenter la probabilité d'une carte. Cette est uniquement utile pour l'intelligence artificielle.
	 * Elle permet de "determiner" si une carte à plus de chance d'être celle du crime ou non, et si elle n'appartient pas au crime elle permet de savoir qui possède la carte.
	 * @author Sacha
	 *
	 */
	private class ProbabiliteCarte
	{
		/**
		 * Carte représentant une carte du jeu Cluedo.
		 */
		Carte carte;
		
		/**
		 * Nom du joueur possèdant la carte. "inconnu" si on ne sait pas qui la possède.
		 */
		String nomJoueurPossedant;
		/**
		 * Entier représentant la probabilité d'une carte. 
		 */
		int indiceProbabilite;
		
		/**
		 * Instancie un nouvel objet ProbabiliteCarte avec le nom du joueur possèdant à "inconnu"
		 * @param carte Carte du jeu Cluedo.
		 */
		public ProbabiliteCarte(Carte carte)
		{
			this.carte = carte;
			this.nomJoueurPossedant = "inconnu";
			indiceProbabilite = 100;
		}
	}
	
	/**
	 * Constante entière représentant la valeur à ajouter aux probalilités dans le cas d'une suggestion que personne n'as pu réfuter.
	 */
	public final static int SUGGEST_NO_REFUTATION = 30;
	
	/**
	 * Constante entière représentant la valeur à ajouter aux probabilités dans le cas d'une accusation d'un joueur qui à echouer.
	 */
	public final static int LOSING_AFTER_ACCUSE = -10;
	
	/**
	 * Constante entière représentant la valeur à ajouter aux probabilités dans le cas d'une suggestion que quelqu'un à pu réfuter.
	 */
	public final static int SUGGEST_REFUTATION = -20;
	
	/**
	 * Liste des ProbabiliteCarte pour les cartes de type Suspect
	 */
	private List<ProbabiliteCarte> listePSuspects;
	
	/**
	 * Liste des ProbabiliteCarte pour les cartes de type Arme
	 */
	private List<ProbabiliteCarte> listePArmes;
	
	/**
	 * Liste des ProbabiliteCarte pour les cartes de type Lieu
	 */
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
	
	/**
	 * Chaine de caractère représentant le joueur en train de jouer.
	 */
	private String joueurActuel;
	
	/**
	 * Chaine de caractère représentant le joueur en train de réfuter.
	 */
	private String joueurRefutant;
	
	/**
	 * Booléen représentant si ma suggestion a pu être réfuter. Vrai si elle n'as pas pu être réfuter, sinon faux.
	 */
	private boolean auncuneRefutationDeMonCoup;
	
	/**
	 * Booléen représentant si la suggestiondu joueur autre que moi a pu être réfuter. Vrai si elle n'as pas pu être réfuter, sinon faux. et null si c'était le tour de ce joueur avant.
	 */
	private Boolean aucuneRefutationAutre;
	
	/**
	 * Entier permettant de savoir si il reste des cartes inconnues à determiner, si l'entier est égale à 3 alors il ne reste plus que les cartes du crime dans les listes de ProbabiliteCarte.
	 * sinon si il est inférieur à 3, il reste plus d'une inconnu dans une des listes de ProbabiliteCarte
	 */
	private int doitAccuser;
	
	/**
	 * Tableau de String représentant la derniere suggestion que ce joueur a fait.
	 */
	private String[] monDernierCoupJouer;
	
	/**
	 * Tableau de String représentant la dernière suggest faite par un joueur autre que celui ci.
	 */
	private String[] dernierCoupJouer;
	
	/**
	 * Nombre de tour dans la partie. Pour le moment elle n'est pas utilisé.
	 */
	private int nombreTour;
	
	/**
	 * Instancie un nouvelle Ordi avec le nom de joueur et le niveau d'intelligence de l'IA choisi.
	 * Si le niveau d'IA choisi est inférieur à 2 instancie une ArrayList pour carteDejaMontrees, sinon instancie une HashMap vide pour CartesMontreesParJoueur.
	 * Initialise auncuneRefutationDeMonCoup à faux, aucuneRefutationAutre à faux, monDernierCoupJouer à null, dernierCoupJouer à null, doitAccuser à 0, joueurActuel à 0, joueurRefutant à 0.
	 * Instancie les trois listes de ProbabiliteCarte avec chacune des cartes du jeu et le joueur possèdant à "inconnu" et l'indice de probabilité à 0;
	 * @param nom Nom du joueur sous la forme de String
	 * @param IALevel Niveau du joueur sous la forme d'entier, de 0 à 2.
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
		doitAccuser = 0;
		joueurActuel = "";
		joueurRefutant = "";
	}
	
	/**
	 * Méthode qui permet de changer le nom du joueur actuel avec celui passé en paramètre.
	 * @param nom Nom du joueur sous la forme de String
	 */
	public void setJoueurActuel(String nom)
	{
		this.joueurActuel = nom;
		if(niveauIA >= 2 && !cartesMontreesParJoueur.containsKey(nom))
		{
			cartesMontreesParJoueur.put(nom, new ArrayList<String>());
		}
	}
	
	/**
	 * Méthode qui permet de changer le nom du joueur réfutant avec celui passé en paramètre.
	 * @param nom Nom du joueur sous la forme de String
	 */
	public void setJoueurRefutant(String nom)
	{
		this.joueurRefutant = nom;
	}
	
	/**
	 * Méthode qui permet de changer le dernier coup joué par un autre joueur avec le tableau de String passé en paramètre.
	 * @param dernierCoup Tableau de String représentant le dernier coup jouer par un autre joueur. [0]->String de Carte [1]->String de Carte [2]->String de Carte
	 */
	public void setDernierCoupJouer(String[] dernierCoup)
	{
		this.dernierCoupJouer = dernierCoup;
	}
	
	/**
	 * Méthode qui permet de récupérer le dernier coup jouer par un autre joueur.
	 * @return le dernier coup jouer par un autre joueur sous la forme d'un tableau de chaine de caractères.
	 */
	public String[] getDernierCoupJouer()
	{
		return this.dernierCoupJouer;
	}
	
	/**
	 * Méthode qui permet de changer le booléen représentant si mon coup à pu être réfuter ou non
	 * @param b Vrai si il n'as pas pu etre réfuter, faux sinon.
	 */
	public void setAucuneRefutationDeMonCoup(boolean b)
	{
		this.auncuneRefutationDeMonCoup = b;
	}
	
	/**
	 * Méthode qui permet de changer le booléen représentant si le coup dd'un joueur autre que moi à pu être réfuter.
	 * @param b Vrai si n'as pas pu être réfuter, faux sinon. et null si c'etait mon tour juste avant.
	 */
	public void setAucuneRefutationAutre(Boolean b)
	{
		this.aucuneRefutationAutre = b;
	}
	
	/**
	 * Méthode qui permet de récupérer le booléen aucuneRefutationAutre
	 * @return aucuneRefutationAutre
	 */
	public Boolean getAucuneRefutationAutre()
	{
		return this.aucuneRefutationAutre;
	}
	
	/**
	 * Méthode qui permet à l'intelligence artificielle de faire une suggestion ou une accusation en fonction de son niveau d'intelligence. 
	 * @return Un tableau de String représentant le coup joué, [0]->"suggest" ou "accuse", [1-3]->Carte sous la forme de String, 1 de chaque type.
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
	 * Méthode qui permet à l'intelligence artificielle de réfuter en fonction de niveau d'intelligence.
	 * @return Chaine représentant la carte montrer.
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
	
	/**
	 * Méthode qui permet de jouer un coup pour une intelligence de niveau 0.
	 * Suggère des cartes au hasard parmis celle qu'elle ne connais pas et accuse des lors qu'elle connait toutes les cartes des joueurs.
	 * @return Un tableau de String représentant le coup joué, [0]->"suggest" ou "accuse", [1-3]->Carte sous la forme de String, 1 de chaque type.
	 */
	private String[] getCoupRandom()
	{
		String[] res = new String[4];
		doitAccuser = 0;
		String tmpArme = this.getCarteProbable(this.listePArmes);
		String tmpLieu = this.getCarteProbable(this.listePLieux);
		String tmpSuspect = this.getCarteProbable(this.listePSuspects);
		
		if(doitAccuser == 3)
		{
			res[0] = "accuse";
			res[1] = tmpArme;
			res[2] = tmpLieu;
			res[3] = tmpSuspect;
		}
		else
		{
			res[0] = "suggest";
			res[1] = tmpArme;
			res[2] = tmpLieu;
			res[3] = tmpSuspect;
		}
		return res;
	}
	
	/**
	 * Méthode qui permet de jouer un coup pour une intelligence de niveau 1.
	 * Suggère des cartes au hasard parmis celle qu'elle ne connais pas. Et accuse dès lors qu'elle à soit suggérer des cartes qui ne lui appartenais pas et que personne n'as pu répondre. Ou des lors qu'elle connais toutes les cartes des autres joueurs.
	 * @return Un tableau de String représentant le coup joué, [0]->"suggest" ou "accuse", [1-3]->Carte sous la forme de String, 1 de chaque type.
	 */
	private String[] getCoupLevelOne()
	{
		String[] res = new String[4];
		doitAccuser = 0;
		String tmpArme = this.getCarteProbable(this.listePArmes);
		String tmpLieu = this.getCarteProbable(this.listePLieux);
		String tmpSuspect = this.getCarteProbable(this.listePSuspects);
		
		
		if(auncuneRefutationDeMonCoup)
		{
			monDernierCoupJouer[0] = "accuse";
			return monDernierCoupJouer;
		}
		else
		{
			if(doitAccuser == 3)
			{
				res[0] = "accuse";
				res[1] = tmpArme;
				res[2] = tmpLieu;
				res[3] = tmpSuspect;
			}
			else
			{
				res[0] = "suggest";
				res[1] = tmpArme;
				res[2] = tmpLieu;
				res[3] = tmpSuspect;
			}
		}
		return res;
	}
	
	/**
	 * Méthode qui permet de jouer un coup pour une intelligence de niveau 2.
	 * Suggère des cartes au hasard parmis celle qu'elle ne connais pas. Et accuse dès lors qu'elle à soit suggérer des cartes qui ne lui appartenais pas et que personne n'as pu répondre. Ou des lors qu'elle connais toutes les cartes des autres joueurs.
	 * @return Un tableau de String représentant le coup joué, [0]->"suggest" ou "accuse", [1-3]->Carte sous la forme de String, 1 de chaque type.
	 */
	private String[] getCoupLevelTwo()
	{
		String[] res = new String[4];
		doitAccuser = 0;
		String tmpArme = this.getCarteProbable(this.listePArmes);
		String tmpLieu = this.getCarteProbable(this.listePLieux);
		String tmpSuspect = this.getCarteProbable(this.listePSuspects);
		
		/*if(nombreTour == 1)
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
				res[1] = getCarteProbable(listePArmes);
			}
			if(res[2] == null)
			{
				res[2] = getCarteProbable(listePLieux);
			}
			if(res[3] == null)
			{
				res[3] = getCarteProbable(listePSuspects);
			}
		}
		else
		{*/
			if(auncuneRefutationDeMonCoup)
			{
				monDernierCoupJouer[0] = "accuse";
				return monDernierCoupJouer;
			}
			else
			{
				if(doitAccuser == 3)
				{
					res[0] = "accuse";
					res[1] = tmpArme;
					res[2] = tmpLieu;
					res[3] = tmpSuspect;
				}
				else
				{
					res[0] = "suggest";
					res[1] = tmpArme;
					res[2] = tmpLieu;
					res[3] = tmpSuspect;
				}
			}
		//}

		return res;
	}
	
	/**
	 * Méthode qui permet de réfuter pour une intelligence de niveau 0.
	 * Montre une carte au hasard parmis celle qu'elle peut réfuter.
	 * @param cartesCommun Liste des cartes en commun entre la suggestion et les cartes de ce joueur.
	 * @return Une carte sous la forme de String.
	 */
	private String getRefuterRandom(List<String> cartesCommun)
	{
		Collections.shuffle(cartesCommun);
		return cartesCommun.get(0);
	}
	
	/**
	 * Méthode qui permet de réfuter pour une intelligence de niveau 1.
	 * Montre une carte qu'elle à déjà montrer si possible, sinon montre une carte au hasard.
	 * @param cartesCommun Liste des cartes en commun entre la suggestion et les cartes de ce joueur.
	 * @return Une carte sous la forme de String.
	 */
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
	
	/**
	 * Méthode qui permet de réfute pour une intelligence de niveau 2.
	 * Montre une carte que le joueur qui suggère lui à déjà demandé en priorité, sinon une carte qu'elle a déjà montrée à un autre joueur, sinon une carte au hasard.
	 * @param cartesCommun cartesCommun Liste des cartes en commun entre la suggestion et les cartes de ce joueur.
	 * @return Une carte sous la forme de String.
	 */
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
	
	/**
	 * Méthode qui permet d'initialiser les liste de ProbabiliteCarte avec toutes les cartes du jeu.
	 */
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
	
	/**
	 * Méthode qui permet d'ajouter une carte aux carte du joueur.
	 * @param c Carte à ajouter.
	 */
	@Override
	public void ajouterCarte(Carte c)
	{
		super.ajouterCarte(c);
		this.ajouterCarteConnue(c, this.getNom());
	}
	
	/**
	 * Méthode qui permet de changer le nomJoueurPossedant de ProbabiliteCarte après avoir découvert à qui elle appartient.
	 * @param c Carte découverte
	 * @param nomJoueur Nom du joueur la possédant.
	 */
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
					break;
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
					break;
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
					break;
				}
			}
		}
	}
	
	/**
	 * Méthode qui permet d'ajouter, à l'indice de probabilité des dernieres carte jouer par un autre joueur, si elle ne sont pas connu, l'entier passé en paramètre.
	 * N'as pas d'effet si le niveauIA est inférieur à 2.
	 * Dans le cas où la valeur vaut la constante SUGGEST_REFUTATION alors on vérifie si nous connaissons les deux autres carte, et si oui changer le nomJoueurPossedant à celui qui vient de réfuter.
	 * @param valeur Entier à ajouter aux ProbabiliteCarte.
	 */
	public void changerProbDerCartes(int valeur)
	{
		if(niveauIA < 2)
		{
			return;
		}
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
					else if(!pc.nomJoueurPossedant.equals(joueurRefutant))
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
						else if(!pc.nomJoueurPossedant.equals(joueurRefutant))
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
							else if(!pc.nomJoueurPossedant.equals(joueurRefutant))
							{
								nbCarteConnues++;
							}
							break;
						}
					}
				}
			}
		}
		if(valeur == SUGGEST_REFUTATION && nbCarteConnues == 2 && pcInconnu != null)
		{	
			pcInconnu.indiceProbabilite = 0;
			pcInconnu.nomJoueurPossedant = joueurRefutant;
		}
	}
	
	/**
	 * Méthode qui retourne la carte qui à le plus de probabilité d'être la carte du meutre.
	 * @param listeATrier Liste de ProbabiliteCarte dont vous souhaitez savoir la carte la plus probable.
	 * @return La carte avec la plus grande probabilité d'être la carte du meutre.
	 */
	private String getCarteProbable(List<ProbabiliteCarte> listeATrier)
	{
		List<ProbabiliteCarte> res = new ArrayList<>();
		for(ProbabiliteCarte pc : listeATrier)
		{
			if(pc.nomJoueurPossedant.equals("inconnu"))
			{
				res.add(pc);
			}
		}
		Collections.sort(res,new Comparator<ProbabiliteCarte>() {

			@Override
			public int compare(ProbabiliteCarte o1, ProbabiliteCarte o2) {
				if(o1.indiceProbabilite > o2.indiceProbabilite)
				{
					return -1;
				}
				else if(o1.indiceProbabilite < o2.indiceProbabilite)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
			
		});
		if(res.size() == 1)
		{
			doitAccuser += 1;
			return res.get(0).carte.getNom();
		}
		int max = res.get(0).indiceProbabilite;
		Iterator<ProbabiliteCarte> it = res.iterator();
		while(it.hasNext())
		{
			if(it.next().indiceProbabilite != max)
			{
				it.remove();
			}
		}
		Collections.shuffle(res);
		return res.get(0).carte.getNom();
	}
}
