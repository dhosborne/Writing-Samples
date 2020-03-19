<form action="retire.php" method="post">
    <fieldset>
        <h4>Enter Confimation</h4>
        <div class="form-group">
            <h6>Type "RETIRE" to Confirm</h6>
            <p>This process cannot be reversed!</p>
            <input class="form-control" name="confirm" placeholder="RETIRE" type="text"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default">RETIRE</button>
        </div>
    </fieldset>
</form>
<div>
    <a href="javascript:history.go(-1);"><span class="glyphicon glyphicon-circle-arrow-left"></span> Cancel</a>
</div>