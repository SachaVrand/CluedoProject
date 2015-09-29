import java.util.List;

/**
 * Classe repr�sentant une partie de jeu du cluedo.
 * @author Sacha et Clement
 *
 */
public class PartieSolo extends PartieHote {
	

	public PartieSolo(List<Joueur> joueursPartie)
	{
		super(joueursPartie);
	}
	
	/**
	 * M�thode qui permet de faire tourner une partie de cluedo.
	 */
	public void boucleJeu()
	{
		String[] tmp;
		while(!partieFinie)
		{
			//on v�rifie que le joueur actuel peut jouer sinon on passe au joueur suivant et ainsi de suite.
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
						System.out.println("\n" + joueursPartie.get(joueurActuel).getNom() + " a gagn� la partie\n");
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
							System.out.println("\nPersonnes n'a gagn�\n");
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
