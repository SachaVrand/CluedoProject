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
}
?>