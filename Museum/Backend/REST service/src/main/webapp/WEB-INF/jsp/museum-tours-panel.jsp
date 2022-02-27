<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="stylesheet-js-includes.jsp"%>
</head>
    <body>
        <%@ include file="admin-dashboard-header.jsp"%>

        <div class="container-fluid vh-100">
            <div class="row g-2">
                <div class="offset-md-4 col-md-4">
                    <form method="post" action="./museums/${museum.getMuseumID()}/tours" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="startDate" class="form-label">Datum početka</label>
                            <input required type="date" class="form-control" id="startDate" name="startDate" placeholder="Unesite datum početka">
                        </div>
                        <div class="mb-3">
                            <label for="startTime" class="form-label">Vrijeme početka</label>
                            <input required type="time" class="form-control" id="startTime" name="startTime" placeholder="Unesite vrijeme početka">
                        </div>
                        <div class="mb-3">
                            <label for="durationHours" class="form-label">Trajanje posjete u satima</label>
                            <input required type="number" min="0" class="form-control" id="durationHours" name="durationHours" placeholder="Trajanje u satima">
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Cijena</label>
                            <input required type="number" min="0" class="form-control" id="price" name="price" placeholder="Cijena karte">
                        </div>
                        <div class="mb-3">
                            <label for="pictures" class="form-label">Odaberite 5 do 10 slika</label>
                            <input required class="form-control" accept="image/*" type="file" id="pictures" name="pictures" multiple placeholder="Odaberite slike">
                        </div>
                        <div class="mb-3">
                            <label for="youtubeLink" class="form-label">YouTube link prezentacije</label>
                            <input type="text" class="form-control" id="youtubeLink" name="youtubeLink" placeholder="Link">
                        </div>
                        <div class="mb-3">
                            <label for="video" class="form-label">Odaberite video</label>
                            <input class="form-control" accept="video/*" type="file" id="video" name="video" multiple placeholder="Odaberite video">
                        </div>

                        <button type="submit" class="btn btn-primary">Dodaj posjetu</button>
                        <h1 style="text-align: center">${tourSubmitMessage}</h1>

                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

