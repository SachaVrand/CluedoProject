package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

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
		gbc.insets = new Insets(25, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		this.add(btnSolo,gbc);
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		this.add(btnRegister, gbc);
		gbc.gridy = 2;
		this.add(btnRegister, gbc);
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 25, 10);
		this.add(btnExit, gbc);
	}
	
	private void loadListeners()
	{
		btnSolo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO afficher MenuSolo
				
			}
		});
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO	afficher MenuRegister
				
			}
		});
		
		btnReferee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO afficher MenuReferee
				
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
