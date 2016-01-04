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
		$userToDisplay = Utilisateur::getUserToDisplay($_SESSION['Connexion'], $_GET['user']);
	}
	
	if(isset($_GET['annee']))
	{
		$annee = $_GET['annee'];
		
		if($annee == "Annee")
		{
			$liste = "Liste";
		}
		else
		{
			$liste = $_GET['liste'];
		}
	}
	else
	{
		$annee = "Annee";
		$liste = "Liste";
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
			Listes de <?php echo $_GET['user']; ?>
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
								echo "<li><a href=searchUser.php?user="."$_GET[user]".">Profil</a></li>";
								echo "<li><a href=listesUser.php?user="."$_GET[user]".">Liste</a></li>";
							?>
						</ul>
					</div>
				</td>
				<td>
					<div id="contentAvecMenu">
						<?php
							echo "<form id=listesUser method=get action=listesUser.php>";
								echo "<input type=hidden name=user value=\"$userToDisplay->pseudo\"/>";
								echo "<table>";
									// les annees
									echo "<tr><td>";
										echo "<select name=annee onchange=submitPage()>";
											echo "<option value=Annee selected=selected> Annee </option>";
											$tabAnnees = Evenement::getYears($_SESSION['Connexion'],$userToDisplay->id);
											foreach($tabAnnees as $element)
											{
												if($annee == $element)
												{
													echo "<option value=\"$element\" selected=selected> $element </option>";
												}
												else
												{
													echo "<option value=\"$element\"> $element </option>";
												}
											}
										echo "</select>";
									echo "</td></tr>";
									// les listes
									echo "<tr><td>";
										echo "<select name=liste onchange=submitPage()>";
											echo "<option value=Liste selected=selected> Liste </option>";
											$tabListes = Evenement::getIds($_SESSION['Connexion'],$userToDisplay->id,$annee);
											foreach($tabListes as $cle => $element)
											{
												if($liste == $cle)
												{
													echo "<option value=\"$cle\" selected=selected> $element </option>";
												}
												else
												{
													echo "<option value=\"$cle\"> $element </option>";
												}
											}
										echo "</select>";
										echo "<script>";
											echo "function submitPage()";
											echo "{";
												echo "document.getElementById('listesUser').submit();";
											echo "}";
										echo "</script>";
									echo "</td></tr>";
								echo "</table>";
								if($liste == "Liste")
								{
									echo "<table>";
										$idEvent = ListeCadeaux::getIdEvenementBdd($_SESSION['Connexion'], $liste);
										$evenement = Evenement::getEvent($_SESSION['Connexion'],$idEvent);
										$nomEvent = $evenement->getNom();
										$dateEvent = $evenement->getDateLimite();
										$typeEvent = $evenement->getNomType();
										$commentaireEvent = $evenement->getCommentaire();
										echo "<tr><td><input type=text name=nomEvent value=\"$nomEvent\" disabled=disabled></td></tr>";
										echo "<tr><td><input type=text name=dateLimite value=\"$dateEvent\" disabled=disabled></td></tr>";
										echo "<tr><td><input type=text name=eventType value=\"$typeEvent\" disabled=disabled></td></tr>";
										echo "<tr><td><textarea name=commentaire rows=6 cols=50 maxlength=300 disabled=disabled>$commentaireEvent</textarea></td></tr>";
									echo "</table>";
									// les cadeaux
									echo "<table id=sesCadeaux>";
										$tabContients = Contient::getContients($_SESSION['Connexion'],$liste);
										foreach($tabContients as $element)
										{
											$idCadeau = $element->idCadeau;
											$nomCadeau = Cadeau::getNomBdd($_SESSION['Connexion'], $idCadeau);
											$nomReserve = Utilisateur::getPseudo($_SESSION['Connexion'], $element->reservePar);
											if($nomReserve != "")
											{
												$nomReserve = "reserve par<br>$nomReserve";
												echo "<tr id=ligneReserve><td>$nomCadeau</td><td><span id=reserve>$nomReserve</span></td><td><button class=btnMesCadeauxR name=btnSupCadeau value=$idCadeau onclick=goToList()>X</button</td></tr>";
											}
											else
											{
												echo "<tr id=ligneNonReserve><td>$nomCadeau</td><td>$nomReserve</td><td><button class=btnMesCadeaux name=btnSupCadeau value=$idCadeau onclick=goToList()>X</button></td></tr>";
											}
										}
									echo "</table>";
								}
								else
								{
									echo "Aucune liste selectionnee";
								}
							echo "</form>";
						?>
					</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>