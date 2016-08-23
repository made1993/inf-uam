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
			if($usuario != null) echo '<br>'.$usuario.'<form action="logout.php"><input type="submit" value="Salir"></form>';
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
		session_start();
		$dbuser='alumnodb';
		$dbpass='alumnodb';
		$hostname='localhost';
		$dbname='SI_P3';
		try{
			$db = new PDO("pgsql:dbname=$dbname; host=$hostname",$dbuser,$dbpass);
		}
		catch(PDOException $e){
			echo $e->getMessage();
		}
		$nombre=$_POST['nombre'];
		$pass=$_POST['password'];
		$pass= md5($pass);$query ="select count(customerid) as numero from customers 
		where username= '".$nombre."' and password= '".$pass."' ";
		$_SESSION['query']=$query;
		foreach($db->query($query)as $row){
			$num=$row['numero'];
		}
		if($num == 1){
			$query="select customerid from customers where
			username= '".$nombre."' and password= '".$pass."'";
			foreach ($db->query($query) as $row) {
				$id=$row['customerid'];
			}
			$usr[0]=$nombre;
			$usr[1]=$id;
			$_SESSION['usuario']=$usr;
			echo "Bienvenido ".$nombre;
		}
		else{
			$_SESSION['error']='login';
			header('Location: error.php');
		}
		$db=null;
	?>

	</div>
	<div id="ppagina"><?php echo date("d-m-Y H:i:s"); ?></div>
</body>
</html>

