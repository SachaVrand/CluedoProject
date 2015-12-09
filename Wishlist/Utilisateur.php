<?php
class Utilisateur{
	
	public $id;
	public $pseudo;
	public $nom;
	public $prenom;
	public $ville;
	public $mail;
	private $permission;
	public $confidentialite;
	public $dateNaissance;
	public $photo;
	
	public function __construct($id,$pseudo,$nom,$prenom,$ville,$mail,$permission,$confidentialite,$dateNaissance,$photo) {
		$this->id = $id;
		$this->confidentialite = $confidentialite;
		$this->dateNaissance = $dateNaissance;
		$this->mail = $mail;
		$this->permission = $permission;
		$this->nom = $nom;
		$this->photo = $photo;
		$this->prenom = $prenom;
		$this->pseudo = $pseudo;
		$this->ville = $ville;
	}
	
	public function getPermission()
	{
		return $this->permission;
	}
	
	public static function getNewId($connexionBase)
	{
		$requete = 'SELECT MAX(idUser) as max FROM utilisateur';
		$res = $connexionBase->getPdo()->query($requete);
		$donnee = $res->fetch();
		if(!$donnee)
		{
			return 1;
		}
		else
		{
			return ($donnee['max'] + 1);
		}
	}
	
	public static function getUser($connexionBase,$pseudo,$motDePasse)
	{
		$req = $connexionBase->getPdo()->prepare("SELECT * from utilisateur WHERE pseudo = :pseudo AND motDePasse = :mdp");
		$req->bindValue(':pseudo',$pseudo,PDO::PARAM_STR);
		$req->bindValue(':mdp',$motDePasse,PDO::PARAM_STR);
		$req->execute();
		$donnees = $req->fetch();
		if($donnees)
		{
			return new Utilisateur($donnees['idUser'],$donnees['pseudo'], $donnees['nom'], $donnees['prenom'], $donnees['ville'], $donnees['mail'], $donnees['permission'], $donnees['confidentialite'], $donnees['dateNaissance'], $donnees['photo']);
		}
		else
		{
			return null;
		}
	}
	
	public static function addUserToDataBase($connexionBase,$utilisateur,$motDePasse)
	{
		if(Utilisateur::existingLogin($connexionBase, $utilisateur->pseudo))
		{
			return 0;
		}
		$requete = 'INSERT INTO utilisateur VALUES (:id,:pseudo,:ville,:dateNaissance,:nom,:prenom,:mail,:photo,:motDePasse,:permission,:confidentialite)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$utilisateur->id,PDO::PARAM_INT);
		$res->bindValue(':pseudo',$utilisateur->pseudo,PDO::PARAM_STR);
		$res->bindValue(':ville',$utilisateur->ville,PDO::PARAM_STR);
		$res->bindValue(':dateNaissance',$utilisateur->dateNaissance,PDO::PARAM_STR);
		$res->bindValue(':nom',$utilisateur->nom,PDO::PARAM_STR);
		$res->bindValue(':prenom',$utilisateur->prenom,PDO::PARAM_STR);
		$res->bindValue(':mail',$utilisateur->mail,PDO::PARAM_STR);
		$res->bindValue(':photo',$utilisateur->photo,PDO::PARAM_STR);
		$res->bindValue(':motDePasse',$motDePasse,PDO::PARAM_STR);
		$res->bindValue(':permission',$utilisateur->getPermission(),PDO::PARAM_INT);
		$res->bindValue(':confidentialite',$utilisateur->confidentialite,PDO::PARAM_INT);
		$res->execute();
		return 1;
	}
	
	private static function existingLogin($connexionBase,$pseudo)
	{
		$requete = 'SELECT pseudo FROM utilisateur WHERE pseudo = :pseudo';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':pseudo',$pseudo,PDO::PARAM_STR);
		$res->execute();
		$donnees = $res->fetch();
		if($donnees)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	public static function updateUserInfos($connexionBase,$newUser,$pseudoHasChanged)
	{
		$requete = '';
		if($pseudoHasChanged)
		{
			if(Utilisateur::existingLogin($connexionBase, $utilisateur->pseudo))
			{
				return 0;
			}
			$requete = 'UPDATE utilisateur SET pseudo = :pseudo, ville = :ville, dateNaissance = :dateNaissance, nom = :nom, prenom = :prenom, mail = :mail, photo = :photo, confidentialite = :confidentialite WHERE idUser = :id';
		}
		else
		{
			$requete = 'UPDATE utilisateur SET ville = :ville, dateNaissance = :dateNaissance, nom = :nom, prenom = :prenom, mail = :mail, photo = :photo, confidentialite = :confidentialite WHERE idUser = :id';
		}
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$utilisateur->id,PDO::PARAM_INT);
		if($pseudoHasChanged)
		{
			$res->bindValue(':pseudo',$utilisateur->pseudo,PDO::PARAM_STR);
		}
		$res->bindValue(':ville',$utilisateur->ville,PDO::PARAM_STR);
		$res->bindValue(':dateNaissance',$utilisateur->dateNaissance,PDO::PARAM_STR);
		$res->bindValue(':nom',$utilisateur->nom,PDO::PARAM_STR);
		$res->bindValue(':prenom',$utilisateur->prenom,PDO::PARAM_STR);
		$res->bindValue(':mail',$utilisateur->mail,PDO::PARAM_STR);
		$res->bindValue(':photo',$utilisateur->photo,PDO::PARAM_STR);
		$res->bindValue(':confidentialite',$utilisateur->confidentialite,PDO::PARAM_INT);
		$res->execute();
		return 1;
	}
}
?>