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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

import principal.Carte;
import principal.Joueur;
import principal.TextAreaOutputStream;

/**
 * Classe représentant le panel de jeu comportant une text area affichant différentes informations sur la partie, des icone permetant de suivre les autres joueurs.
 * Et aussi les carets du joueur.
 * @author Sacha
 *
 */
public class PanelJeu extends JPanel{
	
	/**
	 * Constante permettant de définir que le type du panel crée sera un panel pour un humain.
	 */
	public static final int PANEL_HUMAIN = 0;
	
	/**
	 * Constante permettant de définir que le type du panel crée sera un panel pour un hote de partie.
	 */
	public static final int PANEL_HOTE = 0;
	
	/**
	 * Constante permettant de définir que le type du panel crée sera un panel pour un ordi.
	 */
	public static final int PANEL_ORDI = 0;
	
	private int typePanel;
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilisé.
	 */
	private static final long serialVersionUID = -3738101737134996357L;
	
	/**
	 * Zone de texte permettant d'afficher diférentes informations sur la partie. Il est relié à la sortie standard dès la creation du panel.
	 */
	private JTextArea txtConsole;
	
	/**
	 * Liste de PlayerIcon permettant de connaitre les adversaires ainsi que les cartes qu'ils nous ont montrées.
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
	 * Boolean permettant de savoir si les cartes du joueurs sont affichées sur la panel.
	 */
	private boolean isCardsPanelDisplayed;
	
	/**
	 * Panel comportant les cartes du joueur.
	 */
	private PanelCartes panelCards;
	
	/**
	 * Label de la dernière carte qu'on nous a montrée.
	 */
	private JLabel lblDerCarte;
	
	/**
	 * Label du nom du joueur qui nous a montrée la dernière carte.
	 */
	private JLabel lblJoueurDerCarte;
	
	/**
	 * Constructeur de la classe PanelJeu. Charge les composants et listeners du panel. Lie la textArea à la sortie standard. En passant un joueur avec les cartes voulu, on peut afficher n'importe quelles cartes dans le panel de cartes.
	 * @param j joueur jouant la partie.
	 * @param listeJoueurs liste des joueurs de la partie. Le joueur jouant inclue.
	 * @param constantePanel Constante permettant de définir pour quel type de joueur le panel va être créer.
	 */
	public PanelJeu(Joueur j, List<Joueur> listeJoueurs, final int constantePanel)
	{
		super(new BorderLayout());
		this.joueur = j;
		this.listeJoueurs = listeJoueurs;
		this.typePanel = constantePanel;
		this.load();
		this.loadListeners();
		//ne retiens pas le flux de sortie standard et si le panel n'est plus utilise, si un sysout est fait il tentera d'afficher sur un textArea qui n'existe plus. Mais ce n'est pas censé arrivé.
		System.out.close();
		System.setOut(new PrintStream(new TextAreaOutputStream(txtConsole)));
	}
	
	/**
	 * Méthode permettant de charger les différents composants graphique.
	 */
	private void load()
	{
		JPanel panelBoutons = new JPanel(new FlowLayout());
		JPanel panelPrincipal = new JPanel(new FlowLayout());
		JPanel panelIconesJoueurs = new JPanel();
		JPanel panelConsole = new JPanel(new BorderLayout());
		JPanel panelDerCarte = new JPanel(new BorderLayout());
		panelDerCarte.setBorder(BorderFactory.createTitledBorder("Last response"));
		panelIconesJoueurs.setLayout(new BoxLayout(panelIconesJoueurs, BoxLayout.PAGE_AXIS));
		loadLabelsJoueurs();
		panelIconesJoueurs.add(Box.createVerticalStrut(10));
		for(PlayerIcon lbl : listeIconesJoueurs)
		{
			panelIconesJoueurs.add(lbl);
			panelIconesJoueurs.add(Box.createVerticalStrut(10));
		}
		lblDerCarte = new JLabel(new ImageIcon("Images/Unknown.jpg"));
		lblDerCarte.setBorder(BorderFactory.createLineBorder(Color.black));
		lblJoueurDerCarte = new JLabel("No one");
		lblJoueurDerCarte.setHorizontalAlignment(SwingConstants.CENTER);
		panelCards = new PanelCartes(joueur.getCartesJoueur(), true);
		panelCards.setVisible(false);
		txtConsole = new JTextArea(15,50);
		txtConsole.setEditable(false);
		txtConsole.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		JScrollPane scrollPane = new JScrollPane(txtConsole);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		((DefaultCaret)txtConsole.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		if(typePanel == PANEL_HUMAIN || typePanel == PANEL_ORDI)
			btnShow = new JButton("Show my cards");
		else if(typePanel == PANEL_HOTE)
			btnShow = new JButton("Show murder's cards");
		btnQuit = new JButton("Quit");
		btnQuit.setEnabled(false);
		
		panelDerCarte.add(lblDerCarte,BorderLayout.NORTH);
		panelDerCarte.add(lblJoueurDerCarte,BorderLayout.SOUTH);
		panelConsole.add(scrollPane);
		panelBoutons.add(btnShow);
		panelBoutons.add(btnQuit);
		panelPrincipal.add(panelDerCarte,BorderLayout.WEST);
		panelPrincipal.add(panelConsole,BorderLayout.CENTER);
		panelPrincipal.add(panelIconesJoueurs,BorderLayout.EAST);
		this.add(panelPrincipal,BorderLayout.NORTH);
		this.add(panelBoutons,BorderLayout.CENTER);
		this.add(panelCards, BorderLayout.SOUTH);
		
	}
	
	/**
	 * Méthode permettant de charger les différents listener.
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
				GraphicalUserInterface.sendExitInGame();
				GraphicalUserInterface.desafficherFenJouer();
				GraphicalUserInterface.desafficherFenRefuter();
				GraphicalUserInterface.afficherGUIMenuPrincipal();
			}
		});
	}

	/**
	 * Méthode permettant de charger les différents PlayerIcon des joueurs de la partie.
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
	 * Méthode permettant de changer l'état bouton quitter.
	 * @param b Vrai si il est cliquable, faux sinon.
	 */
	public void setEnabledBtnQuit(boolean b)
	{
		this.btnQuit.setEnabled(b);
	}
	
	/**
	 * Méthode qui met à jour les PlayerIcon du panel.
	 */
	public void updatePlayersIcon()
	{
		for(PlayerIcon pl : listeIconesJoueurs)
		{
			pl.updateCardsForTooltip();
		}
	}
	
	/**
	 * Méthode qui met à jour la denière carte montrée avec le nom du joueur qui l'a montré.
	 * @param c Carte montrée
	 * @param indJoueur indice du joueur qui a montré la carte.
	 */
	public void updateDerCarte(Carte c, int indJoueur)
	{
		if(!listeJoueurs.get(indJoueur).equals(joueur))
		{
			this.lblDerCarte.setIcon(c.getImage());
			this.lblJoueurDerCarte.setText(listeJoueurs.get(indJoueur).getNom());
		}
		
	}
	
}
