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
	
	public static function getUser($base,$pseudo,$motDePasse)
	{
		$requete = "SELECT * from utilisateur WHERE pseudo = :pseudo AND motDePasse = :mdp";
		$req = $base->requetePrepare($requete,array('pseudo' => $pseudo,'mdp' => $motDePasse));
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
	
	public static function addUserToDataBase($utilisateur,$base,$motDePasse)
	{
		$requete = "INSERT INTO utilisateur VALUES (:pseudo,:ville,:dateNaissance,:nom,:prenom,:mail,:photo,:motDePasse,:permission,:confidentialite)";
		$base->requetePrepare($requete,array('pseudo' => $utilisateur->pseudo,'ville' => $utilisateur->ville,'dateNaissance' => $utilisateur->dateNaissance, 'nom' => $utilisateur->nom,'prenom' => $utilisateur->prenom, 'mail' => $utilisateur->mail, 'photo' => $utilisateur->photo,'motDePasse' => $motDePasse,'permission' => $utilisateur->getPermission(),'confidentialite' => $utilisateur->confidentialite));
		
	} 
	
}
?>