<html>
<head>
<title>Successful login</title>
<link rel="stylesheet" type="text/css" href="mystyle.css">
</head>
<?php
/*accessing the database joke held on mydb on localhost using restricted user privileges set on user root with password Jacky007*/
try{
	$pdo = new PDO('mysql:host=localhost;dbname=mydb','root','Jacky007');
	$pdo->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
/*sets attribute, will throw exception whenever it fails to perform request, passes constants which are part of the PDO class, hence the ::*/
	$pdo->exec('SET NAMES "utf8"');
/*sets character encoding*/

//display all users in db
print 'As an error check, step 1 is displaying all users in database <br />';
foreach($pdo->query('SELECT residentName from resident') as $row) { //displaying all users in database
	foreach($row as $two){
			$stringRep = $two;
			print $two . "<br />";
			break;
		}
}
print '<p/>';
    

//find user information
/*
split it into segments:
1. pick up name ($name = $_POST['name'];
2. pick up id ($pdo->query('SELECT ResidentID FROM resident WHERE ResidentName = ' .$name.'');
3. extracting ID from pdo:
	for each ($pdo as $obj){
		$residentID = $row['ResidentID'];
		}
4. pick up car details $residentCar = $pdo->query('SELECT * FROM car WHERE residentID ='.$residentID.');
5. extracting information:
	for each ($pdo as $obj){
			$carLicence = $row['CarLicence'];
			$carColour = $row['CarColour'];
			$carModel = $row['CarModel'];
			}
6. if above works, redesign database layout so as not to be so bloody stupid. 
7.???
8.Profit!

*/
//1.
$name = $_POST['name'];
//2.
$pdo->query("SELECT ResidentID FROM resident WHERE ResidentName ='".$name."'");//stating the obvious: this works because $name is a straight string. The other interrogation attempts are passing a PDO object and not a string, so its not working. This is obvious
print "As a further error check, the query entered: SELECT ResidentID FROM resident WHERE ResidentName ='".$name."'";//error checking
print "<p/>";
//3.
print "Having entered the query, the next is step is attempting to display information. ResidentID of this user: ";
$residentID = 0;
foreach($pdo->query("SELECT ResidentID FROM resident WHERE ResidentName ='".$name."'") as $row){
	foreach($row as $two){
	$residentID = $two;
	print $two . "<p/>";
	break;
	}
}
//4.
print "Step four is then extracting and displaying information from other tables in the database using the above extracted information: <p />";

//5. 
print "Information from the Car table: <br/>";
$currentLic = "";
$results = $pdo->query('SELECT * FROM car WHERE residentID ='.$residentID);
foreach($results as $row){
	print "Current user's car licence number: " . $row["CarLicence"] . "<br/>";
	print "Current user's car model: " . $row["CarModel"] . "<br/>";
	print "Current user's car colour: " . $row["CarColour"]. "<br/>";
	
	$currentLic = $row["CarLicence"];
	}

//5, continued: data drawn from other tables.
print "<p/>Information from the other tables: <br/>";
$results = $pdo->query('SELECT * FROM resident WHERE residentID ='.$residentID);
foreach($results as $row){
	$countyID = $row["CountyID"];
	$stateID = $row["StateID"];
}
$results = $pdo->query('SELECT * FROM county WHERE countyID ='.$countyID);
foreach($results as $row){
	print $name . " lives in " . $row["CountyName"] . " ";
}

$results = $pdo->query('SELECT * FROM state WHERE StateID ='.$stateID);
foreach($results as $row){
	print "in ". $row["StateName"] . ".<p/>";
	}
}
catch(PDOException $e){
	$output = 'Unable to connect to the database server.' . $e->getMessage();
	exit();
}


unset($pdo);
?>
<body>
<p/>
Hello 
<?php
$name = $_POST['name'];
echo $name;
?>. Final step: <br/>
Congratulations, you have successfully logged in. Please use this page to confirm and update your records.<br />
Records available for updating by <?php echo $name;?>: <br/>
Keep in mind that only the car colour will be changed. The other records are for displaying only.
<form action = "changeDetails.php" method = "post">

<?php
print '<input type = "hidden" name = "userName" value ='.$name.'>';
print '<input type = "hidden" name = "residentID" value ='.$residentID.'>';
?>
<div>
<label for="carColour">Enter your car colour here:</label>
<select name = "carColour">
<option value = "blue">Blue</option>
<option value = "red">Red</option>
<option value = "green">Green</option>
<option value = "pink">Pink</option>
</select>
</div>

<div>
Type your car model here:
<input type = "text" name="carModel">
</div>

<div>
Type your licence plate here. Please note that this textbox accepts text in Mozilla Firefox-submission of text will result in the database not being updated:
<input type = "number" name="carLicence">
</div>

<input type = "submit" value = "Submit"/>

</form>
</body>
</html>