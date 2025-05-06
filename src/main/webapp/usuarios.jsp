<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Usuario" %>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
  <link rel="icon" type="image/png" href="assets/favicon.png">
  <title>Usuarios</title>
  <!--     Fonts and icons     -->
  <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  <!-- Nucleo Icons -->
  <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
  <!-- icons font -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <!-- CSS Files -->
  <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
  <!-- CSS Just for demo purpose, don't include it in your project -->
  <link href="assets/demo/demo.css" rel="stylesheet" />
</head>

<body class="">
  <%@ include file="sidebar.jsp"%>
  <%@ include file="navbar.jsp"%>
  <%@ include file="script.jsp"%>
  <%@ include file="footer.jsp"%>

  <div class="main-panel">
    <!-- End Navbar -->
    <div class="content container mt-4">
    <div class="row">
        <div class="col-md-12">
            <div class="card card-upgrade">
                <div class="card-header text-center">
                    <h4 class="card-title">Lista de Usuarios</h4>
                    <p class="card-category">Usuarios registrados en el sistema</p>
                </div>

                <table class="table">
                    <thead>
                        <tr>
                            <th class="text-center">ID</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Password</th>
                            <th class="text-right">Rol</th>
                            <th class="text-right">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Usuario> lista = (List<Usuario>) request.getAttribute("listaUsuarios");
                            if (lista != null && !lista.isEmpty()) {
                                for (Usuario usuario : lista) {
                        %>
                        <tr>
                            <td class="text-center"><%=usuario.getId_usuario()%></td>
                            <td><%= usuario.getNombre() %></td>
                            <td><%= usuario.getEmail() %></td>
                            <td>••••••</td>
                            <td class="text-right"><%= usuario.getRol() %></td>
                            <td class="text-right">
                                <a href="editUsuarioServlet?id=<%= usuario.getId_usuario() %>" class="btn btn-warning btn-sm"><i class="fa-solid fa-pen-to-square"></i></a>
                                <a href="eliminar?id=<%= usuario.getId_usuario() %>" class="btn btn-danger btn-sm"
                                   onclick="return confirm('¿Seguro que deseas eliminar este usuario?');"><i class="fa-solid fa-trash-can"></i></a>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>
                </table>

                <div class="card-footer text-center">
                    <a href="gestionarUsuarios.jsp" class="btn btn-primary">Agregar Nuevo Usuario</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>