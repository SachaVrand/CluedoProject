<?php
class Utilisateur{
	
	public $id;
	public $pseudo;
	public $nom;
	public $prenom;
	public $ville;
	public $mail;
	private $permission;
	public $dateNaissance;
	public $photo;
	public $confidentialite;
	
	public function __construct($id,$pseudo,$nom,$prenom,$ville,$mail,$permission,$dateNaissance,$photo,$confidentialite) {
		$this->id = $id;
		$this->dateNaissance = $dateNaissance;
		$this->mail = $mail;
		$this->permission = $permission;
		$this->nom = $nom;
		$this->photo = $photo;
		$this->prenom = $prenom;
		$this->pseudo = $pseudo;
		$this->ville = $ville;
		$this->confidentialite = $confidentialite;
	}
	
	public static function getPseudo($connexionBase,$id)
	{
		$requete = 'SELECT pseudo FROM utilisateur WHERE idUser = :id';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$id,PDO::PARAM_INT);
		$res->execute();
		$donnee = $res->fetch();
		$res->closeCursor();
		return $donnee['pseudo'];
	}
	
	public function getPermission()
	{
		return $this->permission;
	}
	
	public static function getNewId($connexionBase)
	{
		$requete = 'SELECT MAX(idUser) as max FROM utilisateur';
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
	
	public static function getUser($connexionBase,$pseudo,$motDePasse)
	{
		$req = $connexionBase->getPdo()->prepare("SELECT * from utilisateur WHERE pseudo = :pseudo AND motDePasse = :mdp");
		$req->bindValue(':pseudo',$pseudo,PDO::PARAM_STR);
		$req->bindValue(':mdp',$motDePasse,PDO::PARAM_STR);
		$req->execute();
		$donnees = $req->fetch();
		$req->closeCursor();
		if($donnees)
		{
			return new Utilisateur($donnees['idUser'],$donnees['pseudo'], $donnees['nom'], $donnees['prenom'], $donnees['ville'], $donnees['mail'], $donnees['permission'], $donnees['dateNaissance'], $donnees['photo'],$donnees['confidentialite']);
		}
		else
		{
			return null;
		}
	}
	
	public static function getUserToDisplay($connexionBase, $userPseudoOrMail)
	{
		$isEmail = false;
		for($i = 0; $i < strlen($userPseudoOrMail); $i++)
		{
			if($userPseudoOrMail[$i] === '@')
			{
				$isEmail = true;
			}
		}
		if($isEmail)
		{
			$req = $connexionBase->getPdo()->prepare("SELECT * from utilisateur WHERE mail = :userId AND permission = 0");
		}
		else
		{
			$req = $connexionBase->getPdo()->prepare("SELECT * from utilisateur WHERE pseudo = :userId AND permission = 0");
		}
		$req->bindValue(':userId',$userPseudoOrMail,PDO::PARAM_STR);
		$req->execute();
		$donnees = $req->fetch();
		$req->closeCursor();
		if($donnees)
		{
			return new Utilisateur($donnees['idUser'],$donnees['pseudo'], $donnees['nom'], $donnees['prenom'], $donnees['ville'], $donnees['mail'], $donnees['permission'], $donnees['dateNaissance'], $donnees['photo'],$donnees['confidentialite']);
		}
		else
		{
			return null;
		}
	}
	
	public static function addUserToDataBase($connexionBase,$utilisateur,$motDePasse)
	{
		if(Utilisateur::existingLogin($connexionBase, $utilisateur->pseudo) || Utilisateur::existingMail($connexionBase, $utilisateur->mail))
		{
			return 0;
		}
		$requete = 'INSERT INTO utilisateur VALUES (:id,:pseudo,:ville,:dateNaissance,:nom,:prenom,:mail,:photo,:motDePasse,:permission,:confidentialite)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$utilisateur->id,PDO::PARAM_INT);
		$res->bindValue(':pseudo',$utilisateur->pseudo,PDO::PARAM_STR);
		$res->bindValue(':ville',$utilisateur->ville,PDO::PARAM_STR);
		$res->bindValue(':dateNaissance',$utilisateur->dateNaissance,PDO::PARAM_STR);
		$res->bindValue(':nom',$utilisateur->nom,PDO::PARAM_STR);
		$res->bindValue(':prenom',$utilisateur->prenom,PDO::PARAM_STR);
		$res->bindValue(':mail',$utilisateur->mail,PDO::PARAM_STR);
		$res->bindValue(':photo',$utilisateur->photo,PDO::PARAM_STR);
		$res->bindValue(':motDePasse',$motDePasse,PDO::PARAM_STR);
		$res->bindValue(':permission',$utilisateur->getPermission(),PDO::PARAM_INT);
		$res->bindValue(':confidentialite',$utilisateur->confidentialite,PDO::PARAM_INT);
		$res->execute();
		$res->closeCursor();
		return 1;
	}
	
	private static function existingLogin($connexionBase,$pseudo)
	{
		$requete = 'SELECT pseudo FROM utilisateur WHERE pseudo = :pseudo';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':pseudo',$pseudo,PDO::PARAM_STR);
		$res->execute();
		$donnees = $res->fetch();
		$res->closeCursor();
		if($donnees)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	private static function existingMail($connexionBase,$mail)
	{
		$requete = 'SELECT mail FROM utilisateur WHERE mail = :mail';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':mail',$mail,PDO::PARAM_STR);
		$res->execute();
		$donnees = $res->fetch();
		$res->closeCursor();
		if($donnees)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	public static function updateUserInfos($connexionBase,$newUser,$pseudoHasChanged,$mailHasChanged)
	{
		$requete = 'UPDATE utilisateur SET ville = :ville, dateNaissance = :dateNaissance, nom = :nom, prenom = :prenom, photo = :photo, confidentialite = :confidentialite ';
		if($pseudoHasChanged)
		{
			if(Utilisateur::existingLogin($connexionBase, $newUser->pseudo))
			{
				return 0;
			}
			$requete = $requete.',pseudo = :pseudo ';
		}
		if($mailHasChanged)
		{
			if(Utilisateur::existingMail($connexionBase, $newUser->mail))
			{
				return 0;
			}
			$requete = $requete.',mail = :mail ';
		}
		$requete = $requete.'WHERE idUser = :id';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':ville',$newUser->ville,PDO::PARAM_STR);
		$res->bindValue(':dateNaissance',$newUser->dateNaissance,PDO::PARAM_STR);
		$res->bindValue(':nom',$newUser->nom,PDO::PARAM_STR);
		$res->bindValue(':prenom',$newUser->prenom,PDO::PARAM_STR);
		$res->bindValue(':photo',$newUser->photo,PDO::PARAM_STR);
		$res->bindValue(':confidentialite',$newUser->confidentialite,PDO::PARAM_INT);
		
		if($pseudoHasChanged)
		{
			$res->bindValue(':pseudo',$newUser->pseudo,PDO::PARAM_STR);
		}
		if($mailHasChanged)
		{
			$res->bindValue(':mail',$newUser->mail,PDO::PARAM_STR);
		}
		
		$res->bindValue(':id',$newUser->id,PDO::PARAM_INT);
		$res->execute();
		$res->closeCursor();
		return 1;
	}
	
	public static function updateUserPassword($connexionBase,$user,$newPassword)
	{
		$requete = 'UPDATE utilisateur SET motDePasse = :password WHERE idUser = :id';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':password',$newPassword);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function getFollowing($connexionBase,$user)
	{
		$requete = 'SELECT utilisateur.idUser,pseudo FROM utilisateur,suivre WHERE utilisateur.idUser = suivre.following AND suivre.idUser = :id AND restriction = 0';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$tab = array();
		while($donnees = $res->fetch())
		{
			$tab[$donnees['idUser']] = $donnees['pseudo'];
		}
		$res->closeCursor();
		return $tab;
	}
	
	public static function getFollowers($connexionBase,$user)
	{
		$requete = 'SELECT utilisateur.idUser,pseudo FROM utilisateur,suivre WHERE utilisateur.idUser = suivre.idUser AND following = :id AND restriction = 0';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$tab = array();
		while($donnees = $res->fetch())
		{
			$tab[$donnees['idUser']] = $donnees['pseudo'];
		}
		$res->closeCursor();
		return $tab;
	}
	
	public static function deleteFollowing($connexionBase, $user, $following)
	{
		$requete = 'DELETE FROM suivre WHERE idUser = :idUser AND following = :idFollowing';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idUser',$user->id);
		$res->bindValue(':idFollowing',$following);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function restrictFollower($connexionBase, $user, $follower)
	{
		$requete = 'UPDATE suivre SET restriction = 1 WHERE idUser = :idFollower AND following = :idUser';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idUser',$user->id);
		$res->bindValue(':idFollower',$follower);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function isRestrict($connexionBase, $user, $searchUser)
	{
		$requete = 'SELECT restriction FROM suivre WHERE following = :searchUser AND idUser = :idUser';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':searchUser', $searchUser->id);
		$res->bindValue(':idUser', $user->id);
		$res->execute();
		$donnees = $res->fetch();
		$res->closeCursor();
		if(!$donnees || $donnees['restriction'] == 0)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	public static function getFollowingActivity($connexionBase,$user)
	{
		$requete = 'SELECT distinct(activite.idActivite), activite.nomObjet, activite.nomType, activite.dateActivite, utilisateur.pseudo, evenement.nom, listeCadeaux.idListe, activitesListe.idReservePar';
		$requete = $requete.' FROM activitesListe, activite, listeCadeaux, utilisateur, evenement';
		$requete = $requete.' WHERE listeCadeaux.idUser = utilisateur.idUser';
		$requete = $requete.' AND listeCadeaux.idListe = activitesListe.idListe';
		$requete = $requete.' AND listeCadeaux.idEvenement = evenement.idEvenement';
		$requete = $requete.' AND activitesListe.idActivite = activite.idActivite';
		$requete = $requete.' AND activitesListe.idUser IN (SELECT following FROM suivre WHERE idUser = :id AND restriction = 0)';
		$requete = $requete.' AND dateActivite > DATE_SUB(NOW(),INTERVAL 15 DAY) ORDER BY dateActivite DESC';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$tab = array();
		while($donnees = $res->fetch())
		{
			$tab[] = new ActivitesListe(new Activite($donnees['idActivite'], $donnees['nomType'], $donnees['nomObjet']), $donnees['pseudo'], $donnees['idListe'], $donnees['nom'], $donnees['idReservePar']);
		}
		$res->closeCursor();
		return $tab;
	}
	
	public static function getActivities($connexionBase,$user)
	{
		$requete = 'SELECT DISTINCT(activite.idActivite), activite.nomObjet, activite.nomType, activite.dateActivite, utilisateur.pseudo, evenement.nom, listeCadeaux.idListe, activitesListe.idReservePar';
		$requete = $requete.' FROM activitesListe, activite, listeCadeaux, utilisateur, evenement';
		$requete = $requete.' WHERE listeCadeaux.idUser = utilisateur.idUser';
		$requete = $requete.' AND listeCadeaux.idEvenement = evenement.idEvenement';
		$requete = $requete.' AND listeCadeaux.idListe = activitesListe.idListe';
		$requete = $requete.' AND activitesListe.idActivite = activite.idActivite';
		$requete = $requete.' AND listeCadeaux.idUser = :id';
		$requete = $requete.' AND dateActivite > DATE_SUB(NOW(),INTERVAL 15 DAY) ORDER BY dateActivite DESC';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$tab = array();
		while($donnees = $res->fetch())
		{
			$tab[] = new ActivitesListe(new Activite($donnees['idActivite'], $donnees['nomType'], $donnees['nomObjet']), $donnees['pseudo'], $donnees['idListe'], $donnees['nom'], $donnees['idReservePar']);
		}
		$res->closeCursor();
		return $tab;
	}
	
	public static function getFollowingNumber($connexionBase,$user)
	{
		$requete = 'select count(following) as nb from suivre where idUser = :id;';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$donnees = $res->fetch();
		$res->closeCursor();
		if(!$donnees)
			return 0;
		else
			return $donnees['nb'];
	}
	
	public static function getFollowerNumber($connexionBase,$user)
	{
		$requete = 'select count(idUser) as nb from suivre where following = :id;';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$donnees = $res->fetch();
		$res->closeCursor();
		if(!$donnees)
			return 0;
		else
			return $donnees['nb'];
	}
	
	public static function getListNumber($connexionBase,$user)
	{
		$requete = 'select count(distinct idEvenement) as nb from listeCadeaux where idUser = :id;';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$donnees = $res->fetch();
		$res->closeCursor();
		if(!$donnees)
			return 0;
		else
			return $donnees['nb'];
	}
	
	public static function followUser($connexionBase,$user,$follower)
	{
		$requete = 'INSERT INTO suivre VALUES(:idFollower,:idUser,0)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idUser',$user->id);
		$res->bindValue(':idFollower',$follower->id);
		$res->execute();
		$res->closeCursor();
	}
	
	public static function getUserById($connexionBase,$idUser)
	{
		$requete = 'select * from utilisateur where idUser = :id';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$idUser);
		$res->execute();
		$donnees = $res->fetch();
		$res->closeCursor();
		if(!$donnees)
		{
			return null;
		}
		else
		{
			return new Utilisateur($donnees['idUser'],$donnees['pseudo'], $donnees['nom'], $donnees['prenom'], $donnees['ville'], $donnees['mail'], $donnees['permission'], $donnees['dateNaissance'], $donnees['photo'],$donnees['confidentialite']);
		}
	}
	
	public static function getNotificationsReservation($connexionBase,$user)
	{
		$requete = 'SELECT activite.idActivite, activite.nomObjet, activite.nomType, activite.dateActivite, utilisateur.pseudo, evenement.nom, listeCadeaux.idListe, activitesListe.idReservePar';
		$requete = $requete.' FROM activitesListe, activite, listeCadeaux, utilisateur, evenement';
		$requete = $requete.' WHERE listeCadeaux.idUser = utilisateur.idUser';
		$requete = $requete.' AND listeCadeaux.idListe = activitesListe.idListe';
		$requete = $requete.' AND listeCadeaux.idEvenement = evenement.idEvenement';
		$requete = $requete.' AND activitesListe.idActivite = activite.idActivite';
		$requete = $requete.' AND activitesListe.idReservePar IS NOT NULL';
		$requete = $requete.' AND listeCadeaux.idUser IN (SELECT following FROM suivre WHERE idUser = :id AND restriction = 0)';
		$requete = $requete.' AND dateActivite > DATE_SUB(CURDATE(),INTERVAL 15 DAY) ORDER BY dateActivite DESC';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$tab = array();
		while($donnees = $res->fetch())
		{
			$tab[] = new ActivitesListe(new Activite($donnees['idActivite'], $donnees['nomType'], $donnees['nomObjet']), $donnees['pseudo'], $donnees['idListe'], $donnees['nom'], $donnees['idReservePar']);
		}
		$res->closeCursor();
		return $tab;
	}
	
	public static function getNotificationsEvenement($connexionBase,$user)
	{
		$requete = 'SELECT Evenement.nom, pseudo';
		$requete = $requete.' FROM Utilisateur, listeCadeaux, Evenement';
		$requete = $requete.' WHERE listeCadeaux.idUser = utilisateur.idUser';
		$requete = $requete.' AND listeCadeaux.idEvenement = evenement.idEvenement';
		$requete = $requete.' AND listeCadeaux.idUser IN (SELECT following FROM suivre WHERE idUser = :id AND restriction = 0)';
		$requete = $requete.' AND dateLimite = CURDATE()';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':id',$user->id);
		$res->execute();
		$tab = array();
		while($donnees = $res->fetch())
		{
			$tab[] = array($donnees['pseudo'], $donnees['nom']);
		}
		$res->closeCursor();
		return $tab;
	}
	
	public static function isEmailBanned($connexionBase,$email)
	{
		$requete = 'SELECT email FROM emailbannis WHERE email = :email';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':email',$email);
		$res->execute();
		$donnees = $res->fetch();
		$res->closeCursor();
		if(!$donnees)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	public static function newPassordForUser($connexionBase, $user, $newPassword)
	{
		$requete = 'UPDATE utilisateur SET motDePasse = :np WHERE idUser = :id';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':np', $newPassword);
		$res->bindValue(':id', $user->id,PDO::PARAM_INT);
		$res->execute();
		$res->closeCursor();
		
		ini_set('SMTP', 'smtp.orange.fr');
		ini_set('smtp_port',25);
		ini_set('sendmail_from', 'vrand.sacha@gmail.com');
		$mail = $user->mail;
		if (!preg_match("#^[a-z0-9._-]+@(hotmail|live|msn).[a-z]{2,4}$#", $mail))
		{
			$passage_ligne = "\r\n";
		}
		else
		{
			$passage_ligne = "\n";
		}
		$message_txt = "Votre nouveau mot de passe : ".$newPassword;
		$message_html = "<html><head></head><body><b>Votre nouveau mot de passe : </b>".$newPassword."</body></html>";
		$boundary = "-----=".md5(rand());
		$sujet = "Nouveau mot de passe";
		$header = "From: \"vrand.sacha@gmail.com\"<vrand.sacha@gmail.com>".$passage_ligne;
		$header.= "Reply-to: \"vrand.sacha@gmail.com\" <vrand.sacha@gmail.com>".$passage_ligne;
		$header.= "MIME-Version: 1.0".$passage_ligne;
		$header.= "Content-Type: multipart/alternative;".$passage_ligne." boundary=\"$boundary\"".$passage_ligne;
		$message = $passage_ligne."--".$boundary.$passage_ligne;
		$message.= "Content-Type: text/plain; charset=\"ISO-8859-1\"".$passage_ligne;
		$message.= "Content-Transfer-Encoding: 8bit".$passage_ligne;
		$message.= $passage_ligne.$message_txt.$passage_ligne;
		$message.= $passage_ligne."--".$boundary.$passage_ligne;
		$message.= "Content-Type: text/html; charset=\"ISO-8859-1\"".$passage_ligne;
		$message.= "Content-Transfer-Encoding: 8bit".$passage_ligne;
		$message.= $passage_ligne.$message_html.$passage_ligne;
		$message.= $passage_ligne."--".$boundary."--".$passage_ligne;
		$message.= $passage_ligne."--".$boundary."--".$passage_ligne;
		mail($mail,$sujet,$message,$header);
		ini_restore('SMTP');
		ini_restore('smtp_port');
		ini_restore('sendmail_from');
	}
	
	public static function bannirEmail($connexionBase, $email)
	{
		$requete = 'INSERT INTO emailbannis VALUES(:email)';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':email',$email);
		$res->execute();
	}
	
	public static function getPseudoIdListe($connexionBase, $idListe)
	{
		$requete = 'SELECT pseudo FROM utilisateur, listecadeaux WHERE listecadeaux.idUser = utilisateur.idUser AND listecadeaux.idListe = :idListe';
		$res = $connexionBase->getPdo()->prepare($requete);
		$res->bindValue(':idListe',$idListe,PDO::PARAM_INT);
		$res->execute();
		$donnee = $res->fetch();
		$res->closeCursor();
		return $donnee['pseudo'];
	}
}
?>