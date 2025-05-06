<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
  <link rel="icon" type="image/png" href="assets/favicon.jpg">
  <title>Dashboard</title>
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
     <div class="content">
  <div class="row">
    <div class="col-12">
      <div class="card card-chart shadow rounded-3 border-0 overflow-hidden">
      <style>
      .card-chart {
       transition: box-shadow 0.3s ease;
      border-radius: 12px;
      }

      .card-chart:hover {
      box-shadow: 0 10px 20px rgba(0,0,0,0.15);
      }

      .carousel-inner img {
      object-fit: cover;
      height: 500px;
      }

      </style> 

        <!-- Carrusel -->
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
          <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
          </ol>

          <div class="carousel-inner">
            <div class="carousel-item active">
              <img class="d-block w-100 rounded-top" src="assets/img/carousel1.jpg" alt="Primera imagen">
            </div>
            <div class="carousel-item">
              <img class="d-block w-100 rounded-top" src="assets/img/carousel2.jpg" alt="Segunda imagen">
            </div>
            <div class="carousel-item">
              <img class="d-block w-100 rounded-top" src="assets/img/carousel3.jpg" alt="Tercera imagen">
            </div>
          </div>

          <!-- Controles -->
          <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Anterior</span>
          </a>
          <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Siguiente</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>