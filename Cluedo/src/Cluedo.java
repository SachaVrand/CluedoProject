import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Cluedo
{
	public static void main(String[] args)
	{
		String cmd = "";
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Cluedo 0.1");
		System.out.println("Taper 'aide' pour plus d'informations");
		
		while(!cmd.equals("quitter"))
		{
			cmd = sc.next();
			switch(cmd)
			{
			case "solo" :
				List<Joueur> listJoueur = menuPartieSolo(sc);
				if(listJoueur.size() != 0)
				{
					sc.close();
					Partie p = new Partie(listJoueur);
					p.boucleJeu();
					sc = new Scanner(System.in);
				}
				break;
			case "hote" :
				break;
			case "inscrire" :
				break;
			case "quitter" :
				System.out.println("Au revoir.");
				break;
			case "aide" :
				afficherAide();
				break;
			default :
				System.out.println("Mauvaise commande");
			}
		}
		sc.close();
		System.exit(0);
	}
	
	private static void afficherAide()
	{
		System.out.println("solo");
		System.out.println("\t Commencer une partie solo (Humain + Ordinateur)\n");
		System.out.println("hote");
		System.out.println("\t Commencer une partie en tant qu'hôte\n");
		System.out.println("inscrire");
		System.out.println("\t Rechercher une partie en multijoueur");
		System.out.println("\t register <type> [<nom> [, <addr>]]");
		System.out.println("\t <type> : 'ordi' ou 'joueur'.");
		System.out.println("\t <name> : nom du joueur.");
		System.out.println("\t <addr> : adresse IP de l'hôte.\n");
		System.out.println("quitter");
		System.out.println("\t Quitter le jeu.\n");
		System.out.println("aide");
		System.out.println("\t Afficher ce message.\n");
	}
	
	private static List<Joueur> menuPartieSolo(Scanner sc)
	{
		System.out.println("Mode solo\n");
		System.out.println("Veuillez choisir le nombre de joueurs pour cette partie (3 à 6 joueurs).");
		System.out.println("0 pour retourner au menu principal.");
		
		List<Joueur> joueursPartie = new ArrayList<Joueur>();
		String cmd = "";
		//Scanner sc = new Scanner(System.in);
		
		while(!cmd.equals("0"))
		{
			cmd = sc.next();
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
				System.out.println("Retour au menu principal");
			}
			else
			{
				System.out.println("Mauvaise commande.");
			}
		}
		//sc.close();
		return joueursPartie;
	}
}
