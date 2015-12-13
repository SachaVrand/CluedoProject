<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	$user = $_SESSION['user'];
	
	$nomEvent = "";
	$event = "";
	$jour = "";
	$mois = "";
	$annee = "";
	$commentaire = "";
	
	if (!isset($_POST['addCadeau']))
	{
		$_SESSION['tabCadeaux'] = array();
	}
	

	if (isset($_POST['addCadeau']))
	{
		$nomEvent = $_POST['nom'];
		$event = $_POST['event'];
		$jour = $_POST['jour'];
		$mois = $_POST['mois'];
		$annee = $_POST['annee'];
		$commentaire = $_POST['commentaire'];
		$_SESSION['tabCadeaux'][] = new Cadeau(-1,$_POST['nomCadeau'],$_POST['description'],$_POST['lien'],$_POST['type']);
	}
	elseif (isset($_POST['addListe']))
	{
		// j'ai cliqué sur « ajouter liste »
	}
	else
	{
		if (isset($_POST['event']))
		{
			$nomEvent = $_POST['nom'];
			$event = $_POST['event'];
			$annee = date('Y');
			if (date('M') > $mois)
			{
				$annee += 1;
			}
			$commentaire = $_POST['commentaire'];
			
			if ($_POST['event'] === "Noel")
			{
				$jour = "24";
				$mois = "12";
			}
			elseif ($_POST['event'] === "Anniversaire")
			{
				$tabDate = explode('-',$user->getDateNaissance());
				$mois = $tabDate[1];
				$jour = $tabDate[2];
			}
			elseif( $_POST['event'] === "")
			{
				$jour = "0";
				$mois = "0";
				$annee = "0";
			}
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
			Creer une liste de cadeaux
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
								echo "<h1 style='color:black;'>$notif</h1>";
							}
						?>
						<form id="formCreerListe" method="post" action="creerListe.php">
							Nom : 
								<input type="text" name="nom" required="required" value=<?php echo "$nomEvent"; ?>>
							<br>
							Evenement : 
								<select name="event" onchange="submitEvent();">
									<option value="" selected="selected"></option>
									<?php
											$tabTypeEvents = Evenement::getTypeEvents($_SESSION['Connexion']);
											foreach($tabTypeEvents as $typeEvent)
											{
												if($event != $typeEvent)
												{
													echo "<option value=$typeEvent> $typeEvent </option>";
												}
												else
												{
													echo "<option value=$typeEvent selected=selected> $typeEvent </option>";
												}
											}
									?>
								</select>
								<script>
									function submitEvent()
									{
										document.getElementById('formCreerListe').submit();
									}
								</script>
							<br>
							Date : 
								<select name="jour">
									<option value="" selected="selected"></option>
									<?php
										for($i = 1; $i <= 31; $i++)
										{
											if($jour != $i)
											{
												echo "<option value=$i> $i </option>";
											}
											else
											{
												echo "<option value=$i selected=selected> $i </option>";
											}
										}
									?>
								</select>
								<select name="mois">
									<option value="" selected="selected"></option>
									<?php 
										for($i=1;$i<=12;$i++)
										{
											if($mois != $i)
											{
												echo "<option value=$i> $i </option>";
											}
											else
											{
												echo "<option value=$i selected=selected> $i </option>";
											}
										}
									?>
								</select>
								<select name="annee">
									<option value="" selected="selected"></option>
									<?php
										$y = date('Y');
										for($i=$y+5 ; $i>$y-100 ; $i--)
										{
											if($annee != $i)
											{
												echo "<option value=$i> $i </option>";
											}
											else
											{
												echo "<option value=$i selected=selected> $i </option>";
											}
										}
									?>
								</select>
							<br>
							Commentaire : 
							<br>
							<textarea name="commentaire" rows=6 cols=50 maxlength="300" placeholder="Commentaire"><?php if($commentaire != "") echo "$commentaire"; ?></textarea>
							<br>
							<br>
							<br>
							<br>
							Nom cadeau :
							<input type="text" name="nomCadeau" required="required">
							<br>
							Type :
							<select name="type">
								<?php
										$tabTypeCadeaux = Cadeau::getTypeCadeaux($_SESSION['Connexion']);
										foreach($tabTypeCadeaux as $typeCadeau)
										{
											echo "<option value=$typeCadeau selected=selected> $typeCadeau </option>";
										}
								?>
							</select>
							<br>
							Lien du cadeau :
							<input type="text" name="lien">
							<br>
							<br>
							Description : 
							<textarea name="description" rows=6 cols=50 maxlength="300" placeholder="Description"></textarea>
							<br>
							<input type="submit" name="addCadeau" value="ajouter">
							<br>
							<br>
							<select name="mesCadeaux">
								<?php
									foreach($_SESSION['tabCadeaux'] as $cadeau)
									{
										$tmpNom = $cadeau->getNom();
										echo "<option value=$tmpNom selected=selected> $tmpNom </option>";
									}
								?>
							</select>
							<br>
							<br>
							<input type="submit" name="addListe" value="Creer liste">
						</form>
					
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>