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
			if(message[0].equals("start") && message.length == 2)
			{
				while((!message[0].equals("end")) && (message.length == 1))
				{
					message = client.receive().split(" ");
					if(message[0].equals("error") && message.length == 2)
					{
						
					}
					else if(message[0].equals("move") && message.length == 3)
					{
						
					}
					else if(message[0].equals("play") && message.length == 1)
					{
						
					}
					else if(message[0].equals("ask") && message.length == 2)
					{
						
					}
					else if(message[0].equals("info") && message.length == 2)
					{
						
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
