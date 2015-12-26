<?php
class ListeCadeaux
{
	private $idListe;
	private $idCadeau;
	private $idEvenement;
	private $idUser;
	private $reserverPar;
	
	public function __construct($idListe,$idCadeau,$idEvenement,$idUser,$reserverPar)
	{
		$this->idListe = $idListe;
		$this->idCadeau = $idCadeau;
		$this->idEvenement = $idEvenement;
		$this->idUser = $idUser;
		$this->reserverPar = $reserverPar;
	}
	
	
	public static function getNewId($connexionBase)
	{
		$requete = 'SELECT MAX(idListe) as max FROM listecadeaux';
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
	
	public static function addListeCadeaux($connexionBase,$listeCadeaux)
	{
		$requete = 'INSERT INTO listecadeaux(idListe,idEvenement,idUser) VALUES(:idListe,:idEvenement,:idUser)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idListe',$listeCadeaux->idListe,PDO::PARAM_INT);
		$res->bindValue(':idEvenement',$listeCadeaux->idEvenement,PDO::PARAM_INT);
		$res->bindValue(':idUser',$listeCadeaux->idUser,PDO::PARAM_INT);
		$res->execute();
	}
	
	public static function addContient($connexionBase,$idListe,$idCadeau)
	{
		$requete = 'INSERT INTO contient(idListe,idCadeau) VALUES(:idListe,:idCadeau)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idListe',$idListe,PDO::PARAM_INT);
		$res->bindValue(':idCadeau',$idCadeau,PDO::PARAM_INT);
		$res->execute();
	}
}


?>