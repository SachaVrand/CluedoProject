<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	$user = $_SESSION['user'];
	
	if(isset($_POST['pseudo']))
	{
		$pseudoHasChanged = !($user->pseudo === $_POST['pseudo']);
		$mailHasChanged = !($user->mail === $_POST['mail']);
		if(isset($_POST['photo']))
		{
			$photo = $_POST['photo'];
		}
		else
		{
			$photo = 'images/userIcon.png';
		}
		$user = new Utilisateur($user->id,$_POST['pseudo'], $_POST['nom'], $_POST['prenom'], $_POST['ville'], $_POST['mail'],0, $_POST['annee'].'-'.$_POST['mois'].'-'.$_POST['jour'], $photo,$_POST['confidentialite']);
		$res = Utilisateur::updateUserInfos($_SESSION['Connexion'], $user, $pseudoHasChanged, $mailHasChanged);
		if($res)
		{
			$_SESSION['user'] = $user;
			$notif = "Vos informations ont bien ete enregitrees";
		}
		else
		{
			$existingLogin = 'Un utilisateur possede deja le meme pseudo ou la meme adresse mail.';
		}
	}
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>
			Modification des informations personelles
		</title>
	</head>
	<body>
		<?php 
			include_once('menuPrincipal.php');
		?>
	
		<div id="titre">
			Modifier vos informations
		</div>
		
		<table id="principal">
			<tr>
				<td>
					<?php include_once('sousMenu.php');?>
				</td>
				<td>
					<div id="contentAvecMenu">
						<?php
							if(isset($notif))
							{
								echo "<h1 style='color:green;'>$notif</h1>";
							}
							else if(isset($existingLogin))
							{
								echo "<h1 style='color:red;'>$existingLogin</h1>";
							}
						?>
						<form method="post" action="modifInfosPerso.php">
							<table>
								<tr>
									<td>Pseudo :</td>
								 	<td><input type="text" name="pseudo" required="required" value="<?php echo $user->pseudo; ?>" ></td>
								</tr>
								<tr>
									<td>Nom :</td>
									<td><input type="text" name="nom" required="required" value="<?php echo $user->nom; ?>" ></td>
								</tr>
								<tr>
									<td>Prenom : </td>
									<td><input type="text" name="prenom" required="required" value="<?php echo $user->prenom; ?>" ></td>
								</tr>
								<tr>
									<td>Ville :</td>
									<td><input type="text" name="ville" required="required" value="<?php echo $user->ville; ?>" ></td>
								</tr>
								<tr>
									<td>Mail :</td>
									<td><input type="email" name="mail" required="required" value="<?php echo $user->mail; ?>" ></td>
								</tr>
								<tr>
									<td>Profil confidentiel ?</td>
									<td><?php 
											if($user->confidentialite == 1)
											{
												echo '
												<input type="radio" name="confidentialite" value="0" id="non" /> <label for="non">Non</label>
												<input type="radio" name="confidentialite" value="1" id="oui" checked="checked" /> <label for="oui">Oui</label>
												';
											}
											else
											{
												echo '
												<input type="radio" name="confidentialite" value="0" id="non" checked="checked" /> <label for="non">Non</label>
												<input type="radio" name="confidentialite" value="1" id="oui" /> <label for="oui">Oui</label>
												';
											}
										?>
									</td>
								</tr>
								<tr>
									<td>Date de naissance :</td>
									<td><select name="jour">
										<?php
											$date = explode('-', $user->dateNaissance);
											for($i = 1; $i <= 31; $i++)
											{
												if($date[2] == $i)
												{
													echo "<option value=$i selected=selected> $i </option>";
												}
												else
												{
													echo "<option value=$i> $i </option>";
												}
											}
										?>
										</select>
										<select name="mois">
											<?php 
												for($i=1;$i<=12;$i++)
												{
													if($date[1] == $i)
													{
														echo "<option value=$i selected=selected> $i </option>";
													}
													else
													{
														echo "<option value=$i> $i </option>";
													}
												}
											?>
										</select>
										<select name="annee">
											<?php
												$y = date('Y');
												for($i=$y ; $i>$y-100 ; $i--)
												{
													if($date[0] == $i)
													{
														echo "<option value=$i selected=selected> $i </option>";
													}
													else
													{
														echo "<option value=$i> $i </option>";
													}
												}
											?>
										</select>
									</td>
								</tr>
								<tr>
									<td>URL photo :</td>
									<td><input type="text" name="photo" value="<?php echo $user->photo; ?>" ></td>
								</tr>
								<tr>
									<td><input type="submit" name="Modification" value="Conserver les changements"></td>
								</tr>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>