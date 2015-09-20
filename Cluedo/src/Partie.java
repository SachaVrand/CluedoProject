import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Classe représentant une partie de jeu du cluedo.
 * @author Sacha
 *
 */
public class Partie {
	
	/**
	 * Représente la liste des joueurs dans la partie.
	 */
	private List<Joueur> joueursPartie;
	
	/**
	 * Représente les carte à découvrir pour résoudre le crime.
	 */
	private Carte[] cartesADecouvrir;
	
	/**
	 * Représente l'indice, dans la liste des joueurs, du joueur actuel.
	 */
	private int joueurActuel;
	
	/**
	 * Represente si la partie est finie ou non.
	 */
	private boolean partieFinie;
	
	/**
	 * Instancie une nouvelle partie avec la liste des joueurs passée en paramètre, l'indice du joueur actuel à 0, un nouveau tableau de 3 cartes, partieFinie à false 
	 * et distribue les cartes à chaque joueur.
	 * @param joueursPartie
	 */
	public Partie(List<Joueur> joueursPartie)
	{
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
	
	/**
	 * Méthode qui permet de faire tourner une partie de cluedo.
	 */
	public void boucleJeu()
	{
		String[] tmp;
		while(!partieFinie)
		{
			//on vérifie que le joueur actuel peut jouer sinon on passe au joueur suivant et ainsi de suite.
			while(!joueursPartie.get(joueurActuel).getEncoreEnJeu())
			{
				joueurActuel++;
				if(joueurActuel == joueursPartie.size())
				{
					joueurActuel = 0;
				}
			}
			
			tmp = joueursPartie.get(joueurActuel).jouerCoup();
			if(tmp == null)
			{
				return;
			}
			else
			{
				if(tmp[0].equals("suggest"))
				{
					//boucle pour refuter
					int i = joueurActuel;
					
					do
					{
						if(i + 1 >= joueursPartie.size())
						{
							i = 0;
						}
						else
						{
							i++;
						}
						
						if(i == joueurActuel)	
						{
							break;
						}
					}
					while(!(joueursPartie.get(i).refuter(new String[]{tmp[1],tmp[2],tmp[3]}, joueursPartie.get(joueurActuel).getNom())));
				}
				else
				{
					//verifier accusation
					if(tmp[1].equals(cartesADecouvrir[0].getNom())  && tmp[2].equals(cartesADecouvrir[1].getNom()) && tmp[3].equals(cartesADecouvrir[2].getNom()))
					{
						System.out.println("\n" + joueursPartie.get(joueurActuel).getNom() + " a gagné la partie\n");
						partieFinie = true;
					}
					else
					{
						joueursPartie.get(joueurActuel).setEncoreEnJeu(false);
						System.out.println("\n"+ joueursPartie.get(joueurActuel).getNom() + " a fait une accusation fausse");
						
						//s'il n'y a plus de joueurs en jeu
						for(Joueur j : joueursPartie)
						{
							partieFinie = partieFinie || j.getEncoreEnJeu();
						}
						partieFinie = !partieFinie;
						
						if(partieFinie)
						{
							System.out.println("\nPersonnes n'a gagné\n");
						}
					}
				}
			}
			
			//On passe au joueur suivant
			joueurActuel++;
			if(joueurActuel == joueursPartie.size())
			{
				joueurActuel = 0;
			}
		}
	}
}
