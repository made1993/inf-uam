<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" type="text/css" href="style.css"/>
		<?php
		session_start();
		$orderid=$_SESSION['orderid'];
		$db=new PDO("pgsql:dbname=SI_P3; host=localhost","alumnodb","alumnodb");
		$query="delete from orders where orderid=".$orderid;
		$db->exec($query);
		$query="delete from orderdetail where orderid=".$orderid;
		$db->exec($query);

		$_SESSION['orderid']=null;
		$_SESSION['precio1']=null;
		$_SESSION['titulo1']=null;
		$_SESSION['categoria1']=null;
		$_SESSION['usuario']=null;
		header('Location: index.php');
		?>
	</head>
</html>