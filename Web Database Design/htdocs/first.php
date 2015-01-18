<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Today&rsquo;s Date</title>
<link rel="stylesheet" type="text/css" href="myStyle.css">
</head>
<body>
<p>Today&rsquo;s date (according to this web server) is
<?php
	echo date('l, F jS Y.');
	/*accessing the database joke held on mydb on localhost using restricted user privileges set on user root with password Jacky007*/
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
	$sqlQuery = "SELECT joketext FROM joke";
	$results = $pdo->query($sqlQuery);

}
catch(PDOException $e){
	$output = 'Error fetching from database.'. $e->getMessage();
	include 'output.html.php';
	exit();
}


while ($row = $results->fetch()){
		$jokes[] = $row['joketext'];
	}

$output = 'Database connection established.';
include 'output.html.php';

?>
</p>
<a href="problemOne.html">Click here to move on to more advanced SQL/PHP database interaction. Currently has insert/delete and data display from multiple tables, still needs modification capabilities and CSS.</a>
</body>
</html>