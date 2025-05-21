<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Candidato" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
    <title>Gestionar Candidatos</title>

    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

    <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
    <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
    <link href="assets/demo/demo.css" rel="stylesheet" />

    <script>
        function toggleFormulario() {
            const form = document.getElementById("formularioCandidato");
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

            <%
                String successMessage = (String) session.getAttribute("successMessage");
                String errorMessage = (String) session.getAttribute("errorMessage");
            %>
            <% if (successMessage != null) { %>
                <div class="alert alert-success text-center"><%= successMessage %></div>
                <% session.removeAttribute("successMessage"); %>
            <% } %>
            <% if (errorMessage != null) { %>
                <div class="alert alert-danger text-center"><%= errorMessage %></div>
                <% session.removeAttribute("errorMessage"); %>
            <% } %>

            <div class="text-center mb-4">
                <button onclick="toggleFormulario()" class="btn btn-primary">
                    <i class="fa-solid fa-user-plus"></i> Agregar Candidatos
                </button>
            </div>

            <div class="row" id="formularioCandidato" style="display: none;">
                <div class="col-md-8 ml-auto mr-auto">
                    <div class="card card-upgrade">
                        <div class="card-header text-center">
                            <h4 class="card-title">Alta de Candidatos</h4>
                            <p class="card-category">Registrar nuevos candidatos en el sistema</p>
                        </div>
                        <form action="addCandidatoServlet" method="POST" enctype="multipart/form-data">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6 pr-md-1">
                                        <div class="form-group">
                                            <label for="nombre">Nombres</label>
                                            <input type="text" class="form-control" name="nombre" placeholder="Nombres" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6 pl-md-1">
                                        <div class="form-group">
                                            <label for="apellido">Apellidos</label>
                                            <input type="text" class="form-control" name="apellido" placeholder="Apellidos" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 pr-md-1">
                                        <div class="form-group">
                                            <label for="cedula">Cédula</label>
                                            <input type="text" class="form-control" name="cedula" placeholder="Cédula" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6 pl-md-1">
                                        <div class="form-group">
                                            <label for="partido_politico">Partido Político</label>
                                            <input type="text" class="form-control" name="partido_politico" placeholder="Partido Político" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 pr-md-1">
                                        <div class="form-group">
                                            <label for="imagen">Foto Candidato</label>
                                            <div class="custom-file">
                                                <input type="file" class="custom-file-input" id="foto" name="foto" accept="image/*" required>
                                                <label class="custom-file-label" for="foto">Seleccionar archivo</label>
                                            </div>
                                            <small class="form-text text-muted">Formatos permitidos: JPG, PNG, GIF.</small>
                                        </div>
                                    </div>
                                    <div class="col-md-6 pl-md-1">
                                        <div class="form-group">
                                            <label for="propuesta">Propuesta</label>
                                            <textarea class="form-control" name="propuesta" rows="3" placeholder="Propuesta" required></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 pr-md-1">
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
                            <div class="card-footer text-center">
                                <button type="submit" class="btn btn-fill btn-primary">Registrar Candidato</button>
                                <a href="svCandidatos" class="btn btn-fill btn-danger">Cancelar</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-10 ml-auto mr-auto">
                    <%
                        List<Candidato> listaCandidatos = (List<Candidato>) request.getSession().getAttribute("listaCandidatos");
                        if (listaCandidatos != null && !listaCandidatos.isEmpty()) {
                    %>
                        <div class="card">
                            <div class="card-header text-center">
                                <h4 class="card-title">Candidatos Registrados</h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead class="text-primary">
                                            <tr>
                                                <th>ID</th>
                                                <th>Nombre</th>
                                                <th>Apellido</th>
                                                <th>Cedula</th>
                                                <th>Partido Político</th>
                                                <th>Foto</th>
                                                <th>Propuesta</th>
                                                <th>Estado</th>
                                                <th>Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (Candidato c : listaCandidatos) { %>
                                                <tr>
                                                    <td><%= c.getId() %></td>
                                                    <td><%= c.getNombre() %></td>
                                                    <td><%= c.getApellido() %></td>
                                                    <td><%= c.getCedula() %></td>
                                                    <td><%= c.getPartidoPolitico() %></td>
                                                    <td>
                                                        <% if (c.getImagen() != null && !c.getImagen().isEmpty()) { %>
                                                            <img src="<%= c.getImagen() %>" alt="Foto del candidato" width="50" height="50"/>
                                                        <% } else { %>
                                                            Sin foto
                                                        <% } %>
                                                    </td>
                                                    <td><%= c.getPropuesta() %></td>
                                                    <td><%= c.getEstado() %></td>
                                                    <td>
                                                        <a href="editCandidatoServlet?id=<%= c.getId() %>" class="btn btn-warning btn-sm"><i class="fa-solid fa-pen-to-square"></i></a>
                                                        <a href="eliminarCandidatoServelet?id=<%= c.getId() %>" class="btn btn-danger btn-sm"
                                                           onclick="return confirm('¿Estás seguro de que deseas eliminar este Candidato?');"><i class="fa-solid fa-trash-can"></i></a>
                                                    </td>
                                                </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    <% } else { %>
                        <div class="alert alert-info text-center">No hay candidatos registrados.</div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>

    <script>
        // Mostrar el nombre del archivo en el input de imagen
        document.querySelector('.custom-file-input').addEventListener('change', function(e) {
            var fileName = e.target.files[0]?.name || "Seleccionar archivo";
            e.target.nextElementSibling.innerText = fileName;
        });

        document.getElementById('foto').addEventListener('change', function() {
            const maxFileSize = 10 * 1024 * 1024; // Ejemplo: 10MB
            if (this.files.length > 0 && this.files[0].size > maxFileSize) {
                alert('La imagen es demasiado grande. El tamaño máximo permitido es de ' + (maxFileSize / (1024 * 1024)) + 'MB.');
                this.value = ''; // Limpiar el input file
            }
        });
    </script>
</body>
</html>