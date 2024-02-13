package residentRecord;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ResidentInformationWindowController implements Initializable {

    @FXML
    private TableColumn<Resident, String> basicInfoCol;
    @FXML
    private TableColumn<Resident, String> residentInfoCol;
    @FXML
    private Button updateBtn;
    @FXML
    private TableView<Resident> residentDataPreview;

    private TableView<Resident> residentrecordTableView;
    
    private ResidentUpdateFormController updateForm;

   private ResidentRecordController residentRecord;
    static Resident selectedResident;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        residentDataPreview();
    }
    
    public void setUpdateForm(ResidentUpdateFormController controller){
        this.updateForm = controller;
    }

    public void setResidentRecord(ResidentRecordController controller){
        this.residentRecord = controller;
    }
    
    @FXML
    private void updateResidentInfo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateForm.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Update Resident Information");
                stage.setScene(new Scene(root));

                stage.show();
                  
                stage = (Stage) updateBtn.getScene().getWindow();
                stage.close();
    }

    public void residentDataPreview() {

        basicInfoCol.setCellValueFactory(new PropertyValueFactory<>("basicInfo"));
        residentInfoCol.setCellValueFactory(new PropertyValueFactory<>("actualInfo"));
        
        
        residentDataPreview.setItems(FXCollections.observableArrayList(
                new Resident("Name", selectedResident.getFullName()),
                new Resident("Age", String.valueOf(selectedResident.getAge())),
                new Resident("Gender", selectedResident.getGender()),
                new Resident("Height", String.valueOf(selectedResident.getHeight())),
                new Resident("Weight", String.valueOf(selectedResident.getWeight())),
                new Resident("Birthdate", String.valueOf(selectedResident.getBirthdate())),
                new Resident("BirhtPlace", selectedResident.getBirthplace()),
                new Resident("Blood Type", selectedResident.getBloodType()),
                new Resident("Phone No.", String.valueOf(selectedResident.getPhoneNo())),
                new Resident("Nationality", selectedResident.getNationality()),
                new Resident("Civil Status", selectedResident.getCivilStatus()),
                new Resident("Religion", selectedResident.getReligion()),
                new Resident("HouseHold No.", String.valueOf(selectedResident.getHouseholdNo())),
                new Resident("Relation with Family Head", selectedResident.getRelationWithFamHead()),
                new Resident("Educational Attainment", selectedResident.getEducationAttainment()),
                new Resident("Voter Status", selectedResident.getVoterStatus()),
                new Resident("Occupation", selectedResident.getOccupation()),
                new Resident("Occupational Duration", String.valueOf(selectedResident.getLivingDuration())),
                new Resident("Personal Income", String.valueOf(selectedResident.getPersonalIncome())),
                new Resident("with Disability", selectedResident.getDisability()),
                new Resident("Mother's Name", selectedResident.getMotherName()),
                new Resident("Mother's Phone No.", String.valueOf(selectedResident.getMotherPhoneNo())),
                new Resident("Father's Name", selectedResident.getFatherName()),
                new Resident("Father's Phone No.", String.valueOf(selectedResident.getFatherPhoneNo())),
                 new Resident("Status",selectedResident.getInOutBarangay()),
                new Resident("Email Account", selectedResident.getEmail())
        ));
    }

}
