
package electricity_bills_system;

import java.text.DecimalFormat;

public class NumberFormate {
    public static String formatear(double numero){
        DecimalFormat df= new DecimalFormat("#.00");
        return df.format(numero);
    }
}
