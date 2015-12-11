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
					if(!($userToDisplay || $userToDisplay->confidentialite == 1))
					{
				?>
				<td>
					<div id="sousMenu">
						<ul>
							<li><a href="infosPerso.php">Informations</a></li>
							<li><a href="listeCadeau.php">Listes</a></li>
							<li><a href="follow.php">Follow</a></li>
						</ul>
					</div>
				</td>
				<?php
					}
				?>
				<td>
					<div id=<?php 
								if($userToDisplay || $userToDisplay->confidentialite == 1)
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
							else
							{
								//TODO afficher profil
							}				
						?>
					</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>