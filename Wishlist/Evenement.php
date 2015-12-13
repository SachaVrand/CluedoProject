<?php
class Evenement{
	
	private $idEvenement;
	private $nom;
	private $dateLimite;
	private $commentaire;
	private $nomType;
	private $pseudoUtilisateur;
	
	//lambda-_@
	//CREATE USER 'lambda'@'%' IDENTIFIED BY '***';GRANT SELECT, INSERT, UPDATE, DELETE ON *.* TO 'lambda'@'%' IDENTIFIED BY '***' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
	
	public function __construct($idEvenement,$nom,$dateLimite,$commentaire,$nomType,$pseudoUtilisateur)
	{
		$this->idEvenement = $idEvenement;
		$this->nom = $nom;
		$this->dateLimite = $dateLimite;
		$this->commentaire = $commentaire;
		$this->nomType = $nomType;
	}
	
	public function getIdEvenement()
	{
		return $this->idEvenement;
	}
	
	public function getNom()
	{
		return $this->nom;
	}
	
	public function getDateLimite()
	{
		return $this->dateLimite;
	}
	
	public function getCommentaire()
	{
		return $this->commentaire;
	}
	
	public function getNomType()
	{
		return $this->nomType;
	}
	
	public function getPseudoUtilisateur()
	{
		return $this->pseudoUtilisateur;
	}
	
	public static function getTypeEvents($connexionBase)
	{
		$req = $connexionBase->getPdo()->prepare("SELECT * from typeEvenement");
		$req->execute();
		$tab = array();
		while($ligne = $req->fetch())
		{
			$tab[] = $ligne['nomType'];
		}
		return $tab;
	}
	
	public static function addTypeEvents($connexionBase,$eventName)
	{
		$requete = 'INSERT INTO typeEvenement VALUES(:name)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':name',$eventName);
		$res->execute();
	}
}

?>