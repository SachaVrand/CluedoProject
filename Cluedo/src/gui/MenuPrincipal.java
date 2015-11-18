package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import principal.Cluedo;

public class MenuPrincipal extends JPanel{
	
	private JButton btnSolo;
	private JButton btnRegister;
	private JButton btnReferee;
	private JButton btnExit;
	
	public MenuPrincipal()
	{
		super();
		this.setLayout(new GridBagLayout());
		this.load();
		this.loadListeners();
	}
	
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
	
	private void loadListeners()
	{
		btnSolo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cluedo.afficherGUIMenuSolo();
				
			}
		});
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cluedo.afficherGUIMenuRegister();
				
			}
		});
		
		btnReferee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cluedo.afficherGUIMenuReferee();
				
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
