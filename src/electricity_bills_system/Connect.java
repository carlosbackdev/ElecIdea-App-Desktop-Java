
package electricity_bills_system;

import java.sql.*;

public class Connect {
    Connection c;
    Statement s;
    Connect(){
        try{
            c = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7749848","sql7749848","335YxJ5li8");
            s=c.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
