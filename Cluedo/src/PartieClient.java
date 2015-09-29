import java.io.IOException;

import networking.Client;

public class PartieClient implements IPartie{

	private Joueur joueur;
	private int numeroPort;
	private String hote;
	private Client client;
	
	public PartieClient(Joueur joueur,String hote, int numeroPort)
	{
		this.joueur = joueur;
		this.hote = hote;
		this.numeroPort = numeroPort;
		client = new Client();
	}
	
	@Override
	public void boucleJeu() {
		String[] message;
		try
		{
			client.open(hote, numeroPort);
			client.send("register " + joueur.getNom());
			message = client.receive().split(" ");
			if((!message[0].equals("ack")) && (message.length != 2))
			{
				System.out.println("Message erreur");
				return;
			}
			message = client.receive().split(" ");
			if(message[0].equals("start") && message.length >= 2)
			{
				while((!message[0].equals("end")) && (message.length == 1))
				{
					message = client.receive().split(" ");
					if(message[0].equals("error"))
					{
						if(message[1].equals("exit"))
						{
							System.out.println(message[2] + " a quitté la partie");
							break;
						}
						else if(message[1].equals("forbidden"))
						{
							
						}
						else if(message[1].equals("invalid"))
						{
							
						}
						else if(message[1].equals("other"))
						{
							for(int i = 2; i < message.length ; i++)
							{
								System.out.print(message[i] + " ");
							}
							System.out.println();
							//peut etre faire quelque chose
						}
					}
					else if(message[0].equals("move") && message.length == 6)
					{
						if(message[2].equals("suggest"))
						{
							System.out.println(message[1] + " suggère " + message[3] + message[4] + message[5]);
						}
						else if(message[2].equals("accuse"))
						{
							System.out.println(message[1] + " accuse " + message[3] + message[4] + message[5]);
						}
					}
					else if(message[0].equals("play") && message.length == 1)
					{
						String[] tmp = joueur.jouerCoup();
						if(tmp == null)
						{
							client.send("exit");
						}
						else
							client.send("move " + tmp[0] + " " + tmp[1] + " " + tmp[2] + " " + tmp[3]);
					}
					else if(message[0].equals("ask") && message.length == 4)
					{
						String carteMontrer = joueur.refuter(new String[]{message[1],message[2],message[3]},Carte.cartesContenuDans(joueur.getCartesJoueur(), new String[]{message[1],message[2],message[3]}));
						if(carteMontrer.equals("exit"))
						{
							client.send("exit");
						}
						else 
							client.send("respond " + carteMontrer);
					}
					else if(message[0].equals("info") && message.length > 1)
					{
						if(message[1].equals("noshow") && message.length == 4)
						{
							System.out.println(message[2] + " ne peut pas aider le " + message[3] + " parce qu’il n’a pas les cartes de la suggestion.");
						}
						else if(message[1].equals("show") && message.length == 4)
						{
							System.out.println(message[2] + " a montré une carte au " + message[3]);
						}
						else if(message.length == 2)
						{
							System.out.println(message[1]);
						}
					}
					else
					{
						
					}
				}
			}
			client.send("exit");
			System.out.println("afficher sortie");
		}
		catch(IOException e)
		{
			System.out.println("Message d'erreur");
		}
		
		
	}

}
