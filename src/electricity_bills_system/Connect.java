
package electricity_bills_system;

import java.sql.*;

public class Connect {
    Connection c;
    Statement s;
    Connect(){
        try{
            c = DriverManager.getConnection("jdbc:mysql:///ebs","root","12345");
            s=c.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
