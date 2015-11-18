package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import principal.Cluedo;

public class MenuSolo extends JPanel{
	
	private JLabel lblNbJoueurs;
	private JLabel lblNiveauIA;
	private JLabel lblSolo;
	private JLabel lblNomJoueur;
	private JButton btnJouer;
	private JButton btnRetour;
	private JComboBox<Integer> cbxNiveauIA;
	private JComboBox<Integer> cbxNbJoueurs;
	private JTextField tfNomJoueur;
	
	public MenuSolo()
	{
		super();
		this.setLayout(new GridBagLayout());
		this.load();
		this.loadListeners();
	}
	
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
	
	private void loadListeners()
	{
		btnRetour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cluedo.afficherGUIMenuPrincipal();
			}
		});
		btnJouer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int nivIA = (int) cbxNiveauIA.getSelectedItem();
				int nbJoueurs = (int) cbxNbJoueurs.getSelectedItem();
				String nom = tfNomJoueur.getText();
				
			}
		});
	}

}
