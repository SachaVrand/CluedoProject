import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Armes {

	Candlestick(new ImageIcon("pathManquant")),
	Dagger(new ImageIcon("pathManquant")),
	Lead_pipe(new ImageIcon("pathManquant")),
	Revolver(new ImageIcon("pathManquant")),
	Rope(new ImageIcon("pathManquant")),
	Spanner(new ImageIcon("pathManquant"));
	
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
