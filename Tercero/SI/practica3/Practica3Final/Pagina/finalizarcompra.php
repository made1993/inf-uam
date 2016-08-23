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
		session_start();
		$usuario=$_SESSION['usuario'];
		$orderid=$_SESSION['orderid'];
		$ruta= 'usuarios/' . $usuario."/historial.xml";
		if($usuario == null){
			echo "Necesitas iniciar sesion o registrarte en caso de que no tengas cuenta";
			echo '<form action="login.php"><input type="submit" value="Login"></form>';
			echo '<form action="registrop.php"><input type="submit" value="Registrarse"></form>';
		}
		else{
			$db =new PDO("pgsql:dbname=SI_P3; host=localhost","alumnodb","alumnodb");
			$query="select prod_id from orderdetail join inventory using (prod_id)
			where orderid=".$orderid." and stock - quantity <=0";
			$stmt=$db->query($query);
			$result = $stmt->setFetchMode(PDO::FETCH_NUM);
			while ($row = $stmt->fetch()) {
				$prod_id=$row[0];
				echo "Se ha cambiado la cantidad de articulos que tenias ya que no tenemos tanto STOCK. <br>";
				$query="update orderdetail set quantity=quantity +(stock -quantity) 
				from inventory 
				where orderid= ".$orderid." and inventory.prod_id=".$prod_id." 
				and inventory.prod_id=orderdetail.prod_id";
				$db->exec($query);
			}
			$query="delete from orderdetail where orderid=".$orderid." and quantity = 0";
			$db->exec($query);
			$query="select movietitle, quantity from orderdetail join products using (prod_id) 
			join imdb_movies using (movieid) where orderid=".$orderid;
			$stmt=$db->query($query);
			$result = $stmt->setFetchMode(PDO::FETCH_NUM);
			echo "<table >";
				echo '<tr align=center bgcolor="yellow">';
				echo "<td>";
				echo "titulo";
				echo "</td>";
				echo "<td>";
				echo "cantidad";
				echo "</td>";
				echo "</tr>";
			while ($row = $stmt->fetch()) {
				echo "<tr>";
				echo "<td>";
				echo $row[0];
				echo "</td>";
				echo "<td>";
				echo $row[1];
				echo "</td>";
				echo "</tr>";
			}
			$query="update orders set status='Paid', customerid=".$usuario[1]." , totalamount =netamount + (netamount*tax)/100
			where orderid =".$orderid;
			$db->exec($query);
			$_SESSION['orderid']=null;
		}
	?>
	</div>

	<div id="ppagina"><?php echo date("d-m-Y H:i:s"); ?></div>
</body>
</html>
