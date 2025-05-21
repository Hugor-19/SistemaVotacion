package Servlets;

import Logica.ControladoraLogica;
import Logica.Resultado; // Make sure Resultado class is imported
import Logica.Usuario;
import Logica.Ciudadano;
import Logica.Candidato;
import java.io.IOException;
import java.util.ArrayList; // Import ArrayList
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/svDashboard")
public class svDashboard extends HttpServlet {

    // Instantiate the logic layer controller
    // It's better to initialize this in init() method for better servlet lifecycle management
    // For now, keeping it here as per your original code, but be aware of potential issues in high concurrency
    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // === Section that fetches counts from the database ===

            // Get list of users and count its size
            List<Usuario> listaUsuarios = control.getListaUsuarios();
            int totalUsuarios = listaUsuarios.size();

            // Get list of citizens and count its size
            List<Ciudadano> listaCiudadanos = control.getListaCiudadanos();
            int totalCiudadanos = listaCiudadanos.size();

            // Get list of candidates and count its size
            List<Candidato> listaCandidatos = control.getListaCandidatos();
            int totalCandidatos = listaCandidatos.size();

            // === Fetching results for the chart ===
            List<Resultado> listaResultados = control.getListaResultados();
            int totalVotos = 0; // Initialize totalVotos

            // Lists to hold data for the chart
            List<String> candidateNames = new ArrayList<>();
            List<Integer> candidateVotes = new ArrayList<>();

            if (listaResultados != null && !listaResultados.isEmpty()) {
                for (Resultado resultado : listaResultados) {
                    candidateNames.add(resultado.getNombreCompletoCandidatoSnap());
                    candidateVotes.add(resultado.getVotos());
                    totalVotos += resultado.getVotos(); // Sum up all votes for overall total
                }
            } else {
                // If no results, still provide empty lists to avoid null pointers in JSP
                // And totalVotos remains 0
            }

            // === Section that passes counts and chart data to the JSP ===

            // Store the counts in request attributes so the JSP can access them
            request.setAttribute("totalUsuarios", totalUsuarios);
            request.setAttribute("totalCiudadanos", totalCiudadanos);
            request.setAttribute("totalCandidatos", totalCandidatos);
            request.setAttribute("totalVotos", totalVotos); // Total votes now sums all candidate votes

            // Store chart data in request attributes
            request.setAttribute("candidateNames", candidateNames);
            request.setAttribute("candidateVotes", candidateVotes);

            // Forward the request and response to the dashboard JSP page
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            // Log the error
            Logger.getLogger(svDashboard.class.getName()).log(Level.SEVERE, "Error in svDashboard doGet", e);
            System.err.println("Error en svDashboard: " + e.getMessage()); // Use err for errors
            e.printStackTrace();

            // In case of error, set default values (0) and empty lists before forwarding
            request.setAttribute("totalUsuarios", 0);
            request.setAttribute("totalCiudadanos", 0);
            request.setAttribute("totalCandidatos", 0);
            request.setAttribute("totalVotos", 0);
            request.setAttribute("candidateNames", new ArrayList<String>()); // Empty list
            request.setAttribute("candidateVotes", new ArrayList<Integer>()); // Empty list

            // Still forward to the dashboard page, perhaps with an error message shown in the JSP
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // For this specific dashboard view, POST requests are handled the same as GET
        doGet(request, response);
    }
}