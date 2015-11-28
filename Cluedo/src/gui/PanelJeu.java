package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import principal.Joueur;
import principal.TextAreaOutputStream;

/**
 * Classe repr�sentant le panel de jeu comportant une text area affichant diff�rentes informations sur la partie, des icone permetant de suivre les autres joueurs.
 * Et aussi les carets du joueur.
 * @author Sacha
 *
 */
public class PanelJeu extends JPanel{
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilis�.
	 */
	private static final long serialVersionUID = -3738101737134996357L;
	
	/**
	 * Zone de texte permettant d'afficher dif�rentes informations sur la partie. Il est reli� � la sortie standard d�s la creation du panel.
	 */
	private JTextArea txtConsole;
	
	/**
	 * Liste de PlayerIcon permettant de connaitre les adversaires ainsi que les cartes qu'ils nous ont montr�es.
	 */
	private List<PlayerIcon> listeIconesJoueurs;
	
	/**
	 * Liste des joueurs de la partie.
	 */
	private List<Joueur> listeJoueurs;
	
	/**
	 * Joueur jouant la partie.
	 */
	private Joueur joueur;
	
	/**
	 * Bouton permettant d'afficher les cartes du joueur.
	 */
	private JButton btnShow;
	
	/**
	 * Bouton permettant de quitter la partie.
	 */
	private JButton btnQuit;
	
	/**
	 * Boolean permettant de savoir si les cartes du joueurs sont affich�es sur la panel.
	 */
	private boolean isCardsPanelDisplayed;
	
	/**
	 * Panel comportant les cartes du joueur.
	 */
	private PanelCartes panelCards;
	
	
	/**
	 * Constructeur de la classe PanelJeu. Charge les composants et listeners du panel. Lie la textArea � la sortie standard.
	 * @param j joueur jouant la partie.
	 * @param listeJoueurs liste des joueurs de la partie. Le joueur jouant inclue.
	 */
	public PanelJeu(Joueur j, List<Joueur> listeJoueurs)
	{
		super(new BorderLayout());
		this.joueur = j;
		this.listeJoueurs = listeJoueurs;
		this.load();
		this.loadListeners();
		System.setOut(new PrintStream(new TextAreaOutputStream(txtConsole)));
	}
	
	/**
	 * M�thode permettant de charger les diff�rents composants graphique.
	 */
	private void load()
	{
		JPanel panelBoutons = new JPanel(new FlowLayout());
		JPanel panelPrincipal = new JPanel(new FlowLayout());
		JPanel panelIconesJoueurs = new JPanel();
		JPanel panelConsole = new JPanel(new BorderLayout());
		panelIconesJoueurs.setLayout(new BoxLayout(panelIconesJoueurs, BoxLayout.PAGE_AXIS));
		loadLabelsJoueurs();
		panelIconesJoueurs.add(Box.createVerticalStrut(10));
		for(PlayerIcon lbl : listeIconesJoueurs)
		{
			panelIconesJoueurs.add(lbl);
			panelIconesJoueurs.add(Box.createVerticalStrut(10));
		}
		panelCards = new PanelCartes(joueur.getCartesJoueur(), true);
		panelCards.setVisible(false);
		txtConsole = new JTextArea(15,50);
		txtConsole.setEditable(false);
		txtConsole.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		JScrollPane scrollPane = new JScrollPane(txtConsole);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		((DefaultCaret)txtConsole.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		btnShow = new JButton("Show my cards");
		btnQuit = new JButton("Quit");
		btnQuit.setEnabled(false);
		
		panelConsole.add(scrollPane);
		panelBoutons.add(btnShow);
		panelBoutons.add(btnQuit);
		panelPrincipal.add(panelConsole,BorderLayout.WEST);
		panelPrincipal.add(panelIconesJoueurs,BorderLayout.EAST);
		this.add(panelPrincipal,BorderLayout.NORTH);
		this.add(panelBoutons,BorderLayout.CENTER);
		this.add(panelCards, BorderLayout.SOUTH);
		
	}
	
	/**
	 * M�thode permettant de charger les diff�rents listener.
	 */
	private void loadListeners()
	{
		this.btnShow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(joueur != null)
				{
					if(isCardsPanelDisplayed)
					{
						isCardsPanelDisplayed = false;
						panelCards.setVisible(false);
						JButton tmpBtn = (JButton)e.getSource();
						JFrame tmpMainFrame = (JFrame)tmpBtn.getTopLevelAncestor();
						tmpMainFrame.pack();
					}
					else
					{
						isCardsPanelDisplayed = true;
						panelCards.setVisible(true);
						JButton tmpBtn = (JButton)e.getSource();
						JFrame tmpMainFrame = (JFrame)tmpBtn.getTopLevelAncestor();
						tmpMainFrame.pack();
					}
				}
			}
		});
		
		btnQuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO SEND EXIT ET LA ON VA SE MARRER !
				GraphicalUserInterface.sendExitInGame();
				
				GraphicalUserInterface.desafficherFenJouer();
				GraphicalUserInterface.desafficherFenRefuter();
				GraphicalUserInterface.afficherGUIMenuPrincipal();
				
				
			}
		});
	}

	/**
	 * M�thode permettant de charger les diff�rents PlayerIcon des joueurs de la partie.
	 */
	private void loadLabelsJoueurs()
	{
		listeIconesJoueurs = new ArrayList<PlayerIcon>();
		for(Joueur j : listeJoueurs)
		{
			PlayerIcon tmp = new PlayerIcon(j);
			listeIconesJoueurs.add(tmp);
		}
	}
	
	/**
	 * M�thode permettant de changer l'�tat bouton quitter.
	 * @param b Vrai si il est cliquable, faux sinon.
	 */
	public void setEnabledBtnQuit(boolean b)
	{
		this.btnQuit.setEnabled(b);
	}
	
	/**
	 * M�thode qui met � jour les PlayerIcon du panel.
	 */
	public void updatePlayersIcon()
	{
		for(PlayerIcon pl : listeIconesJoueurs)
		{
			pl.updateCardsForTooltip();
		}
	}
	
}
