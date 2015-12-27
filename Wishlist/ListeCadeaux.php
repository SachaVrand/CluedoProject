<?php
class ListeCadeaux
{
	private $idListe;
	private $idEvenement;
	private $idUser;
	
	public function __construct($idListe,$idEvenement,$idUser)
	{
		$this->idListe = $idListe;
		$this->idEvenement = $idEvenement;
		$this->idUser = $idUser;
	}
	
	public function getId()
	{
		return $this->idListe;
	}
	
	public function getIdEvent()
	{
		return $this->idEvenement;
	}
	
	public static function getListesCadeaux($connexionBase, $user)
	{
		$requete = "SELECT * FROM listecadeaux WHERE idUser = :id";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id,PDO::PARAM_INT);
		$res->execute();
		$listesCadeaux = array();
		while($donnees = $res->fetch())
		{
			$listesCadeaux[] = new ListeCadeaux($donnees['idListe'], $donnees['idEvenement'], $donnees['idUser']);
		}
		$res->closeCursor();
		return $listesCadeaux;
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