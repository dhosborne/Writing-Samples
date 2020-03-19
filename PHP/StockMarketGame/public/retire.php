<?php

    // configure
    require("../includes/config.php");
    
    // if user reached page via GET
    if ($_SERVER["REQUEST_METHOD"]  == "GET")
    {
        //else render form
        render("retire_form.php", ["title" => "Retire"]);
    }
    
    // esle if user reached page via POST
    else if ($_SERVER["REQUEST_METHOD"] == "POST")
    {
        //validate username & password/confirmation
        if (strcmp("RETIRE",$_POST["confirm"]) || empty($_POST["confirm"]))
        {
            apologize('You must type "RETIRE" to finalize');
        }

        else
        {
        	$info = query("SELECT * FROM users WHERE id = ?", $_SESSION["id"]);
        	$name = $info[0]["username"];
        	$email = $info[0]["email"];

        	$retire_history = query("DELETE FROM history WHERE id = ?", $_SESSION["id"]);
	        $retire_holdings = query("DELETE FROM holdings WHERE id = ?", $_SESSION["id"]); 
	        $retire_user = query("DELETE FROM users WHERE id = ?", $_SESSION["id"]);
            goodbye(["recipient" => $email, "name" => $name]);
            redirect("/logout.php");
        }
    }
?>