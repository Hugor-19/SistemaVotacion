package Servlets;

import Logica.Ciudadano;
import Logica.Ciudadano.TipoDocumento;
import Logica.ControladoraLogica;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/addCiudadanoServlet")
public class addCiudadanoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("gestionarCiudadano.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String tipoDocumentoStr = request.getParameter("tipo_documento");
        String documentoCedula = request.getParameter("numero_documento");
        String telefono = request.getParameter("telefono");
        String fechaNacimientoStr = request.getParameter("fecha_nacimiento");
        String estado = request.getParameter("estado");

        HttpSession session = request.getSession();

        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                tipoDocumentoStr == null || tipoDocumentoStr.trim().isEmpty() ||
                documentoCedula == null || documentoCedula.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                fechaNacimientoStr == null || fechaNacimientoStr.trim().isEmpty() ||
                estado == null || estado.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Todos los campos son obligatorios.");
            response.sendRedirect("svCiudadanos");
            return;
        }

        // Validar tipo de documento
        TipoDocumento tipoDocumento;
        try {
            tipoDocumento = TipoDocumento.valueOf(tipoDocumentoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            session.setAttribute("errorMessage", "El tipo de documento es inválido.");
            response.sendRedirect("svCiudadanos");
            return;
        }

        // Validación de numero documento duplicado
        List<Ciudadano> ciudadanosExistentes = control.getListaCiudadanos();
        boolean documentoDuplicado = ciudadanosExistentes.stream()
                .anyMatch(c -> c.getTipoDocumento() == tipoDocumento && c.getDocumentoCedula().equalsIgnoreCase(documentoCedula));
        if (documentoDuplicado) {
            session.setAttribute("errorMessage", "El número de documento ya está registrado.");
            response.sendRedirect("svCiudadanos");
            return;
        }

        // Validar que la cédula tenga entre 7 y 10 dígitos (inclusive)
        if (documentoCedula.length() < 7 || documentoCedula.length() > 10) {
            session.setAttribute("errorMessage", "La cédula debe tener entre 7 y 10 dígitos.");
            response.sendRedirect("svCiudadanos");
            return;
        }

        // Validar fecha de nacimiento
        Date fechaNacimiento;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaNacimiento = sdf.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            session.setAttribute("errorMessage", "La fecha de nacimiento es inválida.");
            response.sendRedirect("svCiudadanos");
            return;
        }

        // Validar que la fecha de nacimiento corresponda a una persona mayor de 18 años
        if (!control.validarFechaNacimiento(fechaNacimientoStr)) {
            session.setAttribute("errorMessage", "La fecha de nacimiento debe corresponder a una persona mayor de 18 años.");
            response.sendRedirect("svCiudadanos");
            return;
        }

        try {
            // Crear un nuevo objeto Ciudadano
            Ciudadano nuevoCiudadano = new Ciudadano();
            nuevoCiudadano.setNombre(nombre.trim());
            nuevoCiudadano.setApellido(apellido.trim());
            nuevoCiudadano.setTipoDocumento(tipoDocumento);
            nuevoCiudadano.setDocumentoCedula(documentoCedula.trim());
            nuevoCiudadano.setTelefono(telefono.trim());
            nuevoCiudadano.setFechaNacimiento(fechaNacimiento);
            nuevoCiudadano.setEstado(estado.trim().toLowerCase());

            // Persistir el ciudadano en la base de datos
            control.crearCiudadano(nuevoCiudadano);

            // Establecer mensaje de éxito en la sesión
            session.setAttribute("successMessage", "Ciudadano creado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Ocurrió un error al registrar el ciudadano.");
        }

        // Redirigir a la página de gestión de ciudadanos
        response.sendRedirect("svCiudadanos");
    }
}