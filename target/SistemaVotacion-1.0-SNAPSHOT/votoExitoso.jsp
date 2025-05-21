<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Voto Registrado</title>
    <style>
        body {
            background-color: #f4f4f4;
            font-family: sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .message-container {
            background-color: #d1e7dd;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            text-align: center;
            color: #155724;
        }
        h2 {
            margin-bottom: 20px;
        }
        p {
            margin-bottom: 15px;
        }
        a {
            color: #0b5ed7;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="message-container">
        <h2>¡Su voto ha sido registrado!</h2>
        <%
            Integer candidatoIdVotado = (Integer) request.getAttribute("votoCandidatoId");
            if (candidatoIdVotado != null) {
                out.println("<p>Usted ha votado por el candidato con ID: " + candidatoIdVotado + "</p>");
                // Si tuvieras la lista de candidatos aquí, podrías buscar el nombre.
            }
        %>
        <p>Gracias por participar.</p>
        <p><a href="index.jsp">Volver al formulario</a></p>
    </div>
</body>
</html>