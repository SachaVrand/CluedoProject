package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import principal.Joueur;

public class PlayerIcon extends JLabel implements MouseListener{
	
	private Joueur joueur;
	
	public PlayerIcon(Joueur j)
	{
		super();
		joueur = j;
		//TODO seticon
		this.setName(joueur.getNom());
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO tooltip perso
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO tooltip perso
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
