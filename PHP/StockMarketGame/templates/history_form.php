<div class="container">
  <h2>Account History</h2>            
  <table class="table table-hover">
			<thead>
				<tr>
					<th>Transaction#</th>
					<th>Type</th>
					<th>Symbol</th>
					<th>Name</th>
					<th>Shares</th>
					<th>Price</th>
					<th>TOTAL</th>
				</tr>
			</thead>
			<tbody>
				<?php
					$history = query("SELECT * FROM history WHERE id = ?", $_SESSION["id"]);
					if ($history != NULL)
					{	
						foreach ($history as $history)
				        {
			        		$quote = lookup($history["symbol"]);
			        		print("<tr>");
				            print("<th>{$history["transaction"]}</th>");
				            print("<th>{$history["type"]}</th>");
				            print("<th>{$history["symbol"]}</th>");
				            print("<th>{$quote["name"]}</th>");
				            print("<th>{$history["shares"]}</th>");
				            print("<th>$ {$quote["price"]}</th>");
				            print("<th>$ {$history["total"]}</th>");
				            print("</tr>");
			        	}
		        	}
	    		?>
			</tbody>
		</table>
	</div>
</div>