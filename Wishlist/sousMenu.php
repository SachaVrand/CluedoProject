<?php
	if($_SERVER['REQUEST_URI'] == '/ProjectL3I/Wishlist/sousMenu.php')
	{
		exit;
	}
?>
<div id="sousMenu">
	<ul>
		<li><a href="infosPerso.php">Informations</a></li>
		<li><a href="modifInfosPerso.php">Modifier vos informations</a>
		<li><a href="modifMotDePasse.php">Modifier votre mot de passe</a>
		<li><a href="listesPerso.php">Listes</a></li>
		<li><a href="userFollow.php">Follow</a></li>
	</ul>
</div>