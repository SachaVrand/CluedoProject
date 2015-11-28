package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe représentant le panel du menu pour lancer une partie solo. Comporte plusieurs composants permettant la création de la partie.
 * @author Sacha
 *
 */
public class MenuSolo extends JPanel{
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilisé.
	 */
	private static final long serialVersionUID = -8159482267328787297L;
	
	/**
	 * Label du nombre de joueurs.
	 */
	private JLabel lblNbJoueurs;
	
	/**
	 * Label du niveau de l'IA.
	 */
	private JLabel lblNiveauIA;
	
	/**
	 * Label du titre du menu.
	 */
	private JLabel lblSolo;
	
	/**
	 * Label du nom du joueur.
	 */
	private JLabel lblNomJoueur;
	
	/**
	 * Bouton permettant de créer et lancer la partie.
	 */
	private JButton btnJouer;
	
	/**
	 * Bouton permettant de revenir au menu principal.
	 */
	private JButton btnRetour;
	
	/**
	 * Liste permettant de chosir le niveau de l'IA
	 */
	private JComboBox<Integer> cbxNiveauIA;
	
	/**
	 * Liste permettant de choisir le nombre de joueurs.
	 */
	private JComboBox<Integer> cbxNbJoueurs;
	
	/**
	 * Zone de texte permettant de récupérer le nom du joueur.
	 */
	private JTextField tfNomJoueur;
	
	
	/**
	 * Constructeur de la classe MenuSolo. Instancie un nouveau panel de menu solo, charge les composants et listeners.
	 */
	public MenuSolo()
	{
		super();
		this.setLayout(new GridBagLayout());
		this.load();
		this.loadListeners();
	}
	
	/**
	 * Méthode permettant de charger les différents composants graphique.
	 */
	private void load()
	{
		lblNbJoueurs = new JLabel("Nombre de joueurs : ");
		lblNiveauIA = new JLabel("Niveau de l'IA : ");
		lblSolo = new JLabel("Solo");
		lblNomJoueur = new JLabel("Nom du joueur : ");
		lblSolo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		btnJouer = new JButton("Jouer");
		btnRetour = new JButton("Retour");
		cbxNbJoueurs = new JComboBox<Integer>(new Integer[]{3,4,5,6});
		cbxNiveauIA = new JComboBox<Integer>(new Integer[]{0,1,2});
		tfNomJoueur = new JTextField();
		tfNomJoueur.setPreferredSize(new Dimension(100, 25));
		JPanel panelButtons = new JPanel(new FlowLayout());
		panelButtons.add(btnJouer);
		panelButtons.add(btnRetour);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(25, 25, 25, 25);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		this.add(lblSolo,gbc);
		gbc.insets = new Insets(0, 25, 10, 5);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(lblNomJoueur, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 5, 10, 25);
		this.add(tfNomJoueur, gbc);
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(10, 25, 10, 5);
		this.add(lblNbJoueurs, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(10, 5, 10, 25);
		this.add(cbxNbJoueurs, gbc);
		gbc.insets = new Insets(10, 25, 10, 5);
		gbc.gridy++;
		gbc.gridx = 0;
		this.add(lblNiveauIA, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(10, 5, 10, 25);
		this.add(cbxNiveauIA, gbc);
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 25, 25, 25);
		gbc.anchor = GridBagConstraints.CENTER;
		this.add(panelButtons, gbc);
	}
	
	/**
	 * Méthode permettant de charger les différents listener.
	 */
	private void loadListeners()
	{
		btnRetour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalUserInterface.afficherGUIMenuPrincipal();
			}
		});
		btnJouer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				int nivIA = (int) cbxNiveauIA.getSelectedItem();
				int nbJoueurs = (int) cbxNbJoueurs.getSelectedItem();
				String nom = tfNomJoueur.getText();
				if(!nom.equals(""))
				{
					GraphicalUserInterface.lancerPartie(nom, nivIA, nbJoueurs);		
				}
				else
				{
					tfNomJoueur.setBorder(BorderFactory.createLineBorder(Color.red));
				}
			}
		});
	}

}
