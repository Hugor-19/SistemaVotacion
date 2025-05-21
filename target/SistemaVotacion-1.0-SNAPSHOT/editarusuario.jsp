<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Usuario" %>
<!DOCTYPE html>
<html lang="en">
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("svUsuarios");
        return;
    }
%>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
    <title>Edita Usuario</title>

    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

    <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
    <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
    <link href="assets/demo/demo.css" rel="stylesheet" />
</head>
<body>

<%@ include file="sidebar.jsp" %>
<%@ include file="navbar.jsp" %>
<%@ include file="script.jsp" %>

<div class="main-panel">
    <div class="content">
        <div class="row">
            <div class="col-md-8 ml-auto mr-auto">
                <div class="card card-upgrade">
                    <div class="card-header text-center">
                        <h4 class="card-title">Editar Usuario</h4>
                        <p class="card-category">Modificar los datos del usuario seleccionado</p>
                    </div>

                    <div class="card-body">
                        <div class="container mt-4">
                            <%
                                String successMessage = (String) session.getAttribute("successMessage");
                                String errorMessage = (String) session.getAttribute("errorMessage");

                                if (successMessage != null) {
                            %>
                                    <div class="alert alert-success"><%= successMessage %></div>
                            <%
                                    session.removeAttribute("successMessage");
                                }

                                if (errorMessage != null) {
                            %>
                                    <div class="alert alert-danger"><%= errorMessage %></div>
                            <%
                                    session.removeAttribute("errorMessage");
                                }
                            %>
                        </div>

                        <!-- ✅ Cambiado el action aquí -->
                        <form action="editUsuarioServlet" method="POST">
                            <input type="hidden" name="id" value="<%= usuario.getId() %>">

                            <div class="row">
                                <div class="col-md-6 pr-md-1">
                                    <div class="form-group">
                                        <label for="nombre">Nombre Usuario</label>
                                        <input type="text" class="form-control" name="nombre" value="<%= usuario.getNombre() %>" required>
                                    </div>
                                </div>

                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" name="email" value="<%= usuario.getEmail() %>" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 pr-md-1">
                                    <div class="form-group">
                                        <label for="password">Contraseña</label>
                                        <input type="password" class="form-control" name="password" value="<%= usuario.getPassword() %>" required>
                                    </div>
                                </div>

                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="rol">Rol</label>
                                        <select name="rol" class="form-control" required>
                                            <option value="admin" <%= "admin".equals(usuario.getRol()) ? "selected" : "" %>>Administrador</option>
                                            <option value="registrador" <%= "registrador".equals(usuario.getRol()) ? "selected" : "" %>>Registrador</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="estado">Estado</label>
                                        <select name="estado" class="form-control" required>
                                            <option value="activo" <%= "activo".equals(usuario.getEstado()) ? "selected" : "" %>>Activo</option>
                                            <option value="inactivo" <%= "inactivo".equals(usuario.getEstado()) ? "selected" : "" %>>Inactivo</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="card-footer">
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

<%@ include file="footer.jsp" %>

</body>
</html>
