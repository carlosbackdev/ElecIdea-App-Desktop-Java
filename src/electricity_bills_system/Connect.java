
package electricity_bills_system;

import java.sql.*;

public class Connect {
    Connection c;
    Statement s;
    Connect(){
        try{
            c = DriverManager.getConnection("jdbc:mysql://45-147-251-176.cloud-xip.com:3306/ebs","root","tH236HNfKCQqNjeeBvc9gO");
            s=c.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
