<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Ciudadano" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
  <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
  <title>Gestionar Ciudadanos</title>
  <!-- Fonts and icons -->
  <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  <!-- Nucleo Icons -->
  <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
  <!-- Icons font -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <!-- CSS Files -->
  <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
  <!-- Demo CSS -->
  <link href="assets/demo/demo.css" rel="stylesheet" />
  <script>
    function toggleFormulario() {
      var form = document.getElementById("formularioUsuario");
      form.style.display = form.style.display === "none" ? "block" : "none";
    }
  </script>
</head>

<body>
  <%@ include file="sidebar.jsp" %>
  <%@ include file="navbar.jsp" %>
  <%@ include file="script.jsp" %>

  <div class="main-panel">
    <div class="content">

      <!-- Mostrar mensajes de éxito o error -->
      <% 
        String successMessage = (String) session.getAttribute("successMessage");
        String errorMessage = (String) session.getAttribute("errorMessage");
      %>
      <% if (successMessage != null) { %>
        <div class="alert alert-success text-center">
          <%= successMessage %>
        </div>
        <% session.removeAttribute("successMessage"); %>
      <% } %>
      <% if (errorMessage != null) { %>
        <div class="alert alert-danger text-center">
          <%= errorMessage %>
        </div>
        <% session.removeAttribute("errorMessage"); %>
      <% } %>

      <!-- Botón Agregar Ciudadano -->
      <div class="text-center mb-4">
        <button onclick="toggleFormulario()" class="btn btn-primary">
          <i class="fa-solid fa-user-plus"></i> Agregar Ciudadano
        </button>
      </div>

      <!-- Formulario de Registro -->
      <div class="row" id="formularioUsuario" style="display: none;">
        <div class="col-md-8 ml-auto mr-auto">
          <div class="card card-upgrade">
            <div class="card-header text-center">
              <h4 class="card-title">Alta de Ciudadanos</h4>
              <p class="card-category">Sección para dar de alta a los diferentes ciudadanos del sistema</p>
            </div>
            <form action="addCiudadanoServlet" method="POST">
              <div class="card-body">
                <!-- Campos del formulario -->
                <div class="row">
                  <div class="col-md-6 pr-md-1">
                    <div class="form-group">
                      <label for="nombre">Nombres</label>
                      <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombres" required>
                    </div>
                  </div>
                  <div class="col-md-6 pl-md-1">
                    <div class="form-group">
                      <label for="apellido">Apellido</label>
                      <input type="text" class="form-control" id="apellido" name="apellido" placeholder="Apellido" required>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6 pr-md-1">
                    <div class="form-group">
                      <label for="tipo_documento">Tipo de Documento</label>
                      <select name="tipo_documento" class="form-control" id="tipo_documento">
                        <option value="CC">Cédula de Ciudadanía</option>
                        <option value="CE">Cédula de Extranjería</option>
                        <option value="Pasaporte">Pasaporte</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-6 pl-md-1">
                    <div class="form-group">
                      <label for="numero_documento">Cédula</label>
                      <input type="text" class="form-control" id="numero_documento" name="numero_documento" placeholder="Cédula" required>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6 pr-md-1">
                    <div class="form-group">
                      <label for="telefono">Teléfono</label>
                      <input type="text" class="form-control" id="telefono" name="telefono" placeholder="Teléfono">
                    </div>
                  </div>
                  <div class="col-md-6 pl-md-1">
                    <div class="form-group">
                      <label for="fecha_nacimiento">Fecha de Nacimiento</label>
                      <input type="date" class="form-control" id="fecha_nacimiento" name="fecha_nacimiento" placeholder="Fecha de Nacimiento">
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6 pr-md-1">
                    <div class="form-group">
                      <label for="estado">Estado</label>
                      <select name="estado" class="form-control" id="estado">
                        <option value="activo">Activo</option>
                        <option value="inactivo">Inactivo</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="card-footer">
                <button type="submit" class="btn btn-fill btn-primary">Registrar Ciudadano</button>
                <a href="svCiudadanos" class="btn btn-fill btn-danger">Cancelar</a>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- Tabla de Ciudadanos -->
      <div class="row">
        <div class="col-md-10 ml-auto mr-auto">
          <% List<Ciudadano> listaCiudadanos = (List<Ciudadano>) request.getAttribute("listaCiudadanos"); %>
          <% if (listaCiudadanos != null && !listaCiudadanos.isEmpty()) { %>
            <div class="card">
              <div class="card-header text-center">
                <h4 class="card-title">Ciudadanos Registrados</h4>
              </div>
              <div class="card-body">
                <div class="table-responsive">
                  <table class="table">
                    <thead class="text-primary">
                      <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Tipo Documento</th>
                        <th>Número Documento</th>
                        <th>Teléfono</th>
                        <th>Fecha Nacimiento</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                      </tr>
                    </thead>
                    <tbody>
                      <% for (Ciudadano c : listaCiudadanos) { %>
                        <tr>
                          <td><%= c.getId() %></td>
                          <td><%= c.getNombre() %></td>
                          <td><%= c.getApellido() %></td>
                          <td><%= c.getTipoDocumento() %></td>
                          <td><%= c.getDocumentoCedula() %></td>
                          <td><%= c.getTelefono() %></td>
                          <td><%= c.getFechaNacimiento() %></td>
                          <td><%= c.getEstado() %></td>
                          <td>
                            <a href="editCiudadanoServlet?id=<%= c.getId() %>" class="btn btn-warning btn-sm"><i class="fa-solid fa-pen-to-square"></i> </a>
                            <a href="eliminarCiudadanoServlet?id=<%= c.getId() %>" class="btn btn-danger btn-sm"
                            onclick="return confirm('¿Estás seguro de que deseas eliminar este Ciudadano?');"><i class="fa-solid fa-trash-can"></i> </a>
                          </td>
                        </tr>
                      <% } %>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          <% } else { %>
            <div class="alert alert-info text-center">No hay Ciudadanos registrados.</div>
          <% } %>
        </div>
      </div>
    </div>
  </div>

  <%@ include file="footer.jsp" %>
</body>
</html>