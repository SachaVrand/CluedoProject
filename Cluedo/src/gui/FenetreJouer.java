package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Classe représentant la fenetre pour jouer un coup. Comporte un panel de toute les cartes du jeu selectionnable avec un bouton pour accuser et un bouton pour suggerer.
 * @author Sacha
 *
 */
public class FenetreJouer extends JFrame{
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilisé.
	 */
	private static final long serialVersionUID = -2829818863755807169L;
	
	/**
	 * Panel comportant toute les cartes du jeu.
	 */
	private PanelAllCards allCardsPane;
	
	/**
	 * Bouton permettant de suggérer
	 */
	private JButton btnSuggest;
	
	/**
	 * Bouton permettant d'accuser.
	 */
	private JButton btnAccuse;
	
	/**
	 * Flux permettant la communication entre le thread graphique et le thread de la boucle de jeu.
	 */
	private PipedOutputStream pipeOut;
	
	/**
	 * Constructeur de la classe FenetreJouer. Charge les composants et les listeners. Instancie un nouveau PipedOutputStream.
	 */
	public FenetreJouer()
	{
		super("Your turn!");
		this.load();
		this.loadListeners();
		pipeOut = new PipedOutputStream();
	}
	
	/**
	 * Méthode permettant de charger les différents composants graphique.
	 */
	private void load()
	{
		this.btnAccuse = new JButton("Accuse");
		this.btnSuggest = new JButton("Suggest");
		JPanel panelButtons = new JPanel(new FlowLayout());
		allCardsPane = new PanelAllCards(true);
		panelButtons.add(btnSuggest);
		panelButtons.add(btnAccuse);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(panelButtons, BorderLayout.SOUTH);
		this.getContentPane().add(allCardsPane, BorderLayout.NORTH);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.pack();
	}
	
	/**
	 * Méthode permettant de charger les différents listener.
	 */
	private void loadListeners()
	{
		btnAccuse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton arme = allCardsPane.getHighlightedCardArme();
				JButton suspect = allCardsPane.getHighlightedCardSuspect();
				JButton lieu = allCardsPane.getHighlightedCardLieu();
				if(arme != null && lieu != null && suspect != null)
				{
					try {
						pipeOut.write(("move " + "accuse" + " " + arme.getName() + " " + lieu.getName() + " " + suspect.getName() + "\n").getBytes());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnSuggest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton arme = allCardsPane.getHighlightedCardArme();
				JButton suspect = allCardsPane.getHighlightedCardSuspect();
				JButton lieu = allCardsPane.getHighlightedCardLieu();
				if(arme != null && lieu != null && suspect != null)
				{
					try {
						pipeOut.write(("move " + "suggest" + " " + arme.getName() + " " + lieu.getName() + " " + suspect.getName() + "\n").getBytes());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
	}

	/**
	 * Méthode permettant de récupérer le flux de type PipeOutputStream de la fenetre.
	 * @return le flux de type PipeOutputStream de la fenetre.
	 */
	public PipedOutputStream getPipeOut() {
		return pipeOut;
	}
	
	/**
	 * Méthode qui permet de fermer le flux de type PipeOutputStream de la fenetre.
	 */
	public void closePipeOut()
	{
		try {
			pipeOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode permettant d'enovyer la commande exit à la boucle de jeu.
	 */
	public void sendExit()
	{
		try {
			pipeOut.write(("exit\n").getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
