<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema Votacion</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="shortcut icon" href="assets/favicon.jpg" />
</head>
<body>
    <section>
        <div class="form-box">
            <div class="form-value">
                <form action="loginvotardServlet" method="POST">
                    <h2>Sistema de Votaciones</h2>
                    
                    <div class="inputbox">
                        <i class="fa-solid fa-id-card"></i>
                        <input type="text" name="documento_identidad" required>
                        <label>Documento Identidad</label>
                    </div>

                    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                    <% if (errorMessage != null) { %>
                        <div class="error-message">
                            <%= errorMessage %>
                        </div>
                    <% } %>

                    <button type="submit">Enviar</button>

                    <div class="register-button">
                        <a href="index.jsp">Volver al inicio de sesión</a>
                    </div>
                </form>
            </div>
        </div>
    </section>
</body>
</html>
