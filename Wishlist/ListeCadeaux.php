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
		$requete = "SELECT * FROM listecadeaux WHERE idUser = :id AND isDelete = 0";
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
		$requete = 'INSERT INTO listecadeaux VALUES(:idListe,:idEvenement,:idUser,:isDelete)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idListe',$listeCadeaux->idListe,PDO::PARAM_INT);
		$res->bindValue(':idEvenement',$listeCadeaux->idEvenement,PDO::PARAM_INT);
		$res->bindValue(':idUser',$listeCadeaux->idUser,PDO::PARAM_INT);
		$res->bindValue(':isDelete',0,PDO::PARAM_INT);
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
	
	public static function delListe($connexionBase,$idListe)
	{
		$requete = 'UPDATE listeCadeaux SET isDelete = 1 WHERE idListe = :idListe';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idListe',$idListe,PDO::PARAM_INT);
		$res->execute();
	}
	
	public static function getIdEvenementBdd($connexionBase,$idListe)
	{
		$requete = "SELECT idEvenement FROM listeCadeaux WHERE idListe = :idListe";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idListe',$idListe,PDO::PARAM_INT);
		$res->execute();
		$donnee = $res->fetch();
		$res->closeCursor();
		return $donnee['idEvenement'];
	}
	
	public static function getExampleList($connexionBase, $type)
	{
		$req = "SELECT Evenement.nom, ListeCadeaux.idListe ";
		$req = $req."FROM Cadeau,ListeCadeaux,Evenement,Contient ";
		$req = $req."WHERE Evenement.idEvenement = ListeCadeaux.idEvenement ";
		$req = $req."AND ListeCadeaux.idListe = Contient.idListe ";
		$req = $req."AND Cadeau.idCadeau = Contient.idCadeau ";
		$req = $req."AND Cadeau.nomType = :type ";
		$res = $connexionBase->getPdo()->prepare($req);
		$res->bindValue(':type',$type);
		$res->execute();
		$donnee = $res->fetch();
		if(!$donnee)
			return NULL;
		$ret = array();
		$ret[] = $donnee['idListe'];
		$ret[] = $donnee['nom'];
		$res->closeCursor();
		return $ret;
	}
	
	public static function existingListe($connexionBase,$idListe)
	{
		$requete = 'SELECT idListe FROM listecadeaux WHERE idListe = :idListe';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idListe',$idListe,PDO::PARAM_INT);
		$res->execute();
		$donnees = $res->fetch();
		$res->closeCursor();
		if($donnees)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}


?>