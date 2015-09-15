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
	
	public boolean equals(Object o)
	{
		if(o instanceof String)
		{
			String tmp = (String)o;
			if(tmp.equals(nom))
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		else if(o instanceof Carte)
		{
			Carte tmp = (Carte)o;
			if(nom.equals(tmp.getNom()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

}
