package Servlets;

import Logica.ControladoraLogica;
import Logica.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "loginServlet", urlPatterns = { "/loginServlet" })
public class loginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ControladoraLogica control = new ControladoraLogica();

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            boolean loginValido = control.comprobarLogin(email, password);

            if (loginValido) {
                Usuario usuarioLogueado = control.obtenerUsuarioPorEmail(email);
                HttpSession miSesion = request.getSession(true);
                miSesion.setAttribute("usuario", usuarioLogueado);

                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", " Email o contraseña incorrectos.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para logs
            request.setAttribute("errorMessage", " Error de conexión con la base de datos.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
