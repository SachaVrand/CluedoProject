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
		<header id="headerMenu">
			<br />
			<table id="menuPrincipal">
				<tr>
					<td><a href="accueil.php">W.</a></td>
					<td></td>
					<td><input type="text" name="recherche" id="recherche" placeholder="Cherchez des personnes" size="50"/></td>
					<td><input type="button" name="ok" value="Ok"></td>
					<td></td>
					<td><a href="accueil.php">Accueil</a></td>
					<td><a href="infoPerso.php">Profil personnel</a>
						<ul>
							<li><a href="infosPerso.php">Informations</a></li>
							<li><a href="listeCadeau.php">Listes</a></li>
							<li><a href="follow.php">Follow</a></li>
						</ul>
					</td>
					<td><a href="messagerie.php">Messagerie</a></td>
					<td><a href="notifications.php">Notifications</a></td>
				</tr>
			</table>
			<br />
		</header>
	
		<div id="titre">
			Profil personnel
		</div>
		
		<table id="principal" width=44%  height=50%>
			<tr>
				<td width=15%>
					<div id="sousMenu">
						<ul>
							<li><a href="infosPerso.php">Informations</a></li>
							<li><a href="listeCadeau.php">Listes</a></li>
							<li><a href="follow.php">Follow</a></li>
						</ul>
					</div>
				</td width=85%>
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