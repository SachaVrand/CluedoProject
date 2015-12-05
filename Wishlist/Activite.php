<?php
class Activite
{
	
	private $idActivite;
	private $nomType;
	private $nomObjet;
	private $nomCadeau;
	private $nomEvenement;
	private $nomProprietaire;
	private $connexion;
	
	public function __construct($idActivite,$nomType,$nomObjet,$nomCadeau,$nomEvement,$nomProprietaire,$connexion)
	{
		$this->idActivite = $idActivite;
		$this->nomType = $nomType;
		$this->nomObjet = $nomObjet;
		$this->nomCadeau = $nomCadeau;
		$this->nomEvenement = $nomEvement;
		$this->nomProprietaire = $nomProprietaire;
		$this->connexion = $connexion;
	}
	
	
	
	
}
?>