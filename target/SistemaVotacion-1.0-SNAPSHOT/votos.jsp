<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Voto" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
    <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
    <title>Resultados de Votos</title>

    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
    <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
    <link href="assets/demo/demo.css" rel="stylesheet" />

    <style>
        .card-body .table th,
        .card-body .table td {
            padding: 1rem; /* Increase padding for better spacing */
            vertical-align: middle; /* Align text vertically in the middle */
            text-align: center; /* Ensure text is centered for consistency */
        }
        .table-responsive {
            margin-top: 20px; /* Add some space above the table */
        }
        .card-title {
            margin-bottom: 20px; /* Space below the title */
        }
        /* Style for the table header, mimicking the 'text-primary' from usuarios.jsp if desired */
        .table thead.text-primary th {
            color: #41a1ff; /* Primary color from black-dashboard theme */
        }
    </style>
</head>

<body class="">

    <%@ include file="sidebar.jsp" %>
    <%@ include file="navbar.jsp" %>
    <%@ include file="script.jsp" %>
    <%@ include file="footer.jsp" %>

    <div class="main-panel">
        <div class="content">
            <div class="row">
                <div class="col-md-10 ml-auto mr-auto">
                    <%
                        List<Voto> listaVotos = (List<Voto>) request.getAttribute("listaVotos");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    %>

                    <% if (listaVotos != null && !listaVotos.isEmpty()) { %>
                        <div class="card">
                            <div class="card-header text-center">
                                <h4 class="card-title">Registro de Votos</h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <%-- Changed table classes to match 'usuarios.jsp' --%>
                                    <table class="table">
                                        <thead class="text-primary"> <%-- Changed from thead-dark to text-primary --%>
                                            <tr>
                                                <th>ID Voto</th>
                                                <th>Nombre del Ciudadano</th>
                                                <th>Nombre del Candidato</th>
                                                <th>Fecha del Voto</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (Voto v : listaVotos) { %>
                                                <tr class="text-center">
                                                    <td><%= v.getId() %></td>
                                                    <td><%= v.getNombreCompletoCiudadano() %></td>
                                                    <td><%= v.getNombreCompletoCandidato() %></td>
                                                    <td><%= v.getFechaVotacion() != null ? sdf.format(v.getFechaVotacion()) : "" %></td>
                                                </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    <% } else { %>
                        <div class="alert alert-info text-center w-100">No hay votos registrados.</div>
                    <% } %>
                </div>
            </div>

        </div>
    </div>

</body>
</html>