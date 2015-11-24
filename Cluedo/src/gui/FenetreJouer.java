package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreJouer extends JFrame{
	
	private PanelAllCards allCardsPane;
	private JButton btnSuggest;
	private JButton btnAccuse;
	private String[] move = null;
	
	public FenetreJouer(Point location)
	{
		super("Your turn!");
		this.load(location);
		this.loadListeners();
	}
	
	private void load(Point location)
	{
		this.btnAccuse = new JButton("Accuse");
		this.btnSuggest = new JButton("Suggest");
		JPanel panelButtons = new JPanel(new FlowLayout());
		allCardsPane = new PanelAllCards(true);
		panelButtons.add(btnSuggest);
		panelButtons.add(btnAccuse);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(panelButtons, BorderLayout.SOUTH);
		this.getContentPane().add(allCardsPane, BorderLayout.NORTH);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(location);
		this.setVisible(true);
		this.pack();
	}
	
	private void loadListeners()
	{
		btnAccuse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton arme = allCardsPane.getHighlightedCardArme();
				JButton suspect = allCardsPane.getHighlightedCardSuspect();
				JButton lieu = allCardsPane.getHighlightedCardLieu();
				if(arme != null && lieu != null && suspect != null)
				{
					move = new String[]{"accuse",arme.getName(),lieu.getName(),suspect.getName()};
				}
			}
		});
		
		btnSuggest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton arme = allCardsPane.getHighlightedCardArme();
				JButton suspect = allCardsPane.getHighlightedCardSuspect();
				JButton lieu = allCardsPane.getHighlightedCardLieu();
				if(arme != null && lieu != null && suspect != null)
				{
					move = new String[]{"suggest",arme.getName(),lieu.getName(),suspect.getName()};
				}
				
			}
		});
	}

}
