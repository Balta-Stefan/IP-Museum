<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="stylesheet-js-includes.jsp"%>

    <script defer>
        <%@ include file="../js/admin-dashboard.js"%>
    </script>
</head>
    <body>
    <%@ include file="admin-dashboard-header.jsp"%>

    <div class="container-fluid vh-100">
        <div class="row g-2">
            <h1>admin</h1>
            <canvas id="myChart" width="400" height="400"></canvas>
        </div>
    </div>
    </body>
</html>

