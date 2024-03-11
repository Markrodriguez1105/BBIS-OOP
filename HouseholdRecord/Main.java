package HouseholdRecord;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Household Record");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.centerOnScreen();
            Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
            stage.getIcons().add(icon);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void changeScene(String fxml, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle(title);
    }

    public void overlayWindow(String fxml, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage overlayStage = new Stage();
        overlayStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        overlayStage.setTitle(title);
        overlayStage.centerOnScreen();
        overlayStage.setScene(scene);
        overlayStage.show();
    }

    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    
    }

