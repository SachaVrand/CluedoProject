import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Lieux {
	
	Cuisine("Cuisine",new ImageIcon("pathManquant")),
	Conservatoire("Conservatoire",new ImageIcon("pathManquant")),
	SalleAManger("Salle à manger",new ImageIcon("pathManquant")),
	SalleDeBal("Salle de bal",new ImageIcon("pathManquant")),
	SalleDeBillard("Salle de billard",new ImageIcon("pathManquant")),
	Bibliotheque("Bibliotheque",new ImageIcon("pathManquant")),
	Salin("Salon",new ImageIcon("pathManquant")),
	Hall("Hall",new ImageIcon("pathManquant")),
	Bureau("Bureau",new ImageIcon("pathManquant"));
	
	private String nom;
	private Icon img;
	
	Lieux(String nom,Icon img)
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
