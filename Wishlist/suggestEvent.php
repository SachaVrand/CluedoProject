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
	
	if(isset($_POST['valider']))
	{
		Message::newMessage($_SESSION['Connexion'],"Demande d'un nouvel évènement","évènement suggérer : $_POST[typeEvent]",1,$user->id);
	}
?>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>
			Suggérer
		</title>
	</head>
	<body>
		<?php 
			include_once('menuPrincipal.php');
		?>
	
		<div id="titre">
			Suggérer un évènement
		</div>
		
		<table id="principal">
			<tr>
				<td>
					<?php include_once('sousMenu.php');?>
				</td>
				<td>
					<div id="contentAvecMenu">
						<?php
							if(isset($_POST['valider']))
							{
								echo "La demande d'évènement a bien été faite.";
							}
							else
							{
								?>
								<form id="suggest" method="post" action="suggestEvent.php">
								<table>
									<tr><td><input type="text" name="typeEvent" placeholder="Type Évènement"></td></tr>
									<tr><td><input type="submit" class="btnValidation" name="valider" value="Suggérer"></td></tr>
								</table>
							</form>
							<?php 
							}
						?>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>