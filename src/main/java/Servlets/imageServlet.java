package Servlets;

import Logica.Candidato;
import Logica.ControladoraLogica;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/imageServlet")
public class imageServlet extends HttpServlet {

    private final ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Candidato candidato = control.getCandidatoById(id);

            if (candidato != null && candidato.getImagen() != null) {
                response.setContentType("image/jpeg");
                OutputStream out = response.getOutputStream();
                out.write(candidato.getImagen());
                out.close();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Imagen no encontrada");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }
}