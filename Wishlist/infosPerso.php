<?php

	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
				<td>
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
							$months = array('janvier','fevrier','mars','avril','mai','juin','juillet','aout','septembre','octobre','novembre','decembre');
							$month = $date[1]-1;
							echo 
							'<table>
								<tr> <td rowspan=2><img src="'.$user->photo.'" /></td> <td colspan=2>'.$user->pseudo.'</td> </tr>
								<tr> <td>'.$user->nom.' '.$user->prenom.', </td> <td>'.$user->mail.'</td> </tr>
							</table>'.
							'<br>'.
							'Ville : '.$user->ville.
							'<br>'.
							'Naissance : '.$date[2].' '.$months[$month].' '.$date[0].
							'<br><br>';
						?>
						<div id="bouton">
							<button type="button" onclick="self.location.href='modifInfosPerso.php'">Modifier</button>
						</div>
					</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>