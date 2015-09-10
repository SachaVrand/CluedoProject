import javax.swing.Icon;

public class Carte {
	
	private Icon image;
	private String nom;
	
	public Carte(String nom, Icon img)
	{
		this.nom = nom;
		this.image = img;
	}
	
	public String getNom()
	{
		return this.nom;
	}

}
