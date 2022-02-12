<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <style>
        html,
        body {
            height: 100%;
        }

        body {
            display: flex;
            align-items: center;
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .form-signin {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: auto;
        }

        .form-signin .checkbox {
            font-weight: 400;
        }

        .form-signin .form-floating:focus-within {
            z-index: 2;
        }

        .form-signin input[type="email"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }

        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        #login_message{
            color: red;
        }
    </style>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="text-center">
    <main class="form-signin">
        <form action="?action=login" method="POST">
            <h1 class="h3 mb-3 fw-normal">Prijavite se</h1>

            <div class="form-floating">
                <input type="text" class="form-control" id="numberInput" name="number" placeholder="Broj">
                <label for="numberInput">Broj kreditne kartice</label>
            </div>
            <div class="form-floating">
                <input type="password" class="form-control" id="pinInput" name="pin" placeholder="Pin">
                <label for="pinInput">Pin</label>
            </div>
            <div class="form-floating">
                <input type="date" class="form-control" id="expirationInput" name="expDate" placeholder="Datum isteka">
                <label for="expirationInput">Datum isteka</label>
            </div>


            <button class="w-100 btn btn-lg btn-primary" type="submit">Prijavite se</button>

            <h1 id="login_message">${login_status}</h1>
        </form>
    </main>
</body>
</html>