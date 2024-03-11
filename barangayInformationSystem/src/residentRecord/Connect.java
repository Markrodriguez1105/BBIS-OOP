package residentRecord;

import java.sql.Connection;
import java.sql.DriverManager;
 
public class Connect {
    
    public static Connection connectDB(){
    
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bbis", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
}
    
}
