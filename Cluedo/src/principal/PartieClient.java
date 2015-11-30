package principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.Timer;

import gui.GraphicalUserInterface;
import networking.Client;

/**
 * Classe représentant une partie en ligne de Cluedo en tant que 'Client'.
 * @author Sacha
 *
 */
public class PartieClient implements IPartie
{
	// Les constantes suivantes permettent de définir les différentes erreurs pouvant se produirent lors de la connexion à un hote.
	private static final String MAUVAISE_IP = "Mauvaise adresse ip pour le serveur.";
	private static final String IMP_CONNEX_SERV = "Erreur, impossible d'ouvir la connexion avec le serveur.";
	private static final String SERV_INCORRECTE = "Le serveur choisi n'est pas correcte.";
	private static final String MESS_SERV_INCO = "Le message du serveur est incorrecte. Fin de la partie.";
	private static final String ABORT_GAME = "La partie n'as pas pu commencé.";
	private static final String ERROR_CLIENT = "Erreur à la reception/envoie d'un message ou à la fermeture du client.";
	public static final String NO_ERROR = "Connexion réussi, la partie peut commencer.";
	
	
	/**
	 * Joueur représentant le client qui va jouer.
	 */
	private Joueur joueur;
	
	/**
	 * Constante représentant le port utilisé pour communiquer avec le serveur.
	 */
	private final int numeroPort = 12345;
	
	/**
	 * L'adresse du serveur distant qui héberge la partie sous forme de chaine de caractères.
	 */
	private String hote;
	
	/**
	 * Instance de Client qui permet de faire la communication avec le serveur.
	 */
	private Client client;
	
	/**
	 * liste des noms des joueurs, permettant de retranscrire les numéros envoyé par le serveur sous la forme de chaine représentant le joueur.
	 */
	private String[] listeJoueurs;
	
	/**
	 * numéro correspondant au joueur
	 */
	private int myNum;
	
	/**
	 * Instancie un partie en tant que client avec le Joueur et l'hote sous forme de String passés en paramètres, un nouveau Client et la listeJoueurs à null.
	 * @param joueur Joueur représentant le client qui va jouer.
	 * @param hote L'adresse du serveur distant qui héberge la partie sous forme de chaine de caractères.
	 */
	public PartieClient(Joueur joueur,String hote)
	{
		this.joueur = joueur;
		this.hote = hote;
		client = new Client();
		listeJoueurs = null;
		myNum = -1;
	}
	
	/**
	 * Méthode permettant d'établir la connexion à l'hôte. Ajoute les cartes au joueur et instancie la lsite des joueurs de la partie si la connexion se déroule correctement.
	 * Retourne la constante NO_ERROR si la connexion s'est passée correctement, sinon une des constantes représentant l'erreur survenue.
	 * @return la constante NO_ERROR si la connexion s'est passée correctement, sinon une des constantes représentant l'erreur survenue.
	 */
	public String connexionServeur()
	{
		String[] message;
		Timer t = new Timer(3000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("En attente...");
				Timer t = (Timer)e.getSource();
				t.stop();
			}
		});
		try
		{
			client.open(hote, numeroPort);
		}
		catch(UnknownHostException e)
		{
			return MAUVAISE_IP;
		}
		catch(IOException e)
		{
			return IMP_CONNEX_SERV;
		}
		try
		{
			client.send("register " + joueur.getNom());
			
			t.start();
			message = client.receive().split(" ");
			t.stop();
			
			if((!message[0].equalsIgnoreCase("ack")) && (message.length != 2))
			{
				client.close();
				return SERV_INCORRECTE;
			}
			else
			{
				myNum = Integer.parseInt(message[1]);
				if(joueur instanceof Ordi)
				{
					((Ordi) joueur).setMyIndInList(myNum);
				}
			}
			
			t.start();
			message = client.receive().split(" ");
			t.stop();
			
			if(message[0].equalsIgnoreCase("start") && message.length == 3)
			{
				listeJoueurs = message[1].split(",");
				
				//Récupération des cartes
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
						client.send("exit");
						return MESS_SERV_INCO;
					}
					joueur.ajouterCarte(c);
					
				}
				String c = "";
				//Affichage des joueurs
				if(Cluedo.mode == Cluedo.CONSOLE_MODE)
				{
					for(int i = 0; i < listeJoueurs.length; i++)
					{
						if(i == myNum) 
							c = " (vous)";
						else 
							c = "";
						System.out.println(i + ") " + listeJoueurs[i] + c);
					}
				}
			}
			else
			{
				return ABORT_GAME;
			}
			return NO_ERROR;
		}
		catch(IOException e)
		{
			return ERROR_CLIENT;
		}
	}
	
	/**
	 * Méthode qui permet de faire jouer une partie de Cluedo en ligne, en tant que 'client'. Etablir une connexion correcte avant de lancer la boucle.
	 */
	@Override
	public void boucleJeu() {
		String[] message = {""};
		Timer t = new Timer(3000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("En attente...");
				Timer t = (Timer)e.getSource();
				t.stop();
			}
		});
		try
		{
			while((!message[0].equalsIgnoreCase("end")) || (message.length != 1))
			{
				t.start();
				message = client.receive().split(" ");
				t.stop();
				
				//Messages d'erreur
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
				//Message d'informtion concernant un coup joué
				else if(message[0].equalsIgnoreCase("move") && message.length == 6)
				{
					if(joueur instanceof Ordi)
					{
						Ordi tmp = (Ordi)joueur;
						tmp.setJoueurActuel(Integer.parseInt(message[1]));
						if(tmp.getAucuneRefutationAutre() != null && tmp.getAucuneRefutationAutre())
						{
							//augmenter les prob des dernieres cartes
							tmp.changerProbDerCartes(Ordi.SUGGEST_NO_REFUTATION);
							tmp.setAucuneRefutationAutre(false);
						}
						else if(tmp.getAucuneRefutationAutre() != null && !tmp.getAucuneRefutationAutre())
						{
							if(tmp.getDernierCoupJouer() != null)
							{
								tmp.changerProbDerCartes(Ordi.SUGGEST_REFUTATION);
							}
						}
						
						if(Integer.parseInt(message[1]) != myNum)
						{
							tmp.setAucuneRefutationAutre(true);
							tmp.setDernierCoupJouer(new String[]{message[3], message[4], message[5]});
						}
						else
						{
							tmp.setAucuneRefutationAutre(null);
						}
					}
					if(message[2].equalsIgnoreCase("suggest"))
					{
						System.out.println(listeJoueurs[Integer.parseInt(message[1])] + " suggère " + message[3] + " " + message[4] + " " + message[5]);
					}
					else if(message[2].equalsIgnoreCase("accuse"))
					{
						System.out.println(listeJoueurs[Integer.parseInt(message[1])] + " accuse " + message[3] + " " + message[4] + " " + message[5]);
					}
				}
				//Demande de suggestion/accusation par le serveur
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
				//Demande de refuter par le serveur
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
				//Messages d'informations diverses
				else if(message[0].equalsIgnoreCase("info") && message.length > 1)
				{
					if(message[1].equalsIgnoreCase("noshow") && message.length == 4)
					{
						System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " ne peut pas aider " + listeJoueurs[Integer.parseInt(message[3])] + " parce qu’il n’a pas les cartes de la suggestion.");
					}
					else if(message[1].equalsIgnoreCase("show") && message.length == 4)
					{
						if(joueur instanceof Ordi)
						{
							if(((Ordi)joueur).getAucuneRefutationAutre() != null)
							{
								((Ordi)joueur).setAucuneRefutationAutre(false);
							}
							((Ordi)joueur).setJoueurRefutant(Integer.parseInt(message[2]));
						}
						System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " a montré une carte à " + listeJoueurs[Integer.parseInt(message[3])]);
					}
					else if(message[1].equalsIgnoreCase("respond") && message.length == 4)
					{
						System.out.println(listeJoueurs[Integer.parseInt(message[2])] + " vous à montré la carte : " + message[3]);
						if(joueur instanceof Ordi)
						{
							Ordi tmp = (Ordi)joueur;
							tmp.ajouterCarteConnue(Carte.retrouverCarte(message[3]), Integer.parseInt(message[2]));
							tmp.setAucuneRefutationDeMonCoup(false);
						}
						//on met à jour les cartes que nous on montrées les autres joueurs.
						joueur.updateKnownedCardForGUI(Integer.parseInt(message[2]), message[3]);
						GraphicalUserInterface.updatePanelJeu();
					}
					else if(message[1].equalsIgnoreCase("wrong") && message.length == 3)
					{
						int ind = Integer.parseInt(message[2]);
						if(ind == myNum)
						{
							System.out.println("Vous avez fait une accusation fausse, vous avez perdu la partie.");
						}
						else
						{
							((Ordi)joueur).changerProbDerCartes(Ordi.LOSING_AFTER_ACCUSE);
							System.out.println(listeJoueurs[ind] + " a fait une accusation fausse, il a perdu la partie.");
						}
						
					}
				}
				//Message n'etant pas dans le protocole
				else if(!message[0].equalsIgnoreCase("end"))
				{
					System.out.println("Le message du serveur est incorrecte.");
					break;
				}
				//Message de fin de partie dans le cas de la victoire d'un joueur.
				else if(message[0].equalsIgnoreCase("end") && message.length == 2)
				{
					int ind = Integer.parseInt(message[1]);
					if(ind == myNum)
					{
						System.out.println("Vous avez gagné la partie.");
					}
					else
					{
						System.out.println(listeJoueurs[ind] + " à gagné la partie.");
					}
					break;
				}
			}
			client.send("exit");
			System.out.println("Fin de la partie.");
			client.close();
		}
		catch(IOException e)
		{
			System.out.println("Erreur à la reception/envoie d'un message ou à la fermeture du client.");
		}
		
	}
	
	public String[] getListeJoueurs() {
		return listeJoueurs;
	}
	
	public int getMyNum() {
		return myNum;
	}

}
