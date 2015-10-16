package principal;
import java.util.List;

/**
 * Classe représentant une partie en solo du cluedo.
 * @author Sacha et Clement
 *
 */
public class PartieSolo extends PartieHote {
	

	/**
	 * Instancie une nouvelle partie avec la liste des joueurs passée en paramètre, l'indice du joueur actuel à 0, un nouveau tableau de 3 cartes, partieFinie à false 
	 * et distribue les cartes à chaque joueur.
	 * @param joueursPartie Collection de Joueur jouant la partie.
	 */
	public PartieSolo(List<Joueur> joueursPartie)
	{
		super(joueursPartie);
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
					String cartesSuggerer[] = new String[]{tmp[1],tmp[2],tmp[3]};
					List<String> carteCommun;
					String carteMontre;
					System.out.println(joueursPartie.get(joueurActuel).getNom() + " suggère " + cartesSuggerer[0] + " " + cartesSuggerer[1] + " " + cartesSuggerer[2]);
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
						
						Joueur j = joueursPartie.get(i);
						
						if((carteCommun = Carte.cartesContenuDans(j.getCartesJoueur(), cartesSuggerer)).size() != 0)
						{
							carteMontre = j.refuter(carteCommun);
							if(carteMontre.equals("exit"))
							{
								System.out.println(j.getNom() + " quitte la partie");
								System.out.println("Fin de la partie");
								return;
							}
							System.out.println(j.getNom() + " montre : " + carteMontre);
							break;
						}
						else
						{
							System.out.println(j.getNom() + " ne peut pas réfuter");
						}
					}
					while(true);
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
						System.out.println("\n"+ joueursPartie.get(joueurActuel).getNom() + " a fait une accusation fausse, il a perdu.");
						
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
