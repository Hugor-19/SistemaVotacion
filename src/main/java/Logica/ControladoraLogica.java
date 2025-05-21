package Logica;

import Persistencia.CandidatojpaController;
import Persistencia.CiudadanojpaController;
import Persistencia.ControladoraPersistencia;
import Persistencia.exceptions.NonexistentEntityException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ControladoraLogica {

    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    private List<Usuario> listaUsuarios;
    CiudadanojpaController ciudadanoJpa;
    CandidatojpaController candidatoJpa;
    
    public ControladoraLogica() {
        ciudadanoJpa = new CiudadanojpaController();
        candidatoJpa = new CandidatojpaController();
    }

    // login
    public boolean comprobarLogin(String email, String passwordPlano) {
        listaUsuarios = controlPersis.getUsuarios();

        for (Usuario usu : listaUsuarios) {
            if (usu.getEmail().equalsIgnoreCase(email)
                    && EncriptadorBCrypt.verificarPassword(passwordPlano, usu.getPassword())) {
                // Si la contraseña es correcta pero no está encriptada, la encriptamos
                if (!usu.getPassword().startsWith("$2a$")) {
                    String passwordEncriptado = EncriptadorBCrypt.encriptarPassword(passwordPlano);
                    usu.setPassword(passwordEncriptado);
                    try {
                        controlPersis.editarUsuario(usu);
                    } catch (Exception e) {
                        System.out.println("Error al actualizar la contraseña: " + e.getMessage());
                    }
                }
                return true;
            }
        }
        return false;
    }

    // Método para obtener un usuario por su email
    public Usuario obtenerUsuarioPorEmail(String email) {
        listaUsuarios = controlPersis.traerUsuarios();
        for (Usuario usu : listaUsuarios) {
            if (usu.getEmail().equalsIgnoreCase(email)) {
                return usu;
            }
        }
        return null;
    }

    // Método para obtener la lista completa de usuarios
    public List<Usuario> getListaUsuarios() {
        return controlPersis.traerUsuarios();
    }

    // Método para crear un nuevo usuario
    public void crearUsuario(Usuario usuario) {
        // Validar si el password no está encriptado
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(EncriptadorBCrypt.encriptarPassword(usuario.getPassword())); // Encriptar password
        }
        controlPersis.crearUsuario(usuario);
    }

    // Método para editar usuario
    public void editarUsuario(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty() && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(EncriptadorBCrypt.encriptarPassword(usuario.getPassword()));
        }
        controlPersis.editarUsuario(usuario);
    }

    // Método para buscar un usuario por ID
    public Usuario getUsuarioById(int id) {
        return controlPersis.buscarUsuarioPorId(id);
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(int id_usuario) {
        controlPersis.eliminarUsuario(id_usuario);
    }


    // ciudadano
    // Método para crear un ciudadano
    public void crearCiudadano(Ciudadano ciudadano) throws Exception {
        ciudadanoJpa.create(ciudadano);
    }

    // Método para obtener la lista completa de ciudadanos
    public List<Ciudadano> getListaCiudadanos() {
        return controlPersis.traerCiudadanos();
    }

    // Método para validar si la fecha de nacimiento corresponde a una persona mayor de 18 años
    public boolean validarFechaNacimiento(String fechaNacimiento) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(fechaNacimiento);
            Date fechaActual = new Date();

            // Calcular la diferencia en años
            long diferenciaEnMilisegundos = fechaActual.getTime() - fecha.getTime();
            long anios = diferenciaEnMilisegundos / (1000L * 60 * 60 * 24 * 365);

            return anios >= 18; // Retorna true si es mayor o igual a 18 años
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para editar un ciudadano
    public void editarCiudadano(Ciudadano ciudadano) throws NonexistentEntityException, Exception {
        ciudadanoJpa.edit(ciudadano);
    }

    // Método para eliminar un ciudadano por su ID
    public void eliminarCiudadano(int id) throws NonexistentEntityException {
        ciudadanoJpa.destroy(id);
    }

    // candidato
    // Método para crear un candidato
    public void crearCandidato(Candidato candidato) throws Exception {
        controlPersis.crearCandidato(candidato);
    }

    // Método para obtener la lista completa de candidatos
    public List<Candidato> getListaCandidatos() {
        return controlPersis.traerCandidatos();
    }

    // Método para editar un candidato
    public void editarCandidato(Candidato candidato) throws NonexistentEntityException, Exception {
        candidatoJpa.edit(candidato);
    }

    // Método para eliminar un candidato por su ID
    public void eliminarCandidato(int id) throws NonexistentEntityException {
        candidatoJpa.destroy(id);
    }

    public Candidato getCandidatoById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Candidato.class, id);
        } finally {
            em.close();
        }
    }

    // votos
    public List<Voto> getListaVotos() {
        return controlPersis.traerVotos();
    }

    // resultado 
    public List<Resultado> getListaResultados() {
        return controlPersis.traerResultados();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaVotacionPU");
        return emf.createEntityManager();
    }
    // buscar ciudadano cedula para realizar el voto
    public Ciudadano buscarCiudadanoPorDocumento(String documentoCedula) {
        EntityManager em = getEntityManager();
        Ciudadano ciudadano = null;
        try {
            javax.persistence.Query query = em.createQuery("SELECT c FROM Ciudadano c WHERE c.documentoCedula = :doc");
            query.setParameter("doc", documentoCedula);
            List<Ciudadano> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                ciudadano = resultados.get(0); // Si hay resultados, toma el primero (debería ser único por la restricción unique)
            }
        } catch (Exception e) {
            // En caso de error al realizar la consulta, puedes loguear el error si lo deseas
            System.err.println("Error al buscar ciudadano por documento: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
        return ciudadano;
    }   
}