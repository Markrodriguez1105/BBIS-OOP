package businessRecord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author renmaee
 */
public class BusinessRecord extends Application {

    public static void changeScene(String FXML, String FXMLDocument) {
        try {
            FXMLLoader loader = new FXMLLoader(BusinessRecord.class.getResource("FXML"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle(FXMLDocument);
            stage.show();
        } catch (IOException ex) {
         
            Logger.getLogger(BusinessRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  @Override
    public void start(Stage primaryStage) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.show();
            primaryStage.setTitle("Business Record");

        } catch (IOException ex) {
            Logger.getLogger(BusinessRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}