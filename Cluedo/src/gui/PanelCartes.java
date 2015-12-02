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
import javax.swing.JButton;
import javax.swing.JPanel;

import principal.Arme;
import principal.Carte;
import principal.Lieu;
import principal.Suspect;

/**
 * Classe représentant un panel composé des cartes passée en paramètre. Implémente l'interface ActionListener pour écouté les cartes(JButton) si demandée.
 * @author Sacha
 *
 */
public class PanelCartes extends JPanel implements ActionListener{
	
	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilisé.
	 */
	private static final long serialVersionUID = 1596893856667466757L;
	
	/**
	 * Liste de boutons représentant les cartes présentes sur la panel.
	 */
	private List<JButton> listeBtnCartes;
	
	/**
	 * Boolean représentant si les cartes(JButton) sont écoutées ou non;
	 */
	private boolean areListened;
	
	/**
	 * Bouton représentant la carte sélectionnée.
	 */
	private JButton highlightedCard = null;
	
	/**
	 * Constructeur de la classe PanelCartes. Instancie un nouveau panel. Charge les composants, et les listeners si demandé.
	 * @param listeCartes Liste des cartes à affichées sur la panel.
	 * @param areListened True si les cartes doivent être écouté et selectionnable, faux sinon.
	 */
	public PanelCartes(List<Carte> listeCartes, boolean areListened)
	{
		super(new FlowLayout());
		this.listeBtnCartes = new ArrayList<JButton>();
		this.areListened = areListened;
		this.load(listeCartes);
	}
	
	/**
	 * Méthode permettant de charger les différents composants graphique.
	 * @param listeCartes Liste des cartes à affiché sur le panel.
	 */
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
			JButton tmp = new JButton(Carte.IMAGE_UNKNOWN);
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
	
	/**
	 * Méthode retournant la carte selectionné sur le panel.
	 * @return la carte selectionnée sur le panel.
	 */
	public JButton getHighlightedCard() {
		return highlightedCard;
	}

}
