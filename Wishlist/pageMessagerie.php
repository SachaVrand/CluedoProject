<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	$user = $_SESSION['user'];
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
			include_once('menuPrincipal.php');
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
									echo "<a href='searchUser.php?user=$msg->pseudoExp'>$msg->pseudoExp</a> | $date[2].'/'.$date[1].'/.$date[0] | $msg->entete | "; 
								?>
							<form method="post" action="afficherMessage.php">
								<input type="hidden" name="message" value="<?php echo $msg->message ;?> ">
								<input type="hidden" name="entete" value="<?php echo $msg->entete ;?> ">
								<input type="hidden" name="pseudoExp" value="<?php echo $msg->pseudoExp ;?> ">
								<input type="submit" value="voir" name="submit">
							</form>
							</td></tr>
						<?php 
						}
					}
				?>
			</table>
		</div>
				
	</body>
</html>