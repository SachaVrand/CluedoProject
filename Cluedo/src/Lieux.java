import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Lieux {
	
	Cuisine(new ImageIcon("pathManquant")),
	Conservatoire(new ImageIcon("pathManquant")),
	SalleAManger(new ImageIcon("pathManquant")),
	SalleDeBal(new ImageIcon("pathManquant")),
	SalleDeBillard(new ImageIcon("pathManquant")),
	Bibliotheque(new ImageIcon("pathManquant")),
	Salin(new ImageIcon("pathManquant")),
	Hall(new ImageIcon("pathManquant")),
	Bureau(new ImageIcon("pathManquant"));
	
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
