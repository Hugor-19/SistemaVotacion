<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Logica.Usuario" %>
<%@ page import="Logica.Ciudadano" %>
<%@ page import="Logica.Candidato" %>
<%@ page import="Logica.Resultado" %>
<%@ page import="Logica.Voto" %>
<!DOCTYPE html>
<html lang="es">
<%
    // Safely retrieve attributes, initializing to 0 or empty lists if null
    Integer totalUsuarios = (Integer) request.getAttribute("totalUsuarios");
    if (totalUsuarios == null) totalUsuarios = 0;

    Integer totalCiudadanos = (Integer) request.getAttribute("totalCiudadanos");
    if (totalCiudadanos == null) totalCiudadanos = 0;

    Integer totalCandidatos = (Integer) request.getAttribute("totalCandidatos");
    if (totalCandidatos == null) totalCandidatos = 0;

    Integer totalVotos = (Integer) request.getAttribute("totalVotos");
    if (totalVotos == null) totalVotos = 0;

    // Get data for the chart from the servlet
    // These lists will now contain all candidates, including those with 0 votes
    List<String> candidateNames = (List<String>) request.getAttribute("candidateNames");
    if (candidateNames == null) candidateNames = new java.util.ArrayList<>();

    List<Integer> candidateVotes = (List<Integer>) request.getAttribute("candidateVotes");
    if (candidateVotes == null) candidateVotes = new java.util.ArrayList<>();
%>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">
    <link rel="icon" type="image/png" href="assets/img/favicon.jpg">
    <title>Dashboard</title>

    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,600,700,800" rel="stylesheet" />
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
    <link href="assets/css/black-dashboard.css?v=1.0.0" rel="stylesheet" />
    <link href="assets/demo/demo.css" rel="stylesheet" />

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        /* Existing styles for cards and dashboard layout */
        .card-title {
            color: #fff;
            font-weight: 600;
            font-size: 1.2rem;
            margin-bottom: 10px;
        }
        .card-category {
            color: rgba(255, 255, 255, 0.7);
        }
        .text-muted {
            color: rgba(255, 255, 255, 0.5) !important;
        }
        .font-weight-bold {
            font-weight: 700 !important;
        }
        .fa-2x {
            font-size: 2.5em; /* Slightly larger icons */
            margin-top: 15px;
        }
        /* Adjusted height for charts */
        .chart-container {
            position: relative;
            height: 400px; /* Consistent height for both charts */
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 15px; /* Add some padding inside the chart card */
        }
        /* Specific colors for icons to match black-dashboard theme */
        .text-primary { color: #41a1ff !important; }
        .text-success { color: #00f2c3 !important; }
        .text-info { color:rgb(206, 142, 45) !important; }
        .text-warning { color: #ff8d72 !important; }

        /* Styles for the "No data" alert */
        .alert-info {
            background-color: #2d89ef;
            color: #fff;
            border-color: #2d89ef;
            text-align: center;
            font-weight: bold;
            margin-top: 20px;
        }
    </style>
</head>

<body>
    <%@ include file="sidebar.jsp" %>
    <%@ include file="navbar.jsp" %>

    <div class="main-panel">
        <div class="content">

            <div class="row">
                <div class="col-lg-3 col-md-6 col-sm-6">
                    <div class="card text-center shadow rounded-3 border-0">
                        <div class="card-body">
                            <h5 class="card-title text-muted">USUARIOS</h5>
                            <h3 class="font-weight-bold"><%= totalUsuarios %></h3>
                            <i class="fas fa-users-cog fa-2x text-primary"></i>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6 col-sm-6">
                    <div class="card text-center shadow rounded-3 border-0">
                        <div class="card-body">
                            <h5 class="card-title text-muted">CIUDADANOS</h5>
                            <h3 class="font-weight-bold"><%= totalCiudadanos %></h3>
                            <i class="fas fa-user-tie fa-2x text-success"></i>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6 col-sm-6">
                    <div class="card text-center shadow rounded-3 border-0">
                        <div class="card-body">
                            <h5 class="card-title text-muted">CANDIDATOS</h5>
                            <h3 class="font-weight-bold"><%= totalCandidatos %></h3>
                            <i class="fas fa-user-friends fa-2x text-info"></i>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6 col-sm-6">
                    <div class="card text-center shadow rounded-3 border-0">
                        <div class="card-body">
                            <h5 class="card-title text-muted">VOTOS</h5>
                            <h3 class="font-weight-bold"><%= totalVotos %></h3>
                            <i class="fas fa-check-double fa-2x text-warning"></i>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6"> <div class="card card-upgrade">
                        <div class="card-header text-center">
                            <h4 class="card-title">Distribución de Votos</h4>
                            <p class="card-category">Porcentaje de votos por candidato</p>
                        </div>
                        <div class="card-body">
                            <div class="card card-chart">
                                <div class="card-body">
                                    <div class="chart-container">
                                        <% if (candidateNames.isEmpty()) { %>
                                            <div class="alert alert-info" role="alert">
                                                No hay datos para mostrar la gráfica de porcentaje.
                                            </div>
                                        <% } else { %>
                                            <canvas id="doughnutChart"></canvas>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6"> <div class="card card-upgrade">
                        <div class="card-header text-center">
                            <h4 class="card-title">Conteo de Votos</h4>
                            <p class="card-category">Votos absolutos por candidato</p>
                        </div>
                        <div class="card-body">
                            <div class="card card-chart">
                                <div class="card-body">
                                    <div class="chart-container">
                                        <% if (candidateNames.isEmpty()) { %>
                                            <div class="alert alert-info" role="alert">
                                                No hay datos para mostrar la gráfica de barras.
                                            </div>
                                        <% } else { %>
                                            <canvas id="barChart"></canvas>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <%@ include file="script.jsp" %>
    <%@ include file="footer.jsp" %>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Retrieve data passed from the servlet and manually format for JavaScript
            const candidateNames = [
                <%
                    for (int i = 0; i < candidateNames.size(); i++) {
                        out.print("'" + candidateNames.get(i).replace("'", "\\'") + "'");
                        if (i < candidateNames.size() - 1) {
                            out.print(",");
                        }
                    }
                %>
            ];

            const candidateVotes = [
                <%
                    for (int i = 0; i < candidateVotes.size(); i++) {
                        out.print(candidateVotes.get(i));
                        if (i < candidateVotes.size() - 1) {
                            out.print(",");
                        }
                    }
                %>
            ];

            // Define a set of consistent and visually appealing colors for the charts
            const chartPalette = [
                'rgba(65, 161, 255, 0.9)',  // Blue
                'rgba(0, 242, 195, 0.9)',   // Green
                'rgba(255, 141, 114, 0.9)', // Orange
                'rgba(173, 52, 235, 0.9)',  // Purple
                'rgba(245, 54, 92, 0.9)',   // Red
                'rgba(255, 204, 0, 0.9)',   // Gold
                'rgba(108, 117, 125, 0.9)', // Gray
                'rgba(253, 126, 20, 0.9)',  // Darker Orange
                'rgba(52, 58, 64, 0.9)'     // Dark Gray
            ];
            // Use a solid version of the colors for borders and hover effects
            const solidChartPalette = chartPalette.map(color => color.replace('0.9)', '1)'));


            // Calculate total votes for percentage calculation in Doughnut chart
            const totalVotes = candidateVotes.reduce((sum, current) => sum + current, 0);

            // Check if there's data to display before attempting to create the charts
            if (candidateNames.length > 0) {
                /* --- Doughnut Chart --- */
                const doughnutCtx = document.getElementById('doughnutChart').getContext('2d');
                new Chart(doughnutCtx, {
                    type: 'doughnut',
                    data: {
                        labels: candidateNames,
                        datasets: [{
                            label: 'Votos',
                            data: candidateVotes,
                            backgroundColor: chartPalette.slice(0, candidateNames.length),
                            borderColor: 'rgba(30, 30, 47, 1)', // Dark border to separate segments
                            borderWidth: 2,
                            hoverOffset: 10
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        cutout: '65%',
                        plugins: {
                            legend: {
                                display: true,
                                position: 'right',
                                labels: {
                                    color: 'rgba(255, 255, 255, 0.8)',
                                    font: {
                                        size: 14,
                                        family: "'Poppins', sans-serif"
                                    },
                                    boxWidth: 20,
                                    padding: 15
                                }
                            },
                            tooltip: {
                                enabled: true,
                                backgroundColor: 'rgba(30, 30, 47, 0.9)',
                                titleColor: '#fff',
                                bodyColor: '#fff',
                                borderColor: 'rgba(255, 255, 255, 0.2)',
                                borderWidth: 1,
                                borderRadius: 6,
                                padding: 10,
                                displayColors: true,
                                callbacks: {
                                    label: function(context) {
                                        const label = context.label || '';
                                        const value = context.raw;
                                        let percentage = 0;
                                        if (totalVotes > 0) {
                                            percentage = ((value / totalVotes) * 100).toFixed(2);
                                        }
                                        return `${label}: ${value} votos (${percentage}%)`;
                                    }
                                }
                            }
                        },
                        animation: {
                            animateRotate: true,
                            animateScale: true,
                            duration: 1500,
                            easing: 'easeOutBounce'
                        }
                    }
                });

                /* --- Horizontal Bar Chart --- */
                const barCtx = document.getElementById('barChart').getContext('2d');
                new Chart(barCtx, {
                    type: 'bar', // Type is 'bar' for both vertical and horizontal in Chart.js v3+
                    data: {
                        labels: candidateNames,
                        datasets: [{
                            label: 'Votos',
                            data: candidateVotes,
                            backgroundColor: chartPalette.slice(0, candidateNames.length),
                            borderColor: solidChartPalette.slice(0, candidateNames.length),
                            borderWidth: 1.5,
                            borderRadius: 4,
                            hoverBackgroundColor: solidChartPalette.slice(0, candidateNames.length),
                            barPercentage: 0.8,
                            categoryPercentage: 0.7
                        }]
                    },
                    options: {
                        indexAxis: 'y', // KEY for horizontal bars in Chart.js v3+
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                display: false,
                            },
                            tooltip: {
                                enabled: true,
                                backgroundColor: 'rgba(30, 30, 47, 0.9)',
                                titleColor: '#fff',
                                bodyColor: '#fff',
                                borderColor: 'rgba(255, 255, 255, 0.2)',
                                borderWidth: 1,
                                borderRadius: 6,
                                padding: 10,
                                displayColors: false,
                                callbacks: {
                                    label: function(context) {
                                        let label = context.dataset.label || '';
                                        if (label) {
                                            label += ': ';
                                        }
                                        if (context.parsed.x !== null) {
                                            label += context.parsed.x + ' votos';
                                        }
                                        return label;
                                    }
                                }
                            }
                        },
                        scales: {
                            x: {
                                beginAtZero: true,
                                ticks: {
                                    color: 'rgba(255, 255, 255, 0.7)',
                                    font: {
                                        size: 14,
                                        family: "'Poppins', sans-serif"
                                    },
                                    callback: function(value) {
                                        if (Number.isInteger(value)) {
                                            return value;
                                        }
                                        return null;
                                    }
                                },
                                grid: {
                                    color: 'rgba(255, 255, 255, 0.1)',
                                    drawBorder: false,
                                    lineWidth: 0.5
                                }
                            },
                            y: {
                                ticks: {
                                    color: 'rgba(255, 255, 255, 0.8)',
                                    font: {
                                        size: 14,
                                        family: "'Poppins', sans-serif"
                                    },
                                    autoSkip: false,
                                    maxRotation: 0,
                                    minRotation: 0
                                },
                                grid: {
                                    display: false,
                                    drawBorder: false
                                }
                            }
                        },
                        animation: {
                            duration: 1000,
                            easing: 'easeOutQuart'
                        }
                    }
                });
            } else {
                console.log("No hay candidatos para renderizar las gráficas.");
            }
        });
    </script>
</body>
</html>