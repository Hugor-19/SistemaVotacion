package Persistencia;

import Logica.Ciudadano;
import Logica.Usuario;
import Persistencia.exceptions.NonexistentEntityException;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CiudadanojpaController implements Serializable {

    private EntityManagerFactory emf = null;

    // Constructor con EntityManagerFactory
    public CiudadanojpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Constructor por defecto
    public CiudadanojpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaVotacionPU");
    }

    // Obtener el EntityManager
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear un nuevo Ciudadano
    public void create(Ciudadano ciudadano) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ciudadano);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el ciudadano: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Editar un Ciudadano existente
    public void edit(Ciudadano ciudadano) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            if (findCiudadano(ciudadano.getId()) == null) {
                throw new NonexistentEntityException("El ciudadano con ID " + ciudadano.getId() + " no existe.");
            }
            em.merge(ciudadano);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException("Error al editar el ciudadano: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Eliminar un Ciudadano por ID
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudadano ciudadano = em.find(Ciudadano.class, id);
            if (ciudadano == null) {
                throw new NonexistentEntityException("El ciudadano con ID " + id + " no existe.");
            }
            em.remove(ciudadano);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el ciudadano: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Obtener todos los Ciudadanos
    public List<Ciudadano> findCiudadanoEntities() {
        return findCiudadanoEntities(true, -1, -1);
    }

    // Obtener un rango de Ciudadanos
    public List<Ciudadano> findCiudadanoEntities(int maxResults, int firstResult) {
        return findCiudadanoEntities(false, maxResults, firstResult);
    }

    // Método privado para obtener Ciudadanos con o sin límites
    private List<Ciudadano> findCiudadanoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Ciudadano> cq = em.getCriteriaBuilder().createQuery(Ciudadano.class);
            cq.select(cq.from(Ciudadano.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // Buscar un Ciudadano por ID
    public Ciudadano findCiudadano(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudadano.class, id);
        } finally {
            em.close();
        }
    }

    // Contar el número total de Ciudadanos
    public int getCiudadanoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Ciudadano> rt = cq.from(Ciudadano.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Ciudadano findCiudadanoByCedula(String cedula) {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery("SELECT c FROM Logica.Ciudadano c WHERE c.documentoCedula = :cedula", Ciudadano.class)
                .setParameter("cedula", cedula)
                .getSingleResult();
    } catch (NoResultException e) {
        return null;
    } finally {
        em.close();
    }
  }
}