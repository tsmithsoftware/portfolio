<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="mystyle.css">
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
<form action="addjoke.php" method="post">

<div>
<label for="jokeRec">Type your joke here:</label>
<textarea id="jokeRec" name="jokeRec" rows="3" cols="40">
</textarea>
</div>

<div>
<select name = "dateDay">
<option value = "1">1</option>
<option value = "2">2</option>
<option value = "3">3</option>
<option value = "4">4</option>
<option value = "5">5</option>
<option value = "6">6</option>
<option value = "7">7</option>
<option value = "8">8</option>
<option value = "9">9</option>
<option value = "10">10</option>
</select>
</div>

<div>
<select name = "dateMonth">
<option value = "1">January</option>
<option value = "2">February</option>
<option value = "3">March</option>
<option value = "4">April</option>
<option value = "5">May</option>
<option value = "6">June</option>
<option value = "7">July</option>
<option value = "8">August</option>
<option value = "9">September</option>
<option value = "10">October</option>
<option value = "11">November</option>
<option value = "12">December</option>
</select>
</div>

<div>
<select name = "dateYear">
<option value = "2011">2011</option>
<option value = "2010">2010</option>
<option value = "2009">2009</option>
<option value = "2008">2008</option>
<option value = "2007">2007</option>
<option value = "2006">2006</option>
<option value = "2005">2005</option>
<option value = "2004">2004</option>
<option value = "2003">2003</option>
<option value = "2002">2002</option>
<option value = "2001">2001</option>
<option value = "2000">2000</option>
<option value = "1999">1999</option>
<option value = "1998">1998</option>
<option value = "1997">1997</option>
</select>
</div>

<div><input type="submit" value="Add"></div>

</form>
</body>