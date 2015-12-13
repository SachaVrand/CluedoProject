<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	$user = $_SESSION['user'];
	if(!isset($_POST['message']) || !isset($_POST['entete']) || !isset($_POST['pseudoExp']))
	{
		exit();
	}
	$message = $_POST['message'];
	$entete = $_POST['entete'];
	$pseudoExp = $_POST['pseudoExp'];
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
			include_once('menuPrincipal.php');
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