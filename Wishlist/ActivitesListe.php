<?php
class ActivitesListe
{
	public $idActivite;
	public $idListe;
	public $idUser;
	public $idReservePar;
	
	public function __construct($idActivite, $idUser, $idListe, $idReservePar)
	{
		$this->idActivite = $idActivite;
		$this->idUser = $idUser;
		$this->idListe = $idListe;
		$this->idReservePar = $idReservePar;
	}
	
	public static function addActivitesListe($connexionBase,$activitesListe)
	{
		$requete = 'INSERT INTO activitesliste (idActivite,idUser,idListe) VALUES(:idActivite,:idUser,:idListe)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idActivite',$activitesListe->idActivite,PDO::PARAM_INT);
		$res->bindValue(':idUser',$activitesListe->idUser,PDO::PARAM_INT);
		$res->bindValue(':idListe',$activitesListe->idListe,PDO::PARAM_INT);
		$res->execute();
	}
}
?>