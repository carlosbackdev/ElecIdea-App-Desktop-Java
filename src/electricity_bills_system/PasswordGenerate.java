
package electricity_bills_system;

import java.security.SecureRandom;

public class PasswordGenerate {
     public static String generarContrasena(int longitud) {       
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
                
        SecureRandom random = new SecureRandom();
        StringBuilder contrasena = new StringBuilder();
       
        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(caracteres.length());
            contrasena.append(caracteres.charAt(index));
        }

        return contrasena.toString();
    }
   
}
    

