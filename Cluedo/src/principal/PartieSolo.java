package principal;
import java.util.List;

import gui.GraphicalUserInterface;

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
			
			//set du joueur actuel pour les ordi
			for(int x=0 ; x < joueursPartie.size() ; x++)
			{
				if(joueursPartie.get(x) instanceof Ordi)
				{
					((Ordi)joueursPartie.get(x)).setJoueurActuel(joueurActuel);
				}
			}
			
			if(tmp == null)
			{
				return;
			}
			else
			{
				String cartesSuggerer[] = new String[]{tmp[1],tmp[2],tmp[3]};
				
				//Informer tous les autres joueurs ordi des dernieres cartes suggérer.
				for(int x=0 ; x < joueursPartie.size() ; x++)
				{
					if(joueursPartie.get(x) instanceof Ordi)
					{
						((Ordi)joueursPartie.get(x)).setDernierCoupJouer(cartesSuggerer);
					}
				}
				
				if(tmp[0].equals("suggest"))
				{
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
						
						//si personne n'a pu refuter
						if(i == joueurActuel)	
						{
							//si personne n'a pu refuter les carte suggérées du joueur actuel alors augmenter les prob des dernieres cartes 
							//pour tous les Ordi autre que le joueur actuel
							for(int x=0 ; x < joueursPartie.size() ; x++)
							{
								if(x != joueurActuel && joueursPartie.get(x) instanceof Ordi)
								{
									((Ordi)joueursPartie.get(x)).changerProbDerCartes(Ordi.SUGGEST_NO_REFUTATION);
								}
							}
							//Pour la gui, si personne n'as pu répondre on update la dernière réponse à unknown
							if(joueurActuel == 0)
							{
								GraphicalUserInterface.updatePanelJeu(null,-3);
							}
							break;
						}
						
						Joueur j = joueursPartie.get(i);
						
						//si quelqu'un peut refuter
						if((carteCommun = Carte.cartesContenuDans(j.getCartesJoueur(), cartesSuggerer)).size() != 0)
						{
							//informer tous les joueurs ordi du joueur réfutant
							for(int x=0 ; x < joueursPartie.size() ; x++)
							{
								if(joueursPartie.get(x) instanceof Ordi)
								{
									((Ordi)joueursPartie.get(x)).setJoueurRefutant(i);
								}
							}
							
							//si le joueur qui à suggerer est un ordi
							if(joueursPartie.get(joueurActuel) instanceof Ordi)
							{
								//si quelqu'un répond à sa suggestion
								((Ordi) joueursPartie.get(joueurActuel)).setAucuneRefutationDeMonCoup(false);
								//pour tous les autres Ordi, informer que quelqu'un a pu refuter
								for(int x=0 ; x < joueursPartie.size() ; x++)
								{
									if(x != joueurActuel && x!= i && joueursPartie.get(x) instanceof Ordi)
									{
										((Ordi)joueursPartie.get(x)).changerProbDerCartes(Ordi.SUGGEST_REFUTATION);
									}
								}
							}
							carteMontre = j.refuter(carteCommun);
							if(carteMontre.equals("exit"))
							{
								System.out.println(j.getNom() + " quitte la partie");
								System.out.println("Fin de la partie");
								return;
							}
							System.out.println(j.getNom() + " montre : " + carteMontre);
							
							//Pour la gui, on retient les cartes qu'on nous a montré. Ne fait rien si la liste n'a pas été set;
							if(joueurActuel == 0)
							{
								joueursPartie.get(joueurActuel).updateKnownedCardForGUI(i, carteMontre);
								GraphicalUserInterface.updatePanelJeu(Carte.retrouverCarte(carteMontre),i);
							}	
							
							//ajouter le fait que l'ordi a retenu la carte qu'on lui à montré, et qui lui a montré
							if(joueursPartie.get(joueurActuel) instanceof Ordi)
							{
								Ordi ordi = (Ordi)joueursPartie.get(joueurActuel);
								ordi.ajouterCarteConnue(Carte.retrouverCarte(carteMontre), i);
							}
							
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
						nomGagnant = joueursPartie.get(joueurActuel).getNom(); 
						System.out.println("\n" + nomGagnant + " a gagné la partie\n");
						partieFinie = true;
					}
					else
					{
						//si le jouer actuel a eu faux, alors changer les prob des cartes accusées à tous les autres joueurs ordi.
						for(int x=0 ; x < joueursPartie.size() ; x++)
						{
							if(x != joueurActuel && joueursPartie.get(x) instanceof Ordi)
							{
								((Ordi)joueursPartie.get(x)).changerProbDerCartes(Ordi.LOSING_AFTER_ACCUSE);
							}
						}
						
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
	
	public int boucleJeuTestIA()
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
			
			//set du joueur actuel pour les ordi
			for(int x=0 ; x < joueursPartie.size() ; x++)
			{
				if(joueursPartie.get(x) instanceof Ordi)
				{
					((Ordi)joueursPartie.get(x)).setJoueurActuel(joueurActuel);
				}
			}
			
			if(tmp == null)
			{
				return 0;
			}
			else
			{
				String cartesSuggerer[] = new String[]{tmp[1],tmp[2],tmp[3]};
				
				//Informer tous les autres joueurs ordi des dernieres cartes suggérer.
				for(int x=0 ; x < joueursPartie.size() ; x++)
				{
					if(joueursPartie.get(x) instanceof Ordi)
					{
						((Ordi)joueursPartie.get(x)).setDernierCoupJouer(cartesSuggerer);
					}
				}
				
				if(tmp[0].equals("suggest"))
				{
					List<String> carteCommun;
					String carteMontre;
					
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
						
						//si personne n'a pu refuter
						if(i == joueurActuel)	
						{
							//si personne n'a pu refuter les carte suggérées du joueur actuel alors augmenter les prob des dernieres cartes 
							//pour tous les Ordi autre que le joueur actuel
							for(int x=0 ; x < joueursPartie.size() ; x++)
							{
								if(x != joueurActuel && joueursPartie.get(x) instanceof Ordi)
								{
									((Ordi)joueursPartie.get(x)).changerProbDerCartes(30);
								}
							}
							
							break;
						}
						
						Joueur j = joueursPartie.get(i);
						
						//si quelqu'un peut refuter
						if((carteCommun = Carte.cartesContenuDans(j.getCartesJoueur(), cartesSuggerer)).size() != 0)
						{
							//informer tous les joueurs ordi du joueur réfutant
							for(int x=0 ; x < joueursPartie.size() ; x++)
							{
								if(joueursPartie.get(x) instanceof Ordi)
								{
									((Ordi)joueursPartie.get(x)).setJoueurRefutant(i);
								}
							}
							
							//si le joueur qui à suggerer est un ordi
							if(joueursPartie.get(joueurActuel) instanceof Ordi)
							{
								//si quelqu'un répond à sa suggestion
								((Ordi) joueursPartie.get(joueurActuel)).setAucuneRefutationDeMonCoup(false);
								//pour tous les autres Ordi, informer que quelqu'un a pu refuter
								for(int x=0 ; x < joueursPartie.size() ; x++)
								{
									if(x != joueurActuel && x!= i && joueursPartie.get(x) instanceof Ordi)
									{
										((Ordi)joueursPartie.get(x)).changerProbDerCartes(-20);
									}
								}
							}
							carteMontre = j.refuter(carteCommun);		
							//ajouter le fait que l'ordi a retenu la carte qu'on lui à montré, et qui lui a montré
							if(joueursPartie.get(joueurActuel) instanceof Ordi)
							{
								Ordi ordi = (Ordi)joueursPartie.get(joueurActuel);
								ordi.ajouterCarteConnue(Carte.retrouverCarte(carteMontre), i);
							}
							
							break;
						}
					}
					while(true);
				}
				else
				{
					//verifier accusation
					if(tmp[1].equals(cartesADecouvrir[0].getNom())  && tmp[2].equals(cartesADecouvrir[1].getNom()) && tmp[3].equals(cartesADecouvrir[2].getNom()))
					{
						return joueurActuel;
					}
					else
					{
						//si le jouer actuel a eu faux, alors changer les prob des cartes accusées à tous les autres joueurs ordi.
						for(int x=0 ; x < joueursPartie.size() ; x++)
						{
							if(x != joueurActuel && joueursPartie.get(x) instanceof Ordi)
							{
								((Ordi)joueursPartie.get(x)).changerProbDerCartes(-10);
							}
						}
						
						joueursPartie.get(joueurActuel).setEncoreEnJeu(false);
						
						//s'il n'y a plus de joueurs en jeu
						for(Joueur j : joueursPartie)
						{
							partieFinie = partieFinie || j.getEncoreEnJeu();
						}
						partieFinie = !partieFinie;
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
		return 0;
	}
}
