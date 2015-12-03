<?php
class Utilisateur{
	
	private $pseudo;
	private $nom;
	private $prenom;
	private $ville;
	private $mail;
	private $permission;
	private $confidentialite;
	private $dateNaissance;
	private $photo;
	
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
	
	public function getPseudo()
	{
		return $this->pseudo;
	}
}

?>