<?php
class ListeActivites
{
	private $idActivites;
	private $nomType;
	private $nomObjet;
	private $nomCadeau;
	private $nomEvenement;
	private $nomProprietaire;
	
	
	public function __construct($idActivites,$nomType,$nomObjet,$nomCadeau,$nomEvenement,$nomProprietaire)
	{
		$this->idActivites = $idActivites;
		$this->nomType = $nomType;
		$this->nomObjet = $nomObjet;
		$this->nomCadeau = $nomCadeau;
		$this->nomEvenement = $nomEvenement;
		$this->nomProprietaire = $nomProprietaire;
	}
}