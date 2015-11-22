package gui;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JFrame;

import principal.Carte;

public class FenetreRefuter extends JFrame{
	
	public FenetreRefuter(List<Carte> listeCartesCommun)
	{
		super("A vous de réfuter !");
		this.load(listeCartesCommun);
	}
	
	private void load(List<Carte> listeCartesCommun)
	{
		this.setLayout(new FlowLayout());
		this.add(new PanelCartes(listeCartesCommun, true));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		//TODO set location
		this.setVisible(true);
		this.pack();
	}

}
