import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal du programme.
 * 
 * Permet de lancer le jeu cluedo. Regroupe quelques fonctions statiques permettant entre autres d'afficher l'aide du menu principal et lancer une partie solo.
 * @author Sacha et Clement
 *
 */
public class Cluedo
{
	
	/**
	 * Scanner permettant de récupérer les commandes entrées par l'utilisateur. Ouvert au démarrage et fermée à la fin du programme.
	 */
	public static Scanner sc = new Scanner(System.in);
	
	/**
	 * Fonction main.
	 * @param args Aucun paramètres attendus.
	 */
	public static void main(String[] args)
	{
		String cmd = "";
		String cmdComplete[];
		
		System.out.println("Cluedo 0.1");
		System.out.println("Taper 'help' pour plus d'informations");
		
		while(!cmd.equals("exit"))
		{
			System.out.print("Menu principal > ");
			cmd = sc.nextLine();
			cmdComplete = cmd.split(" ");
			if(cmdComplete.length == 2 && cmdComplete[0].equals("solo") && (cmdComplete[1].equals("3") || cmdComplete[1].equals("4") || cmdComplete[1].equals("5") || cmdComplete[1].equals("6")))
			{
				List<Joueur> listJoueur = new ArrayList<Joueur>();	
				for(int i = 0; i < Integer.parseInt(cmdComplete[1]); i++)
				{
					listJoueur.add(new Humain("Joueur "+Integer.toString(i)));
				}
				PartieSolo p = new PartieSolo(listJoueur);
				p.boucleJeu();
			}
			else if(cmdComplete.length == 2 && cmdComplete[0].equals("referee") && (cmdComplete[1].equals("3") || cmdComplete[1].equals("4") || cmdComplete[1].equals("5") || cmdComplete[1].equals("6")))
			{
				PartieServeur p;
				try 
				{
					p = new PartieServeur(Integer.parseInt(cmdComplete[1]));
					p.boucleJeu();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}	
			}
			else if((cmdComplete.length == 3 || cmdComplete.length == 4) && cmdComplete[0].equals("register") && (cmdComplete[1].equals("ordi") || cmdComplete[1].equals("joueur")))
			{
				String nomJoueur = "Joueur";
				Joueur j = null;
				if(cmdComplete.length == 4)
				{
					nomJoueur = cmdComplete[2];
				}
				if(cmdComplete[1].equals("ordi"))
				{
					j = new Ordi(nomJoueur);
				}
				else if(cmdComplete[1].equals("joueur"))
				{
					j = new Humain(nomJoueur);
				}
				PartieClient pc = new PartieClient(j, cmdComplete[2]);
				pc.boucleJeu();
			}
			else if(cmdComplete.length == 1 && cmdComplete[0].equals("exit"))
			{
				System.out.println("\nAu revoir.");
			}
			else if(cmdComplete.length == 1 && cmdComplete[0].equals("help"))
			{
				afficherAide();
			}
			else
			{
				System.out.println("Mauvaise commande");
			}
			/*switch(cmd)
			{
			case "solo" :
				List<Joueur> listJoueur = menuPartieSolo();
				if(listJoueur.size() != 0)
				{
					PartieSolo p = new PartieSolo(listJoueur);
					p.boucleJeu();
				}
				break;
			case "referee" :
				int nbJoueur = menuReferee();
				if(nbJoueur != 0)
				{
					PartieServeur p = new PartieServeur(nbJoueur);
					p.boucleJeu();
				}
				break;
			case "register" :
				break;
			case "exit" :
				System.out.println("\nAu revoir.");
				break;
			case "help" :
				afficherAide();
				break;
			default :
				System.out.println("Mauvaise commande");
			}*/
		}
		sc.close();
		System.exit(0);
	}
	
	/**
	 * Méthode statique permettant d'afficher l'aide pour les commandes du menu principal.
	 */
	private static void afficherAide()
	{
		System.out.println("\nsolo <NombreJoueurs>");
		System.out.println("\t Commencer une partie solo (Humain + Ordinateur)\n");
		System.out.println("\n<NombreJoueurs> : de 3 à 6");
		System.out.println("referee <NombreJoueurs>");
		System.out.println("\t Commencer une partie en tant qu'hôte\n");
		System.out.println("\n<NombreJoueurs> : de 3 à 6");
		System.out.println("register");
		System.out.println("\t Rechercher une partie en multijoueur");
		System.out.println("\t register <type> <addr> [<nom>]");
		System.out.println("\t <type> : 'ordi' ou 'joueur'.");
		System.out.println("\t <addr> : adresse IP de l'hôte.\n");
		System.out.println("\t <name> : nom du joueur.");
		System.out.println("exit");
		System.out.println("\t Quitter le jeu.\n");
		System.out.println("help");
		System.out.println("\t Afficher ce message.\n");
	}
	
	/**
	 * Méthode statiques permetttant de lancer une partie solo. Demande à l'utilisateur le nombre de joueurs de la partie sur la console.
	 * @return La liste des joueurs participant à une partie solo. Liste vide si le joueur veut revenir au menu principal.
	 */
	/*private static List<Joueur> menuPartieSolo()
	{
		System.out.println("\n--Mode solo--\n");
		System.out.println("Veuillez choisir le nombre de joueurs pour cette partie (3 à 6 joueurs).");
		System.out.println("'0' pour retourner au menu principal.");
		
		List<Joueur> joueursPartie = new ArrayList<Joueur>();
		String cmd = "";
		
		while(!cmd.equals("0"))
		{
			System.out.print("Solo > ");
			cmd = sc.nextLine();
			if(cmd.equals("3") || cmd.equals("4") || cmd.equals("5") || cmd.equals("6"))
			{
				Joueur joueur = new Humain("Joueur 0");
				joueursPartie.add(joueur);
				
				for(int i = 1; i < Integer.parseInt(cmd); i++)
				{
					joueursPartie.add(new Humain("Joueur "+Integer.toString(i)));
				}
				break;
			}
			else if(cmd.equals("0"))
			{
				break;
			}
			else
			{
				System.out.println("\nMauvaise commande.\n");
			}
		}
		System.out.println();
		return joueursPartie;
	}*/
	
	/*private static int menuReferee()
	{
		System.out.println("\n--Serveur--\n");
		System.out.println("Veuillez choisir le nombre de joueurs pour cette partie (3 à 6 joueurs).");
		System.out.println("'0' pour retourner au menu principal.");
		
		String cmd = "";
		
		while(!cmd.equals("0"))
		{
			System.out.print("Referee > ");
			cmd = sc.nextLine();
			if(cmd.equals("3") || cmd.equals("4") || cmd.equals("5") || cmd.equals("6"))
			{
				return Integer.parseInt(cmd);
			}
			else if(cmd.equals("0"))
			{
				break;
			}
			else
			{
				System.out.println("\nMauvaise commande.\n");
			}
		}
		System.out.println();
		return Integer.parseInt(cmd);
	}*/
}
