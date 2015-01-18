<!DOCTYPE = "html">
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="mystyle.css">
<meta-charset = "utf-8">
<title>Changing user information</title>
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
//do an update statement
echo "Details entered: <br/>";

$col = $_POST['carColour'];
$mod = $_POST['carModel'];
$lic = $_POST['carLicence'];
$usr = $_POST['userName'];
$resID = $_POST['residentID'];

echo $col . "<br />";
echo $mod . "<br />";
echo $lic . "<br />";
echo $usr . "<br />";
echo $resID . "<br />";

//see if this works. Aim is to collect all current car information of the user, print and then update using new information.
$stmt = $pdo->query("SELECT * FROM car WHERE ResidentID ='".$resID."'");
print "Have collected all car information using user information of " . $resID . "<br/>";
foreach($stmt as $row){
	print "Current user's car licence number: " . $row["CarLicence"] . "<br/>";
	print "Current user's car model: " . $row["CarModel"] . "<br/>";
	print "Current user's car colour: " . $row["CarColour"]. "<br/>";
}
print "<br/> Will now execute update query with hardcorded input and print new information. Query will change the car colour to Black.<br/>";
$stmt = $pdo->query("UPDATE car SET CarColour = 'Black' WHERE ResidentID ='". $resID ."'");
echo "Database updated. car with licence 247 is now black. New information: <br />";
$stmt = $pdo->query("SELECT * FROM car WHERE ResidentID ='".$resID."'");
foreach($stmt as $row){
	print "Current user's car licence number: " . $row["CarLicence"] . "<br/>";
	print "Current user's car model: " . $row["CarModel"] . "<br/>";
	print "Current user's car colour: " . $row["CarColour"]. "<br/>";
}

print "<br/> Will now execute update query with hardcoded input and print new information. Query will change the car colour to Purple.<br/>";
$stmt = $pdo->query("UPDATE car SET CarColour = 'Purple' WHERE ResidentID ='". $resID ."'");
echo "Database updated. car with licence 247 is now purple. New information: <br />";
$stmt = $pdo->query("SELECT * FROM car WHERE ResidentID ='".$resID."'");
foreach($stmt as $row){
	print "Current user's car licence number: " . $row["CarLicence"] . "<br/>";
	print "Current user's car model: " . $row["CarModel"] . "<br/>";
	print "Current user's car colour: " . $row["CarColour"]. "<br/>";
}
print "<br /> Will now execute update query with user-entered information. Query will change the car licence to the entered value. <br/>";
if((!$lic=="") && is_numeric($lic)){
	$stmt = $pdo->query("UPDATE car SET CarLicence = '" .$lic."' WHERE ResidentID ='". $resID ."'");
	print "Database updated. For debugging purposes, new information: <br />";
	$stmt = $pdo->query("SELECT * FROM car WHERE ResidentID ='".$resID."'");
		foreach($stmt as $row){
		print "Current user's car licence number: " . $row["CarLicence"] . "<br/>";
		print "Current user's car model: " . $row["CarModel"] . "<br/>";
		print "Current user's car colour: " . $row["CarColour"]. "<br/>";
		}
	}
else{
	print "A value for Car Licence was not entered or was not numeric, database will not be updated.";
	}
}
catch(Exception $e){
echo "Error: ". $e->getMessage();
}
?>
</body>
</html>