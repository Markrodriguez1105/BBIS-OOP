package residentRecord;

import dashboard.*;
import java.sql.*;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class Database {
    
    public Connection connectDb() {
        Connection connect;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bbis", "root", "");
            return connect;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ResultSet executeQuery(String query){
        Connection connect = connectDb();
        PreparedStatement prep;
        ResultSet result = null;
        
        try {
            prep = connect.prepareStatement(query);
            result = prep.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
