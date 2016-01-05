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
		

		<div id="contentSansMenu">
			<table>
				<?php
					if(!$notificationEvenements)
					{
						echo "<tr><td>Aucune notification d'évènement.</td></tr>";
					}
					else
					{
						foreach($notificationEvenements as $v)
						{
							echo "<tr><td>Aujourd'hui c'est l'évènement ".htmlspecialchars($v[1]).' de <a href="searchUser.php?user='.htmlspecialchars($v[0]).'">'.htmlspecialchars($v[0]).'</a> </td></tr>';
						}	
					}
					echo "<tr><td><hr></td></tr>";
					if(!$notifcationsReservations)
					{
						echo "<tr><td>Aucune notification de réservation.</td></tr>";
					}
					else 
					{
						foreach($notifcationsReservations as $activiteListe)
						{
							$userWhoReserved = Utilisateur::getUserById($_SESSION['Connexion'], $activiteListe->idReservePar);
							echo '<tr><td><a href="searchUser.php?user='.htmlspecialchars($userWhoReserved->pseudo).'">'.htmlspecialchars($userWhoReserved->pseudo).'</a> '.htmlspecialchars($activiteListe->activite->nomType).' '.htmlspecialchars($activiteListe->activite->nomObjet)." pour l'évènement <a href=\"listesUser.php?idListe=$activiteListe->idListe\">".htmlspecialchars($activiteListe->nomEvenement).'</a> de <a href="searchUser.php?user='.htmlspecialchars($activiteListe->pseudoUser).'">'.htmlspecialchars($activiteListe->pseudoUser).'</a></td></tr>';
						}
					}
				?>
			</table>
		</div>
				
	</body>
</html>