import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Suspects {

	Rouge(new ImageIcon("pathManquant")),
	Jaune(new ImageIcon("pathManquant")),
	Blanc(new ImageIcon("pathManquant")),
	Violet(new ImageIcon("pathManquant")),
	Bleu(new ImageIcon("pathManquant")),
	Vert(new ImageIcon("pathManquant"));
	
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
