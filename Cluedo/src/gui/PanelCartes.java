package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import principal.Arme;
import principal.Carte;
import principal.Lieu;
import principal.Suspect;

public class PanelCartes extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1596893856667466757L;
	private List<JButton> listeBtnCartes;
	private boolean areListened;
	private JButton highlightedCard = null;
	
	
	public PanelCartes(List<Carte> listeCartes, boolean areListened)
	{
		super(new FlowLayout());
		this.listeBtnCartes = new ArrayList<JButton>();
		this.areListened = areListened;
		this.load(listeCartes);
	}
	
	private void load(List<Carte> listeCartes)
	{
		Collections.sort(listeCartes, new Comparator<Carte>() {

			@Override
			public int compare(Carte o1, Carte o2) {
				if(o1 instanceof Suspect && o2 instanceof Suspect)
				{
					return 0;
				}
				else if(o1 instanceof Suspect && o2 instanceof Arme)
				{
					return -1;
				}
				else if(o1 instanceof Suspect && o2 instanceof Lieu)
				{
					return -1;
				}
				else if(o1 instanceof Arme && o2 instanceof Suspect)
				{
					return 1;
				}
				else if(o1 instanceof Arme && o2 instanceof Arme)
				{
					return 0;
				}
				else if(o1 instanceof Arme && o2 instanceof Lieu)
				{
					return -1;
				}
				else if(o1 instanceof Lieu && o2 instanceof Suspect)
				{
					return 1;
				}
				else if(o1 instanceof Lieu && o2 instanceof Arme)
				{
					return 1;
				}
				else if(o1 instanceof Lieu && o2 instanceof Lieu)
				{
					return 0;
				}
				return 0;
			}
		});
		if(listeCartes.size() > 0)
		{
			for(Carte c : listeCartes)
			{
				JButton tmp = new JButton(c.getImage());
				tmp.setName(c.getNom());
				tmp.setContentAreaFilled(false);
				tmp.setFocusPainted(false);
				tmp.setBorderPainted(false);
				tmp.setBorder(BorderFactory.createLineBorder(Color.red, 3, true));
				listeBtnCartes.add(tmp);
				if(areListened)
				{
					tmp.addActionListener(this);
				}
				this.add(tmp);
			}
		}
		else
		{
			JButton tmp = new JButton(new ImageIcon("Images/Unknown.jpg"));
			tmp.setName("Unknwown");
			tmp.setContentAreaFilled(false);
			tmp.setFocusPainted(false);
			tmp.setBorderPainted(true);
			tmp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
			listeBtnCartes.add(tmp);
			if(areListened)
			{
				tmp.addActionListener(this);
			}
			this.add(tmp);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(highlightedCard != null)
			highlightedCard.setBorderPainted(false);
		highlightedCard = (JButton)e.getSource();
		highlightedCard.setBorderPainted(true);		
	}
	
	public JButton getHighlightedCard() {
		return highlightedCard;
	}

}
