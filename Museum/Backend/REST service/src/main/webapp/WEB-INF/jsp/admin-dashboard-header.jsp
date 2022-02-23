<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a href="#">Korisnici</a>
                </li>
                <li class="nav-item">
                    <a href="#">Muzeji</a>
                </li>
                <li class="nav-item">
                    <a href="#">Obilasci</a>
                </li>
            </ul>
            <a class="btn btn-danger" href="/admin/invalidate_session" role="button">Odjava</a>
        </div>
    </div>
</nav>