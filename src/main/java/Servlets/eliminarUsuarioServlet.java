package Servlets;

import Persistencia.UsuarioJpaController;
import Persistencia.exceptions.NonexistentEntityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/eliminarUsuarioServlet")
public class eliminarUsuarioServlet extends HttpServlet {
    private final UsuarioJpaController usuarioController = new UsuarioJpaController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            usuarioController.destroy(id);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }

        response.sendRedirect("usuarios");
    }
}
