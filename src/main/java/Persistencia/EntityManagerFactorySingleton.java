package Persistencia;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {

    private static EntityManagerFactory emf;

    

    static {
        try {
            emf = Persistence.createEntityManagerFactory("SistemaVotacionPU"); // Reemplaza con el nombre de tu unidad de persistencia
            System.out.println("EntityManagerFactory inicializada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al inicializar EntityManagerFactory: " + e);
            throw new ExceptionInInitializerError(e); // Lanza un error fatal si la inicialización falla
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    private EntityManagerFactorySingleton() {
        // Constructor privado para evitar instanciación externa
    }
}