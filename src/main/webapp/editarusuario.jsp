<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Usuario" %>
<%
    Usuario usuario = (Usuario) request.getAttribute("usuario");
%>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
  <link rel="icon" type="image/png" href="assets/favicon.jpg">
  <title>Usuarios</title>
  <!--     Fonts and icons     -->
  <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  <!-- Nucleo Icons -->
  <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
  <!-- icons font -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <!-- CSS Files -->
  <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
  <!-- CSS Just for demo purpose, don't include it in your project -->
  <link href="assets/demo/demo.css" rel="stylesheet" />
</head>

<body class="">
  <%@ include file="sidebar.jsp"%>
  <%@ include file="navbar.jsp"%>
  <%@ include file="script.jsp"%>
  <%@ include file="footer.jsp"%>

  <div class="main-panel">
    <!-- End Navbar -->
    <div class="content container mt-4">
    <div class="row">
        <div class="col-md-8 offset-md-2">
            <div class="card card-upgrade">
                <div class="card-header text-center">
                    <h4 class="card-title">Editar Usuario</h4>
                    <p class="card-category">Modificar los datos del usuario seleccionado</p>
                </div>

                <div class="card-body">
                <!-- Mensaje de éxito -->
                    <% if (request.getAttribute("mensaje") != null) { %>
                        <div class="alert alert-success text-center">
                            <%= request.getAttribute("mensaje") %>
                        </div>
                    <% } %>
                    <form action="editUsuarioServlet" method="POST">
                        <input type="hidden" name="id" value="<%= usuario.getId() %>">
                        
                        <div class="form-group">
                            <label for="nombre">Nombre</label>
                            <input type="text" name="nombre" class="form-control" value="<%= usuario.getNombre() %>" required>
                        </div>

                        <div class="form-group">
                            <label for="email">Correo electrónico</label>
                            <input type="email" name="email" class="form-control" value="<%= usuario.getEmail() %>" required>
                        </div>

                        <div class="form-group">
                            <label for="password">Nueva contraseña</label>
                            <input type="password" name="password" class="form-control" placeholder="••••••">
                        </div>

                        <div class="form-group">
                            <label for="rol">Rol</label>
                            <select name="rol" class="form-control" required>
                                <option value="admin" <%= usuario.getRol().equals("admin") ? "selected" : "" %>>Admin</option>
                                <option value="doctor" <%= usuario.getRol().equals("doctor") ? "selected" : "" %>>Doctor</option>
                                <option value="secretaria" <%= usuario.getRol().equals("secretaria") ? "selected" : "" %>>Secretaria</option>
                                <option value="usuario" <%= usuario.getRol().equals("usuario") ? "selected" : "" %>>Usuario</option>
                            </select>
                        </div>
                        
                        <div class="text-center">
                            <button type="submit" class="btn btn-fill btn-primary">Guardar Cambios</button>
                            <a href="svUsuarios" class="btn btn-fill btn-danger">Cancelar</a>
                        </div>
                    </form>
                </div>
                
            </div>
        </div>
    </div>
</div>
</div>

</body>
</html>