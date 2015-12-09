<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	if(isset($_POST['pseudo']))
	{
		//ajouter user
		if($_POST['password'] === $_POST['cpassword'])
		{
			$motDePasse = $_POST['password'];
			if(isset($_POST['photo']))
			{
				$photo = $_POST['photo'];
			}
			else
			{
				//TODO changer l'url
				$photo = 'urlImageDeBase';
			}
			$user = new Utilisateur(Utilisateur::getNewId($_SESSION['Connexion']),$_POST['pseudo'], $_POST['nom'], $_POST['prenom'], $_POST['ville'], $_POST['mail'],0, $_POST['confidentialite'], $_POST['annee'].'-'.$_POST['mois'].'-'.$_POST['jour'], $photo);
			$res = Utilisateur::addUserToDataBase($_SESSION['Connexion'], $user, $motDePasse);
			if($res)
			{
				header("Location: connexion.php");
				exit();
			}
			else
			{
				$existingLogin = 'Il existe deja un utilisateur avec le meme login';
			}
		}
		else
		{
			$erreur = 'Les deux mots de passe ne correspondent pas';
		}
	}
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta name="author" content="page de connexion">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>
			Inscription
		</title>
	</head>
	<body>
		<form method="post" action="inscription.php">
			Pseudo : 
			<input type="text" name="pseudo" required="required">
			<?php
				if(isset($existingLogin))
				{
					//TODO afficher en rouge
					echo "$existingLogin";
				}
			?>
			<br>
			Mot de passe : 
			<input type="password" name="password" title="password" required="required">
			<br>
			Entrer a nouveau : 
			<input type="password" name="cpassword" title="confirm password" required="required">
			<?php
				if(isset($erreur))
				{
					echo "$erreur";
					echo '<br>';
				}
			?>
			<br>
			Nom : 
			<input type="text" name="nom" required="required">
			<br>
			Prenom : 
			<input type="text" name="prenom" required="required">
			<br>
			Ville : 
			<input type="text" name="ville" required="required">
			<br>
			Mail : 
			<input type="text" name="mail" required="required">
			<br>
			Souhaitez vous que les autres utilisateurs puissent voir vos listes ?
			<input type="radio" name="confidentialite" value="1" id="oui" checked="checked" /> <label for="oui">Oui</label>
			<input type="radio" name="confidentialite" value="0" id="non" /> <label for="non">Non</label>
			<br>
			Date de naissance : 
			<select name="jour">
				<option value="1" selected="selected">1</option>
				<?php
					for($i = 2; $i <= 31; $i++)
					{
						echo "<option value=$i> $i </option>";
					}
				?>
			</select>
			<select name="mois">
				<option value="1" selected="selected">1</option>
				<?php 
					for($i=2;$i<=12;$i++)
					{
						echo "<option value=$i> $i </option>";
					}
				?>
			</select>
			<select name="annee">
				<?php
					$y = date('Y');
					echo "<option value=$y selected=selected> $y </option>";
					for($i=$y-1 ; $i>$y-100 ; $i--)
					{
						echo "<option value=$i> $i </option>";
					}
				?>
			</select>
			<br>
			URL photo :
			<input type="text" name="photo">
			<br>
			<br>
			<input type="submit" name="Inscription" value="Inscription">
		</form>
	</body>
</html>