package Persistencia;

import Logica.Resultado;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery; // Importar TypedQuery
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class ResultadoJpaController implements Serializable {

    public ResultadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public ResultadoJpaController () {
        this.emf = Persistence.createEntityManagerFactory("SistemaVotacionPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resultado resultado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(resultado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Resultado resultado) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            resultado = em.merge(resultado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            // Verificar si el resultado aún existe antes de lanzar la excepción.
            // Es buena práctica envolver estas verificaciones para evitar excepciones anidadas.
            Resultado foundResultado = findResultado(resultado.getId());
            if (foundResultado == null) {
                throw new Exception("El Resultado con id " + resultado.getId() + " ya no existe.");
            }
            throw ex; // Relanza la excepción original si el resultado existe pero hubo otro error
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Resultado findResultado(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resultado.class, id);
        } finally {
            em.close();
        }
    }

    public List<Resultado> findResultadoEntities() {
        return findResultadoEntities(true, -1, -1);
    }

    public List<Resultado> findResultadoEntities(int maxResults, int firstResult) {
        return findResultadoEntities(false, maxResults, firstResult);
    }

    private List<Resultado> findResultadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resultado.class));
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

    public Resultado findResultadoByCandidatoId(int candidatoId) {
        EntityManager em = getEntityManager();
        try {
            // Assuming 'candidato' is the relationship field in your Resultado entity
            // and 'id' is the primary key of Candidato
            TypedQuery<Resultado> query = em.createQuery(
                "SELECT r FROM Resultado r WHERE r.candidato.id = :candidatoId", Resultado.class);
            query.setParameter("candidatoId", candidatoId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // No result found for this candidate
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


}