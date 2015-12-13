<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	$user = $_SESSION['user'];
	$notifcationsReservations = Utilisateur::getNotificationsReservation($_SESSION['Connexion'],$user);
	$notificationEvenements = Utilisateur::getNotificationsEvenement($_SESSION['Connexion'],$user)
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
			Notifications
		</div>
		

		<div id="contentAvecMenu">
			<table>
				<?php
					if(!$notificationEvenements)
					{
						echo "<tr><td>Aucunes notifications d'evenements.</td></tr>";
					}
					else
					{
						foreach($notificationEvenements as $v)
						{
							echo "<tr><td>Ajourd'hui c'est l'evenement <a href='#'>$v[1]</a> de <a href='searchUser.php?user=$v[0]>$v[0]</a> </td></tr>";
						}	
					}
					echo "<tr><td><hr></td></tr>";
					if(!$notifcationsReservations)
					{
						echo "<tr><td>Aucunes notifications de reservations.</td></tr>";
					}
					else 
					{
						$userWhoReserved = Utilisateur::getUserById($_SESSION['Connexion'], $activiteListe->idReservePar);
						echo "<tr><td><a href='searchUser.php?user=$userWhoReserved->pseudo'>$userWhoReserved->pseudo</a> $activiteListe->activite->nomType $activiteListe->activite->nomObjet pour l'evenement <a href='#'>$activiteListe->activite->nomEvenement</a> de <a href='searchUser.php?user=$activiteListe->pseudoUser'>$activiteListe->pseudoUser</a></td></tr>";
					}
				?>
			</table>
		</div>
				
	</body>
</html>