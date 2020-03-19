<?php

    // configure
    require("../includes/config.php");
    
    $rows = query("SELECT * FROM users WHERE id = ?", $_SESSION["id"]);
	$username = $rows[0]["username"];
	$email = $rows[0]["email"];

    // if user reached page via GET
    if ($_SERVER["REQUEST_METHOD"]  == "GET")
    {
        //else render form
        render("user_form.php", ["title" => $username, "email" => $email]);
    }
?>