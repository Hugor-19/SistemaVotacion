package Servlets;

import Logica.ControladoraLogica;
import Logica.Usuario;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/addUsuarioServlet")
public class addUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("gestionarUsuarios.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rol = request.getParameter("rol");
        String estado = request.getParameter("estado");

        HttpSession session = request.getSession();

        // Validación de campos vacíos
        if (nombre == null || nombre.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                rol == null || rol.trim().isEmpty() ||
                estado == null || estado.trim().isEmpty()) {

            session.setAttribute("errorMessage", "Todos los campos son obligatorios.");
            response.sendRedirect("gestionarUsuarios.jsp");
            return;
        }

        // Validación de email duplicado
        List<Usuario> usuariosExistentes = control.getListaUsuarios();
        boolean emailDuplicado = usuariosExistentes.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));

        if (emailDuplicado) {
            session.setAttribute("errorMessage", "El email ya está registrado.");
            response.sendRedirect("svUsuarios");
            return;
        }

        try {
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre.trim());
            nuevoUsuario.setEmail(email.trim());
            nuevoUsuario.setPassword(password.trim());
            nuevoUsuario.setRol(rol.trim().toLowerCase());
            nuevoUsuario.setEstado(estado.trim().toLowerCase());

            control.crearUsuario(nuevoUsuario);
            session.setAttribute("successMessage", "Usuario registrado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Ocurrió un error al registrar el usuario.");
        }

        response.sendRedirect("svUsuarios");
    }
}