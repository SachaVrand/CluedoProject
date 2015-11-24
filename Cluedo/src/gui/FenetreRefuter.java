package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import principal.Carte;

public class FenetreRefuter extends JFrame{
	
	private JButton btnShow;
	private PanelCartes panelCartes;
	private String refute = null;
	
	public FenetreRefuter(List<Carte> listeCartesCommun, Point location)
	{
		super("You have to refute!");
		this.load(listeCartesCommun,location);
		this.loadListener();
	}
	
	private void load(List<Carte> listeCartesCommun, Point location)
	{
		btnShow = new JButton("show");
		this.setLayout(new BorderLayout());
		JPanel panelRefuter = new JPanel(new FlowLayout());
		JPanel panelButton = new JPanel(new FlowLayout());
		panelCartes = new PanelCartes(listeCartesCommun, true);
		panelRefuter.add(panelCartes);
		panelButton.add(btnShow);
		this.getContentPane().add(panelButton,BorderLayout.SOUTH);
		this.getContentPane().add(panelCartes,BorderLayout.NORTH);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(location);
		this.setVisible(true);
		this.pack();
	}
	
	private void loadListener()
	{
		btnShow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton carte = panelCartes.getHighlightedCard();
				if(carte != null)
				{
					refute = carte.getName();
				}
			}
		});
	}

}
