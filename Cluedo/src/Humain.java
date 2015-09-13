import java.util.Scanner;

public class Humain extends Joueur
{
	public Humain(String nom, String adresse)
	{
		super(nom, adresse);
	}

	@Override
	public String[] jouerCoup()
	{
		String[] cmdComplete = null;
		String cmd = "";
		Scanner sc = new Scanner(System.in);
		
		while(cmd.equals("quitter") && cmdComplete.length == 1)
		{
			System.out.print(nom + " > ");
			cmd = sc.next();
			cmdComplete = cmd.split(" ");
			cmd = cmdComplete[0];
			
			if(cmd.equals("show") && cmdComplete.length == 1)
			{
				afficherIndices();
			}
			else if(cmd.equals("aide") && cmdComplete.length == 1)
			{
				afficherAide();
			}
			else if(cmd.equals("move") && cmdComplete.length == 5)
			{
				//Tester si les cartes passées sont correctes
				
				String[] tmp = {cmdComplete[1],cmdComplete[2],cmdComplete[3],cmdComplete[4]};
				sc.close();
				return tmp;
			}
			else
			{
				System.out.println("Mauvaise commande");
			}
		}
		sc.close();
		return null;
		
	}

	@Override
	public boolean refuter(String[] cartes)
	{
		return false;
	}
	
	private void afficherAide()
	{
		System.out.println("Les commandes disponibles durant la partie :\n");
		System.out.println("show");
		System.out.println("\t Voir les informations vous concernant (Indices et accusations faites).\n");
		System.out.println("move <type> <card1> <card2> <card3>");
		System.out.println("\t <type> : Sois 'suggest' ou 'accuse'.");
		System.out.println("\t <cardN> : une carte.\n");
		System.out.println("quitter");
		System.out.println("\t Quitter la partie.\n");
		System.out.println("aide");
		System.out.println("\t Afficher ce message.\n");
	}
	
	private void afficherIndices()
	{
		System.out.println("\n\t Indices : ");
		for(Carte c : cartesJoueur)
		{
			System.out.println(c.getNom());
		}
		if(encoreEnJeu)
		{
			System.out.println("\n Vous n'avez pas encore fait d'accusation.\n");
		}
		else
		{
			System.out.println("\n Vous avez fait une accusation fausse.\n");
		}
	}
	
	private Carte[] testerCartes(String[] tabCarte)
	{
		//Tester si les cartes passées sont correctes
		//si elles sont correctes, renvoie un tableau de carte
		//sinon renvoie null;
		
		Carte[] cartes = null;
		Carte a = null;
		Carte b = null;
		Carte c = null;
		
		//on test la première carte
		for(Armes arme : Armes.values())
		{
			if(arme.getNom() == tabCarte[0])
			{
				a = new Arme(arme.getNom(), arme.getImage());
			}
		}
		if(a == null)
		{
			for(Lieux lieu : Lieux.values())
			{
				if(lieu.getNom() == tabCarte[0])
				{
					a = new Lieu(lieu.getNom(), lieu.getImage());
				}
			}
		}
		if(a == null)
		{
			for(Suspects suspect : Suspects.values())
			{
				if(suspect.getNom() == tabCarte[0])
				{
					a = new Suspect(suspect.getNom(), suspect.getImage());
				}
			}
		}
		
		//on test la 2ème carte (plus de conditions..)
		if(a != null)
		{
			// si la première carte est une arme
			if(a instanceof Arme)
			{
				for(Lieux lieu : Lieux.values())
				{
					if(lieu.getNom() == tabCarte[1])
					{
						b = new Lieu(lieu.getNom(), lieu.getImage());
					}
				}
				if(b == null)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.getNom() == tabCarte[1])
						{
							b = new Suspect(suspect.getNom(), suspect.getImage());
						}
					}
				}
			}
			// si la première carte est un lieu
			if(a instanceof Lieu)
			{
				for(Armes arme : Armes.values())
				{
					if(arme.getNom() == tabCarte[1])
					{
						b = new Arme(arme.getNom(), arme.getImage());
					}
				}
				if(b == null)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.getNom() == tabCarte[1])
						{
							b = new Suspect(suspect.getNom(), suspect.getImage());
						}
					}
				}
			}
			// si la première carte est un suspect
			if(a instanceof Suspect)
			{
				for(Armes arme : Armes.values())
				{
					if(arme.getNom() == tabCarte[1])
					{
						b = new Arme(arme.getNom(), arme.getImage());
					}
				}
				if(b == null)
				{
					for(Lieux lieu : Lieux.values())
					{
						if(lieu.getNom() == tabCarte[1])
						{
							b = new Lieu(lieu.getNom(), lieu.getImage());
						}
					}
				}
			}
		}
		// on test la 3ème carte (encore plus de test...)
		if(b != null)
		{
			//si la premirèe carte est une arme
			if(a instanceof Arme)
			{
				//si la deuxième carte est un lieu
				if(b instanceof Lieu)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.getNom() == tabCarte[2])
						{
							c = new Suspect(suspect.getNom(), suspect.getImage());
						}
					}
				}
				//si la deuxième carte est un suspect
				else
				{
					for(Lieux lieu : Lieux.values())
					{
						if(lieu.getNom() == tabCarte[2])
						{
							c = new Lieu(lieu.getNom(), lieu.getImage());
						}
					}
				}
			}
			//si la première carte est un lieu
			if(a instanceof Lieu)
			{
				//si la deuxième carte est une arme
				if(b instanceof Arme)
				{
					for(Suspects suspect : Suspects.values())
					{
						if(suspect.getNom() == tabCarte[2])
						{
							c = new Suspect(suspect.getNom(), suspect.getImage());
						}
					}
				}
				//si la deuxième carte est un suspect
				else
				{
					for(Armes arme : Armes.values())
					{
						if(arme.getNom() == tabCarte[2])
						{
							c = new Arme(arme.getNom(), arme.getImage());
						}
					}
				}
			}
			//si la première carte est un suspect
			if(a instanceof Suspect)
			{
				//si la deuxième carte est une arme
				if(b instanceof Arme)
				{
					for(Lieux lieu : Lieux.values())
					{
						if(lieu.getNom() == tabCarte[2])
						{
							c = new Lieu(lieu.getNom(), lieu.getImage());
						}
					}
				}
				//si la deuxième carte est un lieu
				else
				{
					for(Armes arme : Armes.values())
					{
						if(arme.getNom() == tabCarte[2])
						{
							c = new Arme(arme.getNom(), arme.getImage());
						}
					}
				}
			}
		}
		if(c != null)
		{
			cartes = new Carte[]{a,b,c};
		}
		return cartes;
	}

}
