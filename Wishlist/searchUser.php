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
	
	if(isset($_POST['submitBan']) && $user->getPermission() == 1)
	{
		Utilisateur::bannirEmail($_SESSION['Connexion'], $userToDisplay->mail);
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
			if($user->getPermission() == 0)
			{
				include_once('menuPrincipal.php');
			}
			else 
			{
		?>
				<header id="headerMenu">
					<br />
					<form method="get" action="searchUser.php">
					<table id="menuPrincipal">
						<tr>
							<td><a href="creerEvenementAdmin.php">W.</a></td>
							<td></td>
							<td><input type="text" name="user" id="recherche" placeholder="Cherchez des personnes" size="50"/></td>
							<td><input type="submit" name="ok" value="Ok"></td>
							<td></td>
							<td><a href="creerEvenementAdmin.php">Accueil</a></td>
							<td><a href="pageMessagerie.php">Messagerie</a></td>
							<td><a href="deconnexion.php">Déconnexion</a></td>
						</tr>
					</table>
					</form>
					<br />
				</header>
		
		<?php
			}
		?>
	
		<div id="titre">
			Profil de <?php echo htmlspecialchars($_GET['user']); ?>
		</div>
		<?php 
			if(!$userToDisplay || $userToDisplay->confidentialite == 1 || Utilisateur::isRestrict($_SESSION['Connexion'], $user, $userToDisplay))
			{
				?>
					<div id="contentSansMenu">
					<?php
						if(!$userToDisplay)
						{
							echo "Aucun utilisateur n'a pu être trouvé";
						}
						else if($userToDisplay->confidentialite == 1)
						{
							echo "L'utilisateur ne souhaite pas que l'on puisse avoir accès à son profil";
						}
						else if(Utilisateur::isRestrict($_SESSION['Connexion'], $user, $userToDisplay))
						{
							echo "L'utilisateur ne souhaite pas que vous puissiez accéder à son profil.";
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
							<?php
								echo '<li><a href="searchUser.php?user='.htmlspecialchars($_GET['user']).'">Profil</a></li>';
								echo '<li><a href="listesUser.php?user='.htmlspecialchars($_GET['user']).'">Liste</a></li>';
							?>
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
							'<table id="tableauInfo">
								<tr> <td rowspan=2><img src="'.htmlspecialchars($userToDisplay->photo).'" /></td> <td colspan=2>'.htmlspecialchars($userToDisplay->pseudo).'</td> </tr>
								<tr> <td>'.htmlspecialchars($userToDisplay->nom).' '.htmlspecialchars($userToDisplay->prenom).'</td> <td></td> </tr>
							</table>'.
							'<br>'.
							'<span class="enGras">Ville : </span>'.htmlspecialchars($userToDisplay->ville).
							'<br>'.
							'<span class="enGras">Naissance : </span>'.$date[2].' '.$months[$month].' '.$date[0].
							'<br>'.
							'<span class="enGras">Mail : </span>'.htmlspecialchars($userToDisplay->mail).
							'<br><br>';		
						?>
						
						<table>
							<tr><th align="left">Flux d'activitees recentes (2semaines)</th></tr>
							<?php
								$res = Utilisateur::getActivities($_SESSION['Connexion'], $userToDisplay);
								if(!$res)
								{
									echo'<tr><td>Aucune activités récentes.</td></tr>';
								}
								else
								{
									foreach($res as $activiteListe)
									{
										$act = $activiteListe->activite;
										if(!$activiteListe->idReservePar)
										{
											echo '<tr><td><a href="searchUser.php?user='.htmlspecialchars($activiteListe->pseudoUser).'">'.htmlspecialchars($activiteListe->pseudoUser).'</a> '.htmlspecialchars($act->nomType).' '.htmlspecialchars($act->nomObjet)." pour l'évènement <a href=\"listesUser.php?idListe=$activiteListe->idListe\">".htmlspecialchars($activiteListe->nomEvenement).'</a> </td><tr>';
										}
										else
										{
											$userWhoReserved = Utilisateur::getUserById($_SESSION['Connexion'], $activiteListe->idReservePar);
											echo '<tr><td><a href="searchUser.php?user='.htmlspecialchars($userWhoReserved->pseudo).'">'.htmlspecialchars($userWhoReserved->pseudo).'</a> '.htmlspecialchars($act->nomType).' '.htmlspecialchars($act->nomObjet)." pour l'évènement <a href=\"listesUser.php?idListe=$activiteListe->idListe\">".htmlspecialchars($activiteListe->nomEvenement).'</a> de <a href="searchUser.php?user='.htmlspecialchars($activiteListe->pseudoUser).'">'.htmlspecialchars($activiteListe->pseudoUser).'</a></td></tr>';
										}
									}
								}
							?>
						</table>
						<br>
						<br>
						<form method="post" action="searchUser.php?user=<?php echo htmlspecialchars($userToDisplay->pseudo); ?>">
							<table>
								<?php 
									$nbFollowers = Utilisateur::getFollowerNumber($_SESSION['Connexion'], $userToDisplay);
									$nbFollowing = Utilisateur::getFollowingNumber($_SESSION['Connexion'], $userToDisplay);
									$nbListes = Utilisateur::getListNumber($_SESSION['Connexion'], $userToDisplay);
								?>
									<tr><td align="left" style="width:auto;"> Followers : <?php echo $nbFollowers;?></td></tr>
									<tr><td align="left" style="width:auto;"> Following : <?php echo $nbFollowing;?></td></tr>
									<tr><td align="left" style="width:auto;"> Listes : <?php echo $nbListes;?></td></tr>
								<tr><td style="width:auto;"><input type="submit" name="submit" value="suivre"></td><td style="width:auto;"><input type="button" value=" nouveau message " onclick="self.location.href='creationMessage.php?pseudoExp=<?php echo htmlspecialchars($userToDisplay->pseudo);?>'"></td></tr>
							</table>
						</form>
						<?php 
							if($user->getPermission() == 1)
							{
								?>
								<br>
								<form method="post" action="searchUser.php?user=<?php echo htmlspecialchars($userToDisplay->pseudo); ?>">
									<input type="submit" name="submitBan" value="Ban user">
								</form>
								<?php
							}
						?>
					</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>