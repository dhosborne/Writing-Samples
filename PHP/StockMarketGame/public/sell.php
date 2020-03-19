<?php
	
	require("../includes/config.php");
	$users = query("SELECT * FROM users WHERE id = ?", $_SESSION["id"]);
	$curr_cash = $users[0]["cash"];
	is_float($curr_cash);
	$email = $users[0]["email"];
	$name = $users[0]["username"];

	if ($_SERVER["REQUEST_METHOD"] == "GET")
	{
		render("sell_form.php", ["title" => "sell", "curr_cash" => $curr_cash]);
	}

	if ($_SERVER["REQUEST_METHOD"] == "POST")
	{
		if (empty($_POST["symbol"]))
		{
			apologize("Please enter a symbol");
		}
		if (empty($_POST["volume"]))
		{
			apologize("Please specify the volume you wish to sell");
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
				if ($holdings == NULL)
				{
					apologize("You do not own any shares");
				}
				else
				{
					// calculate holdings after sale
					$curr_hold = $holdings[0];
					$update_hold = $curr_hold["shares"] - $_POST["volume"];
					
					// calculate cash after sale
					$trans_total = $_POST["volume"] * $quote["price"];
					is_float($trans_total);
					$update_cash = $curr_cash + $trans_total;
					
					if ($update_hold < 0)
					{
						apologize("Requested volume is greater than current holdings.  Please try again");
					}
					
					// start sell transaction

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

					if ($update_hold > 0) 
					{
						$subject1 = "UPDATE `holdings` SET `shares` = '{shares}' WHERE `id` = '{id}' AND `symbol` = '{symbol}'";
						$search1 = array('{shares}', '{id}', '{symbol}');
						$replace1 = array($update_hold, $_SESSION["id"], $symbol);
						
					}
					else
					{
						$subject1 = "DELETE FROM `holdings` WHERE `id` = '{id}' AND `symbol` = '{symbol}'";
						$search1 = array('{id}', '{symbol}');
						$replace1 = array($_SESSION["id"], $symbol);
					}
					$step1 = str_replace($search1, $replace1, $subject1);

					$subject2 = "UPDATE `users` SET `cash` = '{cash}' WHERE `id` = '{id}'";
					$search2 = array('{cash}', '{id}');
					$replace2 = array($update_cash, $_SESSION["id"]);
					$step2 = str_replace($search2, $replace2, $subject2);

					$subject3 = "INSERT INTO `history` (id, type, symbol, shares, total) VALUES ('{id}', 'SELL', '{symbol}', '{shares}', '{total}')";
					$search3 = array('{id}', '{symbol}', '{shares}', '{total}');
					$replace3  = array($_SESSION["id"], $symbol, $_POST["volume"], $trans_total);
					$step3 = str_replace($search3, $replace3, $subject3);

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

					if ($flag)
					{
						$handle->commit();
						email(["type" => "SELL", "symbol" => $symbol, "volume" => $_POST["volume"], "cost" => $trans_total, "recipient" => $email, "name" => $name]);
						success("Transaction successful!");

					}
					else
					{
						$handle->rollback();
						apologize("Something went wrong! Please try again.");
					}
				}
			}
		}
	}
?>