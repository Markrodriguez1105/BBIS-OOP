/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businessRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author renmaee
 */
public class DbConnect {

    private static String HOST = "localhost";
    private static int PORT = 3306;
    private static String DB_NAME = "record";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/businessrecord", HOST, PORT, DB_NAME), USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        return connection;
    }

}
