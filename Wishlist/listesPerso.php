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
	
	$listesCadeaux = ListeCadeaux::getListesCadeaux($_SESSION['Connexion'], $user)
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
						<table id="btnCreerListe">
							<tr>
								<td><a href="creerListe.php">Creer une nouvelle liste</a></td>
							</tr>
						</table>
						<br>
						<br>
						<form id="mesListes" method="post" action="maListe.php">
							<?php 
								echo "<table id=mesListesCadeaux>";
								foreach($listesCadeaux as $element)
								{
									$tmpNom = Evenement::getNomBdd($_SESSION['Connexion'],$element->getIdEvent());
									$tmpId = $element->getId();
									echo "<tr><td><button class=btnMaListe name=btnListe value=$tmpId onclick=goToList()>$tmpNom</button</td></tr>";
								}
								echo "</table>";
							?>
							<script>
								function goToList()
								{
									document.getElementById('mesListes').submit();
								}
							</script>
						</form>
						<br>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
</html>