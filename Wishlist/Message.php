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
		$this->message = $message;
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
			$messages[] = new Message($donnees['idMessage'], $donnees['entete'], $donnees['message'], $donnees['dateMessage'], $donnees['pseudo']);
		}
		$res->closeCursor();
		return $messages;
	}
	
	public static function newMessage($connexionBase,$entete,$message,$idDest,$idExp)
	{
		$requete = 'INSERT INTO messages VALUES(:id, :entete, :msg, NOW(), :idDest, :idExp)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',Message::getNewId($connexionBase));
		$res->bindValue(':entete',$entete);
		$res->bindValue(':msg',$message);
		$res->bindValue(':idDest',$idDest);
		$res->bindValue(':idExp',$idExp);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function newPasswordRequest($connexionBase,$user)
	{
		$entete = 'Demande de mot de passe';
		$message = "L'utilisateur $user->pseudo a perdu son mot de passe.";
		$requete = 'INSERT INTO messages VALUES(:id, :entete, :msg, NOW(), 1, :idUser)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',Message::getNewId($connexionBase));
		$res->bindValue(':entete',$entete);
		$res->bindValue(':msg',$message);
		$res->bindValue(':idUser',$user->id);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function getNewId($connexionBase)
	{
		$requete = 'SELECT MAX(idMessage) as max FROM messages';
		$res = $connexionBase->getPdo()->query($requete);
		$donnee = $res->fetch();
		$res->closeCursor();
		if(!$donnee)
		{
			return 1;
		}
		else
		{
			return ($donnee['max'] + 1);
		}
	}
	
	public static function deleteMessage($connexionBase,$id)
	{
		$requete = 'DELETE FROM messages WHERE idMessage = :id';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$id);
		$res->execute();
		$res->closeCursor();
	}
}