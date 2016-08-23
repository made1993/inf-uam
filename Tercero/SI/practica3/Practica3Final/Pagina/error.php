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
			<h2><a href="busqueda.html">Busqueda</a></h2>
			<h2><a href="busqueda.php">Catalogo</a></h2>
		</div> 
		<div id="contenido">

		<?php 
			session_start();			
			$query=$_SESSION['query'];
			echo $query;
			$err=$_SESSION['error'];
			if(strcmp($err, 'login')==0){
				print("La contrasena no existe o el usuario no esta registrado.\n");
				echo "<a href=\"login.php\"><br>Login</a>";
				echo "<a href=\"registrop.php\"><br>Registrarse</a>";
			}
		 ?>
		</div>
		<div id="ppagina"><?php echo date("d-m-Y H:i:s"); ?></div>
	</body>
</html>
