package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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
	private boolean isCardsPanelDisplayed;
	
	public PanelJeu()
	{
		super(new BorderLayout());
		this.load();
		this.loadListeners();
		System.setOut(new PrintStream(new TextAreaOutputStream(txtConsole)));
		isCardsPanelDisplayed = false;
	}
	
	private void load()
	{
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		txtConsole = new JTextArea();
		txtConsole.setPreferredSize(new Dimension(500, 200));
		btnShow = new JButton("Show my cards");
		
		panelPrincipal.add(txtConsole);
		//warning si le bouton change de parent, le code du listener devra changer
		panelPrincipal.add(btnShow,BorderLayout.SOUTH);
		
		JPanel panelIconesJoueurs = new JPanel();
		panelIconesJoueurs.setLayout(new BoxLayout(panelIconesJoueurs, BoxLayout.PAGE_AXIS));
		
		panelPrincipal.add(panelIconesJoueurs,BorderLayout.EAST);
		this.add(panelPrincipal,BorderLayout.CENTER);
		
	}
	
	private void loadListeners()
	{
		this.btnShow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO afficher mes cartes.
				if(joueur != null)
				{
					if(isCardsPanelDisplayed)
					{
						//retirer
					}
					else
					{
						isCardsPanelDisplayed = true;
						JButton tmpBtnShow = (JButton)e.getSource();
						tmpBtnShow.getParent().add(new PanelCartesJoueur(joueur), BorderLayout.WEST);
						JFrame tmpMainFrame = (JFrame)tmpBtnShow.getTopLevelAncestor();
						tmpMainFrame.pack();
					}
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
