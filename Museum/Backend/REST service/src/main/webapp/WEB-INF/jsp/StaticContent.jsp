<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Books</title>
</head>
    <body>
        <h1>Upload files:</h1>
    <form method="post" enctype="multipart/form-data">
        <label for="firstName">Ime</label>
        <input type="text" id="firstName" name="firstName" required/>

        <label for="file1">Prvi fajl</label>
        <input type="file" name="file" id="file1" accept="image/*"/>

        <label for="file2">Drugi fajl</label>
        <input type="file" name="file" id="file2" accept="image/*"/>

        <label for="video1">Prvi video</label>
        <input type="file" name="file" id="video1" accept="video/*"/>

        <input type="submit" value="Posalji"/>
    </form>
    </body>
</html>