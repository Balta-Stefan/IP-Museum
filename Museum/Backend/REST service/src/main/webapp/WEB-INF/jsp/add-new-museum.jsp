<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="stylesheet-js-includes.jsp" %>
    <script>
        <%@ include file="../js/add-new-museum.js" %>
    </script>
</head>
<body>
<%@ include file="admin-dashboard-header.jsp"%>

<div class="container-fluid vh-100">
    <div class="row g-2">
        <div class="offset-md-4 col-md-4">
            <form method="post" action="./museums/new" id="museumSubmitForm">
                <div class="mb-3" style="text-align: center">
                    <h1>Dodaj novi muzej</h1>
                </div>

                <input required type="hidden" id="countryAlpha2Code" name="countryAlpha2Code"/>

                <div class="mb-3">
                    <label for="country" class="form-label">Država</label>
                    <select class="form-select" name="country" id="country">
                        <option selected disabled>Odaberite državu</option>
                        <c:forEach var="country" items="${countries}" varStatus="loop">
                            <option value="${country.getCca2()}">${country.getName().getCommon()}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="region" class="form-label">Region</label>
                    <select class="form-select" name="region" id="region">
                        <option selected disabled>Odaberite region</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="city" class="form-label">Grad</label>
                    <select class="form-select" name="city" id="city">
                        <option selected disabled>Odaberite grad</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="name" class="form-label">Ime</label>
                    <input required type="text" class="form-control" id="name" name="name" placeholder="Unesite ime muzeja">
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Adresa</label>
                    <input required type="text" class="form-control" id="address" name="address" placeholder="Adresa muzeja">
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">Broj telefona</label>
                    <input required type="text" class="form-control" id="phone" name="phone" placeholder="Broj telefona">
                </div>

                <div class="mb-3">
                    <label for="latitude" class="form-label">Geografska širina</label>
                    <input required type="text" class="form-control" id="latitude" name="latitude" placeholder="Geografska širina">
                </div>
                <div class="mb-3">
                    <label for="longitude" class="form-label">Geografska dužina</label>
                    <input required type="text" class="form-control" id="longitude" name="longitude" placeholder="Geografska dužina">
                </div>
                <div class="mb-3">
                    <label for="type" class="form-label">Tip muzeja</label>
                    <select required class="form-select" name="type" id="type">
                        <option selected disabled>Odaberite tip muzeja</option>
                        <c:forEach var="type" items="${museumTypes}" varStatus="loop">
                            <option value="${type.getMuseumTypeID()}">${type.getType()}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="thumbnail" class="form-label">Slika (unijeti link)</label>
                    <input required type="text" class="form-control" id="thumbnail" name="thumbnail" placeholder="Link slike">
                </div>

                <div class="mb-3">
                    <button class="btn btn-primary" type="submit">Dodaj muzej</button>
                </div>

                <h1 style="text-align: center">${formSubmissionMessage}</h1>
            </form>
        </div>

    </div>
</div>
</body>
</html>

