package Servlets;

import Logica.Candidato;
import Logica.Ciudadano;
import Logica.Resultado;
import Logica.Voto;
import Persistencia.CandidatojpaController;
import Persistencia.CiudadanojpaController;
import Persistencia.EntityManagerFactorySingleton;
import Persistencia.ResultadoJpaController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction; // Importar EntityTransaction

@WebServlet("/procesarVotoServlet")
public class procesarVotoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String cedulaVotante = (String) session.getAttribute("cedulaVotante");
        String votoIdStr = request.getParameter("voto"); // Este 'voto' debe ser el ID del candidato

        // Validación inicial de datos de entrada
        if (cedulaVotante == null || cedulaVotante.isEmpty() || votoIdStr == null || votoIdStr.isEmpty()) {
            request.setAttribute("error", "Información de voto inválida. Asegúrese de que la cédula y el candidato sean seleccionados.");
            request.getRequestDispatcher("formularioVotacion.jsp").forward(request, response);
            return;
        }

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            int candidatoId = Integer.parseInt(votoIdStr);

            // Obtén la EntityManagerFactory usando el singleton
            emf = EntityManagerFactorySingleton.getEntityManagerFactory();
            
            // Instancia los controladores JPA
            CiudadanojpaController ciudadanoJpa = new CiudadanojpaController(emf);
            CandidatojpaController candidatoJpa = new CandidatojpaController(emf);
            ResultadoJpaController resultadoJpa = new ResultadoJpaController(emf);

            // Busca el ciudadano y el candidato
            Ciudadano ciudadano = ciudadanoJpa.findCiudadanoByCedula(cedulaVotante);
            Candidato candidato = candidatoJpa.findCandidato(candidatoId);

            if (ciudadano == null) {
                request.setAttribute("error", "El ciudadano con cédula " + cedulaVotante + " no fue encontrado.");
                request.getRequestDispatcher("formularioVotacion.jsp").forward(request, response);
                return;
            }
            if (candidato == null) {
                request.setAttribute("error", "El candidato seleccionado no es válido.");
                request.getRequestDispatcher("formularioVotacion.jsp").forward(request, response);
                return;
            }

            // Verifica si el ciudadano ya ha votado

            // Iniciar la transacción para operaciones con Voto y Resultado
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            // 1. Crear el objeto Voto
            Voto voto = new Voto();
            voto.setCiudadano(ciudadano); // Asigna el objeto Ciudadano
            voto.setCandidato(candidato); // Asigna el objeto Candidato
            voto.setFechaVotacion(new Date()); // La fecha actual del voto

            // Persistir el voto en la base de datos
            em.persist(voto); // Usar persist() para un nuevo objeto en una transacción gestionada.

            // 2. Actualizar o crear el objeto Resultado
            // Busca el resultado existente para el candidato
            Resultado resultado = resultadoJpa.findResultadoByCandidatoId(candidato.getId());

            if (resultado != null) {
                // Si existe, incrementa el contador de votos
                resultado.setVotos(resultado.getVotos() + 1);
                em.merge(resultado); // Usa merge para actualizar un objeto existente en la transacción
            } else {
                // Si no existe, crea un nuevo registro de resultado
                Resultado nuevoResultado = new Resultado();
                nuevoResultado.setCandidato(candidato); // Asigna el objeto Candidato
                nuevoResultado.setVotos(1); // Primer voto para este candidato
                em.persist(nuevoResultado); // Persiste el nuevo resultado
            }

            // Confirmar la transacción
            transaction.commit();

            // Mensaje de éxito y redirección
            String mensajeExito = "Su voto por " + candidato.getNombre() + " " + candidato.getApellido() +
                                  " ha sido registrado exitosamente el " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(voto.getFechaVotacion()) + ".";
            request.setAttribute("mensaje", mensajeExito);
            request.getRequestDispatcher("votoExitoso.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de candidato inválido. Por favor, intente de nuevo.");
            request.getRequestDispatcher("formularioVotacion.jsp").forward(request, response);
        } catch (Exception e) {
            // Manejo de errores de la transacción
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); // Deshacer la transacción si hubo un error
            }
            e.printStackTrace(); // Imprime la traza de la excepción para depuración
            request.setAttribute("error", "Ocurrió un error al registrar su voto. Por favor, intente nuevamente. Detalles: " + e.getMessage());
            request.getRequestDispatcher("formularioVotacion.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close(); // Cierra el EntityManager
            }
            // NO CERRAR EL EMF AQUÍ, ya que es un Singleton
        }
    }
}