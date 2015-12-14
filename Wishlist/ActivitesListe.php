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
}
?>