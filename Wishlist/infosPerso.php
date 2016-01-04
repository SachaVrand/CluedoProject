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
					<?php include_once('sousMenu.php');?>
				</td>
				<td>
					<div id="contentAvecMenu">
						<?php
							$user = $_SESSION['user'];
							$date = explode('-', $user->dateNaissance);
							$months = array('janvier','fevrier','mars','avril','mai','juin','juillet','aout','septembre','octobre','novembre','decembre');
							$month = $date[1]-1;
							echo 
							'<table id="tableauInfo">
								<tr> <td rowspan=2><img src="'.htmlspecialchars($user->photo).'" /></td> <td colspan=2>'.htmlspecialchars($user->pseudo).'</td> </tr>
								<tr> <td>'.htmlspecialchars($user->nom).' '.htmlspecialchars($user->prenom).'</td> <td></td> </tr>
							</table>'.
							'<br>'.
							'<span class="enGras">Ville : </span>'.htmlspecialchars($user->ville).
							'<br>'.
							'<span class="enGras">Naissance : </span>'.$date[2].' '.$months[$month].' '.$date[0].
							'<br>'.
							'<span class="enGras">Mail : </span>'.htmlspecialchars($user->mail).
							'<br><br>';
						?>
					</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>