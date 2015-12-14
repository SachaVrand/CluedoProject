<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	$user = $_SESSION['user'];
	
	if(isset($_POST['message']))
	{
		$userT = Utilisateur::getUserToDisplay($_SESSION['Connexion'], $_POST['pseudoExp']);
		if($userT)
		{
			Message::newMessage($_SESSION['Connexion'], $_POST['entete'], $_POST['message'], $userT->id, $user->id);
			header('Location: fluxActiviteFollowing.php');
			exit();
		}
	}

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
			Nouveau message
		</div>
		

		<div id="contentSansMenu">
			<form method="post" action="creationMessage.php?pseudoExp=<?php echo $_GET['pseudoExp'];?>">
				<table>
					<tr>
						<td><input name="pseudoExp" type="text" readonly="readonly" required="required" value="<?php echo $_GET['pseudoExp'];?>"></td>
					</tr>
					<tr>
						<td><input name="entete" type="text" placeholder="Entete" required="required"></td>
					</tr>
					<tr>
						<td><textarea name="message" rows=6 cols=50 maxlength="300" placeholder="Message" required="required"></textarea></td>
					</tr>
					<tr>
						<td><input type="submit" name="submit" value="envoyer"></td>
					</tr>
				</table>
			</form>
		</div>
				
	</body>
</html>