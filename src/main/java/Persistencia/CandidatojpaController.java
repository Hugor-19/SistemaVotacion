package Persistencia;

import Logica.Candidato;
import Persistencia.exceptions.NonexistentEntityException;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CandidatojpaController implements Serializable {

    private EntityManagerFactory emf;

    // Constructor que inicializa el EntityManagerFactory
    public CandidatojpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Constructor por defecto
    public CandidatojpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaVotacionPU");
    }

    // Obtener el EntityManager
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear un nuevo candidato
    public void create(Candidato candidato) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(candidato);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el candidato: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Editar un candidato existente
    public void edit(Candidato candidato) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            if (findCandidato(candidato.getId()) == null) {
                throw new NonexistentEntityException("El candidato con id " + candidato.getId() + " no existe.");
            }
            em.merge(candidato);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error al editar el candidato: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Eliminar un candidato por su ID
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Candidato candidato = em.find(Candidato.class, id);
            if (candidato == null) {
                throw new NonexistentEntityException("El candidato con id " + id + " no existe.");
            }
            em.remove(candidato);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el candidato: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Obtener todos los candidatos
    public List<Candidato> findCandidatoEntities() {
        return findCandidatoEntities(true, -1, -1);
    }

    // Obtener un rango de candidatos
    public List<Candidato> findCandidatoEntities(int maxResults, int firstResult) {
        return findCandidatoEntities(false, maxResults, firstResult);
    }

    // Método privado para obtener candidatos con o sin límites
    private List<Candidato> findCandidatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Candidato> cq = em.getCriteriaBuilder().createQuery(Candidato.class);
            cq.select(cq.from(Candidato.class));
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

    // Buscar un candidato por su ID
    public Candidato findCandidato(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Candidato.class, id);
        } finally {
            em.close();
        }
    }

    // Contar el número total de candidatos
    public int getCandidatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Candidato> rt = cq.from(Candidato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}