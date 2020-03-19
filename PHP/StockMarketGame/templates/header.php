<!DOCTYPE html>

<html>
	<head>
		<link href="/css/bootstrap.min.css" rel="stylesheet"/>
		<link href="/css/bootstrap-theme.min.css" rel="stylesheet"/>
		<link href="/css/styles.css" rel="stylesheet"/>

		<?php if (isset($title)): ?>
		<title>C$50 Finance: <?= htmlspecialchars($title) ?></title>
		<?php else: ?>
		<title>C$50 Finance</title>
		<?php endif ?>
		<?php
			$rows = query("SELECT * FROM users WHERE id = ?", $_SESSION["id"]);
			$portfolio = $rows[0];
		?>
		<script src="/js/jquery-1.11.1.min.js"></script>
		<script src="/js/bootstrap.min.js"></script>
		<script src="/js/scripts.js"></script>
	</head>	
	<body>
		<div class="container">
			<div id="top">
				<a href="/"><img alt="C$50 Finance" src="/img/CS50logosmallest.png"/></a>
			</div>
		<div id="middle">

		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Welcome, <?php print_r($portfolio["username"]) ?> </a>
				</div>
				<div>
					<ul class="nav navbar-nav">
						<li><a href="/#">Portfolio</a></li>
						<li><a href="quote.php">Get A Quote</a></li>
						<li><a href="buy.php">Buy</a></li>
						<li><a href="sell.php">Sell</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="">Current Cash: $<?php print_r($portfolio["cash"])?></a></li>
						<li><a href="user.php"><span class="glyphicon glyphicon-user"></span>Account</a></li>
						<li><a href="logout.php"><span class="glyphicon glyphicon-log-out"></span> Log-Out</a></li>
					</ul>
				</div>
			</div>
		</nav> 