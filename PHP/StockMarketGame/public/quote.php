<?php
	//configure
	require("../includes/config.php");

	// if user reached page via GET
	if ($_SERVER["REQUEST_METHOD"] == "GET")
	{
		render("quote_form.php", ["title" => "Quote"]);
	}

	else if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if (empty($_POST["symbol"]))
		{
			apologize("You must provide a symbol");
		}

		$quote = lookup($_POST["symbol"]);
		if ($quote == false)
		{
			apologize("Could not locate requested symbol, please try again");
		}
		else
		{
			render("return_quote.php", ["title" => "quote" . $quote["symbol"], "quote" => $quote]);
		}
	}
?>
