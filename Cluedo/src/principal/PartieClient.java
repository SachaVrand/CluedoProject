package principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Timer;

import networking.Client;

/**
 * Classe repr�sentant une partie en ligne de Cluedo en tant que 'Client'.
 * @author Sacha
 *
 */
public class PartieClient implements IPartie
{
	/**
	 * Joueur repr�sentant le client qui va jouer.
	 */
	private Joueur joueur;
	
	/**
	 * Constante repr�sentant le port utilis� pour communiquer avec le serveur.
	 */
	private final int numeroPort = 12345;
	
	/**
	 * L'adresse du serveur distant qui h�berge la partie sous forme de chaine de caract�res.
	 */
	private String hote;
	
	/**
	 * Instance de Client qui permet de faire la communication avec le serveur.
	 */
	private Client client;
	
	/**
	 * liste des noms des joueurs, permettant de retranscrire les num�ros envoy� par le serveur sous la forme de chaine repr�sentant le joueur.
	 */
	private String[] listeJoueurs;
	
	/**
	 * Instancie un partie en tant que client avec le Joueur et l'hote sous forme de String pass�s en param�tres, un nouveau Client et la listeJoueurs � null.
	 * @param joueur Joueur repr�sentant le client qui va jouer.
	 * @param hote L'adresse du serveur distant qui h�berge la partie sous forme de chaine de caract�res.
	 */
	public PartieClient(Joueur joueur,String hote)
	{
		this.joueur = joueur;
		this.hote = hote;
		client = new Client();
		listeJoueurs = null;
	}
	
	/**
	 * M�thode qui permet de faire jouer une partie de Cluedo en ligne, en tant que 'client'.
	 */
	@Override
	public void boucleJeu() {
		String[] message;
		Timer t = new Timer(3000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("En attente");
				Timer t = (Timer)e.getSource();
				t.stop();
			}
		});
		try
		{
			client.open(hote, numeroPort);
		}
		catch(IOException e)
		{
			System.out.println("Mauvaise adresse ip, ou autre erreur");
			return;
		}
		try
		{
			client.send("register " + joueur.getNom());
			
			t.start();
			message = client.receive().split(" ");
			t.stop();
			
			if((!message[0].equalsIgnoreCase("ack")) && (message.length != 2))
			{
				System.out.println("Le serveur choisi n'est pas correcte.");
				client.close();
				return;
			}
			
			t.start();
			message = client.receive().split(" ");
			t.stop();
			
			if(message[0].equalsIgnoreCase("start") && message.length == 3)
			{
				listeJoueurs = message[1].split(",");
				
				//R�cup�ration des cartes
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
				
				//R�cup�ration des joueurs
				for(int i = 0; i < listeJoueurs.length; i++)
				{
					System.out.println(i + ") " + listeJoueurs[i]);
				}
				
				while((!message[0].equalsIgnoreCase("end")) || (message.length != 1))
				{
					t.start();
					message = client.receive().split(" ");
					t.stop();
					
					if(message[0].equalsIgnoreCase("error") && message.length > 1)
					{
						if(message[1].equalsIgnoreCase("exit") && message.length == 3)
						{
							System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " a quitt� la partie");
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
							System.out.println(listeJoueurs[Integer.parseInt(message[1])] + " sugg�re " + message[3] + " " + message[4] + " " + message[5]);
						}
						else if(message[2].equalsIgnoreCase("accuse"))
						{
							System.out.println(listeJoueurs[Integer.parseInt(message[1])] + " accuse " + message[3] + " " + message[4] + " " + message[5]);
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
						String carteMontrer = joueur.refuter(Carte.cartesContenuDans(joueur.getCartesJoueur(), new String[]{message[1],message[2],message[3]}));
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
							System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " ne peut pas aider " + listeJoueurs[Integer.parseInt(message[3])] + " parce qu�il n�a pas les cartes de la suggestion.");
						}
						else if(message[1].equalsIgnoreCase("show") && message.length == 4)
						{
							System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " a montr� une carte � " + listeJoueurs[Integer.parseInt(message[3])]);
						}
						else if(message[1].equalsIgnoreCase("respond") && message.length == 4)
						{
							System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " vous � montr� la carte : " + message[3]);
						}
						else if(message[1].equalsIgnoreCase("wrong") && message.length == 3)
						{
							//en supposant que chaque joueur � un nom diff�rent.
							/*if(listeJoueurs[Integer.parseInt(message[1])].equals(joueur.getNom()))
							{
								System.out.println("Vous avez fait une accusation fausse, vous avez perdu.");
							}
							else
							{*/
								System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " a fait une accusation fausse, il a perdu la partie.");
							//}
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
						System.out.println(listeJoueurs[ind] + " � gagn� la partie.");
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
