package Servlets;

import Logica.Usuario;
import Persistencia.UsuarioJpaController;
import Persistencia.exceptions.NonexistentEntityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/editUsuarioServlet")  // Este nombre debe coincidir con el formulario
public class editUsuarioServlet extends HttpServlet {

    private final UsuarioJpaController usuarioController = new UsuarioJpaController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = usuarioController.findUsuario(id);

        // Guardamos el usuario en sesión para acceder desde el JSP
        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuario);

        request.getRequestDispatcher("editarusuario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario no válido.");
            return;
        }

        int id = Integer.parseInt(idStr);
        Usuario u = usuarioController.findUsuario(id);

        u.setNombre(request.getParameter("nombre"));
        u.setEmail(request.getParameter("email"));

        String nuevaPassword = request.getParameter("password");
        if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
            u.setPassword(nuevaPassword);
        }

        u.setRol(request.getParameter("rol"));
        u.setEstado(request.getParameter("estado"));

        try {
            usuarioController.edit(u);

            // ✅ Guardamos mensaje en sesión y redirigimos
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Usuario actualizado correctamente.");
            response.sendRedirect("svUsuarios");

        } catch (NonexistentEntityException e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "El usuario no existe.");
            response.sendRedirect("svUsuarios");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
