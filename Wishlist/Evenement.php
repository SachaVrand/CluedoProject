<?php
class Evenement{
	
	private $idEvenement;
	private $nom;
	private $dateLimite;
	private $commentaire;
	private $nomType;
	private $idUtilisateur;
	
	//lambda-_@
	//CREATE USER 'lambda'@'%' IDENTIFIED BY '***';GRANT SELECT, INSERT, UPDATE, DELETE ON *.* TO 'lambda'@'%' IDENTIFIED BY '***' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
	
	public function __construct($idEvenement,$nom,$dateLimite,$commentaire,$nomType,$idUtilisateur)
	{
		$this->idEvenement = $idEvenement;
		$this->nom = $nom;
		$this->dateLimite = $dateLimite;
		$this->commentaire = $commentaire;
		$this->nomType = $nomType;
		$this->idUtilisateur = $idUtilisateur;
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
	
	public function getIdUtilisateur()
	{
		return $this->idUtilisateur;
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
	
	public static function getNewId($connexionBase)
	{
		$requete = 'SELECT MAX(idEvenement) as max FROM evenement';
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
	
	public static function addEvent($connexionBase,$evenement)
	{
		$requete = 'INSERT INTO evenement VALUES(:idEvent,:nom,:date,:commentaire,:type,:idUser)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idEvent',$evenement->idEvenement,PDO::PARAM_INT);
		$res->bindValue(':nom',$evenement->nom,PDO::PARAM_STR);
		$res->bindValue(':date',$evenement->dateLimite,PDO::PARAM_STR);
		$res->bindValue(':commentaire',$evenement->commentaire,PDO::PARAM_STR);
		$res->bindValue(':type',$evenement->nomType,PDO::PARAM_STR);
		$res->bindValue(':idUser',$evenement->idUtilisateur,PDO::PARAM_INT);
		$res->execute();
	}
}

?>