<?php

	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	//$_SESSION['user'] = $user;
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Language" content="fr" />
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>Informations personnelles</title>
	</head>
	
	<body>
		<?php 
			include_once('menuPrincipal.php');
		?>
	
		<div id="titre">
			Profil personnel
		</div>
		
		<table id="principal">
			<tr>
				<td width=15%>
					<div id="sousMenu">
						<ul>
							<li><a href="infosPerso.php">Informations</a></li>
							<li><a href="listeCadeau.php">Listes</a></li>
							<li><a href="follow.php">Follow</a></li>
						</ul>
					</div>
				</td>
				<td>
					<div id="contentAvecMenu">
						<?php
							$user = $_SESSION['user'];
							$date = explode('-', $user->dateNaissance);
							$months = array('Janvier','Fevrier','Mars','Avril','Mai','Juin','Juillet','Aout','Septembre','Octobre','Novembre','Decembre');
							$month = $date[1]-1;
							//TODO changer l'affichage pour l'image
							echo 
							"<table>
								<tr> <td rowspan=2>Image</td> <td>$user->pseudo</td> <td></td> </tr>
								<tr> <td></td> <td>$user->nom $user->prenom, </td> <td>$user->mail</td> </tr>
							</table>";
							echo '<br>';
							echo 
							"<table>
								<tr><td>Ville : </td><td>$user->ville</td></tr>
								<tr></tr>
								<tr><td>Naissance : </td> <td>$date[2]</td> <td>$months[$month] </td> <td>$date[0]</td> </tr>
							</table>";
						?>
						<button type="button" onclick="self.location.href='modifInfosPerso.php'">Modifier vos informations</button>
					</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>