<?php
	
	require("../includes/config.php");

	$users = query("SELECT * FROM users WHERE id = ?", $_SESSION["id"]);
	$curr_cash = $users[0]["cash"];
	is_float($curr_cash);
	$email = $users[0]["email"];
	$name = $users[0]["username"];

	if ($_SERVER["REQUEST_METHOD"] == "GET")
	{
		render("buy_form.php", ["title" => "buy", "curr_cash" => $curr_cash]);
	}

	if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if (empty($_POST["symbol"]))
		{
			apologize("Please enter a symbol");
		}
		if (empty($_POST["volume"]))
		{
			apologize("Please specify the volume you wish to purchase");
		}
		else
		{
			$symbol = strtoupper($_POST["symbol"]);
			$quote = lookup($symbol);
			if ($quote == false)
			{
				apologize("Could not locate requested symbol, please try again");
			}
			if (preg_match("/^\d+$/", $_POST["volume"]) == FALSE)
			{
				apologize("Please specify a valid volume to trade!");
			}
			else
			{
				// calculate shares remaining after sale
				$holdings = query("SELECT shares FROM holdings WHERE id = ? AND symbol = ?",$_SESSION["id"], $symbol);
				if ($holdings != NULL)
				{
					$curr_hold = $holdings[0]["shares"];
				}
				else
				{
					$curr_hold = 0;
				}

				$update_hold = $curr_hold + $_POST["volume"];

				// calculate cash after sale
				$trans_total = ($_POST["volume"] * $quote["price"]) * -1.0000;
				is_float($trans_total);
				$update_cash = $curr_cash + $trans_total;
				is_float($update_cash);
				
				if ($update_cash < 0)
				{
					apologize("You do not posses the required funds to complete this transaction!");
				}
				
				// update shares and current cash
		        static $handle;
		        if (!isset($handle))
		        {
		            try
		            {
		                // connect to database
		                $handle = new PDO("mysql:dbname=" . DATABASE . ";host=" . SERVER, USERNAME, PASSWORD);

		                // ensure that PDO::prepare returns false when passed invalid SQL
		                $handle->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);; 
		            }
		            catch (Exception $e)
		            {
		                // trigger (big, orange) error
		                trigger_error($e->getMessage(), E_USER_ERROR);
		                exit;
		            }
		        }
		        $handle->beginTransaction();

				$buy1 = "INSERT INTO `holdings` (id, symbol, shares) VALUES ('{id}', '{symbol}', '{volume}') ON DUPLICATE KEY UPDATE shares = shares + VALUES(shares)";
				$val1 = array('{id}', '{symbol}', '{volume}');
				$arr1 = array($_SESSION["id"], $symbol, $_POST["volume"]);
				$step1 = str_replace($val1, $arr1, $buy1);

				$buy2 = "UPDATE `users` SET `cash` = {cash} WHERE `id` = {id}";
				$val2 = array('{cash}', '{id}');				
				$arr2 = array($update_cash, $_SESSION["id"]);
				$step2 = str_replace($val2, $arr2, $buy2);

				$buy3 = "INSERT INTO `history` (id, type, symbol, shares, total) VALUES ('{id}', 'BUY', '{symbol}', '{shares}', '{total}')";
				$val3 = array('{id}', '{symbol}', '{shares}', '{total}');
				$arr3 = array($_SESSION["id"], $symbol, $_POST["volume"], $trans_total);
				$step3 = str_replace($val3, $arr3, $buy3);

				$queries = array($step1, $step2, $step3);
				$flag = true;

				foreach($queries as $query)
				{
					if(!$handle->query($query))
					{
						$handle->rollback();
						$flag = false;
						return;
					}
				}

				// If no query returned an error which caused $flag to be set to false
				if($flag)
				{
					$handle->commit();
					email(["type" => "BUY", "symbol" => $symbol, "volume" => $_POST["volume"], "cost" => $trans_total, "recipient" => $email, "name" => $name]);
					success("Transaction Completed");
				}
				else
				{
					$handle->rollback();
					apologize("Something went wrong! Please try again.");
				}
			}
		}
	}
?>