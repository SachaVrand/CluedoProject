import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Partie {
	
	private List<Joueur> joueursPartie;
	private Carte[] cartesADecouvrir;
	private Joueur joueurActuel;
	
	public Partie(List<Joueur> joueursPartie)
	{
		this.joueursPartie = joueursPartie;
		this.joueurActuel = joueursPartie.get(0);
		this.cartesADecouvrir = new Carte[3];
		distribuerPaquet(creerPaquetDeCartes());
	}
	
	private List<Carte> creerPaquetDeCartes()
	{
		List<Carte> paquet = new ArrayList<Carte>();
		for(Armes a : Armes.values())
		{
			paquet.add(new Arme(a.getNom(),a.getImage()));
		}
		for(Lieux l : Lieux.values())
		{
			paquet.add(new Lieu(l.getNom(),l.getImage()));
		}
		for(Suspects s : Suspects.values())
		{
			paquet.add(new Suspect(s.getNom(),s.getImage()));
		}
		return paquet;
	}

	private void distribuerPaquet(List<Carte> paquet)
	{
		Collections.shuffle(paquet);
		int x = -1,y = -1,z = -1;
		for(int i = 0; i < paquet.size(); i++)
		{
			if(paquet.get(i) instanceof Arme)
			{
				if(!(cartesADecouvrir[0] instanceof Arme))
				{
					cartesADecouvrir[0] = paquet.get(i);
					x = i;
				}
			}
			else if(paquet.get(i) instanceof Lieu)
			{
				if(!(cartesADecouvrir[1] instanceof Lieu))
				{
					cartesADecouvrir[1] = paquet.get(i);
					y = i;
				}		
			}
			else if(paquet.get(i) instanceof Suspect)
			{
				if(!(cartesADecouvrir[2] instanceof Suspect))
				{
					cartesADecouvrir[2] = paquet.get(i);
					z = i;
				}
			}
			
			if(cartesADecouvrir[0] instanceof Arme && cartesADecouvrir[1] instanceof Lieu && cartesADecouvrir[2] instanceof Suspect)
			{
				break;
			}
		}
		paquet.remove(x);
		paquet.remove(y);
		paquet.remove(z);
		
		for(int i = 0, j = joueursPartie.size() - 1; i < paquet.size(); i++)
		{
			joueursPartie.get(j).ajouterCarte(paquet.get(i));
			j--;
			if(j == -1)
				j = joueursPartie.size() - 1;
		}
		
	}
	
	private void afficherAide()
	{
		System.out.println("Les commandes disponibles durant la partie :\n");
		System.out.println("show");
		System.out.println("\t Voir les informations vous concernant.\n");
		System.out.println("move <type> <card1> <card2> <card3>");
		System.out.println("\t <type> : Sois 'suggest' ou 'accuse'.");
		System.out.println("\t <cardN> : une carte.\n");
		System.out.println("exit");
		System.out.println("\t Quitter la partie.\n");
		System.out.println("help");
		System.out.println("\t Afficher ce message.\n");
	}
	
	private void afficherShow()
	{
		System.out.println("\n\t Indices : ");
		for(Carte c : joueurActuel.getCartesJoueur())
		{
			System.out.println(c.getNom());
		}
		if(joueurActuel.getEncoreEnJeu())
		{
			System.out.println("\n Vous n'avez pas encore fait d'accusation.\n");
		}
		else
		{
			System.out.println("\n Vous avez fait une accusation fausse.\n");
		}
	}
}
