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
	$erreur ="";
	
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
		if($_POST['jour'] === "" || $_POST['mois'] === "" || $_POST['annee'] === "")
		{
			$erreur = "La date de l'évènement est incorrecte.";
		}
		else
		{
			$idListe = ListeCadeaux::getNewId($_SESSION['Connexion']);
			//creer et ajoute l'event
			$evenement = new Evenement(Evenement::getNewId($_SESSION['Connexion']),$_POST['nom'], $_POST['annee'].'-'.$_POST['mois'].'-'.$_POST['jour'], $_POST['commentaire'],$_POST['event'],$user->id);
			Evenement::addEvent($_SESSION['Connexion'], $evenement);
			//creer et ajoute les cadeaux et les listeCadeaux
			foreach($_SESSION['tabCadeaux'] as $element)
			{
				$element->setIdCadeau(Cadeau::getNewId($_SESSION['Connexion']));
				Cadeau::addCadeau($_SESSION['Connexion'],$element);
				$listeCadeau = new ListeCadeaux($idListe,$element->getIdCadeau(),$evenement->getIdEvenement(),$user->id,'NULL');
				ListeCadeaux::addListeCadeaux($_SESSION['Connexion'],$listeCadeau);
			}
			// creer et ajoute l'activité
			$_SESSION['tabCadeaux'] = array();
		}
	}
	elseif (isset($_POST['btnSup']))
	{
		$nomEvent = $_POST['nom'];
		$event = $_POST['event'];
		$jour = $_POST['jour'];
		$mois = $_POST['mois'];
		$annee = $_POST['annee'];
		$commentaire = $_POST['commentaire'];
		unset($_SESSION['tabCadeaux'][$_POST['btnSup']]);
	}
	else
	{
		if (isset($_POST['event']))
		{
			$nomEvent = $_POST['nom'];
			$event = $_POST['event'];
			$annee = date('Y');
			$commentaire = $_POST['commentaire'];
			
			if ($_POST['event'] === "Noel")
			{
				$jour = "24";
				$mois = "12";
			}
			elseif ($_POST['event'] === "Anniversaire")
			{
				$tabDate = explode('-',$user->dateNaissance);
				$mois = $tabDate[1];
				$jour = $tabDate[2];
			}
			if ($mois != "" && date('m') > $mois)
			{
				$annee += 1;
			}
			if ($_POST['event'] != "Noel" && $_POST['event'] != "Anniversaire")
			{
				$annee = 0;
			}
		}
		else 
		{
			$_SESSION['tabCadeaux'] = array();
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
						<span class="grandTitre">Ma Liste</span>
						<hr>
						<form id="formCreerListe" method="post" action="creerListe.php">
							<table>
								<tr>
									<td>
									Nom : 
										<input type="text" name="nom" value=<?php echo "$nomEvent"; ?>>
									</td>
								</tr>
								<tr>
									<td>
									Evenement : 
										<select name="event" onchange="submitEvent();">
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
									</td>
								</tr>
								<tr>
									<td>
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
									</td>
								</tr>
								<tr>
									<td>
									Commentaire : 
									<br>
									<textarea name="commentaire" rows=6 cols=50 maxlength="300" placeholder="Commentaire"><?php if($commentaire != "") echo "$commentaire"; ?></textarea>
									</td>
								</tr>
							</table>
							<br>
							<br>
							<span class="grandTitre">Ajouter Cadeau</span>
							<br>
							<br>
							<table>
								<tr>
									<td>
									Nom cadeau :
									<input type="text" name="nomCadeau">
									</td>
								</tr>
								<tr>
									<td>
									Type :
									<select name="type">
										<?php
												$tabTypeCadeaux = Cadeau::getTypeCadeaux($_SESSION['Connexion']);
												foreach($tabTypeCadeaux as $typeCadeau)
												{
													echo "<option value=$typeCadeau> $typeCadeau </option>";
												}
										?>
									</select>
									</td>
								</tr>
								<tr>
									<td>
									Lien du cadeau :
									<input type="text" name="lien">
									</td>
								</tr>
								<tr>
									<td>
									Description : 
									<br>
									<textarea name="description" rows=6 cols=50 maxlength="300" placeholder="Description"></textarea>
									</td>
								</tr>
								<tr>
									<td>
									<input type="submit" name="addCadeau" value="Ajouter">
									</td>
								</tr>
							</table>
							<br>
							<br>
							<span class="moyenTitre">Mes Cadeaux</span>
							<br>
							<br>
							<form id="mesCadeaux" method="post" action="creerListe.php">
								<?php 
									echo "<table id=listeCadeaux>";
									foreach($_SESSION['tabCadeaux'] as $cle => $element)
									{
										$tmpNom = $element->getNom();
										echo "<tr><td>$tmpNom</td><td><button class=btnSupCadeau name=btnSup value=$cle onclick=supCadeau()>X</button</td></tr>";
									}
									echo "</table>";
								?>
								<script>
									function supCadeau()
									{
										document.getElementById('mesCadeaux').submit();
									}
								</script>
							<br>
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