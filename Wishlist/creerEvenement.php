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
	
	if(isset($_POST['evenement']))
	{
		Evenement::addTypeEvents($_SESSION['Connexion'],$_POST['evenement']);
		$notif = 'Evenement cree.';
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
					<td><a href="pageAdmin.php">W.</a></td>
					<td></td>
					<td><input type="text" name="user" id="recherche" placeholder="Cherchez des personnes" size="50"/></td>
					<td><input type="submit" name="ok" value="Ok"></td>
					<td></td>
					<td><a href="pageAdmin.php">Accueil</a></td>
					<td><a href="pageMessagerie.php">Messagerie</a></td>
				</tr>
			</table>
			</form>
			<br />
		</header>
	
		<div id="titre">
			Creer un nouvel evenement
		</div>
		
		<table id="principal">
			<tr>
				<td>
					<?php include_once('sousMenuAdmin.php');?>
				</td>
				<td>
					<div id="contentAvecMenu">
						<form method="post" action="creerEvenement.php">
							<table>
								<?php 
									if(isset($notif))
									{
										echo "<tr><td>$notif</td></tr>";
									}
								?>
								<tr><td>Nouvel evenement : <input type="text" required="required" name="evenement"> <input type="submit" name="submit" value="submit"></td></tr>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>