<?php
class Activite
{
	
	public $idActivite;
	public $nomType;
	public $nomObjet;
	
	public function __construct($idActivite,$nomType,$nomObjet)
	{
		$this->idActivite = $idActivite;
		$this->nomType = $nomType;
		$this->nomObjet = $nomObjet;
	}
	
	public static function getNewId($connexionBase)
	{
		$requete = 'SELECT MAX(idActivite) as max FROM activite';
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
	
	public static function addActivite($connexionBase,$activite)
	{
		$requete = 'INSERT INTO activite VALUES(:idActivite,:nomType,:nomObjet,:date)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idActivite',$activite->idActivite,PDO::PARAM_INT);
		$res->bindValue(':nomType',$activite->nomType,PDO::PARAM_STR);
		$res->bindValue(':nomObjet',$activite->nomObjet,PDO::PARAM_STR);
		$date = date("Y-m-d H:i:s");
		$res->bindValue(':date',$date,PDO::PARAM_STR);
		$res->execute();
		$res->closeCursor();
	}
}
?>