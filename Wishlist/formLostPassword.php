<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	if(isset($_POST['login']))
	{
		$user = Utilisateur::getUserToDisplay($_SESSION['Connexion'], $_POST['login']);
		if(!$user)
		{
			$error = "Ce compte n'existe pas.";
		}
		else
		{
			Message::newPasswordRequest($_SESSION['Connexion'], $user);
			$notif = "Un requete a ete envoye a l'administrateur, vous recevrez un nouveau mot de passe sur votre adresse mail.";
		}
	}
?>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta name="author" content="page de connexion">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="Stylesheet" type="text/css" href="pageConnexion.css" />
		<title>
		</title>
	</head>
	<body>
		<header class="headerConnexion">
			<!-- Connexion -->
			<div class="divCo">
				
			</div>
		</header>
		<div class="sectionInscription">
			<form method="post" action="formLostPassword.php">
				<table>
					<?php
						if(isset($error))
						{
							echo "<tr><td><h4 style='color:red;font-family: helvetica;'>$error</td></tr>";
						}
						else if(isset($notif))
						{
							echo "<tr><td><h4 style='color:green;font-family: helvetica;'>$notif</td></tr>";
						}
					?>
					<tr><th align="left">Requete</th></tr>
					<tr><td><input type="text" name="login" required="required" placeholder="Login"><input type="submit" name="submit" value="ok"></td></tr>
					<tr><td><a href="pageConnexion.php">Retour</a></td></tr>
				</table>
			</form>
		</div>
	</body>
</html>