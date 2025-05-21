<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema Votacion</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="shortcut icon" href="assets/img/favicon.jpg" />
</head>
<body>
    <section>
        <div class="form-box">
            <div class="form-value">
                <form action="votarServlet" method="POST">
                    <h2>Sistema de Votaciones</h2>

                    <div class="inputbox">
                        <i class="fa-solid fa-id-card"></i>
                        <input type="text" name="documento_identidad" required>
                        <label>Cedula</label>
                    </div>

                    <%-- JSP scriptlet to retrieve and display the error message --%>
                    <%
                        String errorMessage = (String) request.getAttribute("errorMessage");
                        // Also check for the "ciudadanoEncontrado" attribute for the specific "not found" message
                        Boolean ciudadanoEncontrado = (Boolean) request.getAttribute("ciudadanoEncontrado");

                        if (errorMessage != null) {
                    %>
                            <div class="error-message">
                                <%= errorMessage %>
                            </div>
                    <%
                        } else if (ciudadanoEncontrado != null && !ciudadanoEncontrado) {
                    %>
                            <div class="error-message">
                                No se encontró un ciudadano con ese número de documento.
                            </div>
                    <%
                        }
                    %>

                    <input type="hidden" name="action" value="login">
                    <button type="submit">Enviar</button>

                    <div class="register-button">
                        <a href="index.jsp">Inicio de sesión</a>
                    </div>
                </form>
            </div>
        </div>
    </section>
</body>
</html>