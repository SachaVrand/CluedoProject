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
				sc.close();
				//Tester si les cartes passées sont correctes
				String[] tmp = {cmdComplete[1],cmdComplete[2],cmdComplete[3],cmdComplete[4]};
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

}
