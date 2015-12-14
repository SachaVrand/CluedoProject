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
		<meta http-equiv="Content-Language" content="fr" />
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>Flux d'actualités</title>
	</head>
	
	<body>
		
		<?php 
			include_once('menuPrincipal.php');
		?>
	
		<div id="titre">
			Flux d'activitees de vos following
		</div>
		
		<table id="principal">
			<tr>
				<td>
					<div id="sousMenu">
						<ul>
							<li><a href="fluxActiviteFollowing.php">Activites Following</a></li>
						</ul>
					</div>
				</td>
				<td>
					<div id="contentAvecMenu">
						<table>
							<?php
								$res = Utilisateur::getFollowingActivity($_SESSION['Connexion'], $_SESSION['user']);
								if(!$res)
								{
									echo'<tr><td>Aucune activites recentes.</td></tr>';
								}
								else
								{
									foreach($res as $activiteListe)
									{
										$activite = $activiteListe->activite;
										if(!$activiteListe->idReservePar)
										{
											
											echo "<tr><td><a href='searchUser.php?user=$activiteListe->pseudoUser'>$activiteListe->pseudoUser</a> $activite->nomType $activite->nomObjet pour l'evenement <a href='#'>$activiteListe->nomEvenement</a> </td><tr>";
										}
										else
										{
											$userWhoReserved = Utilisateur::getUserById($_SESSION['Connexion'], $activiteListe->idReservePar);
											echo "<tr><td><a href='searchUser.php?user=$userWhoReserved->pseudo'>$userWhoReserved->pseudo</a> $activite->nomType $activite->nomObjet pour l'evenement <a href='#'>$activiteListe->nomEvenement</a> de <a href='searchUser.php?user=$activiteListe->pseudoUser'>$activiteListe->pseudoUser</a></td></tr>";
										}
									}
								}
							?>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>