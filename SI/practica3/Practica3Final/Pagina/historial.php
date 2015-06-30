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
			$usr=$_SESSION['usuario'];
			if($usr == null){
				echo "Necesitas iniciar sesion o registrarte en caso de que no tengas cuenta";
				echo '<form action="login.php"><input type="submit" value="Login"></form>';
				echo '<form action="registrop.php"><input type="submit" value="Registrarse"></form>';
			}
			else {
				$dir="usuarios/".$usr."/historial.xml";
				$db=new PDO("pgsql:dbname=SI_P3; host=localhost","alumnodb","alumnodb");
				$query = "select movietitle, quantity, orderdate from orderdetail join products using (prod_id) 
				join imdb_movies using (movieid) join orders using (orderid)
				where customerid=".$usr[1];
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
				echo "<td>";
				echo "fecha del pedido";
				echo "</td>";
				echo "</tr>";
				while ($row = $stmt->fetch()) {
					echo "<tr align=center>";
					echo "<td>";
					echo $row[0];
					echo "</td>";
					echo "<td>";
					echo $row[1];
					echo "</td>";
					echo "<td>";
					echo $row[2];
					echo "</td>";
					echo "</tr>";
				}
				echo "</table>";
			}
			?>
		</div>
		<div id="ppagina"><?php echo date("d-m-Y H:i:s"); ?></div>
	</body>
</html>
