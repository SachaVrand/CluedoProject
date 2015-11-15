package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import principal.Cluedo;

public class MenuRegister extends JPanel{

	private JButton btnRegister;
	private JButton btnRetour;
	private JLabel lblAdrrHote;
	private JLabel lblNomJoueur;
	private JLabel lblTypeJoueur;
	private JLabel lblRegister;
	private JComboBox<String> cbxTypeJoueur;
	private JComboBox<Integer> cbxNivIA;
	private JTextField tfAddrHote;
	private JTextField tfNomJoueur;
	
	public MenuRegister()
	{
		super();
		this.setLayout(new GridBagLayout());
		this.load();
		this.loadListeners();
	}
	
	private void load()
	{
		btnRegister = new JButton("Connexion");
		btnRetour = new JButton("Retour");
		lblNomJoueur = new JLabel("Nom du joueur : ");
		lblAdrrHote = new JLabel("Adresse de l'h�te : ");
		lblTypeJoueur = new JLabel("Type du joueur : ");
		lblRegister = new JLabel("Register");
		lblRegister.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		tfNomJoueur = new JTextField();
		tfNomJoueur.setPreferredSize(new Dimension(120, 25));
		tfAddrHote = new JTextField();
		tfAddrHote.setPreferredSize(new Dimension(120, 25));
		cbxTypeJoueur = new JComboBox<String>(new String[]{"Humain","Ordinateur"});
		cbxNivIA = new JComboBox<Integer>(new Integer[]{0,1,2});
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(25, 25, 25, 25);
		this.add(lblRegister,gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 25, 10, 5);
		this.add(lblNomJoueur, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 5, 10, 25);
		this.add(tfNomJoueur, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 25, 10, 5);
		this.add(lblAdrrHote, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(10, 5, 10, 25);
		this.add(tfAddrHote, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 25, 10, 5);
		this.add(lblTypeJoueur, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(10, 5, 10, 25);
		JPanel panelType = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelType.add(cbxTypeJoueur);
		panelType.add(cbxNivIA);
		this.add(panelType, gbc);
		cbxNivIA.setEnabled(false);
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 25, 10, 5);
		gbc.gridwidth = 2;
		JPanel panelButtons = new JPanel(new FlowLayout());
		panelButtons.add(btnRegister);
		panelButtons.add(btnRetour);
		this.add(panelButtons,gbc);
	}
	
	private void loadListeners()
	{
		btnRetour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cluedo.afficherGUIMenuPrincipal();
				
			}
		});
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nom = tfNomJoueur.getText();
				
			}
		});
		
		cbxTypeJoueur.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				String tmp = (String)cbxTypeJoueur.getSelectedItem();
				if(tmp.equals("Ordinateur"))
				{
					cbxNivIA.setEnabled(true);
				}
				else
				{
					cbxNivIA.setEnabled(false);
				}
			}
		});
		
		
	}
}
