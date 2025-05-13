<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Usuario" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
  <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
  <title>Gestionar Usuarios</title>
  <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
  <link href="assets/demo/demo.css" rel="stylesheet" />
  <script>
    function toggleFormulario() {
      var form = document.getElementById("formularioUsuario");
      form.style.display = form.style.display === "none" ? "block" : "none";
    }
  </script>
</head>

<body class="">
  <%@ include file="sidebar.jsp" %>
  <%@ include file="navbar.jsp" %>
  <%@ include file="script.jsp" %>

  <div class="main-panel">
    <div class="content">

      <!-- Mostrar mensajes de éxito o error -->
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

      <!-- BOTÓN AGREGAR USUARIO -->
      <div class="text-center mb-4">
        <button onclick="toggleFormulario()" class="btn btn-primary">
          <i class="fa-solid fa-user-plus"></i> Agregar Usuario
        </button>
      </div>

      <!-- FORMULARIO DE REGISTRO -->
      <div class="row" id="formularioUsuario" style="display: none;">
        <div class="col-md-8 ml-auto mr-auto">
          <div class="card card-upgrade">
            <div class="card-header text-center">
              <h4 class="card-title">Alta de Usuarios</h4>
              <p class="card-category">Formulario para registrar nuevos usuarios</p>
            </div>
            <form action="addUsuarioServlet" method="POST">
              <div class="card-body">
                <div class="row">
                  <div class="col-md-6 pr-md-1">
                    <div class="form-group">
                      <label for="nombre">Nombre Usuario</label>
                      <input type="text" class="form-control" name="nombre" required>
                    </div>
                  </div>
                  <div class="col-md-6 pl-md-1">
                    <div class="form-group">
                      <label for="email">Email</label>
                      <input type="email" class="form-control" name="email" required>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-6 pr-md-1">
                    <div class="form-group">
                      <label for="password">Contraseña</label>
                      <input type="password" class="form-control" name="password" required>
                    </div>
                  </div>
                  <div class="col-md-6 pl-md-1">
                    <div class="form-group">
                      <label for="rol">Rol</label>
                      <select name="rol" class="form-control" required>
                        <option value="">Seleccione un rol</option>
                        <option value="admin">Administrador</option>
                        <option value="registrador">Registrador</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-6 pl-md-1">
                    <div class="form-group">
                      <label for="estado">Estado</label>
                      <select name="estado" class="form-control" required>
                        <option value="activo">Activo</option>
                        <option value="inactivo">Inactivo</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>

              <div class="card-footer">
                <button type="submit" class="btn btn-fill btn-primary">Crear Usuario</button>
                <button type="button" onclick="toggleFormulario()" class="btn btn-fill btn-danger">Cancelar</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- TABLA DE USUARIOS -->
      <div class="row">
        <div class="col-md-10 ml-auto mr-auto">
          <% List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("listaUsuarios"); %>
          <% if (listaUsuarios != null && !listaUsuarios.isEmpty()) { %>
            <div class="card">
              <div class="card-header text-center">
                <h4 class="card-title">Usuarios Registrados</h4>
              </div>
              <div class="card-body">
                <div class="table-responsive">
                  <table class="table">
                    <thead class="text-primary">
                      <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Email</th>
                        <th>Rol</th>
                        <th>Estado</th>
                      </tr>
                    </thead>
                    <tbody>
                      <% for (Usuario u : listaUsuarios) { %>
                        <tr>
                          <td><%= u.getId() %></td>
                          <td><%= u.getNombre() %></td>
                          <td><%= u.getEmail() %></td>
                          <td><%= u.getRol() %></td>
                          <td><%= u.getEstado() %></td>
                          <td>
                           <a href="editUsuarioServlet?id=<%= u.getId() %>" class="btn btn-warning btn-sm"><i class="fa-solid fa-pen-to-square"></i> Editar</a> 
                           <a href="eliminarUsuarioServlet?id=<%= u.getId() %>" class="btn btn-danger btn-sm" 
                           onclick="return confirm('¿Estás seguro de que deseas eliminar este usuario?');"><i class="fa-solid fa-trash-can"></i> Eliminar</a>
                        </tr>
                      <% } %>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          <% } else { %>
            <div class="alert alert-info text-center">No hay usuarios registrados.</div>
          <% } %>
        </div>
      </div>
    </div>
  </div>

  <%@ include file="footer.jsp" %>
</body>
</html>