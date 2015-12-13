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
	
}
?>