<?php
class Contient
{
	public $idListe;
	public $idCadeau;
	public $reservePar;

	public function __construct($idListe,$idCadeau,$reservePar)
	{
		$this->idListe = $idListe;
		$this->idCadeau = $idCadeau;
		$this->reservePar = $reservePar;
	}
	
	public static function getContients($connexionBase,$idListe)
	{
		$requete = "SELECT * FROM contient WHERE idListe = :id";
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$idListe,PDO::PARAM_INT);
		$res->execute();
		$tabContients = array();
		while($donnees = $res->fetch())
		{
			$tabContients[] = new Contient($donnees['idListe'], $donnees['idCadeau'], $donnees['reservePar']);
		}
		$res->closeCursor();
		return $tabContients;
	}
}
?>