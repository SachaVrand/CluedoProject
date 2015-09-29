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
	 * Scanner permettant de r�cup�rer les commandes entr�es par l'utilisateur. Ouvert au d�marrage et ferm�e � la fin du programme.
	 */
	public static Scanner sc = new Scanner(System.in);
	
	/**
	 * Fonction main.
	 * @param args Aucun param�tres attendus.
	 */
	public static void main(String[] args)
	{
		String cmd = "";
		
		System.out.println("Cluedo 0.1");
		System.out.println("Taper 'help' pour plus d'informations");
		
		while(!cmd.equals("exit"))
		{
			System.out.print("Menu principal > ");
			cmd = sc.nextLine();
			switch(cmd)
			{
			case "solo" :
				List<Joueur> listJoueur = menuPartieSolo();
				if(listJoueur.size() != 0)
				{
					PartieSolo p = new PartieSolo(listJoueur);
					p.boucleJeu();
				}
				break;
			case "refere" :
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
			}
		}
		sc.close();
		System.exit(0);
	}
	
	/**
	 * M�thode statique permettant d'afficher l'aide pour les commandes du menu principal.
	 */
	private static void afficherAide()
	{
		System.out.println("\nsolo");
		System.out.println("\t Commencer une partie solo (Humain + Ordinateur)\n");
		System.out.println("refere");
		System.out.println("\t Commencer une partie en tant qu'h�te\n");
		System.out.println("register");
		System.out.println("\t Rechercher une partie en multijoueur");
		System.out.println("\t register <type> [<nom> [, <addr>]]");
		System.out.println("\t <type> : 'ordi' ou 'joueur'.");
		System.out.println("\t <name> : nom du joueur.");
		System.out.println("\t <addr> : adresse IP de l'h�te.\n");
		System.out.println("exit");
		System.out.println("\t Quitter le jeu.\n");
		System.out.println("help");
		System.out.println("\t Afficher ce message.\n");
	}
	
	/**
	 * M�thode statiques permetttant de lancer une partie solo. Demande � l'utilisateur le nombre de joueurs de la partie sur la console.
	 * @return La liste des joueurs participant � une partie solo. Liste vide si le joueur veut revenir au menu principal.
	 */
	private static List<Joueur> menuPartieSolo()
	{
		System.out.println("\n--Mode solo--\n");
		System.out.println("Veuillez choisir le nombre de joueurs pour cette partie (3 � 6 joueurs).");
		System.out.println("'0' pour retourner au menu principal.");
		
		List<Joueur> joueursPartie = new ArrayList<Joueur>();
		String cmd = "";
		
		while(!cmd.equals("0"))
		{
			System.out.print("Solo > ");
			cmd = sc.nextLine();
			if(cmd.equals("3") || cmd.equals("4") || cmd.equals("5") || cmd.equals("6"))
			{
				Joueur joueur = new Humain("Joueur 0", "0");
				joueursPartie.add(joueur);
				
				for(int i = 1; i < Integer.parseInt(cmd); i++)
				{
					joueursPartie.add(new Humain("Joueur"+Integer.toString(i), Integer.toString(i)));
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
	}
}
