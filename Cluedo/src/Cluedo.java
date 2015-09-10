import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cluedo
{
	public static void main(String[] args) throws UnknownHostException
	{
		String cmd = "";
		Scanner sc = new Scanner(System.in);
		
		
		
		System.out.println("Cluedo 0.1");
		System.out.println("Taper 'aide' pour plus d'informations");
		
		while(cmd.equalsIgnoreCase("quitter"))
		{
			cmd = sc.next();
			switch(cmd)
			{
			case "solo" :
				lancerPartieSolo();
				break;
			case "hote" :
				break;
			case "inscrire" :
				break;
			case "aide" :
				afficherAide();
				break;
			case "quitter" :
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
	
	private static void lancerPartieSolo() throws UnknownHostException
	{
		List<Joueur> joueursPartie = new ArrayList<Joueur>();
		
		try
		{
			InetAddress ip = InetAddress.getLocalHost();
			Joueur hote = new Humain("Joueur", ip.getHostAddress()); // récupérer l'adresse IP de la machine
			joueursPartie.add(hote);
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

}
