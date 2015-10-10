package principal;
import java.io.IOException;

import networking.Client;

public class PartieClient implements IPartie{

	private Joueur joueur;
	private final int numeroPort = 12345;
	private String hote;
	private Client client;
	private String[] listeJoueurs;
	
	public PartieClient(Joueur joueur,String hote)
	{
		this.joueur = joueur;
		this.hote = hote;
		client = new Client();
		listeJoueurs = null;
	}
	
	@Override
	public void boucleJeu() {
		String[] message;
		try
		{
			client.open(hote, numeroPort);
		}
		catch(IOException e)
		{
			System.out.println("Mauvaise adresse ip, ou autre erreur");
		}
		try
		{
			client.send("register " + joueur.getNom());
			message = client.receive().split(" ");
			if((!message[0].equalsIgnoreCase("ack")) && (message.length != 2))
			{
				System.out.println("Le serveur choisi n'est pas correcte.");
				client.close();
				return;
			}
			message = client.receive().split(" ");
			
			if(message[0].equalsIgnoreCase("start") && message.length == 3)
			{
				listeJoueurs = message[1].split(",");
				for(String carte : message[2].split(","))
				{
					Carte c = null;
					for(Armes arme : Armes.values())
					{
						if(arme.toString().equalsIgnoreCase(carte))
						{
							c = new Arme(arme.toString(), arme.getImage());
						}
					}
					if(c == null)
					{
						for(Lieux lieu : Lieux.values())
						{
							if(lieu.toString().equalsIgnoreCase(carte))
							{
								c = new Lieu(lieu.toString(), lieu.getImage());
							}
						}
					}
					if(c == null)
					{
						for(Suspects suspect : Suspects.values())
						{
							if(suspect.toString().equalsIgnoreCase(carte))
							{
								c = new Suspect(suspect.toString(), suspect.getImage());
							}
						}
					}
					if(c == null)
					{
						System.out.println("Le message du serveur est incorrecte. Fin de la partie.");
						client.send("exit");
						return;
					}
					joueur.ajouterCarte(c);
					
				}
				for(int i = 0; i < listeJoueurs.length; i++)
				{
					System.out.println(i + ") " + listeJoueurs[i]);
				}
				while((!message[0].equalsIgnoreCase("end")) || (message.length != 1))
				{
					message = client.receive().split(" ");
					if(message[0].equalsIgnoreCase("error") && message.length > 1)
					{
						if(message[1].equalsIgnoreCase("exit") && message.length == 3)
						{
							System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " a quitté la partie");
							break;
						}
						else if(message[1].equalsIgnoreCase("invalid"))
						{
							for(int i = 2; i < message.length ; i++)
							{
								System.out.print(message[i] + " ");
							}
							System.out.println();
						}
						else if(message[1].equalsIgnoreCase("other"))
						{
							for(int i = 2; i < message.length ; i++)
							{
								System.out.print(message[i] + " ");
							}
							System.out.println();
						}
						else
						{
							System.out.println("Le message du serveur est incorrecte. Fin de la partie.");
							break;
						}
					}
					else if(message[0].equalsIgnoreCase("move") && message.length == 6)
					{
						if(message[2].equalsIgnoreCase("suggest"))
						{
							System.out.println(listeJoueurs[Integer.parseInt(message[1])] + " suggère " + message[3] + message[4] + message[5]);
						}
						else if(message[2].equalsIgnoreCase("accuse"))
						{
							System.out.println(listeJoueurs[Integer.parseInt(message[1])] + " accuse " + message[3] + message[4] + message[5]);
						}
					}
					else if(message[0].equalsIgnoreCase("play") && message.length == 1)
					{
						String[] tmp = joueur.jouerCoup();
						if(tmp == null)
						{
							client.send("exit");					
							break;
						}
						else
							client.send("move " + tmp[0] + " " + tmp[1] + " " + tmp[2] + " " + tmp[3]);
					}
					else if(message[0].equalsIgnoreCase("ask") && message.length == 4)
					{
						String carteMontrer = joueur.refuter(new String[]{message[1],message[2],message[3]},Carte.cartesContenuDans(joueur.getCartesJoueur(), new String[]{message[1],message[2],message[3]}));
						if(carteMontrer.equalsIgnoreCase("exit"))
						{
							client.close();
							return;
						}
						else 
							client.send("respond " + carteMontrer);
					}
					else if(message[0].equalsIgnoreCase("info") && message.length > 1)
					{
						if(message[1].equalsIgnoreCase("noshow") && message.length == 4)
						{
							System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " ne peut pas aider " + listeJoueurs[Integer.parseInt(message[3])] + " parce qu’il n’a pas les cartes de la suggestion.");
						}
						else if(message[1].equalsIgnoreCase("show") && message.length == 4)
						{
							System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " a montré une carte à " + listeJoueurs[Integer.parseInt(message[3])]);
						}
						else if(message.length == 2)
						{
							System.out.println(message[1]);
						}
					}
					else if(!message[0].equalsIgnoreCase("end"))
					{
						System.out.println("Le message du serveur est incorrecte.");
						break;
					}
					else if(message[0].equalsIgnoreCase("end") && message.length == 2)
					{
						int ind = Integer.parseInt(message[1]);
						System.out.println(listeJoueurs[ind] + " à gagné la partie.");
						break;
					}
				}
			}
			client.send("exit");
			System.out.println("Fin de la partie.");
			client.close();
		}
		catch(IOException e)
		{
			System.out.println("Message d'erreur");
		}
		
		
	}

}
