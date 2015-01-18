<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="mystyle.css">
<meta charset="utf-8">
<title>Script Output</title>
</head>
<body>
<p><a href="userform.html.php?addjoke">Add your own joke</a></p>
<p><a href="removejoke.php">Remove a joke</a></p>
<p>Here are all the jokes in the database:</p>
<?php 
echo $output; 
?>

<?php foreach ($jokes as $joke): ?>
 <blockquote>
  <p><?php 
	echo htmlspecialchars($joke, ENT_QUOTES, 'UTF-8'); 
     ?> 
  </p>
 </blockquote>

<?php endforeach; ?>
</p>
</body>
</html>