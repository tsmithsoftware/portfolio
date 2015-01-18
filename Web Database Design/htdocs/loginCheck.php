<?php
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
/*Check to see if passed username is valid*/
try{
$user = $_POST['name'];

$query = $pdo->prepare("SELECT ResidentName FROM resident WHERE ResidentName ='".$user."'");
$query->execute();

//Does not distinguish between multiple identical user records. Does not need to
if($query->fetch()){
//success
include 'userAdd.php';
}
else{
echo "no records for this user found. try 'John'";
}
}
catch(Exception $e){
echo 'Problem in selecting records from database ' . $e;
}
?>