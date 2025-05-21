package Servlets;

import Logica.Ciudadano;
import Logica.ControladoraLogica;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/svCiudadanos")
public class svCiudadanos extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la lista de ciudadanos desde la lógica
        List<Ciudadano> listaCiudadanos = control.getListaCiudadanos();
        request.setAttribute("listaCiudadanos", listaCiudadanos);

        // Redirigir al JSP para mostrar los ciudadanos
        request.getRequestDispatcher("gestionarCiudadano.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}