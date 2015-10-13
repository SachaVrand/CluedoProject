package principal;
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
	public Humain(String nom)
	{
		super(nom);
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
				System.out.println(nom + " quitte la partie.");
			}
			else if(cmd.equals("move") && cmdComplete.length == 5)
			{
				
				if(cmdComplete[1].equals("suggest") || cmdComplete[1].equals("accuse"))
				{
					//Tester si les cartes passées sont correctes
					Carte[] cartes = Carte.testerCartes(new String[]{cmdComplete[2],cmdComplete[3],cmdComplete[4]});
					if(cartes == null)
					{
						System.out.println("Mauvaises cartes. Une carte lieu, une carte arme et une carte suspect sont requises en parametres.");
					}
					else
					{
						String[] res = new String[]{cmdComplete[1],cartes[0].getNom(),cartes[1].getNom(),cartes[2].getNom()};
						// return [0]option [1]arme [2]lieu [3]suspect
						return res;
					}
				}
				else
				{
					System.out.println("Mauvaise option, 'suggest' ou 'accuse'");
				}	
			}
			else
			{
				System.out.println("Mauvaise commande");
			}
		}while(!cmd.equals("exit") || cmdComplete.length != 1);
		return null;
		
	}

	/**
	 * Méthode qui permet à un joueur de réfuter.
	 * @param cartes Tableau de String répresentant les 3 cartes suggérer par un joueur.
	 * @param cartesCommun Cartes en commun dans le paquet du joueur avec les cartes suggerer sous la forme de String
	 * @return chaine correspondant à la carte montrée ou exit si le joueur souhaite quitter.
	 */
	@Override
	public String refuter(List<String> carteCommun)
	{
		String cmd = "";
		String[] cmdComplete;
		
		System.out.println();
		for(String c : carteCommun)
		{
			System.out.println("\t" + c);
		}
		System.out.println();
		
		System.out.println("'help' pour plus d'informations sur les commandes disponibles");
		System.out.println("Vous pouvez refuter la proposition. Quelle carte choisissez vous de montrer ? (show <card>)");
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
			else if(cmd.equals("exit") && cmdComplete.length == 1)
			{
				return "exit";
			}
			else if(cmd.equals("show") && cmdComplete.length == 2)
			{
				if((Carte.contientCarte(cartesJoueur, cmdComplete[1])) && (carteCommun.contains(cmdComplete[1])))
				{
					break;
				}
				else
				{
					System.out.println("Coup invalide");
				}
			}
			else
			{
				System.out.println("Mauvaise commande !");
			}
		}while(true);
		return cmdComplete[1];
	}
	
	/**
	 * Méthode qui permet d'afficher l'aide sur les commandes quand un joueur suggère ou accuse.
	 */
	private void afficherAide()
	{
		System.out.println("\nLes commandes disponibles durant la partie :\n");
		System.out.println("show");
		System.out.println("\t Voir les vos indices.\n");
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
	


}
