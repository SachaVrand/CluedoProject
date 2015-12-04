<?php
class Cadeau
{
	private $idCadeau;
	private $nom;
	private $description;
	private $lien;
	private $nomType;
	
	public function __construct($idCadeau,$nom,$description,$lien,$nomType)
	{
		$this->idCadeau = $idCadeau;
		$this->nom = $nom;
		$this->description = $description;
		$this->lien = $lien;
		$this->nomType = $nomType;
	}
	
	public function getIdCadeau()
	{
		return $this->idCadeau;
	}
	
	public function getNom()
	{
		return $this->nom;
	}
	
	public function getDescription()
	{
		return $this->description;
	}
	
	public function getLien()
	{
		return $this->lien;
	}
	
	public function getNomType()
	{
		return $this->nomType;
	}
}

?>