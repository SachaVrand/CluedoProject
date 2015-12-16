<?php
class ActivitesListe
{
	public $activite;
	public $idListe;
	public $pseudoUser;
	public $nomEvenement;
	public $idReservePar;
	
	public function __construct($activite, $pseudoUser, $idListe, $nomEvenement, $idReservePar)
	{
		$this->activite = $activite;
		$this->pseudoUser = $pseudoUser;
		$this->idListe = $idListe;
		$this->nomEvenement = $nomEvenement;
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
		$res->closeCursor();
	}
}
?>