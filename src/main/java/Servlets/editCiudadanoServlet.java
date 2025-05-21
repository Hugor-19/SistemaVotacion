package Servlets;

import Logica.Ciudadano;
import Persistencia.CiudadanojpaController;
import Persistencia.exceptions.NonexistentEntityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/editCiudadanoServlet")
public class editCiudadanoServlet extends HttpServlet {

    private final CiudadanojpaController ciudadanoController = new CiudadanojpaController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
            return;
        }

        int id = Integer.parseInt(idStr);
        Ciudadano ciudadano = ciudadanoController.findCiudadano(id);

        if (ciudadano == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ciudadano no encontrado");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("ciudadano", ciudadano);

        request.getRequestDispatcher("editarciudadano.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de Ciudadano no válido.");
            return;
        }

        int id = Integer.parseInt(idStr);
        Ciudadano c = ciudadanoController.findCiudadano(id);

        if (c == null) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "El Ciudadano no existe.");
            response.sendRedirect("svCiudadanos");
            return;
        }

        try {
            // 🟢 Asignar valores desde el formulario
            c.setNombre(request.getParameter("nombre"));
            c.setApellido(request.getParameter("apellido"));

            String tipoDoc = request.getParameter("tipo_documento");
            c.setTipoDocumento(Ciudadano.TipoDocumento.valueOf(tipoDoc));

            c.setDocumentoCedula(request.getParameter("numero_documento"));
            c.setTelefono(request.getParameter("telefono"));

            String fechaNacStr = request.getParameter("fecha_nacimiento");
            Date fechaNac = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacStr);
            c.setFechaNacimiento(fechaNac);

            c.setEstado(request.getParameter("estado"));

            ciudadanoController.edit(c);

            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Ciudadano actualizado correctamente.");
            response.sendRedirect("svCiudadanos");

        } catch (NonexistentEntityException e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "El Ciudadano no existe.");
            response.sendRedirect("svCiudadanos");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
