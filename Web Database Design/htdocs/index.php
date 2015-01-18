<?php
$username = "root";
$password = "Jacky007";
$hostname = "localhost";
$dbname = "mydb";
//connect
$dbh = mysqli_connect($hostname, $username, $password, $dbname);

//check connection
if (mysqli_connect_errno()){
echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
else{
echo "Connection established.";
}
//mysql_close($dbh);
?>
