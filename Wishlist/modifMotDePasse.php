<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	$user = $_SESSION['user'];

	if(isset($_POST['password']) && isset($_POST['cpassword']) && $_POST['opassword'])
	{
		if(Utilisateur::getUser($_SESSION['Connexion'], $user->pseudo, $_POST['opassword']))
		{
			if($_POST['password'] === $_POST['cpassword'])
			{
				Utilisateur::updateUserPassword($_SESSION['Connexion'], $user, $_POST['password']);
				$notif = 'Votre mot de passe a bien ete modifie.';
			}
			else
			{
				$error = 'Les deux mots de passes ne correspondent pas.';
			}
		}
		else
		{
			$errorOldPassword = 'Votre mot de passe est incorrecte;';
		}
	}
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta name="author" content="page de connexion">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>
			Modification du mot de passe
		</title>
	</head>
	<body>
		<?php
			if(isset($notif))
			{
				echo "<h1 style='color:green;'>$notif</h1>";
			}
		?>
		<form method="post" action="modifMotDePasse.php">
			<table>
				<tr><th>Modification du mot de passe</th></tr>
				<tr>
					<td><input type="password" name="opassword" required="required" placeholder="Ancien mot de passe"></td>
					<?php
						if(isset($errorOldPassword))
						{
							echo "<td><h1 style='color:red;'>$errorOldPassword</h1></td>";
						}
					?>
				</tr>
				<tr>
					<td><input name="password" type="password" placeholder="Nouveau mot de passe" required="required"></td>
					<?php
						if(isset($error))
						{
							echo "<td><h1 style='color:red;'>$error</h1></td>";
						}
					?>
				</tr>
				<tr><td><input name="cpassword" type="password" placeholder="Entrer a nouveau" required="required"></td></tr>
				<tr>
					<td><input type="submit" name="submit" value="Modifier"></td>
					<td><input type="button" name="retour" value="retour" onclick="self.location.href='modifInfosPerso.php'"></td>
				</tr>
			</table>
		</form>
	</body>
</html>