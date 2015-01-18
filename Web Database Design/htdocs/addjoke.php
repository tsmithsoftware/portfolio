<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Add Joke</title>
<style type="text/css">
textarea {
display: block;
width: 100%;
}
</style>
</head>
<body>

<?php

/*accessing the database joke held on mydb on localhost using restricted user privileges set on user selectUser with password password*/
try{
	$pdo = new PDO('mysql:host=localhost;dbname=mydb','root','Jacky007');
	$pdo->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
/*sets attribute, will throw exception whenever it fails to perform request, passes constants which are part of the PDO class, hence the ::*/
	$pdo->exec('SET NAMES "utf8"');
/*sets character encoding*/

}
catch(PDOException $e){
	$output = 'Unable to connect to the database server.' . $e->getMessage();
	include 'output.html.php';
	exit();
}

try{
	$year = "".$_POST['dateYear'];
	$month = "".$_POST['dateMonth'];
	$day = "".$_POST['dateDay'];
	$joke = "".$_POST['jokeRec'];
	
   $sql = 'INSERT INTO joke SET joketext = "'.$joke.'", jokedate = "'.$year.'-'.$month.'-'.$day.'"';
   
   $pdo->exec($sql);
	include 'first.php';
	
	//echo $month . $day;
  }
  catch(PDOException $e){
	$output = "Error: " . $e;
	include'output.html.php';
  }
	?>
</form>
</body>