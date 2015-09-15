import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Lieux {
	
	Kitchen(new ImageIcon("pathManquant")),
	Ballroom(new ImageIcon("pathManquant")),
	Conservatory(new ImageIcon("pathManquant")),
	Dining_room(new ImageIcon("pathManquant")),
	Billard_room(new ImageIcon("pathManquant")),
	Library(new ImageIcon("pathManquant")),
	Lounge(new ImageIcon("pathManquant")),
	Hall(new ImageIcon("pathManquant")),
	Study(new ImageIcon("pathManquant"));
	
	private Icon img;
	
	Lieux(Icon img)
	{
		this.img = img;
	}
	
	public Icon getImage()
	{
		return this.img;
	}

}
