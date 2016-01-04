<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	if(!$_SESSION['user'])
	{
		header("Location: pageConnexion.php");
	}
	
	$user = $_SESSION['user'];

	if($user->getPermission() != 1)
	{
		echo 'test';
		exit();
	}
	
	if(isset($_POST['evenement']))
	{
		Evenement::addTypeEvents($_SESSION['Connexion'],$_POST['evenement']);
		$notif = 'Évènement créé.';
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
					<td><a href="deconnexion.php">Déconnexion</a></td>
				</tr>
			</table>
			</form>
			<br />
		</header>
	
		<div id="titre">
			Créer un nouvel évènement
		</div>
		
		<table id="principal">
			<tr>
				<td>
					<?php include_once('sousMenuAdmin.php');?>
				</td>
				<td>
					<div id="contentAvecMenu">
						<form method="post" action="creerEvenementAdmin.php">
							<table>
								<?php 
									if(isset($notif))
									{
										echo "<tr><td><h4 style='color:green;font-family: helvetica;'>$notif</h4></td></tr>";
									}
								?>
								<tr><td>Nouvel évènement : <input type="text" required="required" name="evenement"> <input type="submit" name="submit" value="créer"></td></tr>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>