<?php

    // configure
    require("../includes/config.php");
    
    // if user reached page via GET
    if ($_SERVER["REQUEST_METHOD"]  == "GET")
    {
        //else render form
        xeno_render("register_form.php", ["title" => "Register"]);
    }
    
    // esle if user reached page via POST
    else if ($_SERVER["REQUEST_METHOD"] == "POST")
    {
        //validate username & password/confirmation
        if (!preg_match("/^[a-zA-Z0-9]*$/",($_POST["username"])))
        {
            apologize("Name may only contain letters and numbers.");
        }
        
        if (empty($_POST["email"]))
		{
     		apologize("Email is required");
   		}
   		else 
   		{
     		$email = $_POST["email"];
     		// check if e-mail address is well-formed
     		if (!filter_var($email, FILTER_VALIDATE_EMAIL))
     		{
       			apologize ("Invalid email format");
     		}
		}

        if (empty($_POST["password"]))
        {
            apologize("You must provide a password.");
        }
        if ($_POST["password"] != $_POST["confirmation"])
        {
            apologize("passwords do not match, try again!");
        }
        
        $new = query("INSERT INTO `kory`.`users` (`username`, `hash`, `email`) VALUES(?, ?, ?)", $_POST["username"], crypt($_POST["password"]), $_POST["email"]);
        if ($new === false)
        {
            apologize("there was a problem, please contact your admin.");
        }
        else
        {
            $rows = query("SELECT LAST_INSERT_ID() AS id");
            $_SESSION["id"] = $rows[0]["id"];
            redirect("/login.php");
        }
    }
?>
