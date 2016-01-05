<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	$user = $_SESSION['user'];
	$erreur = "";
	
	if(isset($_POST['btnFormAddCadeau']))
	{
		$formAddCadeau = true;
		$idListe = $_POST['idListe'];
		$modifEvent = 0;
	}
	else if(isset($_POST['addCadeau']))
	{
		$idListe = $_POST['idListe'];
		$formAddCadeau = false;
		$modifEvent = 0;
		
		$cadeau = new Cadeau(Cadeau::getNewId($_SESSION['Connexion']),$_POST['nomCadeau'],$_POST['description'],$_POST['lien'],$_POST['type']);
		Cadeau::addCadeau($_SESSION['Connexion'],$cadeau);
		ListeCadeaux::addContient($_SESSION['Connexion'],$idListe,$cadeau->getIdCadeau());
		
		$activite = new Activite(Activite::getNewId($_SESSION['Connexion']),"ajoute","un cadeau");
		Activite::addActivite($_SESSION['Connexion'],$activite);
		ActivitesListe::addActivitesListe($_SESSION['Connexion'],$activite->idActivite,$user->id,$idListe);
	}
	else if(isset($_POST['btnSupCadeau']))
	{
		$idListe = $_POST['idListe'];
		$formAddCadeau = false;
		$modifEvent = 0;
		
		Contient::delContient($_SESSION['Connexion'], $_POST['btnSupCadeau']);
		Cadeau::delCadeau($_SESSION['Connexion'], $_POST['btnSupCadeau']);
		
		$activite = new Activite(Activite::getNewId($_SESSION['Connexion']),"supprime","un cadeau");
		Activite::addActivite($_SESSION['Connexion'],$activite);
		ActivitesListe::addActivitesListe($_SESSION['Connexion'],$activite->idActivite,$user->id,$idListe);
	}
	else if(isset($_POST['delListe']))
	{
		$idListe = $_POST['idListe'];
		$formAddCadeau = false;
		$modifEvent = 0;
		
		$tabIdCadeau = Contient::getIdCadeaux($_SESSION['Connexion'], $idListe);
		foreach($tabIdCadeau as $element)
		{
			Contient::delContient($_SESSION['Connexion'], $element);
			Cadeau::delCadeau($_SESSION['Connexion'], $element);
		}
		
		ListeCadeaux::delListe($_SESSION['Connexion'], $idListe);
		
		$activite = new Activite(Activite::getNewId($_SESSION['Connexion']),"supprime","une liste");
		Activite::addActivite($_SESSION['Connexion'],$activite);
		ActivitesListe::addActivitesListe($_SESSION['Connexion'],$activite->idActivite,$user->id,$idListe);
	}
	else if(isset($_POST['ModifListe']))
	{
		$idListe = $_POST['idListe'];
		$formAddCadeau = false;
		$modifEvent = 1;
	}
	else if(isset($_POST['AnnulerModif']))
	{
		$idListe = $_POST['idListe'];
		$formAddCadeau = false;
		$modifEvent = 0;
	}
	else if(isset($_POST['ValiderModif']))
	{
		$idListe = $_POST['idListe'];
		$formAddCadeau = false;
		
		if($_POST['jour'] === "" || $_POST['mois'] === "" || $_POST['annee'] === "")
		{
			$erreur = "La date de l'évènement est incorrecte.";
			$modifEvent = 1;
		}
		else
		{
			$modifEvent = 0;
			
			$idEvenement = ListeCadeaux::getIdEvenementBdd($_SESSION['Connexion'], $idListe);
			$dateLimite = $_POST['annee'].'-'.$_POST['mois'].'-'.$_POST['jour'];
			$evenement = new Evenement($idEvenement,"$_POST[nomEvent]", $dateLimite, "$_POST[commentaire]","$_POST[event]",$user->id);
			Evenement::updateEvent($_SESSION['Connexion'], $evenement);
		}
	}
	else if(isset($_POST['event']))
	{
		$idListe = $_POST['idListe'];
		$formAddCadeau = false;
		$modifEvent = 1;
	}
	else
	{
		$idListe = $_POST['btnListe'];
		$formAddCadeau = false;
		$modifEvent = 0;
	}
	
	$idEvenement = ListeCadeaux::getIdEvenementBdd($_SESSION['Connexion'], $idListe);
	$monEvent = Evenement::getEvent($_SESSION['Connexion'], $idEvenement);
	$jour = "";
	$mois = "";
	$annee = "";
	
	if ($modifEvent && isset($_POST['event']))
	{
		$nomListe = $_POST['nomEvent'];
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
		$nomListe = $monEvent->getNom();
		$event = $monEvent->getNomType();
		$commentaire = $monEvent->getCommentaire();
		
		$date = explode('-',$monEvent->getDateLimite());
		$annee = $date[0];
		$mois = $date[1];
		$jour = $date[2];
	}
	
	if(!isset($_POST['delListe']))
	{
		$tabContients = Contient::getContients($_SESSION['Connexion'],$idListe);
	}
?>


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
		<title>
			Ma liste
		</title>
	</head>
	<body>
		<?php 
			include_once('menuPrincipal.php');
		?>
	
		<div id="titre">
			<?php
				echo "$nomListe";
			?>
		</div>
		
		<table id="principal">
			<tr>
				<td>
					<?php include_once('sousMenu.php');?>
				</td>
				<td>
					<div id="contentAvecMenu">
						<?php
							if(isset($_POST['delListe']))
							{
								echo "La liste ".htmlspecialchars($nomListe)." a été supprimée";
							}
							else
							{
								echo "<form id=maListe method=post action=maListe.php>";
								if($modifEvent == 0)
								{
									echo "<table>";
										echo "<tr><td><input type=text name=nomEvent value=\"".htmlspecialchars($nomListe)."\" disabled=disabled></td></tr>";
										echo "<tr><td>";
											echo "<select name=event onchange=submitEvent() disabled=disabled>";
												$tabTypeEvents = Evenement::getTypeEvents($_SESSION['Connexion']);
												foreach($tabTypeEvents as $typeEvent)
												{
													if($event != $typeEvent)
													{
														echo "<option value=\"".htmlspecialchars($typeEvent)."\">".htmlspecialchars($typeEvent)."</option>";
													}
													else
													{
														echo "<option value=\"".htmlspecialchars($typeEvent)."\" selected=selected>".htmlspecialchars($typeEvent)."</option>";
													}
												}
											echo "</select>";
											echo "<script>";
												echo "function submitEvent()";
												echo "{";
													echo "document.getElementById('maListe').submit();";
												echo "}";
											echo "</script>";
										echo "</td></tr>";
										echo "<tr><td>";
											echo "<select name=jour disabled=disabled>";
												echo "<option value=\"\" selected=selected></option>";
												for($i = 1; $i <= 31; $i++)
												{
													if($jour != $i)
													{
														echo "<option value=\"$i\"> $i </option>";
													}
													else
													{
														echo "<option value=\"$i\" selected=selected> $i </option>";
													}
												}
											echo "</select>";
											echo "<select name=mois disabled=disabled>";
												echo "<option value=\"\" selected=selected></option>";
												for($i=1;$i<=12;$i++)
												{
													if($mois != $i)
													{
														echo "<option value=\"$i\"> $i </option>";
													}
													else
													{
														echo "<option value=\"$i\" selected=selected> $i </option>";
													}
												}
											echo "</select>";
											echo "<select name=annee disabled=disabled>";
												echo "<option value=\"\" selected=selected></option>";
												$y = date('Y');
												for($i=$y+5 ; $i>$y-100 ; $i--)
												{
													if($annee != $i)
													{
														echo "<option value=\"$i\"> $i </option>";
													}
													else
													{
														echo "<option value=\"$i\" selected=selected> $i </option>";
													}
												}
											echo "</select>";
										echo "</td></tr>";
										echo "<tr><td><textarea name=commentaire rows=6 cols=50 maxlength=300 placeholder=Commentaire disabled=disabled>".htmlspecialchars($commentaire)."</textarea></td></tr>";
										echo "<tr><td><button class=btnValidation name=ModifListe onclick=goToList()>Mofidier la liste</button></td></tr>";
									echo "</table>";
								}
								else
								{
									if($erreur != "")
									{
										echo "<span class=erreur>$erreur</span><br><br>";
									}
									echo "<table>";
									echo "<tr><td><input type=text name=nomEvent value=\"".htmlspecialchars($nomListe)."\"></td></tr>";
									echo "<tr><td>";
									echo "<select name=event onchange=submitEvent()>";
									$tabTypeEvents = Evenement::getTypeEvents($_SESSION['Connexion']);
									foreach($tabTypeEvents as $typeEvent)
									{
										if($event != $typeEvent)
										{
											echo "<option value=\"".htmlspecialchars($typeEvent)."\">".htmlspecialchars($typeEvent)."</option>";
										}
										else
										{
											echo "<option value=\"".htmlspecialchars($typeEvent)."\" selected=selected>".htmlspecialchars($typeEvent)."</option>";
										}
									}
									echo "</select>";
									echo "<script>";
										echo "function submitEvent()";
										echo "{";
											echo "document.getElementById('maListe').submit();";
										echo "}";
									echo "</script>";
									echo "</td></tr>";
									echo "<tr><td>";
									echo "<select name=jour>";
									echo "<option value=\"\" selected=selected></option>";
									for($i = 1; $i <= 31; $i++)
									{
										if($jour != $i)
										{
											echo "<option value=\"$i\"> $i </option>";
										}
										else
										{
											echo "<option value=\"$i\" selected=selected> $i </option>";
										}
									}
									echo "</select>";
									echo "<select name=mois>";
									echo "<option value=\"\" selected=selected></option>";
									for($i=1;$i<=12;$i++)
									{
										if($mois != $i)
										{
											echo "<option value=\"$i\"> $i </option>";
										}
										else
										{
											echo "<option value=\"$i\" selected=selected> $i </option>";
										}
									}
									echo "</select>";
									echo "<select name=annee>";
									echo "<option value=\"\" selected=selected></option>";
									$y = date('Y');
									for($i=$y+5 ; $i>$y-100 ; $i--)
									{
										if($annee != $i)
										{
											echo "<option value=\"$i\"> $i </option>";
										}
										else
										{
											echo "<option value=\"$i\" selected=selected> $i </option>";
										}
									}
									echo "</select>";
									echo "</td></tr>";
									echo "<tr><td><textarea name=commentaire rows=6 cols=50 maxlength=300 placeholder=Commentaire>".htmlspecialchars($commentaire)."</textarea></td></tr>";
									echo "<tr><td><button class=btnValidation name=ValiderModif onclick=goToList()>Valider</button>  <button class=btnValidation name=AnnulerModif onclick=goToList()>Annuler</button></tr>";
									echo "</table>";
								}
								echo "<br>";
								echo "<br>";
								echo "<br>";
								echo "<input type=hidden name=idListe value=$idListe/>";
								if($formAddCadeau)
								{
									echo "<br>";
									echo "<table>";
										echo '<tr><td><input type=text name=nomCadeau placeholder="Nom du cadeau"></td></tr>';
										echo "<tr><td>";
											echo "<select name=type>";
												$tabTypeCadeaux = Cadeau::getTypeCadeaux($_SESSION['Connexion']);
												foreach($tabTypeCadeaux as $typeCadeau)
												{
													echo "<option value=\"".htmlspecialchars($typeCadeau)."\">".htmlspecialchars($typeCadeau)."</option>";
												}
											echo "</select>";
										echo "</td></tr>";
										echo '<tr><td><input type=text name=lien placeholder="Lien"></td></tr>';
										echo "<tr><td><textarea name=description rows=6 cols=50 maxlength=300 placeholder=Description></textarea></td></tr>";
										echo "<tr><td><button class=btnValidation name=addCadeau onclick=goToList()>Ajouter le cadeau</button>  <button class=btnValidation name=AnnulerModif onclick=goToList()>Annuler</button></td></tr>";
									echo "</table>";
									echo "<br><br>";
										
									echo "<table id=maListeCadeaux>";
								}
								else
								{
									echo "<table id=maListeCadeaux>";
									echo "<tr id=ligneAjouter><td colspan=2><button class=btnFormAddCadeau name=btnFormAddCadeau value=$idListe onclick=goToList()>Ajouter un cadeau</button></td></tr>";
								}
								foreach($tabContients as $element)
								{
									$idCadeau = $element->idCadeau;
									$nomCadeau = Cadeau::getNomBdd($_SESSION['Connexion'], $idCadeau);
									$nomReserve = Utilisateur::getPseudo($_SESSION['Connexion'], $element->reservePar);
									if($nomReserve != "")
									{
										$nomReserve = "reserve par<br>".htmlspecialchars($nomReserve);
										echo "<tr id=ligneReserve><td>".htmlspecialchars($nomCadeau)."</td><td><span class=reserve>$nomReserve</span></td><td><button class=btnMesCadeauxR name=btnSupCadeau value=$idCadeau onclick=goToList()>X</button</td></tr>";
									}
									else
									{
										echo "<tr id=ligneNonReserve><td>".htmlspecialchars($nomCadeau)."</td><td>$nomReserve</td><td><button class=btnMesCadeaux name=btnSupCadeau value=$idCadeau onclick=goToList()>X</button></td></tr>";
									}
								}
								echo "</table>";
								echo "<br><br>";
								echo "<table id=btnCreerListe>";
								echo "<tr><td><button class=btnValidation name=delListe onclick=goToList()>Supprimer la liste</button></td></tr>";
								echo "</table>";
								echo "<script>";
									echo "function goToList()";
									echo "{";
									echo "document.getElementById('mesListes').submit();";
									echo "}";
								echo "</script>";
								echo "</form>";
							}
						?>
						<br>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
</html>