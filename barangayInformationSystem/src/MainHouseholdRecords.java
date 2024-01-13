package householdRecord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainHouseholdRecords extends Application {


@Override
public void start(Stage primaryStage) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("householdRecord.fxml"));
Parent root = loader.load();
HouseholdRecordController controller = loader.getController();
primaryStage.setScene(new Scene(root));
primaryStage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        launch(args);
    }
}


