package principal;
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
				List<Joueur> listJoueur = new ArrayList<Joueur>();
				for(int i = 0; i < Integer.parseInt(cmdComplete[1]); i++)
				{
					listJoueur.add(new Humain("Joueur "+Integer.toString(i)));
				}
				try 
				{
					p = new PartieServeur(listJoueur);
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
					nomJoueur = cmdComplete[3];
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
		System.out.println("\t Commencer une partie solo (Humain + Ordinateur)");
		System.out.println("\t <NombreJoueurs> : de 3 à 6\n");
		System.out.println("referee <NombreJoueurs>");
		System.out.println("\t Commencer une partie en tant qu'hôte");
		System.out.println("\t <NombreJoueurs> : de 3 à 6 \n");
		System.out.println("register");
		System.out.println("\t Rechercher une partie en multijoueur");
		System.out.println("\t register <type> <addr> [<nom>]");
		System.out.println("\t <type> : 'ordi' ou 'joueur'.");
		System.out.println("\t <addr> : adresse IP de l'hôte.");
		System.out.println("\t <name> : nom du joueur.\n");
		System.out.println("exit");
		System.out.println("\t Quitter le jeu.\n");
		System.out.println("help");
		System.out.println("\t Afficher ce message.\n");
	}
}
