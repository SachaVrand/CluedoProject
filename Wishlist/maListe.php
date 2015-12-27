<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	$user = $_SESSION['user'];
	
	$idListe = $_POST['btnListe'];
	
	
	$tabContients = Contient::getContients($_SESSION['Connexion'],$idListe);
?>


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>
			Ma liste
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
								<td><a href="maListe.php">Supprimer la liste</a></td>
							</tr>
						</table>
						<br>
						<br>
						<form id="maListe" method="post" action="maListe.php">
							<?php 
								echo "<table id=maListeCadeaux>";
								foreach($tabContients as $element)
								{
									$idCadeau = $element->idCadeau;
									$nomCadeau = Cadeau::getNomBdd($_SESSION['Connexion'], $idCadeau);
									$nomReserve = Utilisateur::getPseudo($_SESSION['Connexion'], $element->reservePar);
									if($nomReserve != "")
									{
										$nomReserve = "reserve par : $nomReserve";
										echo "<tr><td>$nomCadeau</td><td><span id=reserve>$nomReserve</span></td><td><button class=btnMaListe name=btnListe value=$idCadeau onclick=goToList()>X</button</td></tr>";
									}
									else
									{
										echo "<tr><td>$nomCadeau</td><td>$nomReserve</td><td><button class=btnMaListe name=btnListe value=$idCadeau onclick=goToList()>X</button</td></tr>";
									}
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