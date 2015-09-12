import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cluedo
{
	public static void main(String[] args)
	{
		String cmd = "";
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Cluedo 0.1");
		System.out.println("Taper 'aide' pour plus d'informations");
		
		while(!cmd.equalsIgnoreCase("quitter"))
		{
			cmd = sc.next();
			switch(cmd)
			{
			case "solo" :
				List<Joueur> listJoueur = menuPartieSolo();
				if(listJoueur.size() != 0)
				{
					lancerPartieSolo(listJoueur);
				}
				break;
			case "hote" :
				break;
			case "inscrire" :
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
	
	private static List<Joueur> menuPartieSolo()
	{
		System.out.println("Mode solo\n");
		System.out.println("Veuillez choisir le nombre de joueurs pour cette partie (3 à 6 joueurs).");
		System.out.println("0 pour retourner au menu principal.");
		
		List<Joueur> joueursPartie = new ArrayList<Joueur>();
		String cmd = "";
		Scanner sc = new Scanner(System.in);
		
		while(cmd != "0")
		{
			cmd = sc.next();
			if(cmd == "3" || cmd == "4" || cmd == "5" || cmd == "6")
			{
				Joueur joueur = new Humain("Joueur 0", "0");
				joueursPartie.add(joueur);
				
				for(int i = 1; i < Integer.parseInt(cmd); i++)
				{
					joueursPartie.add(new Humain("Joueur"+Integer.toString(i), Integer.toString(i)));
				}
				break;
			}
			else
			{
				System.out.println("Mauvaise commande.");
			}
		}
		sc.close();
		return joueursPartie;
	}

	private static void lancerPartieSolo(List<Joueur> listJoueur)
	{
		
	}
}
