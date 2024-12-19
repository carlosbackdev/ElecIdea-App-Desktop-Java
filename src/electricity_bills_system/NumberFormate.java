
package electricity_bills_system;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberFormate {
    public static String formatear(double numero){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df= new DecimalFormat("#.00",symbols);
        return df.format(numero);
    }
}
