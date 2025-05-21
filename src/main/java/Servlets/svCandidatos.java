package Servlets;

import Logica.Candidato;
import Logica.ControladoraLogica;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/svCandidatos")
public class svCandidatos extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la lista de Candidatos desde la lógica
        List<Candidato> listaCandidatos = control.getListaCandidatos();
        request.getSession().setAttribute("listaCandidatos", listaCandidatos);

        // Redirigir al jsp de candidatos
        request.getRequestDispatcher("gestionarCandidato.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}