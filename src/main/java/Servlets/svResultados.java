package Servlets;

import Logica.Resultado;
import Logica.ControladoraLogica;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/svResultados")
public class svResultados extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // obtener los resultados
        List<Resultado> listaResultados = control.getListaResultados();
        request.setAttribute("listaResultados", listaResultados);

        // redirigir a la página de resultados
        request.getRequestDispatcher("resultados.jsp").forward(request, response);
     }

     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

