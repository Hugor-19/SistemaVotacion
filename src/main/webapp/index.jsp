<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema Votación</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="shortcut icon" href="assets/img/favicon.jpg" />
</head>
<body>
    <section>
        <div class="form-box">
            <div class="form-value">
                <form action="loginServlet" method="POST">
                    <h2>Ingrese su Usuario</h2>
                    <div class="inputbox">
                        <i class="fa-solid fa-envelope"></i>
                        <input type="email" name="email" required>
                        <label>Email</label>
                    </div>
                    <div class="inputbox">
                        <i class="fa-solid fa-lock"></i>
                        <input type="password" name="password" required>
                        <label>Password</label>
                    </div>
					 <%-- Mostrar mensaje de error si existe --%>
					<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                    <% if (errorMessage != null) { %>
                        <div class="error-message">
                            <%= errorMessage %>
                        </div>
                    <% } %>
                    <button type="submit">Iniciar Sesión</button>
                    <div class="register-button">
                        <a href="votar.jsp">Realizar Votacion</a>
                    </div>
                </form>
            </div>
        </div>
    </section>
    
</body>
</html>
