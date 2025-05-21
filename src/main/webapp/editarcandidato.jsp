<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Logica.Candidato" %>
<!DOCTYPE html>
<html lang="en">
<%
    Candidato candidato = (Candidato) session.getAttribute("candidato");
    if (candidato == null) {
        response.sendRedirect("svCandidatos");
        return;
    }
%>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
    <title>Editar Candidato</title>

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
                        <h4 class="card-title">Editar Candidato</h4>
                        <p class="card-category">Modificar los datos del Candidato seleccionado</p>
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

                        <form action="editCandidatoServlet" method="POST" enctype="multipart/form-data">
                            <input type="hidden" name="id" value="<%= candidato.getId() %>">

                            <div class="row">
                                <div class="col-md-6 pr-md-1">
                                    <div class="form-group">
                                        <label for="nombre">Nombres</label>
                                        <input type="text" class="form-control" name="nombre" value="<%= candidato.getNombre() %>" required>
                                    </div>
                                </div>

                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="apellido">Apellidos</label>
                                        <input type="text" class="form-control" name="apellido" value="<%= candidato.getApellido() %>" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 pr-md-1">
                                    <div class="form-group">
                                        <label for="cedula">Cédula</label>
                                        <input type="text" class="form-control" name="cedula" value="<%= candidato.getCedula() %>" readonly>
                                    </div>
                                </div>

                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="partido_politico">Partido Político</label>
                                        <input type="text" class="form-control" name="partido_politico" value="<%= candidato.getPartidoPolitico() %>" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 pr-md-1">
                                    <div class="form-group">
                                        <label for="imagen">Foto Candidato</label>
                                        <div class="custom-file">
                                            <input type="file" class="custom-file-input" id="foto" name="foto" accept="image/*">
                                            <label class="custom-file-label" for="foto">
                                                <% if (candidato.getImagen() != null && !candidato.getImagen().isEmpty()) { %>
                                                    <%= candidato.getImagen().substring(candidato.getImagen().lastIndexOf('/') + 1) %>
                                                <% } else { %>
                                                    Seleccionar archivo
                                                <% } %>
                                            </label>
                                        </div>
                                        <small class="form-text text-muted">Formatos permitidos: JPG, PNG, GIF. Dejar en blanco para no cambiar.</small>
                                    </div>
                                </div>

                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="propuesta">Propuesta</label>
                                        <textarea class="form-control" name="propuesta" rows="3" required><%= candidato.getPropuesta() %></textarea>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label for="estado">Estado</label>
                                        <select name="estado" class="form-control" required>
                                            <option value="activo" <%= "activo".equals(candidato.getEstado()) ? "selected" : "" %>>Activo</option>
                                            <option value="inactivo" <%= "inactivo".equals(candidato.getEstado()) ? "selected" : "" %>>Inactivo</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="card-footer">
                                <button type="submit" class="btn btn-fill btn-primary">Guardar Cambios</button>
                                <a href="svCandidatos" class="btn btn-fill btn-danger">Cancelar</a>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

<script>
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