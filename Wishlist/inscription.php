<?php
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
			Utilisateur::addUserToDataBase($user, $_SESSION['Connexion'], $motDePasse);
			//TODO changer la page
			header("Location: mapage.php");
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
			<input type="text" name="pseudo" required="required">
			<br>
			<input type="password" name="password" title="password" required="required">
			<br>
			<input type="password" name="cpassword" title="confirm password" required="required">
			<?php
				if(isset($erreur))
				{
					echo "$erreur";
					echo '<br>';
				}
			?>
			<br>
			<input type="text" name="nom" required="required">
			<br>
			<input type="text" name="prenom" required="required">
			<br>
			<input type="text" name="ville" required="required">
			<br>
			<input type="text" name="mail" required="required">
			<br>
			Souhaitez vous que les autres utilisateurs puissent voir vos listes ?
			<input type="radio" name="confidentialite" value="oui" id="oui" checked="checked" /> <label for="oui">Oui</label>
			<input type="radio" name="confidentialite" value="non" id="non" /> <label for="non">Non</label>
			<br>
			<input type="date" name="dateNaissance" required="required">
			<br>
			<input type="text" name="photo">
			<br>
			<input type="submit" name="Inscription" value="Inscription">
		</form>
	</body>
</html>