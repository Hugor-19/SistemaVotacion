package Servlets;

import Logica.Candidato;
import Logica.ControladoraLogica;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/addCandidatoServlet")
@MultipartConfig
public class addCandidatoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ControladoraLogica control = new ControladoraLogica();

    private final String pathFiles = "C:\\Users\\hugor\\OneDrive\\Documentos\\NetBeansProjects\\SistemaVotacion\\src\\main\\webapp\\files\\";
    private final File uploads = new File(pathFiles);
    private final String[] extens = {"jpg", "png", "gif"};

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

        if (nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                cedula == null || cedula.trim().isEmpty() ||
                partidoPolitico == null || partidoPolitico.trim().isEmpty() ||
                propuesta == null || propuesta.trim().isEmpty() ||
                estado == null || estado.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Todos los campos son obligatorios.");
            response.sendRedirect("svCandidatos");
            return;
        }

        // Validar que la cédula no se repita
        List<Candidato> listaCandidatos = control.getListaCandidatos();
        boolean cedulaDuplicada = listaCandidatos.stream()
                .anyMatch(c -> c.getCedula().equalsIgnoreCase(cedula.trim()));

        if (cedulaDuplicada) {
            session.setAttribute("errorMessage", "La cédula ya está registrada.");
            response.sendRedirect("svCandidatos");
            return;
        }

        // Validar que la cédula tenga estrictamente más de 7 dígitos
        if (cedula.length() <= 7) {
           session.setAttribute("errorMessage", "La cédula debe tener más de 7 dígitos.");
           response.sendRedirect("svCiudadanos");
           return;
       }

        // Procesar imagen
        Part filePart = request.getPart("foto"); // campo input name="foto"
        String imagenRelativa = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            if (isExtension(fileName, extens)) {
                File file = new File(uploads, fileName);
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, file.toPath());
                    imagenRelativa = "files/" + fileName;
                } catch (IOException e) {
                    e.printStackTrace();
                    session.setAttribute("errorMessage", "Error al guardar la imagen.");
                    response.sendRedirect("svCandidatos");
                    return;
                }
            } else {
                session.setAttribute("errorMessage", "Formato de imagen no permitido.");
                response.sendRedirect("svCandidatos");
                return;
            }
        }

        try {
            Candidato nuevoCandidato = new Candidato(
                    nombre.trim(),
                    apellido.trim(),
                    cedula.trim(),
                    partidoPolitico.trim(),
                    imagenRelativa,
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
        for (String ext : extensions) {
            if (fileName.toLowerCase().endsWith(ext)) return true;
        }
        return false;
    }
}