<?php
class ConnexionBase
{
	protected $pdo, $serveur, $utilisateur, $motDePasse, $dataBase;
	
	public function __construct()
	{
		$this->serveur = 'localhost';
		$this->utilisateur = 'lambda';
		$this->motDePasse = '';
		$this->dataBase = 'wishlist';
		$this->connexionBDD();
	}
	
	protected function connexionBDD()
	{
		$this->pdo = new PDO('mysql:host='.$this->serveur.';dbname='.$this->dataBase, $this->utilisateur, $this->motDePasse);
	}
	
	public function getPdo()
	{
		return $this->pdo;
	}
	
	public function __sleep()
	{
		return ['serveur', 'utilisateur', 'motDePasse', 'dataBase'];
	}
	
	public function __wakeup()
	{
		$this->connexionBDD();
	}
}
?>