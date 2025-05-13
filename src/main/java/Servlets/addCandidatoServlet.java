package Servlets;

import Logica.Candidato;
import Logica.ControladoraLogica;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet("/addCandidatoServlet")
@MultipartConfig
public class addCandidatoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("gestionarCandidato.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String cedula = request.getParameter("cedula");
        String partidoPolitico = request.getParameter("partido_politico");
        String propuesta = request.getParameter("propuesta");
        String estado = request.getParameter("estado");

        HttpSession session = request.getSession();

        // Validación
        if (nombre == null || apellido == null || cedula == null || partidoPolitico == null || propuesta == null || estado == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() || cedula.trim().isEmpty() || partidoPolitico.trim().isEmpty() || propuesta.trim().isEmpty() || estado.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Todos los campos son obligatorios.");
            response.sendRedirect("svCandidatos");
            return;
        }

        List<Candidato> listaCandidatos = control.getListaCandidatos();
        boolean cedulaDuplicada = listaCandidatos.stream()
                .anyMatch(c -> c.getCedula().equalsIgnoreCase(cedula.trim()));
        if (cedulaDuplicada) {
            session.setAttribute("errorMessage", "La cédula ya está registrada.");
            response.sendRedirect("svCandidatos");
            return;
        }

        // Procesar la imagen
        Part filePart = request.getPart("imagen");
        byte[] imagenBytes = null;

        if (filePart != null && filePart.getSize() > 0 && isExtension(filePart.getSubmittedFileName(), new String[]{"jpg", "png", "gif"})) {
            try (InputStream input = filePart.getInputStream()) {
                imagenBytes = input.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error específico al procesar la imagen: " + e.getMessage());
                session.setAttribute("errorMessage", "Error al procesar la imagen.");
                response.sendRedirect("svCandidatos");
                return;
            }
        } else if (filePart != null && filePart.getSize() > 0) {
            session.setAttribute("errorMessage", "Formato de imagen no válido (JPG, PNG, GIF).");
            response.sendRedirect("svCandidatos");
            return;
        } else {
            session.setAttribute("errorMessage", "Debe seleccionar una imagen.");
            response.sendRedirect("svCandidatos");
            return;
        }

        try {
            Candidato nuevoCandidato = new Candidato(
                    nombre.trim(),
                    apellido.trim(),
                    cedula.trim(),
                    partidoPolitico.trim(),
                    imagenBytes, // Usar los bytes de la imagen
                    propuesta.trim(),
                    estado.trim()
            );

            control.crearCandidato(nuevoCandidato);
            session.setAttribute("successMessage", "Candidato registrado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Ocurrió un error al registrar el candidato.");
        }

        response.sendRedirect("svCandidatos");
    }

    private boolean isExtension(String fileName, String[] extensions) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        String lowerCaseFileName = fileName.toLowerCase();
        for (String ext : extensions) {
            if (lowerCaseFileName.endsWith(ext)) return true;
        }
        return false;
    }
}