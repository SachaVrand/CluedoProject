<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	$user = $_SESSION['user'];
	
	if(isset($_POST['suppr']))
	{
		Message::deleteMessage($_SESSION['Connexion'],$_POST['id']);
	}
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Language" content="fr" />
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>Messagerie</title>
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
							<td><a href="deconnexion.php">Deconnexion</a></td>
						</tr>
					</table>
					</form>
					<br />
				</header>
		
		<?php
			}
		?>
		<div id="titre">
			Messagerie
		</div>
		

		<div id="contentSansMenu">
			<table>
				<?php
					$res = Message::getMessages($_SESSION['Connexion'], $user);
					if(!$res)
					{
						echo 'Aucun messages';
					}
					{
						foreach($res as $msg)
						{
							$date = explode('-', $msg->dateMessage);
							?>
							<tr><td>
								<?php
									echo "<a href='searchUser.php?user=$msg->pseudoExp'>$msg->pseudoExp</a> | $date[2]/$date[1]/$date[0] | $msg->entete | "; 
								?>
							</td>
							<td>
							<form method="post" action="afficherMessage.php">
								<input type="hidden" name="message" value="<?php echo $msg->message ;?> ">
								<input type="hidden" name="entete" value="<?php echo $msg->entete ;?> ">
								<input type="hidden" name="pseudoExp" value="<?php echo $msg->pseudoExp ;?> ">
								<input type="submit" value="voir" name="submit">
							</form>
							</td>
							<td>
								<form method="post" action="pageMessagerie.php">
									<input type="hidden" name="id" value="<?php echo $msg->id; ?>">
									<input type="submit" name="suppr" value="supprimer">
								</form>
							</td>
							</tr>
						<?php 
						}
					}
				?>
			</table>
		</div>
				
	</body>
</html>