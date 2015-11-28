package gui;

import java.awt.Dimension;
import java.io.IOException;
import java.io.PipedInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import principal.Carte;
import principal.Cluedo;
import principal.Humain;
import principal.Joueur;
import principal.Ordi;
import principal.PartieClient;
import principal.PartieSolo;

/**
 * Classe fournissant des méthodes et variables statiques permettant de gérer l'interface graphique du programme.
 * Cette classe ne peut pas être dérivée ou instanciée.
 * @author Sacha
 *
 */
public final class GraphicalUserInterface {
	
	/**
	 * Constructeur de la classe GraphicalUserInterface, mais ne doit pas être utilisée car la classe ne doit pas être instanciée.
	 */
	private GraphicalUserInterface()
	{
		throw new RuntimeException();
	}
	
	/**
	 * Variable statique représentant la fenêtre étant affichée quand le joueur doit réfuter.
	 */
	private static FenetreRefuter fenRefuter;
	
	/**
	 * Variable statique représentant la fenêtre étant affichée quand le joueur doit jouer un coup.
	 */
	private static FenetreJouer fenJouer;
	
	/**
	 * Variable statique représentant la fenetre principal du programme sous forme graphique.
	 */
	private static JFrame fenetrePrincipal = null;
	
	/**
	 * Variable static permettant représentant les dimensions de l'écran de l'utilisateur. 
	 */
	public static Dimension screenSize = null;
	
	/**
	 * Fonction permettant désafficher la fenêtre pour réfuter. Cette opération est toujours effectuée dans l'EDT. 
	 * Désactive aussi le bouton quit de la fenêtre principal si elle contient le panel jeu.
	 */
	public static void desafficherFenRefuter()
	{
		if(fenRefuter == null) return;
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenRefuter.setVisible(false);
					fenRefuter = null;
					if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
					{
						PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
						tmp.setEnabledBtnQuit(false);
					}
				}
			});		
		}
		//sinon on est déjà dans l'EDT
		else
		{
			fenRefuter.setVisible(false);
			fenRefuter = null;
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.setEnabledBtnQuit(false);
			}
		}
	}
	
	/**
	 * Fonction permettant désafficher la fenêtre pour jouer. Cette opération est toujours effectuée dans l'EDT.
	 * Désactive aussi le bouton quit de la fenêtre principal si elle contient le panel jeu.
	 */
	public static void desafficherFenJouer()
	{
		if(fenJouer == null) return;
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					fenJouer.setVisible(false);
					fenJouer = null;
					if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
					{
						PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
						tmp.setEnabledBtnQuit(false);
					}
					
				}
			});		
		}
		//sinon on est déjà dans l'EDT
		else
		{
			fenJouer.setVisible(false);
			fenJouer = null;
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.setEnabledBtnQuit(false);
			}
		}
	}
	
	/**
	 * Fonction permettant d'afficher et d'instancier la fenêtre principale. Cette opération est toujours effectuée dans l'EDT.
	 * Le content pane de la fenetre principale instanciée est le menu principal.
	 */
	public static void afficherFenPrincipal()
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					fenetrePrincipal = new JFrame("Cluedo 1.0");
					fenetrePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					fenetrePrincipal.setResizable(false);
					afficherGUIMenuPrincipal();
					screenSize = fenetrePrincipal.getToolkit().getScreenSize(); 
					fenetrePrincipal.setLocation((screenSize.width/2)-(fenetrePrincipal.getWidth()/2), (screenSize.height/2)-(fenetrePrincipal.getHeight()/2));
					fenetrePrincipal.setVisible(true);
				}
			});
		}
		//sinon on est déjà dans l'EDT
		else
		{
			fenetrePrincipal = new JFrame("Cluedo 1.0");
			fenetrePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenetrePrincipal.setResizable(false);
			afficherGUIMenuPrincipal();
			screenSize = fenetrePrincipal.getToolkit().getScreenSize(); 
			fenetrePrincipal.setLocation((screenSize.width/2)-(fenetrePrincipal.getWidth()/2), (screenSize.height/2)-(fenetrePrincipal.getHeight()/2));
			fenetrePrincipal.setVisible(true);
		}
	}
	
	/**
	 * Fonction permettant d'afficher le menu principal dans la fenêtre principale. Cette opération est toujours effectuée dans l'EDT.
	 */
	public static void afficherGUIMenuPrincipal()
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenetrePrincipal.setContentPane(new MenuPrincipal());
					fenetrePrincipal.pack();
				}
			});
		}
		//sinon on est déjà dans l'EDT
		else
		{
			fenetrePrincipal.setContentPane(new MenuPrincipal());
			fenetrePrincipal.pack();
		}
		
	}
	
	/**
	 * Fonction permettant d'afficher le menu solo dans la fenêtre principale. Cette opération est toujours effectuée dans l'EDT.
	 */
	public static void afficherGUIMenuSolo()
	{	
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenetrePrincipal.setContentPane(new MenuSolo());
					fenetrePrincipal.pack();
				}
			});
		}
		//sinon on est déjà dans l'EDT
		else
		{
			fenetrePrincipal.setContentPane(new MenuSolo());
			fenetrePrincipal.pack();
		}	
	}
	
	/**
	 * Fonction permettant d'afficher le menu register dans la fenêtre principale. Cette opération est toujours effectuée dans l'EDT.
	 */
	public static void afficherGUIMenuRegister()
	{	
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenetrePrincipal.setContentPane(new MenuRegister());
					fenetrePrincipal.pack();
				}
			});
		}
		//sinon on est déjà dans l'EDT
		else
		{
			fenetrePrincipal.setContentPane(new MenuRegister());
			fenetrePrincipal.pack();
		}	
	}
	
	/**
	 * Fonction permettant d'afficher le menu referee dans la fenêtre principale. Cette opération est toujours effectuée dans l'EDT.
	 */
	public static void afficherGUIMenuReferee()
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					fenetrePrincipal.setContentPane(new MenuReferee());
					fenetrePrincipal.pack();
				}
			});
		}
		//sinon on est déjà dans l'EDT
		else
		{
			fenetrePrincipal.setContentPane(new MenuReferee());
			fenetrePrincipal.pack();
		}	
	}
	
	/**
	 * Fonction permettant d'afficher et d'instancier la fenêtre pour jouer. Cette opération est toujours effectuée dans l'EDT.
	 * Active le bouton quit de la fenetre principal si son contentPane est le panel jeu.
	 */
	public static void afficherFenJouer()
	{	
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					
					@Override
					public void run() {
						fenJouer = new FenetreJouer();
						fenJouer.setLocation((screenSize.width/2)-(fenJouer.getWidth()/2), (screenSize.height/2)-(fenJouer.getHeight()/2));
						fenJouer.setVisible(true);
						if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
						{
							PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
							tmp.setEnabledBtnQuit(true);
						}
					}
				});
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//sinon on est déjà dans l'EDT
		else
		{
			fenJouer = new FenetreJouer();
			fenJouer.setLocation((screenSize.width/2)-(fenJouer.getWidth()/2), (screenSize.height/2)-(fenJouer.getHeight()/2));
			fenJouer.setVisible(true);
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.setEnabledBtnQuit(true);
			}
		}
		//connecter le pipeOut avec le pipeIn pour établir la connexion entre les deux thread 
		try {
			PipedInputStream pipeIn = new PipedInputStream();
			pipeIn.connect(fenJouer.getPipeOut());
			Cluedo.sc.close();
			Cluedo.sc = new Scanner(pipeIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fonction permettant d'afficher et d'instancier la fenêtre pour réfuter. Cette opération est toujours effectuée dans l'EDT.
	 * Active le bouton quit de la fenetre principal si son contentPane est le panel jeu.
	 * @param listeCartesCommun liste des cartes que le joueur à droit de montrer pour réfuter.
	 */
	public static void afficherFenRefuter(final List<Carte> listeCartesCommun)
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					
					@Override
					public void run() {
						fenRefuter = new FenetreRefuter(listeCartesCommun);
						fenRefuter.setLocation((screenSize.width/2)-(fenRefuter.getWidth()/2), (screenSize.height/2)-(fenRefuter.getHeight()/2));
						fenRefuter.setVisible(true);
						if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
						{
							PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
							tmp.setEnabledBtnQuit(true);
						}
					}
				});
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//sinon on est déjà dans l'EDT
		else
		{
			fenRefuter = new FenetreRefuter(listeCartesCommun);
			fenRefuter.setLocation((screenSize.width/2)-(fenRefuter.getWidth()/2), (screenSize.height/2)-(fenRefuter.getHeight()/2));
			fenRefuter.setVisible(true);
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.setEnabledBtnQuit(true);
			}
		}
		//connecter le pipeOut avec le pipeIn pour établir la connexion entre les deux thread 
		try {
			PipedInputStream pipeIn = new PipedInputStream();
			pipeIn.connect(fenRefuter.getPipeOut());
			Cluedo.sc.close();
			Cluedo.sc = new Scanner(pipeIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fonction permettant d'afficher le panel de jeu dans la fenêtre principale. Cette opération est toujours effectuée dans l'EDT.
	 * @param listeJoueurs Liste des joueurs de la partie. Doit contenir le joueur jouant avec toutes ses cartes, et les autres joueurs mais sans leurs cartes.
	 * @param j Joueur jouant la partie.	
	 */
	public static void afficherGUIJeu(final List<Joueur> listeJoueurs, final Joueur j)
	{	
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					
					@Override
					public void run() {
						PanelJeu panelJeu = new PanelJeu(j,listeJoueurs);
						fenetrePrincipal.setContentPane(panelJeu);
						fenetrePrincipal.pack();
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//sinon on est déjà dans l'EDT
		else
		{
			PanelJeu panelJeu = new PanelJeu(j,listeJoueurs);
			fenetrePrincipal.setContentPane(panelJeu);
			fenetrePrincipal.pack();
		}
	}
	
	/**
	 * Fonction d'envoyer le message exit a travers le pipeStream de la fenetre jouer ou réfuter.
	 */
	public static void sendExitInGame()
	{
		if(fenJouer == null && fenRefuter == null)
			return;
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					if(fenJouer != null)
					{
						fenJouer.sendExit();
					}
					else if(fenRefuter != null)
					{
						fenRefuter.sendExit();
					}
				}
			});
		}
		//sinon on est déjà dans l'EDT
		else
		{
			if(fenJouer != null)
			{
				fenJouer.sendExit();
			}
			else if(fenRefuter != null)
			{
				fenRefuter.sendExit();
			}
		}
	}
	
	/**
	 * Fonction qui met à jour le panel Jeu. Met à jour les icone de joueurs et leurs tooltips.
	 */
	public static void updatePanelJeu()
	{
		//si on est dans un autre thread que l'EDT
		if(!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
					{
						PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
						tmp.updatePlayersIcon();
					}
				}
			});
		}
		//sinon on est déjà dans l'EDT
		else
		{
			if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
			{
				PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
				tmp.updatePlayersIcon();
			}
		}
	}
	
	/**
	 * Fonction qui permet de lancer une partie en tant que client. Créer un nouveau thread pour la boucle de la partie. 
	 * Si la connection n'a pas pu être effectuée, affiche le message d'erreur dans le label d'erreur du menu register.
	 * @param j Joueur jouant la partie.
	 * @param hote Adresse de l'hote de la partie, sous forme de string.
	 */
	public static void lancerPartieClient(final Joueur j, final String hote)
	{
		Thread threadClient = new Thread(new Runnable() {
			
			@Override
			public void run() {

				final String err;
				PartieClient pc = new PartieClient(j, hote);
				err = pc.connexionServeur();
				if(!err.equals(PartieClient.NO_ERROR))
				{
					//afficher erreur
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof MenuRegister)
							{
								MenuRegister tmp = (MenuRegister)fenetrePrincipal.getContentPane();
								tmp.changeTextlblError(err);
								tmp.setButtonsEnabled(true);
								fenetrePrincipal.pack();
							}
						}
					});
				}
				else
				{
					if(j.getCartesJoueur().size() == 0)
						System.out.println("aucune carte");
					String[] tmpListeNomsJoueurs = pc.getListeJoueurs();
					if(tmpListeNomsJoueurs.length == 0)
						System.out.println("aucun joueur");
					
					//creation liste joueurs
					List<Joueur> listeJoueurs = new ArrayList<Joueur>();
					for(int i = 0; i < tmpListeNomsJoueurs.length; i++)
					{
						if(i == pc.getMyNum())
							listeJoueurs.add(j);
						else
							listeJoueurs.add(new Humain(tmpListeNomsJoueurs[i]));
					}
					j.setPlayersInTheGame(listeJoueurs);
					afficherGUIJeu(listeJoueurs, j);
					pc.boucleJeu();
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
							{
								PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
								tmp.setEnabledBtnQuit(true);
							}	
						}
					});
				}
			}
		});
		threadClient.start();
	}
	
	/**
	 * Fonction qui permet de lancer une partie en solo. Créer un nouveau thread pour la boucle de la partie. 
	 * @param nomJoueur le nom du joueur jouant la partie.
	 * @param nivIA le niveau choisi pour l'IA de la partie.
	 * @param nbJoueurs le nombre de joueurs choisi pour la partie.
	 */
	public static void lancerPartie(final String nomJoueur, final int nivIA, final int nbJoueurs)
	{
		Thread threadJeu = new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<Joueur> listeJoueurs = new ArrayList<Joueur>();
				List<Joueur> listeAAffJeu = new ArrayList<Joueur>();
				Joueur humain = new Humain(nomJoueur);
				listeJoueurs.add(humain);
				listeAAffJeu.add(humain);
				for(int i = 1; i < nbJoueurs; i++)
				{
					listeJoueurs.add(new Ordi("Joueur "+Integer.toString(i),nivIA));
					listeAAffJeu.add(new Ordi("Joueur "+Integer.toString(i),nivIA));
				}
				PartieSolo partie = new PartieSolo(listeJoueurs);
				afficherGUIJeu(listeAAffJeu,humain);
				humain.setPlayersInTheGame(listeAAffJeu);
				partie.boucleJeu();
				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if(fenetrePrincipal != null && fenetrePrincipal.getContentPane() instanceof PanelJeu)
						{
							PanelJeu tmp = (PanelJeu)fenetrePrincipal.getContentPane();
							tmp.setEnabledBtnQuit(true);
						}	
					}
				});
			}
		});
		threadJeu.start();
	}

}
