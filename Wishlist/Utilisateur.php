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
}
?>