<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	if(!isset($_SESSION['Connexion']))
	{
		$db = new ConnexionBase();
		$_SESSION['Connexion'] = $db;
	}
	
	if(isset($_POST['login']) && $_POST['password'])
	{
		$user = Utilisateur::getUser($_SESSION['Connexion'], $_POST['login'], $_POST['password']);
		if($user == null)
		{
			$erreur = 'Mot de passe ou identifiant incorrect';
		}
		else
		{
			$_SESSION['user'] = $user;
			//TODO changer la page
			//header("Location: mapage.php");
			//exit();
			echo "test ok !!!!!";
		}
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
			<br><br>
			Login : 
			<br>
			<input type="text" name="login" required="required">
			<br>
			Mot de passe : 
			<br>
			<input type="password" name="password" required="required">
			<br><br>
			<input type="submit" name="Connexion" value="Connexion">
			<br>
			<?php
				if(isset($erreur))
				{
					echo "$erreur";
				}
			?>
		</form>
		<br>
		<a href="inscription.php">S'inscrire</a>
	</body>
</html>
