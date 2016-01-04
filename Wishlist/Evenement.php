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
		$req->closeCursor();
		return $tab;
	}
	
	public static function addTypeEvents($connexionBase,$eventName)
	{
		$requete = 'INSERT INTO typeEvenement VALUES(:name)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':name',$eventName);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function getNewId($connexionBase)
	{
		$requete = 'SELECT MAX(idEvenement) as max FROM evenement';
		$res = $connexionBase->getPdo()->query($requete);
		$donnee = $res->fetch();
		$res->closeCursor();
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
		$res->closeCursor();
	}
	
	public static function getNomBdd($connexionBase, $idEvenement)
	{
		$requete = 'SELECT nom FROM evenement WHERE idEvenement = :idEvenement';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idEvenement',$idEvenement);
		$res->execute();
		$donnee = $res->fetch();
		return $donnee['nom'];
	}
	
	public static function getEvent($connexionBase, $idEvenement)
	{
		$requete = "SELECT * FROM evenement WHERE idEvenement = :idEvenement";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idEvenement',$idEvenement,PDO::PARAM_INT);
		$res->execute();
		$donnee = $res->fetch();
		$res->closeCursor();
		$evenement = new Evenement($donnee['idEvenement'], $donnee['nom'], $donnee['dateLimite'], $donnee['commentaire'], $donnee['nomType'], $donnee['idUser']);
		return $evenement;
	}
	
	public static function updateEvent($connexionBase, $evenement)
	{
		$requete = 'UPDATE evenement SET nom = :nom, dateLimite = :dateLimite, commentaire = :commentaire, nomType = :nomType WHERE idEvenement = :idEvenement';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idEvenement',$evenement->idEvenement,PDO::PARAM_INT);
		$res->bindValue(':nom',$evenement->nom,PDO::PARAM_STR);
		$res->bindValue(':dateLimite',$evenement->dateLimite,PDO::PARAM_STR);
		$res->bindValue(':commentaire',$evenement->commentaire,PDO::PARAM_STR);
		$res->bindValue(':nomType',$evenement->nomType,PDO::PARAM_STR);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function getYears($connexionBase,$idUser)
	{
		$requete = 'SELECT DISTINCT year(dateLimite) as annee FROM evenement, listecadeaux WHERE listecadeaux.idEvenement = evenement.idEvenement AND evenement.idUser = :idUser AND listecadeaux.isDelete = 0 ORDER BY year(dateLimite) DESC';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idUser', $idUser);
		$res->execute();
		$tab = array();
		while($ligne = $res->fetch())
		{
			$tab[] = $ligne['annee'];
		}
		$res->closeCursor();
		return $tab;
	}
	
	public static function getIds($connexionBase,$idUser,$annee)
	{
		$requete = 'SELECT listecadeaux.idListe as idListe, evenement.nom as nom FROM evenement, listecadeaux WHERE listecadeaux.idEvenement = evenement.idEvenement AND evenement.idUser = :idUser AND year(dateLimite) = :annee AND listecadeaux.isDelete = 0 ORDER BY evenement.nom';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idUser', $idUser);
		$res->bindValue(':annee', $annee);
		$res->execute();
		$tab = array();
		while($ligne = $res->fetch())
		{
			$id = $ligne['idListe'];
			$tab[$id] = $ligne['nom'];
		}
		$res->closeCursor();
		return $tab;
	}
}

?>