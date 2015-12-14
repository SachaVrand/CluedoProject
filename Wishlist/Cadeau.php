<?php
class Cadeau
{
	private $idCadeau;
	private $nom;
	private $description;
	private $lien;
	private $nomType;
	
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
		return $tab;
	}
	
	public static function getNewId($connexionBase)
	{
		$requete = 'SELECT MAX(idCadeau) as max FROM cadeau';
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
	
	public static function addCadeau($connexionBase,$cadeau)
	{
		$requete = 'INSERT INTO cadeau VALUES(:idCadeau,:nom,:description,:lien,:type)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idCadeau',$cadeau->idCadeau,PDO::PARAM_INT);
		$res->bindValue(':nom',$cadeau->nom,PDO::PARAM_STR);
		$res->bindValue(':description',$cadeau->description,PDO::PARAM_STR);
		$res->bindValue(':lien',$cadeau->lien,PDO::PARAM_STR);
		$res->bindValue(':type',$cadeau->nomType,PDO::PARAM_STR);
		$res->execute();
	}
}

?>