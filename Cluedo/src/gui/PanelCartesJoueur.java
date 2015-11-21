package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import principal.Arme;
import principal.Carte;
import principal.Joueur;
import principal.Lieu;
import principal.Suspect;

public class PanelCartesJoueur extends JPanel{
	
	private Joueur joueur;
	
	public PanelCartesJoueur(Joueur joueur)
	{
		super(new GridBagLayout());
		this.joueur = joueur;
		this.load();
	}
	
	private void load()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		JLabel lblSuspects = new JLabel("Suspects");
		JLabel lblArmes = new JLabel("Armes");
		JLabel lblLieux = new JLabel("Lieux");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(25, 25, 5, 25);
		this.add(lblSuspects,gbc);
		gbc.gridy++;
		gbc.gridwidth = 6;
		gbc.insets = new Insets(5, 5, 25, 5);
		this.add(sep, gbc);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(25, 25, 25, 25);
		for(Carte c : joueur.getCartesJoueur())
		{
			if(c instanceof Suspect)
			{
				this.add(new JLabel(c.getImage()), gbc);
				gbc.gridx++;
			}
		}
		gbc.gridy++;
		gbc.insets = new Insets(25, 25, 5, 25);
		gbc.gridx = 0;
		this.add(lblArmes,gbc);
		gbc.gridy++;
		gbc.gridwidth = 6;
		gbc.insets = new Insets(5, 5, 25, 5);
		this.add(sep, gbc);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(25, 25, 25, 25);
		for(Carte c : joueur.getCartesJoueur())
		{
			if(c instanceof Arme)
			{
				this.add(new JLabel(c.getImage()), gbc);
				gbc.gridx++;
			}
		}
		gbc.gridy++;
		gbc.insets = new Insets(25, 25, 5, 25);
		gbc.gridx = 0;
		this.add(lblArmes,gbc);
		gbc.gridy++;
		gbc.gridwidth = 6;
		gbc.insets = new Insets(5, 5, 25, 5);
		this.add(sep, gbc);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(25, 25, 25, 25);
		for(Carte c : joueur.getCartesJoueur())
		{
			if(c instanceof Lieu)
			{
				this.add(new JLabel(c.getImage()), gbc);
				gbc.gridx++;
			}
		}
	}

}
