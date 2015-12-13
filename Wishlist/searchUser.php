<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	$user = $_SESSION['user'];
	
	if(!isset($_GET['user']))
	{
		exit();
	}
	else 
	{
		$userToDisplay = Utilisateur::getUserToDisplay($_SESSION['Connexion'], $_GET['user']);
	}
	
	if(isset($_POST['submit']))
	{
		Utilisateur::followUser($_SESSION['Connexion'], $userToDisplay, $_SESSION['user']);
	}
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Language" content="fr" />
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>Utilisateur</title>
	</head>
	
	<body>
		<?php 
			include_once('menuPrincipal.php');
		?>
	
		<div id="titre">
			Profil de <?php echo $_GET['user']; ?>
		</div>
		<?php 
			if(!$userToDisplay || $userToDisplay->confidentialite == 1 || Utilisateur::isRestrict($_SESSION['Connexion'], $user, $userToDisplay))
			{
				?>
					<div id="contentSansMenu">
					<?php
						if(!$userToDisplay)
						{
							echo "Aucun utilisateur n'a pu etre trouve";
						}
						else if($userToDisplay->confidentialite === 1)
						{
							echo "L'utilisateur ne souhaite pas que l'on puisse avoir acces a son profil";
						}
						else if(Utilisateur::isRestrict($_SESSION['Connexion'], $user, $userToDisplay))
						{
							echo "L'utilisateur ne souhaite pas que vous puissiez acceder a son profil.";
						}
					?>
					</div>
				<?php
				exit();
			}
		?>
		<table id="principal">
			<tr>
				<td>
					<div id="sousMenu">
						<ul>
							<li><a href="#">Not implemented</a></li>
						</ul>
					</div>
				</td>
				<td>
					<div id="contentAvecMenu">
						<?php
							$date = explode('-', $userToDisplay->dateNaissance);
							$months = array('janvier','fevrier','mars','avril','mai','juin','juillet','aout','septembre','octobre','novembre','decembre');
							$month = $date[1]-1;
							echo
							'<table>
								<tr> <td rowspan=2><img src="'.$userToDisplay->photo.'" /></td> <td colspan=2>'.$userToDisplay->pseudo.'</td> </tr>
								<tr> <td>'.$userToDisplay->nom.' '.$userToDisplay->prenom.', </td> <td>'.$userToDisplay->mail.'</td> </tr>
							</table>'.
							'<br>'.
							'Ville : '.$userToDisplay->ville.
							'<br>'.
							'Naissance : '.$date[2].' '.$months[$month].' '.$date[0].
							'<br><br>';			
						?>
						
						<table>
							<tr><th align="left">Flux d'activitees recentes (2semaines)</th></tr>
							<?php
								$res = Utilisateur::getActivities($_SESSION['Connexion'], $userToDisplay);
								if(!$res)
								{
									echo'<tr><td>Aucune activites recentes.</td></tr>';
								}
								else
								{
									foreach($res as $activiteListe)
									{
										if(!$activiteListe->idReservePar)
										{
											echo "<tr><td><a href='searchUser.php?user=$activiteListe->pseudoUser'>$activiteListe->pseudoUser</a> $activiteListe->activite->nomType $activiteListe->activite->nomObjet pour l'evenement <a href='#'>$activiteListe->activite->nomEvenement</a> </td><tr>";
										}
										else
										{
											$userWhoReserved = Utilisateur::getUserById($_SESSION['Connexion'], $activiteListe->idReservePar);
											echo "<tr><td><a href='searchUser.php?user=$userWhoReserved->pseudo'>$userWhoReserved->pseudo</a> $activiteListe->activite->nomType $activiteListe->activite->nomObjet pour l'evenement <a href='#'>$activiteListe->activite->nomEvenement</a> de <a href='searchUser.php?user=$activiteListe->pseudoUser'>$activiteListe->pseudoUser</a></td></tr>";
										}
									}
								}
							?>
						</table>
						<br>
						<br>
						<form method="post" action="searchUser.php?user=<?php echo "$userToDisplay->pseudo"; ?>">
							<table>
								<?php 
									$nbFollowers = Utilisateur::getFollowerNumber($_SESSION['Connexion'], $userToDisplay);
									$nbFollowing = Utilisateur::getFollowingNumber($_SESSION['Connexion'], $userToDisplay);
									$nbListes = Utilisateur::getListNumber($_SESSION['Connexion'], $userToDisplay);
								?>
								<tr><td>Followers : <?php echo $nbFollowers;?> | Following : <?php echo $nbFollowing;?> | Listes : <?php echo $nbListes;?> </td><td><input type="submit" name="submit" value="suivre"></td></tr>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>