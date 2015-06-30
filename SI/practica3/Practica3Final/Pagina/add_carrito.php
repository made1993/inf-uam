<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" type="text/css" href="style.css"/>
		<?php
		session_start();
		$prod_id=$_GET['prod_id'];
		$titulo=$_GET['titulo1'];
		$categoria=$_GET['categoria1'];
		$precio=$_GET['precio1'];
		$orderid=$_SESSION['orderid'];


		$db=new PDO("pgsql:dbname=SI_P3; host=localhost","alumnodb","alumnodb");
		if ($prod_id==null){
			header('Location: error.php');
		}
		if ($orderid==null){
			$query = "select orderid+1 from orders order by orderid desc limit(1)";
			echo $query."=";
			$stmt=$db->query($query);
			$result = $stmt->setFetchMode(PDO::FETCH_NUM);
			while ($row = $stmt->fetch()) {
				$orderid=$row[0];
			}
			$query ="insert into orders(orderid,orderdate,tax,status) 
			values(".$orderid.",current_date,21,'NoPaid')";
			echo "<br>".$query."<br>";
			$db->exec($query);
		}
		$query = "select prod_id from orderdetail where orderid=".$orderid." and prod_id =".$prod_id;
		echo $query."<br>";
		$stmt=$db->query($query);
		$result = $stmt->setFetchMode(PDO::FETCH_NUM);
		while ($row = $stmt->fetch()) {
			if($prod_id==$row[0]){
				$prod_id1=$prod_id;
			}
		}
		if ($prod_id1==null){
			$query="insert into orderdetail values(".$orderid.",".$prod_id.",
			(select price from products where prod_id =".$prod_id.") , 1)";
			echo $query."<br>";
			$db->exec($query);
		}
		else {
			$query="update orderdetail set quantity= quantity+1 where 
			prod_id=".$prod_id." and orderid = ".$orderid;
			echo $query."<br>";
			$db->exec($query);
		}


		$_SESSION['orderid']=$orderid;
		$_SESSION['precio1']=$precio;
		$_SESSION['titulo1']=$titulo;
		$_SESSION['categoria1']=$categoria;
		header('Location: busqueda.php');
		?>
	</head>
</html>