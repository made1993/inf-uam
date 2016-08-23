<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="style.css"/>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script>
function psd(f) {
  var pass=document.getElementById("pass").value;
  if(pass.length>7){
    $("p").removeClass();	
	$("p").addClass("green");
  }
  else if(pass.length>4){
    $("p").removeClass();
  	$("p").addClass("yellow");
  }
  else{
    $("p").removeClass();
  	$("p").addClass("red");
  }

}

</script>
<style>
.green{
color:green;
}
.red{
color:red;
}
.yellow{
color:yellow;
}
</style>
		<script type="text/javascript">
			function valida(f) {
			  var ok = true;
			  var msg = "Debes escribir algo en los campos:\n";
			  var mail = f.elements["mail"].value;
			  if(f.elements["name"].value == "")
			  {
			   	alert("No introdujo un nombre");
			    return false;
			  }

			  if(f.elements["pass"].value !=f.elements["passc"].value)
			  {
			    alert("Su password no coincide");
			    return false;
			  }
			   if(!(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(mail)))
			  {
			    alert("La direccion de correo "+mail+" no es valida");
			    return false;
			  }
			  if (!(/^\d{16}$/.test(f.elements["card"].value))){
			  	alert("El numero de tarjeta no es valido");
			  	return false;
			  }
			 

			  if(ok == false)
			    alert(msg);
			  return ok;
			}
		</script>
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
			<form id="id2" action="registro.php" method="post" onsubmit="return valida(this)">
			Nombre:<input type="text" name="name" id="name">
			<p>Pass:<input type="password" name="pass" id="pass" onkeypress="psd()"></p>
			Pass confirmation:<input type="password" name="passc" id="passc">
			Email:<input type="text" name="mail" id="mail">
			Card Number:<input type="text" name="card" id="card">
			<input type="submit" value="submit">
			</form>
		</div>
		<div id="ppagina"><?php echo date("d-m-Y H:i:s"); ?></div>
	</body>
</html>
