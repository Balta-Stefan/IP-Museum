<%@ page import="com.virtualbank.models.TransactionDTO" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <a href="?action=logout">Odjava</a>
    <a href="?action=changeActivity">Izmjena statusa aktivnosti naloga</a>
    <p>${activity_change_status}</p>

    <table>
        <tr>
            <th>Datum transakcije</th>
            <th>Iznos transakcije</th>
        </tr>
        <%for(TransactionDTO t: (List<TransactionDTO>)session.getAttribute("transactions")){%>
        <tr>
            <td><%= t.getTimestamp().toString() %></td>
            <td><%= t.getAmount()%></td>
        </tr>
        <% } %>
    </table>
</body>
</html>