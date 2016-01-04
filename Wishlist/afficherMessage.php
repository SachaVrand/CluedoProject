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
	if(!isset($_POST['message']) || !isset($_POST['entete']) || !isset($_POST['pseudoExp']))
	{
		exit();
	}
	$message = htmlspecialchars($_POST['message']);
	$entete = htmlspecialchars($_POST['entete']);
	$pseudoExp = htmlspecialchars($_POST['pseudoExp']);
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Language" content="fr" />
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>Messagerie</title>
	</head>
	
	<body>
		
		<?php 
			if($user->getPermission() == 0)
			{
				include_once('menuPrincipal.php');
			}
			else 
			{
		?>
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
						</tr>
					</table>
					</form>
					<br />
				</header>
		
		<?php
			}
		?>
	
		<div id="titre">
			<?php echo "$pseudoExp : $entete "; ?>
		</div>
		

		<div id="contentSansMenu">
			<table>
				<tr>
					<td><?php echo "$message"; ?></td>
				</tr>
				<tr>
					<td><button name="retour" onclick="self.location.href='pageMessagerie.php'">Retour</button></td>
				</tr>
			</table>
		</div>
				
	</body>
</html>