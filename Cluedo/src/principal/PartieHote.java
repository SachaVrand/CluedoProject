package principal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import networking.RegServer;

public abstract class PartieHote implements IPartie{

	/**
	 * Représente la liste des joueurs dans la partie.
	 */
	protected List<Joueur> joueursPartie;
	
	/**
	 * Représente les carte à découvrir pour résoudre le crime.
	 */
	protected Carte[] cartesADecouvrir;
	
	/**
	 * Représente l'indice, dans la liste des joueurs, du joueur actuel.
	 */
	protected int joueurActuel;
	
	/**
	 * Represente si la partie est finie ou non.
	 */
	protected boolean partieFinie;
	
	protected RegServer server;
	/**
	 * Instancie une nouvelle partie avec la liste des joueurs passée en paramètre, l'indice du joueur actuel à 0, un nouveau tableau de 3 cartes, partieFinie à false 
	 * et distribue les cartes à chaque joueur.
	 * @param joueursPartie Collection de Joueur jouant la partie.
	 */
	public PartieHote(List<Joueur> joueursPartie)
	{
		this.server = null;
		this.joueursPartie = joueursPartie;
		this.joueurActuel = 0;
		this.cartesADecouvrir = new Carte[3];
		this.partieFinie = false;
		distribuerPaquet(creerPaquetDeCartes());
	}

	
	/**
	 * Méthode qui crée le paquet de cartes du jeu Cluedo.
	 * @return Collection des cartes du jeu Cluedo.
	 */
	private List<Carte> creerPaquetDeCartes()
	{
		List<Carte> paquet = new ArrayList<Carte>();
		for(Armes a : Armes.values())
		{
			paquet.add(new Arme(a.toString(),a.getImage()));
		}
		for(Lieux l : Lieux.values())
		{
			paquet.add(new Lieu(l.toString(),l.getImage()));
		}
		for(Suspects s : Suspects.values())
		{
			paquet.add(new Suspect(s.toString(),s.getImage()));
		}
		return paquet;
	}

	/**
	 * Méthode qui permet de distribuer les cartes passées en paramètre à chaque joueur présent dans la partie 
	 * et de mettre les 3 carte du crime dans cartesADecouvrir.
	 * @param paquet Paquet de cartes complet du jeu Cluedo. 
	 */
	private void distribuerPaquet(List<Carte> paquet)
	{
		Collections.shuffle(paquet);
		Iterator<Carte> it = paquet.iterator();
		while(it.hasNext())
		{
			Carte c = it.next();
			if(c instanceof Arme)
			{
				if(!(cartesADecouvrir[0] instanceof Arme))
				{
					cartesADecouvrir[0] = c;
					it.remove();
				}
			}
			else if(c instanceof Lieu)
			{
				if(!(cartesADecouvrir[1] instanceof Lieu))
				{
					cartesADecouvrir[1] = c;
					it.remove();
				}		
			}
			else if(c instanceof Suspect)
			{
				if(!(cartesADecouvrir[2] instanceof Suspect))
				{
					cartesADecouvrir[2] = c;
					it.remove();
				}
			}
			
			if(cartesADecouvrir[0] instanceof Arme && cartesADecouvrir[1] instanceof Lieu && cartesADecouvrir[2] instanceof Suspect)
			{
				break;
			}
		}
		
		for(int i = 0, j = joueursPartie.size() - 1; i < paquet.size(); i++)
		{
			joueursPartie.get(j).ajouterCarte(paquet.get(i));
			j--;
			if(j == -1)
				j = joueursPartie.size() - 1;
		}
		
	}
}
