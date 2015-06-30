
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">
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
				$db =new PDO("pgsql:dbname=SI_P3; host=localhost","alumnodb","alumnodb");


				$titulo=$_POST['titulo1'];
				$categoria=$_POST['categoria1'];
				$precio=$_POST['precio1'];
				$orderid=$_SESSION['orderid'];

				if($orderid!=NULL){
					if ($titulo==null){
					$titulo=$_SESSION['titulo1'];
					}
					if ($categoria==null){
					$categoria=$_SESSION['categoria1'];
					}
					if ($precio==null){
						$precio=$_SESSION['precio1'];
					}
					echo "pedido numero:".$orderid."<br>";
					$query="select prod_id, movietitle, quantity from orderdetail 
						join products using (prod_id) 
						join imdb_movies using (movieid) where orderid =".$orderid;
					echo "<table >";
					echo '<tr align=center bgcolor="yellow"><td>carrito</td>';					
					echo "</tr>";
					echo '<tr align=center bgcolor="red">';	
					echo "<td>";
					echo "titulo";
					echo "</td>";
					echo "<td>";
					echo "cantidad";
					echo "</td>";
					echo "</tr>";
					foreach ($db->query($query) as $row){
						echo '<tr bgcolor="green">';
						echo "\t\t<td> Titulo: \n";
						print $row['movietitle'];
						echo "</td>\n";

						echo "\t\t<td> Cantidad: \n";
						print $row['quantity'];
						echo "</td>\n"; 
						echo "<td>";
						echo '<form action="rm_carrito.php" method="get">';						
						echo '<input type="hidden" name="titulo1" value="'.$titulo.'">';
						echo '<input type="hidden" name="categoria1" value="'.$categoria.'">';
						echo '<input type="hidden" name="precio1" value="'.$precio.'">';
						echo '<input type="hidden" name="orderid" value="'.$orderid.'">';
						echo '<input type="hidden" name="prod_id" value="'.$row['prod_id'].'">';
						echo '<input type="submit" name="Quitar" value="Quitar"><br>';
						echo '</form>';
						echo "</td>";
						echo "\t</tr>\n";
					}
					echo "</table>";
					echo '<form action="finalizarcompra.php" method="get">';	
					echo '<input type="submit" name="Pagar" value="Pagar">';
					echo '</form>';
				}

				$query="select movietitle, year,price*1.21,description,prod_id ,stock from imdb_movies join products using (movieid) 
						join imdb_moviegenres using (movieid)
						join imdb_genres using (genreid)
						join inventory using (prod_id) ";

				if($titulo==NULL&&$precio==NULL&&$categoria==NULL){
				}else{
					$query=$query ." where ";
					if($titulo!=NULL){
						$query=$query ."movietitle like'%".$titulo."%'";
						$nbus++;
					}
					if($precio!=NULL){
						if($nbus>0){
							$query=$query ."and ";
						}
						$query= $query . "price*1.21	 <=".$precio;
						$nbus++;
					}
					if($categoria!=NULL){
						if($nbus>0){
							$query=$query ."and ";
						}
						$query= $query . "gnrname like '%".$categoria."%'";
						$nbus++;
					}				
				}
				$query=$query."group by prod_id,movietitle,year,stock";
				echo "<table >";
				echo '<tr align=center bgcolor="yellow">';					
				echo "</tr>";
				echo '<tr align=center bgcolor="red">';	
				echo "<td>";
				echo "Titulo";
				echo "</td>";
				echo "<td>";
				echo "Estreno";
				echo "</td>";
				echo "<td>";
				echo "P.V.P.";
				echo "</td>";
				echo "<td>";
				echo "Edicion";
				echo "</td>";
				echo "<td>";
				echo "Stock.";
				echo "</td>";
				echo "<td>";
				echo "</td>";				
				echo "</tr>";
				$stmt =$db->query($query);
				$result= $stmt->setFetchMode(PDO::FETCH_NUM);
				while($row=$stmt->fetch()){
					echo "\t<tr>\n";
					echo "\t\t<td> ";
					print $row[0];
					echo "</td>\n"; 
					echo "\t\t<td>";
					print $row[1];
					echo "</td>\n";
					echo "\t\t<td> \n";
					print $row[2];
					echo " </td>\n";
					echo "\t\t<td>\n";
					print $row[3];
					echo " </td>\n";
					echo "\t\t<td> \n";
					print $row[5];
					echo " </td>\n";

					echo " <td>";
					if($row[5]>0){
						echo '<form action="add_carrito.php" method="get">';
						echo '<input type="hidden" name="titulo1" value="'.$titulo.'">';
						echo '<input type="hidden" name="categoria1" value="'.$categoria.'">';
						echo '<input type="hidden" name="precio1" value="'.$precio.'">';
						echo '<input type="hidden" name="prod_id" value="'.$row[4].'">';
						echo '<input type="submit" name="Comprar" value="Comprar">';

						echo '</form>';

					echo " </td>\n";
					echo " </tr>	";
					}
					
				
				}
				echo "<table>";
				$row=null;
				$db=null;
				$result=null;
	
			?>
		</div>
		<div id="ppagina"><?php echo date("d-m-Y H:i:s"); ?></div>
	</body>
</html>
