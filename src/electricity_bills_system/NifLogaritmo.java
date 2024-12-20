
package electricity_bills_system;


public class NifLogaritmo {
    
     public static boolean validarNIF(String nif) {
        try {
            
            String nif2=nif.substring(0, 8);
            char letra=nif.charAt(8);            
            int n=Integer.parseInt(nif2);
            
            char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 
                             'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
            char letraEsperada = letras[n % 23];
            
            return Character.toUpperCase(letra)==letraEsperada;
        } catch (NumberFormatException e) {            
            return false;
        }
    }
    
}
