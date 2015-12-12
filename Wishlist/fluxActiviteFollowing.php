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
		<title>Informations personnelles</title>
	</head>
	
	<body>
		
		<?php 
			include_once('menuPrincipal.php');
		?>
	
		<div id="titre">
			Flux d'activite
		</div>
		
		<table id="principal">
			<tr>
				<td>
					<div id="sousMenu">
						<ul>
							<li><a href="fluxActiviteFollowing.php">Activites Following</a></li>
							<li><a href="fluxActiviteListeFollowing.php">Activites listes Following</a>
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
									foreach($res as $v)
									{
										echo "<tr><td><a href='searchUser.php?user=$v->user'>$v->user</a> $v->activite->nomType $v->activite->nomObjet pour l'evenement $v->activite->nomEvenement </td><tr>";
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