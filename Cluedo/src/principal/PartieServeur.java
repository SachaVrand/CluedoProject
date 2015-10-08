package principal;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import networking.ComServer;
import networking.RegServer;

public class PartieServeur extends PartieHote
{
	private RegServer server;
		
	public PartieServeur(List<Joueur> joueurs) throws IOException
	{
		super(joueurs);
		server = new RegServer(12345,joueurs.size(),180000);
	}

	
	public void boucleJeu()
	{
		String[] message = null;
		int i = 0;
		
		try
		{
			// ouvre le serveur
			server.open();
			// donne le bon nom au joueur (au lieu de Joueur 1, Joueur 2, Joueur 3 ...) en fonction du nom dans les ComServer du server
			while(i < joueursPartie.size())
			{
				for(Joueur j : joueursPartie)
				{
					j.setNom(server.getClients().get(i).getNom());
				}
				i++;
			}
			i = 0;
						
			//envoye le message de début de partie en indiquant les cartes que possèdent chaque joueur
			// ======================================== A REVOIR ============================================== (envoyer aussi le nom de tout les joueurs à chaque joueur)
			while(i < joueursPartie.size())
			{
				String msgStart = "start";
				for(Carte c : joueursPartie.get(i).cartesJoueur)
				{
					msgStart += " "+c.getNom();
				}
				server.send(i, msgStart);
				i++;
			}
			i = 0;
			
			// =====================
			// === boucle de jeu ===
			// =====================
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
				
				do
				{
					server.send(joueurActuel, "play");
					message = server.receive(joueurActuel).split(" ");
					
					if(message[0].equals("exit"))
					{
						for(i = 0; i < server.getNumClients(); i++)
						{
							server.send(i, "error exit "+joueurActuel);
						}
					}
					else if(message[0].equals("move") && message.length == 5)
					{
						String[] cartes = {message[2],message[3],message[4]};
						// Met dans l'ordre les cartes
						Carte[] ordre = Carte.testerCartes(cartes);
						String[] tmp = {ordre[0].getNom(), ordre[1].getNom(), ordre[2].getNom()};
						// Vérifie sur le coup est valide
						if((message[1].equals("suggest") || message[1].equals("accuse")) && (Carte.testerCartes(tmp) != null))
						{
							if(message[1].equals("suggest"))
							{
								// A TRAITER
								// =========
								for(i = 0; i < server.getNumClients(); i++)
								{
									server.send(i, "move "+joueurActuel+" suggest "+message[2]+" "+message[3]+" "+message[4]);
								}
								
								
								
								// =========
								// A TRAITER
							}
							else if(message[1].equals("accuse"))
							{
								for(i = 0; i < server.getNumClients(); i++)
								{
									server.send(i, "move "+joueurActuel+" accuse "+message[2]+" "+message[3]+" "+message[4]);
								}
								// Si l'accusation est bonne
								if(message[2].equals(cartesADecouvrir[0].getNom()) && message[3].equals(cartesADecouvrir[1].getNom()) && message[4].equals(cartesADecouvrir[2].getNom()))
								{
									for(i = 0; i < server.getNumClients(); i++)
									{
										//!\\ "win <num>" N'EXISTE PAS //!\\
										server.send(i, "win "+joueurActuel);
									}
									partieFinie = true;
									break;
								}
								// Si l'accusation est fausse
								else
								{
									joueursPartie.get(joueurActuel).setEncoreEnJeu(false);
									for(i = 0; i < server.getNumClients(); i++)
									{
										//!\\ "end <num>" PAS EXPLIQUEE //!\\
										server.send(i, "end "+joueurActuel);
									}
								
									// s'il n'y a plus de joueurs en jeu
									for(Joueur j : joueursPartie)
									{
										partieFinie = partieFinie || j.getEncoreEnJeu();
									}
									partieFinie = !partieFinie;
								
									if(partieFinie)
									{
										for(i = 0; i < server.getNumClients(); i++)
										{
											//!\\ "end all" N'EXISTE PAS //!\\
											server.send(i, "end all");
										}
									}
								}
							}
						}
						else
						{
							server.send(joueurActuel, "error invalid Mauvaise commande");
						}
					}
					else
					{
						server.send(joueurActuel, "error invalid Mauvaise commande");
					}
				} while(!message[0].equals("exit"));
				if(message[0].equals("exit"))
				{
					break;
				}
			}
			for(i = 0; i < server.getNumClients(); i++)
			{
				server.send(i, "end ");
			}
			//TERMINE COMMUNICATION
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
