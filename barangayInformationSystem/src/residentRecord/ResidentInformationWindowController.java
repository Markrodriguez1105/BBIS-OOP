package residentRecord;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    
    
//    private ResidentRecordViewController residentRecord;
    static Resident selectedResident;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        residentDataPreview();
    }


    @FXML
    private void updateResidentInfo(ActionEvent event) {
        
        
    }

    public void residentDataPreview() {


        basicInfoCol.setCellValueFactory(new PropertyValueFactory<>("basicInfo"));
        residentInfoCol.setCellValueFactory(new PropertyValueFactory<>("actualInfo"));
        // Add sample data to the TableView
        residentDataPreview.setItems(FXCollections.observableArrayList(
                new Resident("Name", selectedResident.getFullName()),
                new Resident("Age", String.valueOf(selectedResident.getAge())),
                new Resident("Birthdate", String.valueOf(selectedResident.getBirthdate())),
                new Resident("Gender", selectedResident.getGender()),
                new Resident("Civil Status", selectedResident.getCivilStatus()),
                new Resident("Religion", selectedResident.getReligion()),
                new Resident("In School/ Out School", selectedResident.getInOutSchool()),
                new Resident("Educational Attainment", selectedResident.getEducAttainment()),
                new Resident("Occupation", selectedResident.getOccupation()),
                new Resident("Labor Force", selectedResident.getLaborForce()),
                new Resident("Pregnant", selectedResident.getIspregnant()),
                new Resident("Nursing Mother", selectedResident.getNursingMother()),
                new Resident("Practice Family Planning", selectedResident.getFamilyPlanning()),
                new Resident("Name", selectedResident.getDisability())
        ));
    }
    
    public void setSelectedResident(Resident selectedResident) {
        this.selectedResident = selectedResident;

        // Update the TableView with the new selected resident
        residentDataPreview.getItems().setAll(
                new Resident(selectedResident.getfName(),selectedResident.getmName(),selectedResident.getlName(),selectedResident.getSuffix(),selectedResident.getGender(),
                        selectedResident.getAge(),selectedResident.getBirthdate(),selectedResident.getCivilStatus(),selectedResident.getReligion(),
                        selectedResident.getInOutSchool(),selectedResident.getEducAttainment(),selectedResident.getOccupation(),selectedResident.getLaborForce(),
                        selectedResident.getIspregnant(),selectedResident.getNursingMother(),selectedResident.getFamilyPlanning(),selectedResident.getDisability()
                        ,selectedResident.getPurok(),selectedResident.getHousehold()));
    }

}
