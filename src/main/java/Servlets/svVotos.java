package Servlets;

import Logica.Voto;
import Logica.ControladoraLogica;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/svVotos")
public class svVotos  extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

        // Obtener los votos 
        List<Voto> listaVotos = control.getListaVotos();
        request.setAttribute("listaVotos", listaVotos);

        // Redirigir al jsp de votos 
        request.getRequestDispatcher("votos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);        
    }
}
