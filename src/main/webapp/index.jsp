<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página de Bienvenida - Cine</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('https://www.concierto.cl/wp-content/uploads/2022/09/Dia-del-cine-1.jpg');
            background-size: cover;
            background-position: center;
            color: white;
            text-align: center;
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            box-sizing: border-box;
        }

        h1 {
            font-size: 3.5em;
            color: #fff;
            text-shadow: 3px 3px 10px rgba(0, 0, 0, 0.8);
            margin-bottom: 20px;
            padding: 10px;
            background-color: rgba(0, 0, 0, 0.5); /* Fondo semitransparente */
            border-radius: 10px;
        }

        p {
            font-size: 1.5em;
            color: #fff;
            margin-bottom: 30px;
            text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.7);
            padding: 10px;
            background-color: rgba(0, 0, 0, 0.5); /* Fondo semitransparente */
            border-radius: 10px;
        }

        .icon {
            font-size: 50px;
            color: #f39c12;
            margin: 10px;
            text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.7);
        }

        .btn-iniciar {
            background-color: #e74c3c;
            color: white;
            border: none;
            padding: 20px 40px;
            font-size: 1.5em;
            cursor: pointer;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            transition: background-color 0.3s ease;
        }

        .btn-iniciar:hover {
            background-color: #c0392b;
        }

        form {
            margin-top: 30px;
        }

        /* Para los íconos */
        .icon-container {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<h1>¡Bienvenido al Sistema de Gestión del Cine!</h1>
<p>¡La experiencia del cine, ahora a su alcance!</p>
<div class="icon-container">
    <i class="fas fa-film icon"></i>
    <i class="fas fa-ticket-alt icon"></i>
    <i class="fas fa-popcorn icon"></i>
</div>
<form action="TipoSala.jsf" method="get">
    <button class="btn-iniciar">Iniciar</button>
</form>
</body>
</html>
