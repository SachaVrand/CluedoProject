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
		
	public PartieServeur(int nbJoueur) throws IOException
	{
		super(nbJoueur);
	}

	@Override
	public void boucleJeu()
	{
		String[] message = null;
		
		try
		{
			while(!partieFinie)
			{
				joueurActuel = joueurActuel++;
				server.send(joueurActuel, "play");
				message = server.receive(joueurActuel).split(" ");
				if(message[0].equals("exit"))
				{
					for(int i = 0; i < server.getNumClients(); i++)
					{
						server.send(i, "Le joueur "+joueursPartie.get(joueurActuel).getNom()+" a quitté la partie");
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
