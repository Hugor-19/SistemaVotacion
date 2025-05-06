<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
    <link rel="icon" type="image/png" href="assets/favicon.jpg">
    <title>Gestionar Votos</title>
    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
    <link href="assets/demo/demo.css" rel="stylesheet" />
</head>

<body class="">
    <%@ include file="sidebar.jsp"%>
    <%@ include file="navbar.jsp"%>
    <%@ include file="script.jsp"%>
    <%@ include file="footer.jsp"%>

    <div class="main-panel">
        <div class="content">
            <div class="row">
                <div class="col-md-8 ml-auto mr-auto">
                    <div class="card card-upgrade">
                        <div class="card-header text-center">
                            <h4 class="card-title">Registro de Votos</h4>
                            <p class="card-category">Sección para registrar los votos de los ciudadanos</p>
                        </div>
                        <form action="registrarVotoServlet" method="POST">
                            <div class="card-body">
                                <%-- Mostrar mensaje de éxito --%>
                                <% String successMessageVoto = (String) request.getAttribute("successMessage"); %>
                                <% if (successMessageVoto != null) { %>
                                    <div class="alert alert-success">
                                        <%= successMessageVoto %>
                                    </div>
                                <% } %>

                                <%-- Mostrar mensaje de error --%>
                                <% String errorMessageVoto = (String) request.getAttribute("errorMessage"); %>
                                <% if (errorMessageVoto != null) { %>
                                    <div class="alert alert-danger">
                                        <%= errorMessageVoto %>
                                    </div>
                                <% } %>

                                <div class="row">
                                    <div class="col-md-6 pr-md-1">
                                        <div class="form-group">
                                            <label for="id_votante">ID Votante</label>
                                            <input type="number" class="form-control" id="id_votante" name="id_votante" placeholder="ID Votante" required>
                                        </div>
                                    </div>
                                    <div class="col-md-6 pl-md-1">
                                        <div class="form-group">
                                            <label for="id_candidato">ID Candidato</label>
                                            <input type="number" class="form-control" id="id_candidato" name="id_candidato" placeholder="ID Candidato" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="id_eleccion">ID Eleccion</label>
                                            <input type="number" class="form-control" id="id_eleccion" name="id_eleccion" placeholder="ID Eleccion" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <button type="submit" class="btn btn-fill btn-primary">Registrar Voto</button>
                                <a href="svVotos" class="btn btn-fill btn-danger">Cancelar</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
