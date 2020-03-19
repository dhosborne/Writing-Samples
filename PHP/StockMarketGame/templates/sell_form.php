<div class="container">
  <h4> Current Holdings</h4>           
  <table class="table">
    <thead>
      <tr>
		<th>Symbol</th>
		<th>Name</th>
		<th>Shares</th>
		<th>Price</th>
		<th>TOTAL</th>
      </tr>
    </thead>
    <tbody>
		<?php 
			$holdings = query("SELECT * FROM holdings WHERE id = ?", $_SESSION["id"]);
			if ($holdings != NULL)
			{												
				foreach ($holdings as $holdings)
				{
					$quote = lookup($holdings["symbol"]); 
					$totes = $holdings["shares"] * $quote["price"];
					is_float($totes);
					print("<tr>");							            
					print("<th>{$holdings["symbol"]}</th>");
					print("<th>{$quote["name"]}</th>");
					print("<th>{$holdings["shares"]}</th>");
					print("<th>$ {$quote["price"]}</th>");
					print("<th>$ {$totes}</th>");
					print("</tr>");									            
				}									        
			}
		?>
    </tbody>
  </table>
</div>
<form action="sell.php" method="post">
	<fieldset>
		<div class="form-group">
			<input autofocus class="form-control" name="symbol" placeholder="symbol" type="text" />
			<input class="form-control" name="volume" placeholder="# of shares" type="text">
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-default">Sell</button>
		</div>
	</fieldset>
</form>