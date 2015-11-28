package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Classe représentant le panel du menu principal. Comporte différents bouton permettant d'accéder aux menus suivant ou de quitter le jeu.
 * @author Sacha
 *
 */
public class MenuPrincipal extends JPanel{
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilisé.
	 */
	private static final long serialVersionUID = -2665638510165801518L;
	
	/**
	 * Bouton permettant de passer au menu solo.
	 */
	private JButton btnSolo;
	
	/**
	 * Bouton permettant de passer au menu register.
	 */
	private JButton btnRegister;
	
	/**
	 * Bouton permettant de passer au menu referee.
	 */
	private JButton btnReferee;
	
	/**
	 * Bouton permettant de quitter le jeu.
	 */
	private JButton btnExit;
	
	/**
	 * Constructeur de la classe MenuPrincipal. Instancie un nouveau panel MenuPrincipal. Charge les composants et les listeners.
	 */
	public MenuPrincipal()
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
		btnSolo = new JButton("Solo");
		btnRegister = new JButton("Register");
		btnReferee = new JButton("Referee");
		btnExit = new JButton("Exit");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(25, 25, 5, 25);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 30;
		gbc.ipady = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(btnSolo,gbc);
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 25, 5, 25);
		this.add(btnRegister, gbc);
		gbc.gridy = 2;
		this.add(btnReferee, gbc);
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 25, 25, 25);
		this.add(btnExit, gbc);
	}
	
	/**
	 * Méthode permettant de charger les différents listener.
	 */
	private void loadListeners()
	{
		btnSolo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalUserInterface.afficherGUIMenuSolo();
			}
		});
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalUserInterface.afficherGUIMenuRegister();
				
			}
		});
		
		btnReferee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalUserInterface.afficherGUIMenuReferee();
				
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Cluedo.sc.close();
				System.exit(0);
			}
		});
	}
}
