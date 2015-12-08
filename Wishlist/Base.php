<?php
class Base
{
	private $HoteEtBase;
	private $loginBase;
	private $mdpBase;
	private $bdd;
	
	public function __construct()
	{
		$HoteEtBase = "'mysql:host=localhost;dbname=wishlist;charset=utf8'";
		$loginBase = "lambda";
		$mdpBase = "lambda-_@";
		try 
		{
			$this->bdd = new PDO($HoteEtBase,$loginBase,$mdpBase,array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
		}
		catch(Exception $e)
		{
			die('Erreur : '.$e->getMessage());
		}
	}
	
	public function requetePrepare($requetePrep,array $tabVar)
	{
		if(!is_string($requetePrep)) return NULL;
		$requeteCourante = $this->bdd->prepare($requetePrep);
		$requeteCourante->execute($tabVar);
		return $requeteCourante;
	}
	
	public function toArrayOfArray(PDOStatement $requete)
	{
		$res = array();
		while($donnes = $requete->fetch())
		{
			$res[] = $donnes;
		}
		$requete->closeCursor();
		return res;
	}
}
?>