<?php
class Contient
{
	public $idListe;
	public $idCadeau;
	public $reservePar;

	public function __construct($idListe,$idCadeau,$reservePar)
	{
		$this->idListe = $idListe;
		$this->idCadeau = $idCadeau;
		$this->reservePar = $reservePar;
	}
	
	public static function getContients($connexionBase,$idListe)
	{
		$requete = "SELECT * FROM contient WHERE idListe = :id";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$idListe,PDO::PARAM_INT);
		$res->execute();
		$tabContients = array();
		while($donnees = $res->fetch())
		{
			$tabContients[] = new Contient($donnees['idListe'], $donnees['idCadeau'], $donnees['reservePar']);
		}
		$res->closeCursor();
		return $tabContients;
	}
	
	public static function delContient($connexionBase,$idCadeau)
	{
		$requete = "DELETE FROM contient WHERE idCadeau = :idCadeau";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idCadeau',$idCadeau,PDO::PARAM_INT);
		$res->execute();
	}
	
	public static function getIdCadeaux($connexionBase,$idListe)
	{
		$requete = "SELECT idCadeau FROM contient WHERE idListe = :idListe";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idListe',$idListe,PDO::PARAM_INT);
		$res->execute();
		$tabIdCadeaux = array();
		while($donnees = $res->fetch())
		{
			$tabIdCadeaux[] = $donnees['idCadeau'];
		}
		$res->closeCursor();
		return $tabIdCadeaux;
	}
	
	public static function reserveCadeau($connexionBase, $idCadeau, $idUser)
	{
		$requete = 'UPDATE contient SET reservePar = :idUser WHERE idCadeau = :idCadeau';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idUser',$idUser,PDO::PARAM_INT);
		$res->bindValue(':idCadeau',$idCadeau,PDO::PARAM_INT);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function dereserveCadeau($connexionBase, $idCadeau)
	{
		$requete = 'UPDATE contient SET reservePar = NULL WHERE idCadeau = :idCadeau';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idCadeau',$idCadeau,PDO::PARAM_INT);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function idUserR($connexionBase, $idCadeau)
	{
		$requete = "SELECT reservePar FROM contient WHERE idCadeau = :idCadeau";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idCadeau',$idCadeau,PDO::PARAM_INT);
		$res->execute();
		$donnee = $res->fetch();
		$idUserR = $donnee['reservePar'];
		$res->closeCursor();
		return $idUserR;
	}
}
?>