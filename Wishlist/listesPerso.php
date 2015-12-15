<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	$user = $_SESSION['user'];
	
?>


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>
			Mes listes
		</title>
	</head>
	<body>
		<?php 
			include_once('menuPrincipal.php');
		?>
	
		<div id="titre">
			Mes listes de cadeaux
		</div>
		
		<table id="principal">
			<tr>
				<td>
					<?php include_once('sousMenu.php');?>
				</td>
				<td>
					<div id="contentAvecMenu">
					
					</div>
				</td>
			</tr>
		</table>
		
	</body>
</html>