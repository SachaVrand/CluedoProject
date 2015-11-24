package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JTextArea;

import principal.Cluedo;
import principal.Joueur;
import principal.TextAreaOutputStream;

public class PanelJeu extends JPanel{
	
	private JTextArea txtConsole;
	private List<PlayerIcon> listeIconesJoueurs;
	private List<Joueur> listeJoueurs;
	private Joueur joueur;
	private JButton btnShow;
	private JButton btnQuit;
	private boolean isCardsPanelDisplayed;
	private PanelCartes panelCards;
	
	public PanelJeu(Joueur j, List<Joueur> listeJoueurs)
	{
		super(new BorderLayout());
		this.joueur = j;
		this.listeJoueurs = listeJoueurs;
		this.load();
		this.loadListeners();
		System.setOut(new PrintStream(new TextAreaOutputStream(txtConsole)));
	}
	
	private void load()
	{
		JPanel panelBoutons = new JPanel(new BorderLayout());
		JPanel panelPrincipal = new JPanel(new FlowLayout());
		JPanel panelIconesJoueurs = new JPanel();
		JPanel panelConsole = new JPanel(new BorderLayout());
		JPanel panelAhhhh = new JPanel(new FlowLayout());
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
		txtConsole = new JTextArea();
		txtConsole.setPreferredSize(new Dimension(500, 200));
		txtConsole.setEditable(false);
		txtConsole.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		btnShow = new JButton("Show my cards");
		btnQuit = new JButton("Quit");
		
		panelConsole.add(txtConsole);
		panelBoutons.add(btnShow);
		panelBoutons.add(btnQuit);
		panelAhhhh.add(panelBoutons);
		panelPrincipal.add(panelConsole,BorderLayout.WEST);
		panelPrincipal.add(panelIconesJoueurs,BorderLayout.EAST);
		this.add(panelPrincipal,BorderLayout.NORTH);
		this.add(panelBoutons,BorderLayout.CENTER);
		this.add(panelCards, BorderLayout.SOUTH);
		
	}
	
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
				Cluedo.afficherGUIMenuPrincipal();
				//TODO SEND EXIT
				
			}
		});
	}

	private void loadLabelsJoueurs()
	{
		listeIconesJoueurs = new ArrayList<PlayerIcon>();
		for(Joueur j : listeJoueurs)
		{
			PlayerIcon tmp = new PlayerIcon(j);
			listeIconesJoueurs.add(tmp);
		}
	}
	
	public void setJoueur(Joueur j)
	{
		this.joueur = j;
	}
	
}
