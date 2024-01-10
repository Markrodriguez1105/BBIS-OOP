package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public  class main extends Application {

    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LogIn/LogIn.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.centerOnScreen();
        Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
        stage.getIcons().add(icon);
        stage.show();
        stg = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void changeScene(String fxml, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(root);
        stg.setTitle(title);
    }

    public void overlayWindow(String fxml, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.alwaysOnTopProperty();
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
