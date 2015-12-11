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
		<meta name="author" content="page de connexion">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>
			Modification des informations personelles
		</title>
	</head>
	<body>
		<?php
			if(isset($notif))
			{
				echo "<h1 style='color:green;'>$notif</h1>";
			}
		?>
		<button type="button" onclick="self.location.href='modifMotDePasse.php'">Modifier vos informations</button>
		<form method="post" action="modifInfosPerso.php">
			Pseudo : 
			<input type="text" name="pseudo" required="required"value=<?php $user->pseudo; ?> >
			<?php
				if(isset($existingLogin))
				{
					echo "<h1 style='color:red;'>$existingLogin</h1>";
				}
			?>
			<br>
			Nom : 
			<input type="text" name="nom" required="required" value=<?php $user->nom; ?> >
			<br>
			Prenom : 
			<input type="text" name="prenom" required="required" value=<?php $user->prenom; ?> >
			<br>
			Ville : 
			<input type="text" name="ville" required="required" value=<?php $user->ville; ?> >
			<br>
			Mail : 
			<input type="text" name="mail" required="required" value=<?php $user->mail; ?> >
			<br>
			Profil confidentiel ?
			<br>
			<?php 
				if($user->confidentialite == 1)
				{
					echo '
					<input type="radio" name="confidentialite" value="1" id="oui" checked="checked" /> <label for="oui">Oui</label>
					<input type="radio" name="confidentialite" value="0" id="non" /> <label for="non">Non</label>
					';
				}
				else
				{
					echo '
					<input type="radio" name="confidentialite" value="1" id="oui" /> <label for="oui">Oui</label>
					<input type="radio" name="confidentialite" value="0" id="non" checked="checked" /> <label for="non">Non</label>
					';
				}
			?>
			<br>
			Date de naissance : 
			<select name="jour">
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
			<br>
			URL photo :
			<input type="text" name="photo" value=<?php $user->photo; ?> >
			<br>
			<br>
			<input type="submit" name="Modification" value="Conserver les changements">
		</form>
	</body>
</html>