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

public final class GraphicalUserInterface {
	
	private GraphicalUserInterface()
	{
		throw new RuntimeException();
	}
	
	private static FenetreRefuter fenRefuter;
	
	private static FenetreJouer fenJouer;
	
	/**
	 * Fenetre principal du programme sous forme graphique.
	 */
	private static JFrame fenetrePrincipal = null;
	
	public static Dimension screenSize = null;
	
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
					//TODO verifier que rien ne se passe avant que le panel jeu soit affichée. PROB
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
