<?php
class Cadeau
{
	public $idCadeau;
	public $nom;
	public $description;
	public $lien;
	public $nomType;
	
	public function __construct($idCadeau,$nom,$description,$lien,$nomType)
	{
		$this->idCadeau = $idCadeau;
		$this->nom = $nom;
		$this->description = $description;
		$this->lien = $lien;
		$this->nomType = $nomType;
	}
	
	public function setIdCadeau($id)
	{
		$this->idCadeau = $id;
	}
	
	public function getIdCadeau()
	{
		return $this->idCadeau;
	}
	
	public function getNom()
	{
		return $this->nom;
	}
	
	public function getDescription()
	{
		return $this->description;
	}
	
	public function getLien()
	{
		return $this->lien;
	}
	
	public function getNomType()
	{
		return $this->nomType;
	}
	
	public static function getTypeCadeaux($connexionBase)
	{
		$req = $connexionBase->getPdo()->prepare("SELECT * from typeCadeau");
		$req->execute();
		$tab = array();
		while($ligne = $req->fetch())
		{
			$tab[] = $ligne['nomType'];
		}
		$req->closeCursor();
		return $tab;
	}
	
	public static function getNewId($connexionBase)
	{
		$requete = 'SELECT MAX(idCadeau) as max FROM cadeau';
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
	
	public static function addCadeau($connexionBase,$cadeau)
	{
		$requete = 'INSERT INTO cadeau VALUES(:idCadeau, :nom, :description, :lien, :type)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idCadeau',$cadeau->idCadeau);
		$res->bindValue(':nom',$cadeau->nom);
		$res->bindValue(':description',$cadeau->description);
		$res->bindValue(':lien',$cadeau->lien);
		$res->bindValue(':type',$cadeau->nomType);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function getNomBdd($connexionBase,$id)
	{
		$requete = 'SELECT nom FROM cadeau WHERE idCadeau = :id';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$id,PDO::PARAM_INT);
		$res->execute();
		$donnee = $res->fetch();
		$res->closeCursor();
		return $donnee['nom'];
	}
	
	public static function delCadeau($connexionBase,$idCadeau)
	{
		$requete = "DELETE FROM cadeau WHERE idCadeau = :idCadeau";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idCadeau',$idCadeau,PDO::PARAM_INT);
		$res->execute();
	}
	
	public static function getCadeau($connexionBase, $idCadeau)
	{
		$requete = "SELECT * FROM cadeau WHERE idCadeau = :idCadeau";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idCadeau',$idCadeau,PDO::PARAM_INT);
		$res->execute();
		$donnee = $res->fetch();
		$res->closeCursor();
		$cadeau = new Cadeau($donnee['idCadeau'], $donnee['nom'], $donnee['description'], $donnee['lien'], $donnee['nomType']);
		return $cadeau;
	}
	
	public static function getListeVogues($connexionBase)
	{
		$req = "SELECT nomType,count(nomType) as somme FROM Cadeau, Contient WHERE Cadeau.idCadeau = Contient.idCadeau GROUP BY nomType ORDER BY somme DESC";
		$res = $connexionBase->getPdo()->query($req);
		$tab = array();
		while($donnees = $res->fetch())
		{
			$tab[] = $donnees['nomType'];
		}
		$res->closeCusor();
		return $tab;
	}
}

?>