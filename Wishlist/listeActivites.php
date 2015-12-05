<?php
class listeActivite
{
	private $reservePar;
	private $createur;
	private $listeCadeaux;
	private $evenement;
	
	public function __construct(Utilisateur $reservePar, Utilisateur $createur, array $listeCadeaux, Evenement $evenement)
	{
		$this->createur = $createur;
		$this->reservePar = $reservePar;
		$this->evenement = $evenement;
		$this->listeCadeaux = $listeCadeaux;
	}
	
}