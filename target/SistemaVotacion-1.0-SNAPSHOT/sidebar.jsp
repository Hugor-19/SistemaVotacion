<style>
.logo {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    padding: 10px 0;
}

.logo-image {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.logo-mini,
.logo-normal {
    margin: 0;
    padding: 0;
    font-weight: bold;
    color: #2c3e50;
    font-size: 16px;
    text-transform: uppercase;
    letter-spacing: 1px;
    display: inline-block;
}
</style>
<div class="wrapper">
    <div class="sidebar">      
      <div class="sidebar-wrapper">
        <div class="logo">
        <img src="assets/img/favicon.jpg" alt="Logo" class="logo-image">
          <a href="javascript:void(0)" class="simple-text logo-mini">
          Net
          </a>
          <a href="javascript:void(0)" class="simple-text logo-normal">
           Votly 
          </a>
        </div>
        <ul class="nav">
          <li class="active ">
            <a href="svDashboard">
              <i class="fa-solid fa-chart-line"></i>
              <p>Dashboard</p>
            </a>
          </li>
          <li>
            <a href="svVotos">
              <i class="fa-solid fa-check-to-slot"></i>
              <p>votos</p>
            </a>
          </li>
          <li>
            <a href="svResultados">
              <i class="fa-solid fa-square-poll-vertical"></i>
              <p>Resultados</p>
            </a>
          </li>
          <li>
            <a data-toggle="collaspse" href="#"  class="submenu-toggle" >
              <i class="fa-solid fa-chart-line"></i>
              <p>Administracion</p>
            </a>
            <ul class="submenu">
            <li><a href="svUsuarios">Gestionar Usuarios</a></li>
            <li><a href="svCiudadanos">Gestionar ciudadanos</a></li>
            <li><a href="svCandidatos">Gestionar Candidatos</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
    