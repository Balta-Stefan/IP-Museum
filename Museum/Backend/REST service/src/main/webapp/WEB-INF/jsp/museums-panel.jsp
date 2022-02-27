<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="stylesheet-js-includes.jsp" %>
</head>
<body>
<%@ include file="admin-dashboard-header.jsp"%>

<div class="container-fluid vh-100">
    <div class="row g-2">
        <h1 id="neki_region">Ovdje je neki region: </h1>
        <div class="row">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Ime</th>
                    <th scope="col">Adresa</th>
                    <th scope="col">Broj telefona</th>
                    <th scope="col">Država</th>
                    <th scope="col">Grad</th>
                    <th scope="col">Tip</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="museum" items="${museums}" varStatus="loop">
                    <tr>
                        <th scope="row">${loop.count}</th>
                        <td>${museum.getName()}</td>
                        <td>${museum.getAddress()}</td>
                        <td>${museum.getPhone()}</td>
                        <td>${museum.getCountry()}</td>
                        <td>${museum.getCity()}</td>
                        <td>${museum.getType().getType()}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <nav>
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                        <li class="page-item ${(loop.current-1 == pageNumber) ? 'active' : ''}">
                            <a class="page-link" href="./museums?pageNumber=${loop.index-1}&pageSize=${pageSize}">
                                <c:out value="${loop.current}"/>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>

        <div class="row">
            <p>Ukupno muzeja: ${totalMuseums}</p>
        </div>

        <div class="row">
            <div class="col-md-4">
                <a class="btn btn-primary" href="./museums/new">Dodaj novi muzej</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>

