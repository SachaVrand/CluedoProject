<?php
class Activite
{
	
	public $idActivite;
	public $nomType;
	public $nomObjet;
	public $nomCadeau;
	public $nomEvenement;
	
	public function __construct($idActivite,$nomType,$nomObjet,$nomCadeau,$nomEvement)
	{
		$this->idActivite = $idActivite;
		$this->nomType = $nomType;
		$this->nomObjet = $nomObjet;
		$this->nomCadeau = $nomCadeau;
		$this->nomEvenement = $nomEvement;
	}
	
}
?>