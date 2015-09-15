import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Suspects {

	Miss_Scarlett(new ImageIcon("pathManquant")),
	Colonel_Mustard(new ImageIcon("pathManquant")),
	Mrs_White(new ImageIcon("pathManquant")),
	Professor_Plum(new ImageIcon("pathManquant")),
	Mrs_Peacock(new ImageIcon("pathManquant")),
	Reverend_Green(new ImageIcon("pathManquant"));
	
	private Icon img;
	
	Suspects(Icon img)
	{
		this.img = img;
	}
	
	public Icon getImage()
	{
		return this.img;
	}
}
