<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	$user = $_SESSION['user'];
	
	if($user->getPermission() != 1)
	{
		exit();
	}
	
	if(isset($_POST['login']) && isset($_POST['password']))
	{
		$userT = Utilisateur::getUserToDisplay($_SESSION['Connexion'], $_POST['login']);
		if(!$userT)
		{
			$error = 'Utilisateur inconnu.';
		}
		else
		{
			Utilisateur::newPassordForUser($_SESSION['Connexion'], $userT, $_POST['password']);
			$notif= 'Changement effectuee';
		}
	}
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Language" content="fr" />
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>Page d'administration</title>
	</head>
	
	<body>
		
		<header id="headerMenu">
			<br />
			<form method="get" action="searchUser.php">
			<table id="menuPrincipal">
				<tr>
					<td><a href="creerEvenementAdmin.php">W.</a></td>
					<td></td>
					<td><input type="text" name="user" id="recherche" placeholder="Cherchez des personnes" size="50"/></td>
					<td><input type="submit" name="ok" value="Ok"></td>
					<td></td>
					<td><a href="creerEvenementAdmin.php">Accueil</a></td>
					<td><a href="pageMessagerie.php">Messagerie</a></td>
					<td><a href="deconnexion.php">Deconnexion</a></td>
				</tr>
			</table>
			</form>
			<br />
		</header>
	
		<div id="titre">
			Fournir un nouveau mot de passe.
		</div>
		
		<table id="principal">
			<tr>
				<td>
					<?php include_once('sousMenuAdmin.php');?>
				</td>
				<td>
					<div id="contentAvecMenu">
						<form method="post" action="fournirMotDePasse.php">
							<table>
								<?php
									if(isset($notif))
									{
										echo "<tr><td><h4 style='color:green;font-family: helvetica;'>$notif</h4></td></tr>";
									}
									else if(isset($error))
									{
										echo "<tr><td><h4 style='color:red;font-family: helvetica;'>$error</h4></td></tr>";
									}
								?>
								<tr><td><input name="login" type="text" placeholder="login de l'utilisateur" required="required"></td><td><input type="password" name="password" placeholder="Nouveau mot de passe" required="required"></td><td><input type="submit" name="submit" value="changer"></td></tr>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>