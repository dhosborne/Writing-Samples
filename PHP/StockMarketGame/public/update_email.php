<?php

require("../includes/config.php");

if ($_SERVER["REQUEST_METHOD"] == "GET")
{
	render("email_form.php", ["title" => "Change Email"]);
}

else if ($_SERVER["REQUEST_METHOD"] == "POST")
{
	if (empty($_POST["email"]) || empty($_POST["confirmation"]))
	{
		apologize("Email and confirmation do not match");
	}
	else
	{
		$email = $_POST["email"];

		if(!filter_var($email, FILTER_VALIDATE_EMAIL))
		{
			apologize("Invalid email format");
		}
	}
	if ($_POST["email"] != $_POST["confirmation"])
        {
            apologize("passwords do not match, try again!");
        }
    $replace = query("UPDATE users SET email = ? WHERE id = ?", $_POST["email"], $_SESSION["id"]);
    if ($replace === false)
    {
    	apologize("An Error Occured.  Please try again!");
    }
    else
    {
    	success("Email updated");
    }
}
?>