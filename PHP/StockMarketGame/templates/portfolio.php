		<div class="container">
			<h2>My Portfolio Summary</h2>
			<div class="panel-group" id="accordion">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapse1"> My Portfolio</a>
						</h4>
					</div>
					<div id="collapse1" class="panel-collapse collapse">
						<table class="table table-hover">
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
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Recent Transactions</a>
						</h4>
					</div>
					<div id="collapse2" class="panel-collapse collapse">
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
									$history = query("SELECT * FROM history WHERE id = ? ORDER BY transaction DESC LIMIT 5", $_SESSION["id"]);
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
							<a href='history.php'>All History</a>
					</div>
				</div> 
			</div>
		</div>