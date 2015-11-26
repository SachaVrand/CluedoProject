package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import principal.Carte;

public class FenetreRefuter extends JFrame{
	
	private JButton btnShow;
	private PanelCartes panelCartes;
	//private String refute = null;
	private PipedOutputStream pipeOut;
	
	public FenetreRefuter(List<Carte> listeCartesCommun)
	{
		super("You have to refute!");
		pipeOut = new PipedOutputStream();
		this.load(listeCartesCommun);
		this.loadListener();
		
	}
	
	private void load(List<Carte> listeCartesCommun)
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
					//refute = carte.getName();
					try {
						pipeOut.write(("show " + carte.getName() + "\n").getBytes());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	/*public String getRefute() {
		return refute;
	}*/
	
	public PipedOutputStream getPipeOut() {
		return pipeOut;
	}
}
