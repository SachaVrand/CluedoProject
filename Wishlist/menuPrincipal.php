<?php
	if($_SERVER['REQUEST_URI'] == '/ProjectL3I/Wishlist/menuPrincipal.php')
	{
		exit;
	}
?>

<header id="headerMenu">
	<br />
	<form method="get" action="searchUser.php">
	<table id="menuPrincipal">
		<tr>
			<td><a href="fluxActiviteFollowing.php">W.</a></td>
			<td></td>
			<td><input type="text" name="user" id="recherche" placeholder="Cherchez des personnes" size="50"/></td>
			<td><input type="submit" name="ok" value="Ok"></td>
			<td></td>
			<td><a href="fluxActiviteFollowing.php">Accueil</a></td>
			<td><a href="infosPerso.php">Profil personnel</a></td>
			<td><a href="pageMessagerie.php">Messagerie</a></td>
			<td><a href="notifications.php">Notifications</a></td>
			<td><a href="deconnexion.php">Déconnexion</a></td>
		</tr>
	</table>
	</form>
	<br />
</header>