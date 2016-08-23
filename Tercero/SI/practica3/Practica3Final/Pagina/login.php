<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
	
	<div id="login">
		<?php 
			session_start();
			$usuario=$_SESSION['usuario'];
			if($usuario != null) echo '<br>'.$usuario[0].'<form action="logout.php"><input type="submit" value="Salir"></form>';
			else{
				echo '<form action="login.php"><input type="submit" value="Login"></form>';
				echo '<form action="registrop.php"><input type="submit" value="Registrarse"></form>';
			}
		?>
		</div>
		<div id="cabecera"><a href="index.php"><h1>Alquiler</h1></div>	

		<div id="mlateral"><h2>
			<h2><a href="busquedap.php">Busqueda</a></h2>
			<h2><a href="busqueda.php">Catalogo</a></h2>
			<h2><a href="historial.php">Historial</a></h2>
		</div>  

	<div id="contenido">
	<form action="loginV.php" id="form2" method="POST">
	User:<input name="nombre"> <br>
	Password:<input type="password" name="password">
	<input type="submit" value="Submit">
	
	</form>
</div>

	<div id="ppagina"><?php echo date("d-m-Y H:i:s"); ?></div>
</body>
</html>
