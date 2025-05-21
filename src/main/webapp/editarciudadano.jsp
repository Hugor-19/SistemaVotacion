<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Logica.Ciudadano" %>
<!DOCTYPE html>
<html lang="en">
<%
Ciudadano ciudadano = (Ciudadano) session.getAttribute("ciudadano");
if (ciudadano == null) {
    response.sendRedirect("svCiudadanos");
    return;
}

// ✅ Formatear fecha correctamente para el input date
String fechaFormateada = "";
if (ciudadano.getFechaNacimiento() != null) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    fechaFormateada = sdf.format(ciudadano.getFechaNacimiento());
}
%>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
    <title>Editar Ciudadano</title>

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
                        <h4 class="card-title">Editar Ciudadano</h4>
                        <p class="card-category">Modificar los datos del Ciudadano seleccionado</p>
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

                        <form action="editCiudadanoServlet" method="POST">
                            <input type="hidden" name="id" value="<%= ciudadano.getId() %>">

                            <div class="row">
                                <div class="col-md-6 pr-md-1">
                                    <div class="form-group">
                                        <label for="nombre">Nombre Ciudadano</label>
                                        <input type="text" class="form-control" name="nombre" value="<%= ciudadano.getNombre() %>" required>
                                    </div>
                                </div>

                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="apellido">Apellido Ciudadano</label>
                                        <input type="text" class="form-control" name="apellido" value="<%= ciudadano.getApellido() %>" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="tipo_documento">Tipo de Documento</label>
                                        <select name="tipo_documento" class="form-control" required>
                                            <option value="CC" <%= "CC".equals(ciudadano.getTipoDocumento()) ? "selected" : "" %>>Cédula de Ciudadanía</option>
                                            <option value="CE" <%= "CE".equals(ciudadano.getTipoDocumento()) ? "selected" : "" %>>Cédula de Extranjería</option>
                                            <option value="Pasaporte" <%= "Pasaporte".equals(ciudadano.getTipoDocumento()) ? "selected" : "" %>>Pasaporte</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-md-6 pr-md-1">
                                    <div class="form-group">
                                        <label for="numero_documento">Cédula</label>
                                        <input type="text" class="form-control" name="numero_documento" value="<%= ciudadano.getDocumentoCedula() %>" readonly>
                                    </div>
                                </div>
                            </div>

                            <!-- ✅ Se corrigió la estructura mal anidada -->
                            <div class="row">
                                <div class="col-md-6 pr-md-1">
                                    <div class="form-group">
                                        <label for="telefono">Teléfono</label>
                                        <input type="text" class="form-control" name="telefono" value="<%= ciudadano.getTelefono() %>" required>
                                    </div>
                                </div>

                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="fecha_nacimiento">Fecha de Nacimiento</label>
                                        <input type="date" class="form-control" name="fecha_nacimiento" value="<%= fechaFormateada %>" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 pl-md-1">
                                    <div class="form-group">
                                        <label for="estado">Estado</label>
                                        <select name="estado" class="form-control" required>
                                            <option value="activo" <%= "activo".equals(ciudadano.getEstado()) ? "selected" : "" %>>Activo</option>
                                            <option value="inactivo" <%= "inactivo".equals(ciudadano.getEstado()) ? "selected" : "" %>>Inactivo</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="card-footer">
                                <button type="submit" class="btn btn-fill btn-primary">Guardar Cambios</button>
                                <a href="svCiudadanos" class="btn btn-fill btn-danger">Cancelar</a>
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
