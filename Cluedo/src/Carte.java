import java.util.List;

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
	
	@Override
	public boolean equals(Object o)
	{
		//new truc
		if(o == this)
			return true;
		
		if(o instanceof Carte)
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
		return false;
	}
	
	public static boolean contientCarte(List<Carte> cartes, String c)
	{
		return false;
	}

}
