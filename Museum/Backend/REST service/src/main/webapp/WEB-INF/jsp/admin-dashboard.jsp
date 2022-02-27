<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="stylesheet-js-includes.jsp"%>


    <script defer>
        const usersPerHoursArray = [
            <c:forEach var="value" items="${usersPerHours}">
                <c:out value="${value},"/>
            </c:forEach>
        ];
        const xLabels = [
            <c:forEach begin="0" end="23" varStatus="loop">
                <c:out value="${loop.current},"/>
            </c:forEach>
        ]

        <%@ include file="../js/admin-dashboard-graphs.js"%>
    </script>
</head>
    <body>
    <%@ include file="admin-dashboard-header.jsp"%>

    <div class="container-fluid vh-100">
        <div class="row g-2">
            <div class="row g-2">
                <div class="col-md">
                    <h4>Broj trenutno prijavljenih korisnika: ${numOfLoggedInUsers}</h4>
                </div>
                <div class="col-md">
                    <h4>Broj ukupno registrovanih korisnika: ${numOfRegisteredUsers}</h4>
                </div>
            </div>

            <div class="row" style="max-height: 25rem">
                <canvas id="users_chart" width="400" height="400"></canvas>
            </div>

        </div>
    </div>
    </body>
</html>

