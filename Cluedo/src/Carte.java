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
	
	public static boolean contientCarte(List<Carte> cartes, String nom)
	{
		for(Carte c : cartes)
		{
			if(c.getNom().equals(nom))
			{
				return true;
			}
		}
		return false;
	}
	
	// retoure true si la liste de carte contient au moins l'une des cartes se trouvant dans le tableau de noms de cartes
	public static boolean contientCarte(List<Carte> cartes, String[] noms)
	{
		for(Carte c : cartes)
		{
			for(String n : noms)
			{
				if(c.getNom().equals(n))
				{
					return true;
				}
			}
		}
		return false;
	}

}
