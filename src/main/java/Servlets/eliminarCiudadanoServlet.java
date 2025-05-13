package Servlets;

import Persistencia.CiudadanojpaController;
import Persistencia.exceptions.NonexistentEntityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/eliminarCiudadanoServlet")
public class eliminarCiudadanoServlet extends HttpServlet {
    private final CiudadanojpaController ciudadanoController = new CiudadanojpaController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            ciudadanoController.destroy(id);
        }catch (NonexistentEntityException e) {
            e.printStackTrace();
        }

        response .sendRedirect("ciudadanos");
    }
    
}
