<?php
class Utilisateur{
	
	public $pseudo;
	public $nom;
	public $prenom;
	public $ville;
	public $mail;
	private $permission;
	public $confidentialite;
	public $dateNaissance;
	public $photo;
	
	public function __construct($pseudo,$nom,$prenom,$ville,$mail,$permission,$confidentialite,$dateNaissance,$photo) {
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
	
	public static function getUser($connexionBase,$pseudo,$motDePasse)
	{
		$req = $connexionBase->getPdo()->prepare("SELECT * from utilisateur WHERE pseudo = :pseudo AND motDePasse = :mdp");
		$req->bindValue(':pseudo',$pseudo,PDO::PARAM_STR);
		$req->bindValue(':mdp',$motDePasse,PDO::PARAM_STR);
		$req->execute();
		$donnees = $req->fetch();
		if($donnees)
		{
			return new Utilisateur($donnees['pseudo'], $donnees['nom'], $donnees['prenom'], $donnees['ville'], $donnees['mail'], $donnees['permission'], $donnees['confidentialite'], $donnees['dateNaissance'], $donnees['photo']);
		}
		else
		{
			return null;
		}
	}
	
	public static function addUserToDataBase($connexionBase,$utilisateur,$motDePasse)
	{
		$requete = 'INSERT INTO utilisateur VALUES (:pseudo,:ville,:dateNaissance,:nom,:prenom,:mail,:photo,:motDePasse,:permission,:confidentialite)';
		$res = $connexionBase->getPdo()->prepare($requete);
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
	} 
	
}
?>