<?php 
	session_start();
	$dbuser='alumnodb';
	$dbpass='alumnodb';
	$hostname='localhost';
	$dbname='si1';

	$customerid=$_GET['customerid'];
	if($customerid == null){
		echo 	'<form action="borraCliente.php" method="get">
			customerid: <input type="text" name="customerid"><br>
			usopdo: <input type="radio" name="usopdo"><br>
			<input type="submit">
			</form>';
	}
	else{
		if (isset($_GET['usopdo'])) {
			try{
				$db = new PDO("pgsql:dbname=$dbname; host=$hostname",$dbuser,$dbpass);
				$db->beginTransaction();
				$db->setAttribute (PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
				$sql= "DELETE FROM orderdetail using orders WHERE orders.customerid= '$customerid' AND orderdetail.orderid = orders.orderid";
				$result = $db->exec($sql);
				if ($result == 0){
					echo 'Error detectado';
					$db->rollback();
				}

				$sql= "DELETE FROM orders WHERE customerid= '$customerid'";
				$result = $db->exec($sql);
				if ($result == 0){
					echo 'Error detectado';
					$db->rollback();
				}
				$sql= "DELETE FROM customers WHERE customerid= '$customerid'";
				$result = $db->exec($sql);
				if ($result == 0){
					echo 'Error detectado';
					$db->rollBack();
				}
				$db->commit();		
			}
			catch(PDOException $e){
				echo $e->getMessage();
			}	
		}
		else{
			try{
				$db = new PDO("pgsql:dbname=$dbname; host=$hostname",$dbuser,$dbpass);
				$db->setAttribute (PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
				$db->exec(BEGIN);
				$sql= "DELETE FROM orderdetail using orders WHERE orders.customerid= '$customerid' AND orderdetail.orderid = orders.orderid";
				$result = $db->exec($sql);
				if ($result == 0){
					echo 'Error detectado';
					$db->exec(ROLLBACK);
				}
				$sql= "DELETE FROM orders WHERE customerid= '$customerid'";
				$result = $db->exec($sql);
				if ($result == 0){
					echo 'Error detectado';
					$db->exec(ROLLBACK);
				}	
				$sql= "DELETE FROM customers WHERE customerid= '$customerid'";
				$result = $db->exec($sql);
				if ($result == 0){
					echo 'Error detectado';
					$db->exec(ROLLBACK);
				}
				$db->exec(COMMIT);		
			}
			catch(PDOException $e){
				echo $e->getMessage();
			}	

		}

	}

	
?>
