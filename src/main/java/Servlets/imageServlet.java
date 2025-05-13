package Servlets;

import Logica.Candidato;
import Logica.ControladoraLogica;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/imageServlet")
public class imageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ControladoraLogica control = new ControladoraLogica();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Candidato candidato = control.getCandidatoById(id);
                if (candidato != null && candidato.getImagen() != null) {
                    response.setContentType("image/*"); // El navegador intentará determinar el tipo de imagen
                    OutputStream out = response.getOutputStream();
                    out.write(candidato.getImagen());
                    out.close();
                } else {
                    // Si no se encuentra el candidato o no tiene imagen, puedes mostrar una imagen por defecto o un mensaje
                    response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de candidato inválido."); // 400 Bad Request
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Se requiere el ID del candidato."); // 400 Bad Request
        }
    }
}