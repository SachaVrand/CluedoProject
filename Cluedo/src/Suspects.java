import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Suspects {

	Rouge("Mademoiselle Rose",new ImageIcon("pathManquant")),
	Jaune("Colonel Moutarde",new ImageIcon("pathManquant")),
	Blanc("Madame Leblanc",new ImageIcon("pathManquant")),
	Violet("Professeur Violet",new ImageIcon("pathManquant")),
	Bleu("Madame Pervenche",new ImageIcon("pathManquant")),
	Vert("Révérend Olive",new ImageIcon("pathManquant"));
	
	private String nom;
	private Icon img;
	
	Suspects(String nom,Icon img)
	{
		this.nom = nom;
		this.img = img;
	}
	
	public String getNom()
	{
		return this.nom;
	}
	
	public Icon getImage()
	{
		return this.img;
	}
}
