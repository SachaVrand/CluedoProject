import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Armes {

	Chandelier("Chandelier",new ImageIcon("pathManquant")),
	Poignard("Poignard",new ImageIcon("pathManquant")),
	Tuyau("Tuyau de plomb",new ImageIcon("pathManquant")),
	Revolver("Revolver",new ImageIcon("pathManquant")),
	Corde("Corde",new ImageIcon("pathManquant")),
	CleAnglaise("Clé anglaise",new ImageIcon("pathManquant"));
	
	private String nom;
	private Icon img;
	
	Armes(String nom,Icon img)
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
