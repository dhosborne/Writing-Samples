<body>
	<div id="section">
		<h2><?php print_r($quote["name"])?></h2>
		<table class="table">
			<thead>
				<tr>
					<th>Symbol</th>
					<th>Name</th>
					<th>Price</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th><?php print_r($quote["symbol"]) ?></th>
					<th><?php print_r($quote["name"]) ?> </th>
					<th><?php print_r($quote["price"]) ?></th>
				</tr>
			</tbody>
		</table>
	</div>
</body>