<?php

    // configure
    require("../includes/config.php");
    
    // if user reached page via GET
    if ($_SERVER["REQUEST_METHOD"]  == "GET")
    {
        //else render form
        render("password_form.php", ["title" => "Change Password"]);
    }
    
    // esle if user reached page via POST
    else if ($_SERVER["REQUEST_METHOD"] == "POST")
    {
        if (empty($_POST["password"]) || empty($_POST["confirmation"]))
        {
            apologize("You must provide a password.");
        }
        if ($_POST["password"] != $_POST["confirmation"])
        {
            apologize("passwords do not match, try again!");
        }
        else
        {
            $update = query("UPDATE users SET hash  = ? WHERE id = ?", crypt($_POST["password"]), $_SESSION["id"]);
            if ($update === false)
            {
                apologize("An Error Occured, Please try again!");
            }
            else
            {
                success("Password Updated");
            }
        }
    }
?>