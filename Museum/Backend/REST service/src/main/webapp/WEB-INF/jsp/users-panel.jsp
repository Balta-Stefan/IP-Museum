<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="stylesheet-js-includes.jsp"%>

    <script>
        const msg = "${message}";
        if(msg != ""){
            alert("${message}");
        }
    </script>
</head>
<body>
<%@ include file="admin-dashboard-header.jsp"%>

<div class="container-fluid vh-100">
    <div class="row g-2">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Ime</th>
                    <th scope="col">Prezime</th>
                    <th scope="col">Email</th>
                    <th scope="col">Uloga</th>
                    <th scope="col">Aktivan</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}" varStatus="loop">
                <tr>
                    <th scope="row">${loop.count}</th>
                    <td>${user.getFirstName()}</td>
                    <td>${user.getLastName()}</td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getRole()}</td>
                    <td>${(user.getActive()) == true ? "Da" : "Ne"}</td>
                    <form method="post" action="./users?onlyInactive=${onlyInactive}&pageNumber=${pageNumber}&pageSize=${pageSize}">
                        <input type="hidden" value="${user.getUserID()}" name="userID"/>
                        <input type="hidden" value="${!user.getActive()}" name="newStatus"/>
                        <td>
                            <c:if test="${user.getActive() == true}">
                                <button type="submit" class="btn btn-danger">Deaktiviraj</button>
                            </c:if>
                            <c:if test="${user.getActive() == false}">
                                <button type="submit" class="btn btn-primary">Aktiviraj</button>
                            </c:if>
                        </td>
                    </form>
                    <form method="post" action="./users?onlyInactive=${onlyInactive}&pageNumber=${pageNumber}&pageSize=${pageSize}">
                        <td>
                            <input type="hidden" value="${user.getUserID()}" name="userID"/>
                            <input type="hidden" value="${true}" name="changePassword"/>
                            <button type="submit" class="btn btn-warning">Izmijeni lozinku</button>
                        </td>
                    </form>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <nav>
            <ul class="pagination justify-content-center">
                <!-- /users?onlyInactive=${onlyInactive}&pageNumber=${pageNumber}&pageSize=${pageSize} -->
                <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                    <li class="page-item ${(loop.current-1 == pageNumber) ? 'active' : ''}">
                        <a class="page-link" href="./users?onlyInactive=${onlyInactive}&pageNumber=${loop.index-1}&pageSize=${pageSize}">
                            <c:out value="${loop.current}"/>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
        <p>Ukupno korisnika: ${totalUsers}</p>
    </div>
</div>
</body>
</html>

