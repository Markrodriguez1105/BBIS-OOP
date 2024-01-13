/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OperationTable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author renmaee
 */
public class OperationTable extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/businessrecord/FXMLDocument.fxml"));
             Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        
        } catch (IOException ex) {
            Logger.getLogger(OperationTable.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
 
