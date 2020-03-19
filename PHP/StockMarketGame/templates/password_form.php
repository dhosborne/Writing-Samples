<form action="update_password.php" method="post">
    <fieldset>
        <h4>Change Password</h4>
        <div class="form-group">
            <h6>Enter New Password</h6>
            <input class="form-control" name="password" placeholder="Password" type="password"/>
        </div>
        <div class="form-group">
            <h6>Confirm Password</h6>
            <input class="form-control" name="confirmation" placeholder="Confirm Password" type="password"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default">Update</button>
        </div>
    </fieldset>
</form>
<div>
    <a href="javascript:history.go(-1);"><span class="glyphicon glyphicon-circle-arrow-left"></span> Cancel</a>
</div>