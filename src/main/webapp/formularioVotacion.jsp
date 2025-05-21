<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Candidato" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
    <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
    <title>Formulario de Votación</title>

    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
    <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
    <link href="assets/demo/demo.css" rel="stylesheet" />

    <style>
        .candidate-card {
            text-align: center;
            padding: 15px;
            border: 1px solid #e3e3e3;
            border-radius: 10px;
            margin-bottom: 20px;
            background-color: rgba(255, 255, 255, 0.1); /* semi-transparente */
            transition: 0.3s;
        }
        .candidate-card:hover {
            box-shadow: 0 4px 10px rgba(0,0,0,0.2);
        }
        .candidate-card img {
            width: 100%;
            max-width: 150px;
            height: auto;
            margin-bottom: 10px;
            border-radius: 8px;
        }
        .vote-button-container {
            text-align: center;
            margin-top: 30px;
        }
        h4.title {
            text-align: center;
            color: #fff;
            margin-bottom: 30px;
        }
    </style>
</head>

<body class="">
<div class="main-panel">
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8">

                    <%
                        String cedulaVotante = (String) session.getAttribute("cedulaVotante");
                        List<Candidato> listaCandidatos = (List<Candidato>) request.getAttribute("listaCandidatos");

                        if (cedulaVotante != null && listaCandidatos != null && !listaCandidatos.isEmpty()) {
                    %>
                    
                        <h4 class="title">SISTEMA DE VOTACION</h4>
                        <form action="procesarVotoServlet" method="POST">
                            <input type="hidden" name="cedulaVotante" value="<%= cedulaVotante %>">

                            <div class="row">
                                <% for (Candidato candidato : listaCandidatos) { %>
                                    <div class="col-md-6 col-lg-4">
                                        <div class="candidate-card">
                                            <label>
                                                <% if (candidato.getImagen() != null && !candidato.getImagen().isEmpty()) { %>
                                                    <img src="<%= candidato.getImagen() %>" alt="<%= candidato.getNombre() %> <%= candidato.getApellido() %>">
                                                <% } else { %>
                                                    <img src="assets/img/default-candidate.png" alt="Sin foto">
                                                <% } %>
                                                <h6 style="color:white;"><%= candidato.getNombre() %> <%= candidato.getApellido() %></h6>
                                                <input type="radio" name="voto" value="<%= candidato.getId() %>" required>
                                            </label>
                                        </div>
                                    </div>
                                <% } %>
                            </div>

                            <div class="vote-button-container">
                                <button type="submit" class="btn btn-info btn-fill">Votar</button>
                            </div>
                        </form>
                    <%
                        } else if (cedulaVotante == null) {
                    %>
                        <div class="alert alert-danger text-center">
                            Error: No se encontró la cédula del votante.<br>
                            <a href="votar.jsp" class="alert-link">Volver a ingresar la cédula</a>
                        </div>
                    <%
                        } else {
                    %>
                        <div class="alert alert-warning text-center">
                            No hay candidatos disponibles para votar en este momento.
                        </div>
                    <%
                        }
                    %>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
