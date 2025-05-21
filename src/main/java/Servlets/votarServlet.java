package Servlets;

import Logica.Ciudadano;
import Logica.ControladoraLogica;
import Logica.Candidato;
import Persistencia.EntityManagerFactorySingleton; // Import the singleton
import Persistencia.VotoJpaController; // Import the VotoJpaController

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/votarServlet")
public class votarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        ControladoraLogica controladora = new ControladoraLogica();
        HttpSession session = request.getSession();

        if ("login".equals(action)) {
            String documentoCedula = request.getParameter("documento_identidad");

            Ciudadano ciudadano = controladora.buscarCiudadanoPorDocumento(documentoCedula);

            if (ciudadano != null) {
                // Instantiate VotoJpaController using the singleton EMF
                VotoJpaController votoJpa = new VotoJpaController(EntityManagerFactorySingleton.getEntityManagerFactory());

                // **** NEW CHECK: Has the citizen already voted? ****
                if (votoJpa.hasCiudadanoVoted(ciudadano.getId())) {
                    // MODIFIED: Removed ' + documentoCedula + ' from the message
                    request.setAttribute("errorMessage", "Ya ha registrado su voto.");
                    request.getRequestDispatcher("votar.jsp").forward(request, response);
                    return; // Stop further processing
                }
                // ***************************************************

                session.setAttribute("cedulaVotante", documentoCedula);
                request.setAttribute("ciudadanoEncontrado", true);

                List<Candidato> listaCandidatos = controladora.getListaCandidatos();
                request.setAttribute("listaCandidatos", listaCandidatos);

                request.getRequestDispatcher("formularioVotacion.jsp").forward(request, response);
            } else {
                request.setAttribute("ciudadanoEncontrado", false);
                request.setAttribute("errorMessage", "No se encontró un ciudadano con ese número de documento.");
                request.getRequestDispatcher("votar.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String cedulaVotante = (String) session.getAttribute("cedulaVotante");

        if (cedulaVotante != null) {
            ControladoraLogica controladora = new ControladoraLogica();
            Ciudadano ciudadano = controladora.buscarCiudadanoPorDocumento(cedulaVotante);

            // If for some reason the citizen is no longer found (e.g., deleted from DB), redirect to login
            if (ciudadano == null) {
                session.removeAttribute("cedulaVotante"); // Clear invalid session data
                request.setAttribute("errorMessage", "Su sesión ha expirado o su registro no es válido. Por favor, ingrese su cédula nuevamente.");
                request.getRequestDispatcher("votar.jsp").forward(request, response);
                return;
            }

            // Instantiate VotoJpaController using the singleton EMF
            VotoJpaController votoJpa = new VotoJpaController(EntityManagerFactorySingleton.getEntityManagerFactory());

            // **** NEW CHECK: Has the citizen already voted? (for direct GET access) ****
            if (votoJpa.hasCiudadanoVoted(ciudadano.getId())) {
                // MODIFIED: Removed ' + cedulaVotante + ' from the message
                request.setAttribute("errorMessage", "Ya ha registrado su voto.");
                request.getRequestDispatcher("votar.jsp").forward(request, response);
                return; // Stop further processing
            }
            // ***************************************************

            List<Candidato> listaCandidatos = controladora.getListaCandidatos();
            request.setAttribute("listaCandidatos", listaCandidatos);
            request.getRequestDispatcher("formularioVotacion.jsp").forward(request, response);
        } else {
            // If not logged in, show the page to enter the cedula
            request.getRequestDispatcher("votar.jsp").forward(request, response);
        }
    }
}