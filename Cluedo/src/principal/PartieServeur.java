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
			}
			i = 0;
						
			//envoye le message de début de partie en indiquant les cartes que possèdent chaque joueur
			while(i < joueursPartie.size())
			{
				String msgStart = "start";
				for(Carte c : joueursPartie.get(i).cartesJoueur)
				{
					msgStart += " "+c.getNom();
				}
				server.send(i, msgStart);
			}
			
			
			// boucle de jeu
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
				
				server.send(joueurActuel, "play");
				
				message = server.receive(joueurActuel).split(" ");
				
				if(message[0].equals("exit"))
				{
					for(i = 0; i < server.getNumClients(); i++)
					{
						server.send(i, "error exit "+joueursPartie.get(joueurActuel).getNom());
					}
					break;
				}
				//move <accuse/suggest> <card1> <card2> <card3>  -> soit 5 string dans le tableau 'message'
				else if(message[0].equals("move") && message.length == 5)
				{
					//if(coup valide)
						//send all  'move <num_client> <move_str>'
						//answers = les réponses à la sugestion
						//for each  a in  answers
							//if(a == cannot help)
								//send all 'info <info_str>'
							//else
								//send player 'info <resp_str>
								//send all 'info <info_str>
					//else
						//send 'error <err_str>'
				}
				else
				{
					//send 'error <err_str>'
				}
			}
			//send all "end"
			//termine communication
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
