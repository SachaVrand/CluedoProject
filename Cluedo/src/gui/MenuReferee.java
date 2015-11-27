package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import principal.Cluedo;

public class MenuReferee extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4903624604136421379L;
	private JButton btnReferee;
	private JButton btnRetour;
	private JLabel lblAdrrHote;
	private JLabel lblReferee;
	private JLabel lblNbJoueurs;
	private JLabel lblAddrIp;
	private JComboBox<Integer> cbxNbJoueurs;
	
	public MenuReferee()
	{
		super();
		this.setLayout(new GridBagLayout());
		this.load();
		this.loadListeners();
	}
	
	private void load()
	{
		btnReferee = new JButton("Connexion");
		btnRetour = new JButton("Retour");
		lblAdrrHote = new JLabel("Adresse de l'hôte : ");
		lblReferee = new JLabel("Register");
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
	}
	
	private void loadListeners()
	{
		btnRetour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cluedo.afficherGUIMenuPrincipal();
				
			}
		});
		
		btnReferee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

}
