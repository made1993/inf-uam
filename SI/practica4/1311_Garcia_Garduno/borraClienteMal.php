<?php 

function printTable ($dbRef, $customer){
	$sql = "SELECT * FROM orderdetail, orders WHERE orders.customerid= '$customer' AND orderdetail.orderid = orders.orderid limit 5";
	foreach($dbRef->query($sql) as $row){
		print $row['orderid'] .'-'. $row['prod_id'] .'-'. $row['customerid'] . '<br/>';
	}
	print '<p>';

}
	session_start();
	$dbuser='alumnodb';
	$dbpass='alumnodb';
	$hostname='localhost';
	$dbname='si1';

	$customerid=$_GET['customerid'];
	if($customerid == null){
		echo 	'<form action="borraClienteMal.php" method="get">
			customerid: <input type="text" name="customerid"><br>
			usopdo: <input type="radio" name="usopdo"><br>
			commitAdicional: <input type="radio" name="commitAdicional"><br>
			<input type="submit">
			</form>';
	}
	else{
		if (isset($_GET['usopdo'])) {
			try{
				$db = new PDO("pgsql:dbname=$dbname; host=$hostname",$dbuser,$dbpass);
				$db->beginTransaction();
				$db->setAttribute (PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
				echo 'Estado de la tabla orderdetail antes de la ejecucion de la query';
				echo '<br/>';
				
				printTable($db, $customerid);

				$sql= "DELETE FROM orderdetail using orders WHERE orders.customerid= '$customerid' AND orderdetail.orderid = orders.orderid";
				$result = $db->exec($sql);
				
				echo 'Estado de la tabla orderdetail despues de la ejecucion de la query';
				echo '<br/>';
				printTable($db, $customerid);

				if (isset($_GET['commitAdicional'])) {
					$db->commit();	
					$db->beginTransaction();
				}

				$sql= "DELETE FROM customers WHERE customerid= '$customerid'";
				$result = $db->exec($sql);


				$sql= "DELETE FROM orders WHERE customerid= '$customerid'";
				$result = $db->exec($sql);
	
				$db->commit();		
			}
			catch(PDOException $e){
				echo $e->getMessage();
				$db->rollBack();
				echo '<br/>';
				echo '<br/>';
				echo 'Estado de la tabla orderdetail despues del rollback';
				echo '<br/>';
				printTable($db, $customerid);
			}	
		}
		else{
			try{
				$db = new PDO("pgsql:dbname=$dbname; host=$hostname",$dbuser,$dbpass);
				$db->setAttribute (PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
				echo 'Estado de la tabla orderdetail antes de la ejecucion de la query';
				echo '<br/>';

				$db->exec(BEGIN);
				printTable($db, $customerid);
				$sql= "DELETE FROM orderdetail using orders WHERE orders.customerid= '$customerid' AND orderdetail.orderid = orders.orderid";
				$result = $db->exec($sql);

				echo 'Estado de la tabla orderdetail despues de la ejecucion de la query';
				echo '<br/>';
				printTable($db, $customerid);
		
				if (isset($_GET['commitAdicional'])) {
					$db->exec(COMMIT);
					$db->exec(BEGIN);
				}
				$sql= "DELETE FROM customers WHERE customerid= '$customerid'";
				$result = $db->exec($sql);
				
				$sql= "DELETE FROM orders WHERE customerid= '$customerid'";
				$result = $db->exec($sql);
	
				$db->exec(COMMIT);		
			}
			catch(PDOException $e){
				echo $e->getMessage();
				$db->exec(ROLLBACK);
				echo '<br/>';
				echo '<br/>';
				echo 'Estado de la tabla orderdetail despues del rollback';
				echo '<br/>';
				printTable($db, $customerid);
			}	

		}

	}

	
?>
