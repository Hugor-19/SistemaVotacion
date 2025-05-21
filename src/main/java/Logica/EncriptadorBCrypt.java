package Logica;

import org.mindrot.jbcrypt.BCrypt;

public class EncriptadorBCrypt {
    
    private static final int ROUNDS = 12;
    
    public static String encriptarPassword(String passwordPlano) {
        if (passwordPlano == null || passwordPlano.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        return BCrypt.hashpw(passwordPlano, BCrypt.gensalt(ROUNDS));
    }
    
    public static boolean verificarPassword(String passwordPlano, String passwordHash) {
        if (passwordPlano == null || passwordPlano.trim().isEmpty()) {
            return false;
        }
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            return false;
        }
        
        // Si el hash no es un hash BCrypt válido, comparamos directamente
        if (!passwordHash.startsWith("$2a$")) {
            return passwordPlano.equals(passwordHash);
        }
        
        try {
            return BCrypt.checkpw(passwordPlano, passwordHash);
        } catch (IllegalArgumentException e) {
            // Si hay un error con el hash, comparamos directamente
            return passwordPlano.equals(passwordHash);
        }
    }
}
