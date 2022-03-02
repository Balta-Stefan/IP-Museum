<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.virtualbank.models.TransactionDTO" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Title</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
    <a class="btn btn-primary" href="?action=logout">Odjava</a>
    <a class="btn btn-danger" href="?action=changeActivity">Izmjena statusa aktivnosti naloga</a>
    <p>${activity_change_status}</p>


    <table class="table">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Datum transakcije</th>
                <th scope="col">Iznos</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <c:forEach begin="1" end="${transactions.size()}" varStatus="loop">
                <tr>
                    <td>${loop.current}</td>
                    <td>${transactions.get(loop.index-1).getTimestamp().toString()}</td>
                    <td>${transactions.get(loop.index-1).getAmount()}</td>
                </tr>
                </c:forEach>
            </tr>
        </tbody>
    </table>
</body>
</html>