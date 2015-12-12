<?php
class ActivitesUtilisateur
{
	public $activite;
	public $user;
	
	public function __construct($activite, $user)
	{
		$this->activite = $activite;
		$this->user = $user;
	}
}
?>