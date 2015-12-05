<?php

	if(isset($_POST['login']) && $_POST['password'])
	{
		$user = Utilisateur::getUser(new Base(), $_POST['login'], $_POST['password']);
		if(!$user)
		{
			$erreur = 'Mot de passe ou identifiant incorrect';
		}
		else
		{
			//faire redirection
		}
	}
	else
	{
		$erreur = 'Veuillez remplir tous les champs';
	}
			
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta name="author" content="page de connexion">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>
		</title>
	</head>
	<body>
		<form action="connexion.php" method="post">
			<label>Connexion</label>
			<input type="text" name="login">
			<input type="password" name="password">
			<?php
				if(isset($erreur))
				{
					echo "$erreur";
				}
			?>
		</form>
		<a href="inscription.php">S'inscrire</a>
	</body>
</html>
