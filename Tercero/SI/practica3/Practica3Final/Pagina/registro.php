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

			<?php
			$dbuser='alumnodb';
			$dbpass='alumnodb';
			$hostname='localhost';
			$dbname='SI_P3';
			$default='4';
			try{
				$db = new PDO("pgsql:dbname=$dbname; host=$hostname",$dbuser,$dbpass);
			}
			catch(PDOException $e){
				echo $e->getMessage();
			}
			$nombre=$_POST['name'];
			$pass=$_POST['pass'];
			$passc=$_POST['passc'];
			$mail=$_POST['mail'];
			$card=$_POST['card'];
			echo $pass;
			$pass = md5($pass);

			foreach($db->query("select count(customerid) as numero from customers where username='".$nombre."'")as $row){
				$num=$row['numero'];
			}
			if($num == 0){
				if($nombre==null|| $pass==null || $mail==null || $card==null){
					echo 'No se ha podido registrar un usuario. Faltan datos.';
				}
				else{
					foreach($db->query("select customerid+1 as id from customers order by customerid desc limit(1)")as $row){
					$id=$row['id'];
				}
					$count = $db->exec("INSERT INTO customers VALUES('".$id."','".$nombre."', '".$default."', '".$default."',
					 '".$default."', '".$default."', '".$default."', '".$default."', '".$default."', '".$default."', '".$mail."',
					  '".$default."', '".$default."','".$card."', '".$default."', '".$nombre."', '".$pass."' ,'".$default."',
					   '".$default."','".$default."')");
					echo 'El usuario '.$nombre.' se ha creado correctamente';
				}
			}
			else{
				echo 'Ya existe un usuario con ese nombre de usuario';
			}
	
			$db=null;
			?>
		</div>
		<div id="ppagina"><?php echo date("d-m-Y H:i:s"); ?></div>
	</body>
</html>
