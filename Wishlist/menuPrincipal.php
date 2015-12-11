<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <meta http-equiv="Content-Language" content="fr" />
	    <link rel="Stylesheet" type="text/css" href="interfaceAvecMenu.css" />
	</head>
	<body>
		<header id="headerMenu">
			<br />
			<table id="menuPrincipal">
				<tr>
					<td><a href="accueil.php">W.</a></td>
					<td></td>
					<td><input type="text" name="recherche" id="recherche" placeholder="Cherchez des personnes" size="50"/></td>
					<td><input type="button" name="ok" value="Ok"></td>
					<td></td>
					<td><a href="accueil.php">Accueil</a></td>
					<td><a href="infoPerso.php">Profil personnel</a>
						<ul>
							<li><a href="infoPerso.php">Informations</a></li>
							<li><a href="listeCadeau.php">Listes</a></li>
							<li><a href="follow.php">Follow</a></li>
						</ul>
					</td>
					<td><a href="messagerie.php">Messagerie</a></td>
					<td><a href="notifications.php">Notifications</a></td>
				</tr>
			</table>
			<br />
		</header>
		
		<div id="titre">
		Profil personnel
		</div>
		
		<table id="principal" width=44%  height=50%>
			<tr>
				<td width=15%>
					<div id="sousMenu">
						<ul>
							<li><a href="infoPerso.php">Informations</a></li>
							<li><a href="listeCadeau.php">Listes</a></li>
							<li><a href="follow.php">Follow</a></li>
						</ul>
					</div>
				</td width=85%>
				<td>
					<div id="contentAvecMenu">
					blablabla
					</div>
				</td>
			</tr>
		</table>
	</body>
	
</html>