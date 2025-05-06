package Servlets;

import Logica.Candidato;
import Logica.ControladoraLogica;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/addCandidatoServlet")
@MultipartConfig(maxFileSize = 16177215) // 16MB
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

        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String cedula = request.getParameter("cedula");
        String partidoPolitico = request.getParameter("partido_politico");
        String propuesta = request.getParameter("propuesta");
        String estado = request.getParameter("estado");

        HttpSession session = request.getSession();

        // Validar campos obligatorios
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

        // Validar cédula duplicada
        List<Candidato> listaCandidatos = control.getListaCandidatos();
        boolean cedulaDuplicada = listaCandidatos.stream()
                .anyMatch(c -> c.getCedula().equalsIgnoreCase(cedula.trim()));
        if (cedulaDuplicada) {
            session.setAttribute("errorMessage", "La cédula ya está registrada.");
            response.sendRedirect("svCandidatos");
            return;
        }

        // Leer imagen como byte[]
        Part filePart = request.getPart("imagen");
        byte[] imagenBytes = null;
        if (filePart != null && filePart.getSize() > 0) {
            imagenBytes = filePart.getInputStream().readAllBytes();
        }

        if (imagenBytes == null || imagenBytes.length == 0) {
            session.setAttribute("errorMessage", "La imagen del candidato es obligatoria.");
            response.sendRedirect("svCandidatos");
            return;
        }

        try {
            // Crear un nuevo objeto Candidato
            Candidato nuevoCandidato = new Candidato(
                    nombre.trim(),
                    apellido.trim(),
                    cedula.trim(),
                    partidoPolitico.trim(),
                    imagenBytes, // Pasar el array de bytes de la imagen
                    propuesta.trim(),
                    estado.trim()
            );

            // Persistir el candidato en la base de datos a través de la ControladoraLogica
            control.crearCandidato(nuevoCandidato);

            // Establecer mensaje de éxito en la sesión
            session.setAttribute("successMessage", "Candidato registrado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Ocurrió un error al registrar el candidato.");
        }

        // Redirigir a la página de gestión de candidatos
        response.sendRedirect("svCandidatos");
    }
}