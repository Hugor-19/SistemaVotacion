package Logica;

public class BCryptGenerator {
    public static void main(String[] args) {
        String passwordPlano = "admin1234";
        String hash = org.mindrot.jbcrypt.BCrypt.hashpw(passwordPlano, org.mindrot.jbcrypt.BCrypt.gensalt(12));
        System.out.println("Contraseña encriptada: " + hash);
    }
}
