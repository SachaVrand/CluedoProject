package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import principal.Joueur;
import principal.TextAreaOutputStream;

public class PanelJeu extends JPanel{
	
	private JTextArea txtConsole;
	private List<JLabel> listeJoueurs;
	private Joueur joueur;
	private JButton btnShow;
	
	public PanelJeu()
	{
		super();
		this.load();
		this.loadListeners();
		System.setOut(new PrintStream(new TextAreaOutputStream(txtConsole)));
	}
	
	private void load()
	{
		txtConsole = new JTextArea();
		btnShow = new JButton("Show my cards");
		JPanel panelIconesJoueurs = new JPanel();
		panelIconesJoueurs.setLayout(new BoxLayout(panelIconesJoueurs, BoxLayout.PAGE_AXIS));
	}
	
	private void loadListeners()
	{
		this.btnShow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO afficher mes cartes.
				if(joueur != null)
				{
					
				}
			}
		});
	}
	
	public void ajouterIconesJoueurs(List<String> listeNomsJoueurs)
	{
		
	}

	public void setJoueur(Joueur j)
	{
		this.joueur = j;
	}
}
