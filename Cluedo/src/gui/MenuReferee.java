package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import principal.Humain;
import principal.Joueur;
import principal.PartieServeur;

/**
 * Classe représentant le panel du menu pour créer une partie en tant qu'hôte. Comporte plusieurs permettant la création de la partie.
 * @author Sacha
 *
 */
public class MenuReferee extends JPanel{
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilisé.
	 */
	private static final long serialVersionUID = 4903624604136421379L;
	
	/**
	 * Bouton permettant de lancer une partie en tant que hote.
	 */
	private JButton btnReferee;
	
	/**
	 * Bouton permettant de retourner au menu principal
	 */
	private JButton btnRetour;
	
	/**
	 * Label de l'adresse de l'hote.
	 */
	private JLabel lblAdrrHote;
	
	/**
	 * Label du titre du menu
	 */
	private JLabel lblReferee;
	
	/**
	 * Label du nombre de joueurs
	 */
	private JLabel lblNbJoueurs;
	
	/**
	 * Label représentant l'adresse ip de l'hote.
	 */
	private JLabel lblAddrIp;
	
	/**
	 * Liste permettant de selectionner le nombre de joueurs.
	 */
	private JComboBox<Integer> cbxNbJoueurs;
	
	/**
	 * Label d'erreur, affiché en cas d'erreur lors de la tentative de connexion à l'hôte.
	 */
	private JLabel lblError;
	
	/**
	 * Constructeur de la classe MenuReferee. Instancie un nouveau panel MenuReferee et charge ses compsoants, et listeners.
	 */
	public MenuReferee()
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
		btnReferee = new JButton("Connexion");
		btnRetour = new JButton("Retour");
		lblAdrrHote = new JLabel("Adresse de l'hôte : ");
		lblReferee = new JLabel("Referee");
		lblError = new JLabel();
		lblError.setVisible(false);
		lblNbJoueurs = new JLabel("Nombre de joueurs : ");
		String addresseIp;
		try {
			addresseIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			addresseIp = "Unknown";
		}
		lblAddrIp = new JLabel(addresseIp);
		lblReferee.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		cbxNbJoueurs = new JComboBox<Integer>(new Integer[]{3,4,5,6});
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(25, 25, 25, 25);
		this.add(lblReferee,gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 25, 10, 5);
		this.add(lblAdrrHote, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 5, 10, 25);
		this.add(lblAddrIp, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 25, 10, 5);
		this.add(lblNbJoueurs, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(10, 5, 10, 25);
		this.add(cbxNbJoueurs, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 25, 10, 5);
		gbc.gridwidth = 2;
		JPanel panelButtons = new JPanel(new FlowLayout());
		panelButtons.add(btnReferee);
		panelButtons.add(btnRetour);
		this.add(panelButtons,gbc);
		gbc.gridy++;
		this.add(lblError, gbc);
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
		
		btnReferee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int nbJoueur = (int) cbxNbJoueurs.getSelectedItem();
				List<Joueur> listJoueur = new ArrayList<Joueur>();
				for(int i = 0; i < nbJoueur; i++)
				{
					listJoueur.add(new Humain("Joueur "+Integer.toString(i)));
				}
				setButtonsEnabled(false);
				GraphicalUserInterface.lancerPartieServer(listJoueur);
			}
		});
		
		
	}
	
	public void changeTextlblError(String text)
	{
		this.lblError.setText(text);
		this.lblError.setVisible(true);
	}

	public void setButtonsEnabled(boolean b)
	{
		btnReferee.setEnabled(b);
		btnRetour.setEnabled(b);
	}
}
