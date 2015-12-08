<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	if(isset($_POST['pseudo']))
	{
		//ajouter user
		if($_POST['password'] === $_POST['cpassword'])
		{
			$motDePasse = $_POST['password'];
			if(isset($_POST['photo']))
			{
				$photo = $_POST['photo'];
			}
			else
			{
				$photo = 'urlImageDeBase';
			}
			$user = new Utilisateur($_POST['pseudo'], $_POST['nom'], $_POST['prenom'], $_POST['ville'], $_POST['mail'],0, $_POST['confidentialite'], $_POST['dateNaissance'], $photo);
			Utilisateur::addUserToDataBase($_SESSION['Connexion'], $user, $motDePasse);
			//TODO changer la page
			header("Location: connexion.php");
			exit();
		}
		else
		{
			$erreur = 'Les deux mots de passe ne correspondent pas';
		}
	}
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta name="author" content="page de connexion">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>
			Inscription
		</title>
	</head>
	<body>
		<form method="post" action="inscription.php">
			Pseudo : 
			<input type="text" name="pseudo" required="required">
			<br>
			Mot de passe : 
			<input type="password" name="password" title="password" required="required">
			<br>
			Entrer a nouveau : 
			<input type="password" name="cpassword" title="confirm password" required="required">
			<?php
				if(isset($erreur))
				{
					echo "$erreur";
					echo '<br>';
				}
			?>
			<br>
			Nom : 
			<input type="text" name="nom" required="required">
			<br>
			Prenom : 
			<input type="text" name="prenom" required="required">
			<br>
			Ville : 
			<input type="text" name="ville" required="required">
			<br>
			Mail : 
			<input type="text" name="mail" required="required">
			<br>
			Souhaitez vous que les autres utilisateurs puissent voir vos listes ?
			<input type="radio" name="confidentialite" value="1" id="oui" checked="checked" /> <label for="oui">Oui</label>
			<input type="radio" name="confidentialite" value="0" id="non" /> <label for="non">Non</label>
			<br>
			Date de naissance : 
			<input type="datetime" name="dateNaissance" required="required">
			<br>
			URL photo :
			<input type="text" name="photo">
			<br>
			<br>
			<input type="submit" name="Inscription" value="Inscription">
		</form>
	</body>
</html>