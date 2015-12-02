package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import principal.Carte;

/**
 * Classe représentant la fenetre pour réfuter. Comporte un panel des cartes, selectionnable, que le joueur peut montrer pour réfuter.
 * @author Sacha
 *
 */
public class FenetreRefuter extends JFrame{
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilisé.
	 */
	private static final long serialVersionUID = 2957925619579505L;
	
	/**
	 * Bouton permettant d'afficher ou non les cartes du joueur.
	 */
	private JButton btnShow;
	
	/**
	 * Panel permettant d'afficher les cartes du joueur.
	 */
	private PanelCartes panelCartes;
	
	/**
	 * Flux permettant la communication entre le thread graphique et le thread de la boucle de jeu.
	 */
	private PipedOutputStream pipeOut;
	
	/**
	 * Constructeur de la classe FenetreRefuter. Charge les composants et les listeners. Instancie un nouveau flux de type PipedOutputStream.
	 * @param listeCartesCommun Les cartes que le joueur peut montrer pour réfuter.
	 */
	public FenetreRefuter(List<Carte> listeCartesCommun)
	{
		super("You have to refute!");
		pipeOut = new PipedOutputStream();
		this.load(listeCartesCommun);
		this.loadListener();
		
	}
	
	/**
	 * Méthode permettant de charger les différents composants graphique.
	 * @param listeCartesCommun Liste des cartes en que le joueur peut montrer pour réfuter une suggestion
	 */
	private void load(List<Carte> listeCartesCommun)
	{
		btnShow = new JButton("show");
		this.setLayout(new BorderLayout());
		JPanel panelRefuter = new JPanel(new FlowLayout());
		JPanel panelButton = new JPanel(new FlowLayout());
		panelCartes = new PanelCartes(listeCartesCommun, true);
		panelRefuter.add(panelCartes);
		panelButton.add(btnShow);
		this.getContentPane().add(panelButton,BorderLayout.SOUTH);
		this.getContentPane().add(panelCartes,BorderLayout.NORTH);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.pack();
	}
	
	/**
	 * Méthode permettant de charger les différents listener.
	 */
	private void loadListener()
	{
		btnShow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton carte = panelCartes.getHighlightedCard();
				if(carte != null)
				{
					try {
						pipeOut.write(("show " + carte.getName() + "\n").getBytes());
					} catch (IOException e1) {
						System.err.println("Imposible d'écrire dans le flux");
						e1.printStackTrace();
						System.exit(1);
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
			System.err.println("Imposible de fermer le flux");
			e.printStackTrace();
			System.exit(1);
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
			System.err.println("Imposible d'écrire dans le flux");
			e1.printStackTrace();
			System.exit(1);
		}
	}
}
