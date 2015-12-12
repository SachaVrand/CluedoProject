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
		$userToDisplay = Utilisateur::getUserToDisplay($connexionBase, $pseudo);
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
		
		<table id="principal">
			<tr>
				<?php 
					if(!($userToDisplay || $userToDisplay->confidentialite == 1 || Utilisateur::isRestrict($_SESSION['Connexion'], $user, $userToDisplay)))
					{
				?>
				<td>
					<div id="sousMenu">
						<ul>
							<?php
								
							?>
							<li><a href="#">Not implemented</a></li>
						</ul>
					</div>
				</td>
				<?php
					}
				?>
				<td>
					<div id=<?php 
								if($userToDisplay || $userToDisplay->confidentialite == 1 || Utilisateur::isRestrict($_SESSION['Connexion'], $user, $userToDisplay))
								{
									echo '"ContentSansMenu"';
								}
								else
								{
									echo '"ContentAvecMenu"';
								}
							?> >
						<?php
							if($userToDisplay)
							{
								echo "Aucun utilisateur n'a pu etre trouve";
							}
							else if($userToDisplay->confidentialite == 1)
							{
								echo "L'utilisateur ne souhaite pas que l'on puisse avoir acces a son profil";
							}
							else if(Utilisateur::isRestrict($_SESSION['Connexion'], $user, $userToDisplay))
							{
								echo "L'utilisateur ne souhaite que vous puissiez acceder a son profil.";
							}
							else
							{
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
							}				
						?>
					</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>