<?php
class Utilisateur{
	
	public $id;
	public $pseudo;
	public $nom;
	public $prenom;
	public $ville;
	public $mail;
	private $permission;
	public $dateNaissance;
	public $photo;
	
	public function __construct($id,$pseudo,$nom,$prenom,$ville,$mail,$permission,$dateNaissance,$photo) {
		$this->id = $id;
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
			return new Utilisateur($donnees['idUser'],$donnees['pseudo'], $donnees['nom'], $donnees['prenom'], $donnees['ville'], $donnees['mail'], $donnees['permission'], $donnees['dateNaissance'], $donnees['photo']);
		}
		else
		{
			return null;
		}
	}
	
	public static function addUserToDataBase($connexionBase,$utilisateur,$motDePasse)
	{
		if(Utilisateur::existingLogin($connexionBase, $utilisateur->pseudo) || Utilisateur::existingMail($connexionBase, $utilisateur->mail))
		{
			return 0;
		}
		$requete = 'INSERT INTO utilisateur VALUES (:id,:pseudo,:ville,:dateNaissance,:nom,:prenom,:mail,:photo,:motDePasse,:permission)';
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
	
	private static function existingMail($connexionBase,$mail)
	{
		$requete = 'SELECT mail FROM utilisateur WHERE mail = :mail';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':mail',$mail,PDO::PARAM_STR);
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
	
	public static function updateUserInfos($connexionBase,$newUser,$pseudoHasChanged,$mailHasChanged)
	{
		$requete = 'UPDATE utilisateur SET ville = :ville, dateNaissance = :dateNaissance, nom = :nom, prenom = :prenom, photo = :photo ';
		if($pseudoHasChanged)
		{
			if(Utilisateur::existingLogin($connexionBase, $utilisateur->pseudo))
			{
				return 0;
			}
			$requete = $requete.',pseudo = :pseudo ';
		}
		if($mailHasChanged)
		{
			if(Utilisateur::existingMail($connexionBase, $utilisateur->mail))
			{
				return 0;
			}
			$requete = $requete.',mail = :mail ';
		}
		$requete = $requete.'WHERE idUser = :id';
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
		if($mailHasChanged)
		{
			$res->bindValue(':mail',$utilisateur->mail,PDO::PARAM_STR);
		}
		$res->bindValue(':photo',$utilisateur->photo,PDO::PARAM_STR);
		$res->execute();
		return 1;
	}
	
	public static function updateUserPassword($connexionBase,$user,$newPassword)
	{
		$requete = 'UPDATE utilisateur SET motDePasse = :password WHERE id = :id';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':password',$newPassword);
		$res->execute();
	}
	
	public static function getFollowing($connexionBase,$user)
	{
		$requete = 'SELECT utilisateur.idUser,pseudo FROM utilisateur,suivre WHERE utilisateur.idUser = suivre.following AND idUser = :id';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$tab = array();
		while($donnees = $res->fetch())
		{
			$tab[$donnees['idUser']] = $donnees['pseudo'];
		}
		return $tab;
	}
	
	public static function getFollowers($connexionBase,$user)
	{
		$requete = 'SELECT utilisateur.idUser,pseudo FROM utilisateur,suivre WHERE utilisateur.idUser = suivre.idUser AND following = :id AND restriction = 0';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$tab = array();
		while($donnees = $res->fetch())
		{
			$tab[$donnees['idUser']] = $donnees['pseudo'];
		}
		return $tab;
	}
	
	public static function deleteFollowing($connexionBase, $user, $following)
	{
		$requete = 'DELETE FROM suivre WHERE idUser = :idUser AND following = :idFollowing';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idUser',$user->id);
		$res->bindValue(':idFollowing',$following);
		$res->execute();
	}
	
	public static function restrictFollower($connexionBase, $user, $follower)
	{
		$requete = 'UPDATE suivre SET restriction = 1 WHERE idUser = :idFollower AND following = :idUser';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idUser',$user->id);
		$res->bindValue(':idFollower',$follower);
		$res->execute();
	}
}
?>