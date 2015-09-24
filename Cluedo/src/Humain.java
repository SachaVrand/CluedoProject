import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classe représentant un joueur de type Humain.
 * @author Sacha et Clement
 *
 */
public class Humain extends Joueur
{
	/**
	 * Instancie un nouveau Joueur. cf Constructeur Joueur.
	 * @see Joueur#Joueur(String, String)
	 * @param nom Nom du joueur sous la forme de chaine de caractères.
	 * @param adresse Adresse ip du joueur sous la forme de chaine de caractères.
	 */
	public Humain(String nom, String adresse)
	{
		super(nom, adresse);
	}

	/**
	 * Méthode qui permet de faire une suggestion ou une accusation à un joueur sur la console..
	 * @return Tableau de String représentant les commandes taper par l'utilisateur. Null si le joueur a décider de quitter. String[4]. String[0] option. String[1-3] carte. [1]arme [2]lieu [3]suspect
	 */
	@Override
	public String[] jouerCoup()
	{
		String[] cmdComplete = null;
		String cmd = "";
		System.out.println("\n'help' pour plus d'informations sur les commandes disponibles");
		
		do
		{
			System.out.print("[JOUER]" + nom + " > ");
			
			try
			{
				cmd = Cluedo.sc.nextLine();
			}
			catch(NoSuchElementException e)
			{
				e.printStackTrace();
			}
			catch(IllegalStateException e)
			{
				e.printStackTrace();
			}
			
			cmdComplete = cmd.split(" ");
			cmd = cmdComplete[0];
			
			if(cmd.equals("show") && cmdComplete.length == 1)
			{
				afficherIndices();
			}
			else if(cmd.equals("help") && cmdComplete.length == 1)
			{
				afficherAide();
			}
			else if(cmd.equals("exit") && cmdComplete.length == 1)
			{
				System.out.println(nom + "quitte la partie.");
			}
			else if(cmd.equals("move") && cmdComplete.length == 5)
			{
				//Tester si les cartes passées sont correctes
				Carte[] ordreTypeCarte = testerCartes(new String[]{cmdComplete[2],cmdComplete[3],cmdComplete[4]});
				if(ordreTypeCarte == null)
				{
					System.out.println("\nMauvaises cartes. Une carte lieu, une carte arme et une carte suspect sont requises en parametres.\n");
				}
				else
				{
					if(cmdComplete[1].equals("suggest") || cmdComplete[1].equals("accuse"))
					{
						String[] tmp = new String[4];
						// tmp [0]option [1]arme [2]lieu [3]suspect
						tmp[0] = cmdComplete[1];
						if(ordreTypeCarte[0] instanceof Arme)
						{
							tmp[1] = cmdComplete[2];
							if(ordreTypeCarte[1] instanceof Lieu)
							{
								tmp[2] = cmdComplete[3];
								tmp[3] = cmdComplete[4];
							}
							else if(ordreTypeCarte[1] instanceof Suspect)
							{
								tmp[3] = cmdComplete[3];
								tmp[2] = cmdComplete[4];
							}
						}
						else if(ordreTypeCarte[0] instanceof Lieu)
						{
							tmp[2] = cmdComplete[2];
							if(ordreTypeCarte[1] instanceof Suspect)
							{
								tmp[3] = cmdComplete[3];
								tmp[1] = cmdComplete[4];
							}
							else if(ordreTypeCarte[1] instanceof Arme)
							{
								tmp[1] = cmdComplete[3];
								tmp[3] = cmdComplete[4];
							}
							
						}
						else if(ordreTypeCarte[0] instanceof Suspect)
						{
							tmp[3] = cmdComplete[2];
							if(ordreTypeCarte[1] instanceof Lieu)
							{
								tmp[2] = cmdComplete[3];
								tmp[1] = cmdComplete[4];
							}
							else if(ordreTypeCarte[1] instanceof Arme)
							{
								tmp[1] = cmdComplete[3];
								tmp[2] = cmdComplete[4];
							}
						}
						return tmp;
					}
					else
					{
						System.out.println("\nMauvaise option, 'suggest' ou 'accuse'\n");
					}	
				}	
			}
			else
			{
				System.out.println("\nMauvaise commande\n");
			}
		}while(!cmd.equals("exit") || cmdComplete.length != 1);
		return null;
		
	}

	/**
	 * Méthode qui permet à un joueur de réfuter ou non, sur la console.
	 * @param cartes Tableau de String répresentant les 3 cartes suggérer par un joueur.
	 * @param nomJoueur String répresentant le joueur ayant fait la suggestion.
	 * @return true si joueur a pu réfuter sinon false.
	 */
	@Override
	public boolean refuter(String[] cartes, String nomJoueur)
	{

		String cmd = "";
		String[] cmdComplete;
		Boolean carteCorrecte = false;
		List<String> tmp;
		System.out.println("\n"+nomJoueur + " suggère : " + cartes[0] + " " + cartes[1] + " " + cartes[2]+ "\n");
		if((tmp = Carte.cartesContenuDans(cartesJoueur, cartes)).size() != 0)
		{
			System.out.println("'help' pour plus d'informations sur les commandes disponibles\n");
			for(String carte : tmp)
			{
				System.out.println("\t" + carte);
			}
			System.out.println("\nVous pouvez refuter la proposition. Quelle carte choisissez vous de montrer ? (show <card>)");
			do
			{
				System.out.print("[REFUTER] "+nom+" > ");
				cmd = Cluedo.sc.nextLine();
				cmdComplete = cmd.split(" ");
				cmd = cmdComplete[0];
				if(cmd.equals("help") && cmdComplete.length == 1)
				{
					afficherAideRefuter();
				}
				else if(cmdComplete[0].equals("show") && cmdComplete.length > 1)
				{
					carteCorrecte = (cmdComplete[1].equals(cartes[0]) || cmdComplete[1].equals(cartes[1]) || cmdComplete[1].equals(cartes[2]));
					if(!carteCorrecte)
					{
						System.out.println("Mauvaise commande !\n");
					}
				}
				else if(cmd.equals("exit") && cmdComplete.length == 1)
				{
					System.out.println(nom + "quitte la partie.");
				}
				else
				{
					System.out.println("Mauvaise commande !\n");
				}
			}while(!cmd.equals("show") || cmdComplete.length != 2 || !Carte.contientCarte(cartesJoueur, cmdComplete[1]) || !carteCorrecte);
			System.out.println("\n" + nomJoueur + " montre la carte " + cmdComplete[1]);
			return true;
		}
		else
		{
			System.out.println("[REFUTER] " + nom + " n'as aucune cartes pour réfuter.");
			return false;
			
			
			/*System.out.println("Vous n'avez aucune carte pour réfuter la proposition. (skip pour passer)");
			while(!cmd.equals("skip"))
			{
				System.out.print("[REFUTER] "+nom+" > ");
				cmd = Cluedo.sc.nextLine();
				if(cmd.equals("help"))
				{
					afficherAideRefuter();
				}
				else if(!cmd.equals("skip"))
				{
					System.out.println("Mauvaise commande !\n");
				}
			}
			return false;*/
		}
	}
	
	/**
	 * Méthode qui permet d'afficher l'aide sur les commandes quand un joueur suggère ou accuse.
	 */
	private void afficherAide()
	{
		System.out.println("\nLes commandes disponibles durant la partie :\n");
		System.out.println("show");
		System.out.println("\t Voir les informations vous concernant (Indices et accusations faites).\n");
		System.out.println("move <type> <card1> <card2> <card3>");
		System.out.println("\t <type> : Sois 'suggest' ou 'accuse'.");
		System.out.println("\t <cardN> : une carte.\n");
		System.out.println("exit");
		System.out.println("\t Quitter la partie.\n");
		System.out.println("help");
		System.out.println("\t Afficher ce message.\n");
		String c = "Cartes du jeu : Armes ->";
		for(Armes a : Armes.values())
		{
			c += " " + a.toString() + ",";
		}
		c += "\n                Lieux ->";
		for(Lieux l : Lieux.values())
		{
			c += " " + l.toString() +",";
		}
		c += "\n                Suspects ->";
		for(Suspects s : Suspects.values())
		{
			c += " " + s.toString() + ",";
		}
		System.out.println(c +"\n");
	}
	
	/**
	 * Méthode qui permet d'afficher l'aide sur les commandes quand un joueur réfute.
	 */
	private void afficherAideRefuter()
	{
		System.out.println("Les commandes disponibles :\n");
		System.out.println("show <card>");
		System.out.println("\t la carte que vous souhaiter montrer si vous les pouvez \n");
		/*System.out.println("skip");
		System.out.println("\t permet de passer si vous n'avez aucune carte pour réfuter \n");*/
		System.out.println("help");
		System.out.println("\t affiche ce message \n");
	}
	
	/**
	 * Méthode qui permet d'afficher les indices que le joueur possède.
	 */
	private void afficherIndices()
	{
		System.out.println("\n\t Indices : ");
		for(Carte c : cartesJoueur)
		{
			System.out.println(c.getNom());
		}
		System.out.println();
	}
	
	/**
	 * Méthode qui permet de tester les cartes représentées sous forme d'un tableau de string, si elle sont correctes.
	 * Pour qu'elles soient correctes il faut une carte de chaque type(Lieu,Suspect,Arme).
	 * @param tabCarte Tableau de String représentant les 3 cartes suggérer par le joueur.
	 * @return Un tableau de carte avec l'ordre de chaque type de carte. Ex: tab = {Arme,Lieu,Suspect} ou tab = {Suspect,LieuArme}... Null si les cartes ne sont pas correctes.
	 */
	private Carte[] testerCartes(String[] tabCarte)
	{
		//Tester si les cartes passées sont correctes
		//si elles sont correctes, renvoie un tableau de carte
		//sinon renvoie null;
		
		Carte[] cartes = null;
		Carte a = null;
		Carte b = null;
		Carte c = null;
		
		//on test la première carte
		for(Armes arme : Armes.values())
		{
			if(arme.toString().equals(tabCarte[0]))
			{
				a = new Arme(arme.toString(), arme.getImage());
			}
		}
		if(a == null)
		{
			for(Lieux lieu : Lieux.values())
			{
				if(lieu.toString().equals(tabCarte[0]))
				{
					a = new Lieu(lieu.toString(), lieu.getImage());
				}
			}
		}
		if(a == null)
		{
			for(Suspects suspect : Suspects.values())
			{
				if(suspect.toString().equals(tabCarte[0]))
				{
					a = new Suspect(suspect.toString(), suspect.getImage());
				}
			}
		}
		
		//on test la 2ème carte (plus de conditions..)
		if(a != null)
		{
			// si la première carte est une arme
			if(a instanceof Arme)
			{
				for(Lieux lieu : Lieux.values())
				{
					if(lieu.toString().equals(tabCarte[1]))
					{
						b = new Lieu(lieu.toString(), lieu.getImage());
					}
				}
				if(b == null)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.toString().equals(tabCarte[1]))
						{
							b = new Suspect(suspect.toString(), suspect.getImage());
						}
					}
				}
			}
			// si la première carte est un lieu
			if(a instanceof Lieu)
			{
				for(Armes arme : Armes.values())
				{
					if(arme.toString().equals(tabCarte[1]))
					{
						b = new Arme(arme.toString(), arme.getImage());
					}
				}
				if(b == null)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.toString().equals(tabCarte[1]))
						{
							b = new Suspect(suspect.toString(), suspect.getImage());
						}
					}
				}
			}
			// si la première carte est un suspect
			if(a instanceof Suspect)
			{
				for(Armes arme : Armes.values())
				{
					if(arme.toString().equals(tabCarte[1]))
					{
						b = new Arme(arme.toString(), arme.getImage());
					}
				}
				if(b == null)
				{
					for(Lieux lieu : Lieux.values())
					{
						if(lieu.toString().equals(tabCarte[1]))
						{
							b = new Lieu(lieu.toString(), lieu.getImage());
						}
					}
				}
			}
		}
		// on test la 3ème carte (encore plus de test...)
		if(b != null)
		{
			//si la premirèe carte est une arme
			if(a instanceof Arme)
			{
				//si la deuxième carte est un lieu
				if(b instanceof Lieu)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.toString().equals(tabCarte[2]))
						{
							c = new Suspect(suspect.toString(), suspect.getImage());
						}
					}
				}
				//si la deuxième carte est un suspect
				else
				{
					for(Lieux lieu : Lieux.values())
					{
						if(lieu.toString().equals(tabCarte[2]))
						{
							c = new Lieu(lieu.toString(), lieu.getImage());
						}
					}
				}
			}
			//si la première carte est un lieu
			if(a instanceof Lieu)
			{
				//si la deuxième carte est une arme
				if(b instanceof Arme)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.toString().equals(tabCarte[2]))
						{
							c = new Suspect(suspect.toString(), suspect.getImage());
						}
					}
				}
				//si la deuxième carte est un suspect
				else
				{
					for(Armes arme : Armes.values())
					{
						if(arme.toString().equals(tabCarte[2]))
						{
							c = new Arme(arme.toString(), arme.getImage());
						}
					}
				}
			}
			//si la première carte est un suspect
			if(a instanceof Suspect)
			{
				//si la deuxième carte est une arme
				if(b instanceof Arme)
				{
					for(Lieux lieu : Lieux.values())
					{
						if(lieu.toString().equals(tabCarte[2]))
						{
							c = new Lieu(lieu.toString(), lieu.getImage());
						}
					}
				}
				//si la deuxième carte est un lieu
				else
				{
					for(Armes arme : Armes.values())
					{
						if(arme.toString().equals(tabCarte[2]))
						{
							c = new Arme(arme.toString(), arme.getImage());
						}
					}
				}
			}
		}
		if(c != null)
		{
			cartes = new Carte[]{a,b,c};
		}
		return cartes;
	}

}
