
package electricity_bills_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.*;

public class Connect {
    Connection c;
    Statement s;

    public Connect() {
        try {
            // Establecer conexión
           c = DriverManager.getConnection("jdbc:mysql://104.155.64.232:3306/ebs", "root", "C1TcAh:~Umtj)(4%");
            s = c.createStatement(); // Crear el Statement por defecto
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para crear un nuevo Statement
    public Statement createStatement() throws SQLException {
        return c.createStatement();
    }

    // Método para crear un PreparedStatement
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return c.prepareStatement(query);
    }
}
