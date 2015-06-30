<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" type="text/css" href="style.css"/>
		<?php
		session_start();
		$precio1=$_GET['precio1'];
		$titulo1=$_GET['titulo1'];
		$categoria1=$_GET['categoria1'];
		$orderid=$_GET['orderid'];
		$prod_id=$_GET['prod_id'];
		$db=new PDO("pgsql:dbname=SI_P3; host=localhost","alumnodb","alumnodb");
		if (($prod_id==null)||($orderid==null)){
			header('Location: error.php');
		}
		else{
			$query="select quantity from orderdetail where orderid=".$orderid."and prod_id =".$prod_id;
			foreach ($db->query($query) as $row) {
				if($row['quantity']>1){					
					$db->exec("update orderdetail set quantity = quantity -1 
						where orderid=".$orderid." and prod_id=".$prod_id);
				}
				else{
					$db->exec("delete from orderdetail where orderid=".$orderid." and prod_id=".$prod_id);
				}
			}
		}
		$_SESSION['precio1']=$precio1;
		$_SESSION['titulo1']=$titulo1;
		$_SESSION['categoria1']=$categoria1;
		header('Location: busqueda.php');
		?>
	</head>
</html>