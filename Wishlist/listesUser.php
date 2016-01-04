<?php
	function chargerClasse($classe)
	{
		require $classe . '.php';
	}
	spl_autoload_register('chargerClasse');
	session_start();
	
	$user = $_SESSION['user'];
	$formReserve = 0;
	
	// si je reçoit que idListe
	if(count($_GET) == 1 && isset($_GET['idListe']))
	{
		$_SESSION['liste'] = $_GET['idListe'];
		$pseudoUser = Utilisateur::getPseudoIdListe($_SESSION['Connexion'],$_SESSION['liste']);
		$userToDisplay = Utilisateur::getUserToDisplay($_SESSION['Connexion'], $pseudoUser);
		$_SESSION['annee'] = Evenement::getYearIdListe($_SESSION['Connexion'],$_SESSION['liste']);
	}
	else 
	{
		if(!isset($_GET['user']))
		{
			exit();
		}
		else
		{
			$userToDisplay = Utilisateur::getUserToDisplay($_SESSION['Connexion'], $_GET['user']);
		}
		
		if(!isset($_GET['annee']))
		{
			$_SESSION['annee'] = "Annee";
			$_SESSION['liste'] = "Liste";
		}
		
		if(isset($_GET['annee']) || isset($_GET['btnReserve']))
		{	
			if($_SESSION['annee'] != $_GET['annee'])
			{
				$_SESSION['liste'] = "Liste";
			}
			else
			{
				$_SESSION['liste'] = $_GET['liste'];
			}
	
			$_SESSION['annee'] = $_GET['annee'];
		}
		else
		{
			$_SESSION['annee'] = "Annee";
			$_SESSION['liste'] = "Liste";
		}
		
		if(isset($_GET['btnReserve']))
		{
			if($user->id != $userToDisplay->id)
			{
				$formReserve = 1;
			}
		}
		
		if(isset($_GET['btnValideReserve']))
		{
			Contient::reserveCadeau($_SESSION['Connexion'],$_GET['btnValideReserve'],$user->id);
			
			$activite = new Activite(Activite::getNewId($_SESSION['Connexion']),"reserve","un cadeau");
			Activite::addActivite($_SESSION['Connexion'],$activite);
			ActivitesListe::addActivitesListe($_SESSION['Connexion'],$activite->idActivite,$user->id,$_SESSION['liste']);
		}
		else if(isset($_GET['btnReserveR']))
		{
			$idUserR = Contient::idUserR($_SESSION['Connexion'],$_GET['btnReserveR']);
			if($user->id == $idUserR)
			{
				Contient::dereserveCadeau($_SESSION['Connexion'],$_GET['btnReserveR']);
			}
		}
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
			Listes de <?php echo htmlspecialchars($userToDisplay->pseudo); ?>
		</div>
		<?php 
			if(!$userToDisplay || $userToDisplay->confidentialite == 1 || Utilisateur::isRestrict($_SESSION['Connexion'], $user, $userToDisplay))
			{
				?>
					<div id="contentSansMenu">
					<?php
						if(!$userToDisplay)
						{
							echo "Aucun utilisateur n'a pu etre trouve";
						}
						else if($userToDisplay->confidentialite == 1)
						{
							echo "L'utilisateur ne souhaite pas que l'on puisse avoir acces a son profil";
						}
						else if(Utilisateur::isRestrict($_SESSION['Connexion'], $user, $userToDisplay))
						{
							echo "L'utilisateur ne souhaite pas que vous puissiez acceder a son profil.";
						}
					?>
					</div>
				<?php
				exit();
			}
		?>
		<table id="principal">
			<tr>
				<td>
					<div id="sousMenu">
						<ul>
							<?php
								echo "<li><a href=\"searchUser.php?user=".htmlspecialchars($userToDisplay->pseudo)."\">Profil</a></li>";
								echo "<li><a href=\"listesUser.php?user=".htmlspecialchars($userToDisplay->pseudo)."\">Liste</a></li>";
							?>
						</ul>
					</div>
				</td>
				<td>
					<div id="contentAvecMenu">
						<?php
							if($formReserve == 0)
							{
								echo "<form id=listesUser method=get action=listesUser.php>";
									echo "<input type=hidden name=user value=\"".htmlspecialchars($userToDisplay->pseudo)."\"/>";
									echo "<table>";
										// les annees
										echo "<tr><td>";
											echo "<select name=annee onchange=submitPage()>";
												echo "<option value=Annee selected=selected> Annee </option>";
												$tabAnnees = Evenement::getYears($_SESSION['Connexion'],$userToDisplay->id);
												foreach($tabAnnees as $element)
												{
													if($_SESSION['annee'] == $element)
													{
														echo "<option value=\"".htmlspecialchars($element)."\" selected=selected>".htmlspecialchars($element)."</option>";
													}
													else
													{
														echo "<option value=\"".htmlspecialchars($element)."\">".htmlspecialchars($element)."</option>";
													}
												}
											echo "</select>";
										echo "</td></tr>";
										// les listes
										echo "<tr><td>";
											echo "<select name=liste onchange=submitPage()>";
												echo "<option value=Liste selected=selected> Liste </option>";
												$tabListes = Evenement::getIds($_SESSION['Connexion'],$userToDisplay->id,$_SESSION['annee']);
												foreach($tabListes as $cle => $element)
												{
													if($_SESSION['liste'] == $cle)
													{
														echo "<option value=\"".htmlspecialchars($cle)."\" selected=selected>".htmlspecialchars($element)."</option>";
													}
													else
													{
														echo "<option value=\"".htmlspecialchars($cle)."\">".htmlspecialchars($element)."</option>";
													}
												}
											echo "</select>";
										echo "</td></tr>";
									echo "</table>";
									echo "<br><br>";
									if($_SESSION['liste'] != "Liste")
									{
										echo "<table>";
											$idEvent = ListeCadeaux::getIdEvenementBdd($_SESSION['Connexion'], $_SESSION['liste']);
											$evenement = Evenement::getEvent($_SESSION['Connexion'],$idEvent);
											$dateEvent = $evenement->getDateLimite();
											$typeEvent = $evenement->getNomType();
											$commentaireEvent = $evenement->getCommentaire();
											echo "<tr><td><input type=text name=dateLimite value=\"".htmlspecialchars($dateEvent)."\" disabled=disabled></td></tr>";
											echo "<tr><td><input type=text name=eventType value=\"".htmlspecialchars($typeEvent)."\" disabled=disabled></td></tr>";
											echo "<tr><td><textarea name=commentaire rows=6 cols=50 maxlength=300 disabled=disabled>".htmlspecialchars($commentaireEvent)."</textarea></td></tr>";
										echo "</table>";
										echo "<br><br>";
										// les cadeaux
										$tabContients = Contient::getContients($_SESSION['Connexion'],$_SESSION['liste']);
										if(count($tabContients) != 0)
										{
											echo "<table id=sesCadeaux>";
												foreach($tabContients as $element)
												{
													$idCadeau = $element->idCadeau;
													$nomCadeau = Cadeau::getNomBdd($_SESSION['Connexion'], $idCadeau);
													$nomReserve = Utilisateur::getPseudo($_SESSION['Connexion'], $element->reservePar);
													if($nomReserve != "")
													{
														$nomReserve = "reserve par<br>".htmlspecialchars($nomReserve);
														echo "<tr id=ligneReserve><td><button class=btnReserveR name=btnReserveR value=".htmlspecialchars($idCadeau)." onclick=goToList()>".htmlspecialchars($nomCadeau)."</button></td><td><button class=btnReserveRR name=btnReserveR value=".htmlspecialchars($idCadeau)." onclick=goToList()>$nomReserve</button></td></tr>";
													}
													else
													{
														echo "<tr id=ligneNonReserve><td><button class=btnReserve name=btnReserve value=".htmlspecialchars($idCadeau)." onclick=goToList()>".htmlspecialchars($nomCadeau)."</button></td><td><button class=btnReserve name=btnReserve value=".htmlspecialchars($idCadeau)." onclick=goToList()>$nomReserve</button></td></tr>";
													}
												}
											echo "</table>";
										}
										else
										{
											echo "Cette liste ne possède aucun cadeaux";
										}
									}
									else
									{
										echo "Aucune liste selectionnee";
									}
									echo "<script>";
									echo "function submitPage()";
									echo "{";
									echo "document.getElementById('listesUser').submit();";
									echo "}";
									echo "</script>";
								echo "</form>";
							}
							else if($formReserve == 1)
							{
								echo "<form id=cadeauSelect method=get action=listesUser.php>";
								echo "<input type=hidden name=user value=\"".htmlspecialchars($userToDisplay->pseudo)."\"/>";
								echo "<input type=hidden name=annee value=\"".htmlspecialchars($_SESSION['annee'])."\"/>";
								echo "<input type=hidden name=liste value=\"".htmlspecialchars($_SESSION['liste'])."\"/>";
								echo "<table>";
									$cadeau = Cadeau::getCadeau($_SESSION['Connexion'],$_GET['btnReserve']);
									$nomCadeau = $cadeau->nom;
									$typeCadeau = $cadeau->nomType;
									$lienCadeau = $cadeau->lien;
									$descriptionCadeau = $cadeau->description;
									echo "<tr><td><input type=text name=nomCadeau value=\"".htmlspecialchars($nomCadeau)."\" readonly=readonly></td></tr>";
									echo "<tr><td><input type=text name=typeCadeau value=\"".htmlspecialchars($typeCadeau)."\" readonly=readonly></td></tr>";
									echo "<tr><td><input type=text name=lienCadeau value=\"".htmlspecialchars($lienCadeau)."\" readonly=readonly></td></tr>";
									echo "<tr><td><textarea name=descriptionCadeau rows=6 cols=50 maxlength=300 readonly=readonly>".htmlspecialchars($descriptionCadeau)."</textarea></td></tr>";
								echo "</table>";
								echo "<table>";
									echo "<tr><td><button class=btnValideReserve name=btnValideReserve value=".htmlspecialchars($_GET['btnReserve'])." onclick=goToList()>Réserver</button>  <button class=btnAnnuleReserve name=btnAnnuleReserve value=".htmlspecialchars($_GET['btnReserve'])." onclick=goToList()>Annuler</button></td></tr>";
								echo "</table>";
								echo "<script>";
									echo "function submitPage()";
									echo "{";
										echo "document.getElementById('cadeauSelect').submit();";
									echo "}";
								echo "</script>";
							}
						?>
					</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>