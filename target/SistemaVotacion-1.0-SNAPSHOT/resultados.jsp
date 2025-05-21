<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Resultado" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
    <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
    <title>Resultados de Elecciones</title>

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
        /* Style for candidate images */
        .candidate-img {
            max-width: 60px; /* Set a maximum width for the image */
            max-height: 60px; /* Set a maximum height for the image */
            border-radius: 50%; /* Make images circular, optional */
            object-fit: cover; /* Ensure images cover the area without distortion */
            border: 1px solid #ddd; /* Subtle border for images */
            padding: 2px;
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
                        List<Resultado> listaResultados = (List<Resultado>) request.getAttribute("listaResultados");
                    %>

                    <% if (listaResultados != null && !listaResultados.isEmpty()) { %>
                        <div class="card">
                            <div class="card-header text-center">
                                <h4 class="card-title">Resultado de Elecciones</h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead class="text-primary">
                                            <tr>
                                                <th>ID</th>
                                                <th>Cédula</th>
                                                <th>Nombre del Candidato</th>
                                                <th>Foto</th>
                                                <th>Total de Votos</th> <%-- Changed from 'Total' for clarity --%>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (Resultado r : listaResultados) { %>
                                                <tr class="text-center">
                                                    <td><%= r.getId() %></td>
                                                    <td><%= r.getCedulaCandidatoSnap() %></td>
                                                    <td><%= r.getNombreCompletoCandidatoSnap() %></td>
                                                    <td>
                                                        <%-- Display the image using an <img> tag --%>
                                                        <img src="<%= r.getImagenCandidatoSnap() %>" alt="Foto de <%= r.getNombreCompletoCandidatoSnap() %>" class="candidate-img">
                                                    </td>
                                                    <td><%= r.getVotos() %></td>
                                                </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    <% } else { %>
                        <div class="alert alert-info text-center w-100">No hay resultados de elecciones registrados.</div>
                    <% } %>
                </div>
            </div>

        </div>
    </div>

</body>
</html>