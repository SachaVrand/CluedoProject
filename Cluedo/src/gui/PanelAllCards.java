package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.CredentialExpiredException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import principal.Arme;
import principal.Carte;
import principal.Lieu;
import principal.Suspect;

public class PanelAllCards extends JPanel{
	
	private List<JLabel> lblCartesSuspect;
	private List<JLabel> lblCartesArme;
	private List<JLabel> lblCartesLieu;
	private MouseListener mlSuspect = null;
	private MouseListener mlLieu = null;
	private MouseListener mlArme = null;
	
	public PanelAllCards()
	{
		super(new GridBagLayout());
		this.lblCartesArme = new ArrayList<JLabel>();
		this.lblCartesLieu = new ArrayList<JLabel>();
		this.lblCartesSuspect = new ArrayList<JLabel>();
		this.load();
	}
	
	/*public PanelAllCards(List<Carte> listeCartes, MouseListener mlSuspect, MouseListener mlArme, MouseListener mlLieu)
	{
		super(new GridBagLayout());
		this.listeCartes = listeCartes;
		this.lblCartesArme = new ArrayList<JLabel>();
		this.lblCartesLieu = new ArrayList<JLabel>();
		this.lblCartesSuspect = new ArrayList<JLabel>();
		this.mlArme = mlArme;
		this.mlSuspect = mlSuspect;
		this.mlLieu = mlLieu;
		this.load();
	}*/
	
	private void load()
	{
		loadLabelsCartes();
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel lblSuspects = new JLabel("Suspects");
		JLabel lblArmes = new JLabel("Armes");
		JLabel lblLieux = new JLabel("Lieux");
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.insets = new Insets(10, 15, 5, 15);
		this.add(lblSuspects,gbc);
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(5, 5, 10, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JSeparator sepSuspect = new JSeparator();
		sepSuspect.setPreferredSize(new Dimension(100, 1));
		this.add(sepSuspect, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(10, 5, 10, 5);
		for(JLabel lbl : lblCartesSuspect)
		{
			this.add(lbl, gbc);
			gbc.gridx++;
		}
		gbc.gridy++;
		gbc.insets = new Insets(10, 15, 5, 15);
		gbc.gridx = 0;
		this.add(lblArmes,gbc);
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(5, 5, 10, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JSeparator sepArmes = new JSeparator();
		sepArmes.setPreferredSize(new Dimension(100, 1));
		this.add(sepArmes, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(10, 5, 10, 5);
		for(JLabel lbl : lblCartesArme)
		{
			this.add(lbl, gbc);
			gbc.gridx++;
		}
		gbc.gridy++;
		gbc.insets = new Insets(10, 15, 5, 15);
		gbc.gridx = 0;
		this.add(lblLieux,gbc);
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(5, 5, 10, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JSeparator sepLieux = new JSeparator();
		sepLieux.setPreferredSize(new Dimension(100, 1));
		this.add(sepLieux, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(10, 5, 10, 5);
		for(JLabel lbl : lblCartesLieu)
		{
			this.add(lbl, gbc);
			gbc.gridx++;
		}
	}
	
	private void loadLabelsCartes()
	{
		List<Carte> listeCartes = Carte.creerPaquetDeCartes();
		for(Carte c : listeCartes)
		{
			JLabel tmp = new JLabel(c.getImage());
			tmp.setName(c.getNom());
			if(c instanceof Suspect)
			{
				if(mlSuspect != null) 
					tmp.addMouseListener(mlSuspect);
				lblCartesSuspect.add(tmp);
			}
			else if(c instanceof Arme)
			{
				if(mlArme != null)
					tmp.addMouseListener(mlArme);
				lblCartesArme.add(tmp);
			}
			else if(c instanceof Lieu)
			{
				if(mlLieu != null)
					tmp.addMouseListener(mlLieu);
				lblCartesLieu.add(tmp);
			}
		}
	}

}
