<!DOCTYPE html>
<html lang="en">
    <head>
        <title>RoomDisplay</title>
        <style>.error { color: red; } .success { color:${param.color}; }</style>
    </head>
    <body>
        <form action="RoomProcess" method="post">
            <p>
                <label for="direction">What room do you want to go?</label>
                <input id="direction" name="direction" value="${direction}">
                <input type="submit">
                <br>
            </p>
            <p>${message}</p> 
        </form>
    </body>
</html>