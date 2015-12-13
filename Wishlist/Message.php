<?php
class Message
{
	public $id;
	public $entete;
	public $pseudoExp;
	public $message;
	public $dateMessage;
	
	public function __construct($id,$entete,$message,$dateMessage,$pseudoExp)
	{
		$this->id = $id;
		$this->entete = $entete; 
		$this->message = message;
		$this->pseudoExp = $pseudoExp;
		$this->dateMessage = $dateMessage;
	}
	
	public static function getMessages($connexionBase,$user)
	{
		$requete = 'SELECT pseudo, idMessage, entete, message, dateMessage FROM messages, utilisateur WHERE idDestinataire = :id AND utilisateur.idUser = messages.idExpediteur ORDER BY dateMessage DESC';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$messages = array();
		while($donnees = $res->fetch())
		{
			$messages[] = new Message($donnees['id'], $donnees['entete'], $donnees['message'], $donnees['dateMessage'], $donnees['pseudo']);
		}
		return $messages;
	}
}