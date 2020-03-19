
<form action="update_email.php" method="post">
    <fieldset>
        <h4>Update Email Address</h4>
        <div class="form-group">
            <h6>Enter New Email</h6>
            <input class="form-control" name="email" placeholder="name@example.com" type="email"/>
        </div>
        <div class="form-group">
            <h6>Confirm New Email</h6>
            <input class="form-control" name="confirmation" placeholder="name@example.com" type="email"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default">Update</button>
        </div>
    </fieldset>
</form>
<div>
    <a href="javascript:history.go(-1);"><span class="glyphicon glyphicon-circle-arrow-left"></span> Cancel</a>
</div>