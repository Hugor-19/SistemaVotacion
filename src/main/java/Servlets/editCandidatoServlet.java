package Servlets;

import Logica.Candidato;
import Persistencia.CandidatojpaController;
import Persistencia.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/editCandidatoServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10) // 10 MB max file size
public class editCandidatoServlet extends HttpServlet {

    private final CandidatojpaController candidatoController = new CandidatojpaController();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
            return;
        }

        int id = Integer.parseInt(idStr);
        Candidato candidato = candidatoController.findCandidato(id);

        if (candidato == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Candidato no encontrado");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("candidato", candidato);

        request.getRequestDispatcher("editarcandidato.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de Candidato no válido.");
            return;
        }

        int id = Integer.parseInt(idStr);
        Candidato c = candidatoController.findCandidato(id);

        if (c == null) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "El Candidato no existe.");
            response.sendRedirect("svCandidatos");
            return;
        }

        try {
            // 🟢 Asignar valores desde el formulario
            c.setNombre(request.getParameter("nombre"));
            c.setApellido(request.getParameter("apellido"));
            c.setCedula(request.getParameter("cedula"));
            c.setPartidoPolitico(request.getParameter("partido_politico"));
            c.setPropuesta(request.getParameter("propuesta"));
            c.setEstado(request.getParameter("estado"));

            // Manejo de la imagen
            Part filePart = request.getPart("foto");
            String imagen = c.getImagen(); // Mantener la imagen existente por defecto

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = filePart.getInputStream();
                String uploadPath = getServletContext().getRealPath("/") + "assets/img/candidatos/";
                java.io.File uploadDir = new java.io.File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                java.io.File file = new java.io.File(uploadPath + fileName);
                try (java.io.OutputStream out = new java.io.FileOutputStream(file);
                     InputStream input = fileContent) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = input.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }
                imagen = "assets/img/candidatos/" + fileName;
            }
            c.setImagen(imagen);

            candidatoController.edit(c);

            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Candidato actualizado correctamente.");
            response.sendRedirect("svCandidatos");

        } catch (NonexistentEntityException e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "El Candidato no existe.");
            response.sendRedirect("svCandidatos");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}