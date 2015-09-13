import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Armes {

	Chandelier(new ImageIcon("pathManquant")),
	Poignard(new ImageIcon("pathManquant")),
	Tuyau(new ImageIcon("pathManquant")),
	Revolver(new ImageIcon("pathManquant")),
	Corde(new ImageIcon("pathManquant")),
	CleAnglaise(new ImageIcon("pathManquant"));
	
	private Icon img;
	
	Armes(Icon img)
	{
		this.img = img;
	}
	
	public Icon getImage()
	{
		return this.img;
	}
}
