package gui;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import principal.Arme;
import principal.Carte;
import principal.Lieu;
import principal.Suspect;

public class PanelCartes extends JPanel{
	
	private List<Carte> listeCartes;
	private List<JLabel> listeLblCartes;
	private boolean areListened;
	
	
	public PanelCartes(List<Carte> listeCartes, boolean areListened)
	{
		super(new FlowLayout());
		this.listeCartes = listeCartes;
		this.listeLblCartes = new ArrayList<JLabel>();
		this.areListened = areListened;
		this.load();
	}
	
	private void load()
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
		
		for(Carte c : listeCartes)
		{
			JLabel tmp = new JLabel(c.getImage());
			tmp.setName(c.getNom());
			listeLblCartes.add(tmp);
			if(areListened)
			{
				//ajouter listener
			}
			this.add(tmp);
		}
	}

}
