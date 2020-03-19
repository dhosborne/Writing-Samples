<div class="container">
  <h2>User Info</h2>
  <table id = "small_table" class="table table-bordered">
    <thead>
      <tr>
        <th>Username</th>
        <th>Email</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th><?= $title ?></th>
        <th><?= $email ?></th>
        <th><a href="retire.php" class= "btn btn-default" style = "color:#B22222"><span class="glyphicon glyphicon-flag" style= "color:#B22222"></span>Retire?</a></th>
      </tr>
    </tbody>
  </table>
</div>
<div>
  
</div>
<div class="container">
	<ul id="menu">
			<li><a href = "update_email.php"><span class="glyphicon glyphicon-envelope"></span> Update Email</a></li>
			<li><a href = "update_password.php"><span class="glyphicon glyphicon-lock"></span> Change Password</a></li>
	</ul>
</div>
<div>
	<a href = "/#"><span class="glyphicon glyphicon-circle-arrow-left"></span> Back to Portfolio</a>
</div>
