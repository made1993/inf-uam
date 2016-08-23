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
			<form id="id3" action="busqueda.php" method="post" >
			Titulo:<input type="text" name="titulo1" id="titulo">
			Categoria:<input type="text" name="categoria1" id="categoria">
			Precio:<input type="number" name="precio1" id="precio">
			<input type="submit" value="submit">
			</form>
		</div>
		<div id="ppagina"><?php echo date("d-m-Y H:i:s"); ?></div>
	</body>
</html>
