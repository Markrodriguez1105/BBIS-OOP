package reports;

import java.io.File;
import java.io.IOException;

//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.embed.swing.SwingFXUtils;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.rendering.PDFRenderer;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;
//import javafx.scene.layout.StackPane;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//import javafx.print.PrinterJob;
//import javafx.scene.Node;
//import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class ReportDetailsController {

    @FXML
    private VBox reportDetailsVBox;
    
    @FXML
    private ToggleGroup statusToggleGroup;

    @FXML
    private RadioButton solvedRadioButton;

    @FXML
    private RadioButton pendingRadioButton;

    @FXML
    private Button PrintButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private TextField reportIdLabel;

    @FXML
    private TextField reportTypeLabel;

    @FXML
    private TextField nameLabel;

    @FXML
    private TextField contactNumberLabel;

    @FXML
    private TextField emailLabel;

    @FXML
    private TextField dateLabel;

    @FXML
    private TextArea reasonLabel;

    // Database connector
    private DatabaseConnector databaseConnector;
    
    
    public ReportDetailsController() {
        this.databaseConnector = new DatabaseConnector();
    }

    private void disableTextFieldsAndTextArea() {
        reportIdLabel.setEditable(false);
        reportTypeLabel.setEditable(false);
        nameLabel.setEditable(false);
        contactNumberLabel.setEditable(false);
        emailLabel.setEditable(false);
        dateLabel.setEditable(false);
        reasonLabel.setEditable(false);

        // If you have more text fields, add them here
    }

    // Fetch and set report details from the database based on report ID
    public void loadReportDetailsFromDatabase(String reportId) {
        try (Connection connection = databaseConnector.connect()) {
            String query = "SELECT `report_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `report_type`, "
                    + "`date_recorded`, `email`, `phone_num`, `reason`, `status`, `report_status` "
                    + "FROM `report` WHERE `report_id` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, reportId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    ReportsData report = new ReportsData(
                            resultSet.getString("report_id"),
                            resultSet.getString("report_type"),
                            resultSet.getString("first_name"),
                            resultSet.getString("middle_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("suffix"),
                            resultSet.getString("phone_num"),
                            resultSet.getString("email"),
                            resultSet.getString("date_recorded"),
                            resultSet.getString("reason"),
                            resultSet.getString("status"),
                            resultSet.getString("report_status")
                    );
                    setReportDetails(report);

                    disableTextFieldsAndTextArea();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }


    // Set the report details to the UI components
    public void setReportDetails(ReportsData report) {
        reportIdLabel.setText(report.getReportId());
        reportTypeLabel.setText(report.getReportType());

        // Use bind for the nameLabel
        nameLabel.textProperty().bind(report.nameProperty());

        contactNumberLabel.setText(report.getContactNumber());
        emailLabel.setText(report.getEmail());
        dateLabel.setText(report.getDate());
        reasonLabel.setText(report.getReason());

        // Set the default status based on the retrieved value
        if ("Solved".equals(report.getStatus())) {
            solvedRadioButton.setSelected(true);
        } else {
            pendingRadioButton.setSelected(true);
        }
    }

    
    @FXML
    private void showPreviewDialog() {
        // Method to show a print preview dialog with report details

        // Create a dialog for print preview
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Print Preview");
        dialog.setHeaderText("Print Preview for Report Details");

        // Configure buttons in the dialog
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create a GridPane to display report details
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        // Populate the GridPane with report details
        gridPane.add(new Label("Report ID:"), 0, 0);
        gridPane.add(new Label(reportIdLabel.getText()), 1, 0);

        gridPane.add(new Label("Report Type:"), 0, 1);
        gridPane.add(new Label(reportTypeLabel.getText()), 1, 1);

        gridPane.add(new Label("Name:"), 0, 2);
        gridPane.add(new Label(nameLabel.getText()), 1, 2);

        gridPane.add(new Label("Contact Number:"), 0, 3);
        gridPane.add(new Label(contactNumberLabel.getText()), 1, 3);

        gridPane.add(new Label("Email:"), 0, 4);
        gridPane.add(new Label(emailLabel.getText()), 1, 4);

        gridPane.add(new Label("Date:"), 0, 5);
        gridPane.add(new Label(dateLabel.getText()), 1, 5);

        gridPane.add(new Label("Reason:"), 0, 6);
        TextArea reasonTextArea = new TextArea(reasonLabel.getText());
        reasonTextArea.setEditable(false);
        reasonTextArea.setWrapText(true);
        gridPane.add(reasonTextArea, 1, 6);

        // Set the content of the dialog to the created GridPane
        dialogPane.setContent(gridPane);

        // Show the dialog and wait for user input
        dialog.showAndWait();
    }

    
//    private Image generatePdfPreview(String content) throws IOException {
//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage();
//        document.addPage(page);
//
//        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
//            contentStream.beginText();
//            contentStream.newLineAtOffset(20, 700);
//            contentStream.showText(content);
//            contentStream.endText();
//        }
//
//        // Convert PDF to image
//        BufferedImage image = new PDFRenderer(document).renderImageWithDPI(0, 300);
//        document.close();
//
//        // Convert BufferedImage to JavaFX Image
//        return SwingFXUtils.toFXImage(image, null);
//    }



    private void printDirectly(Node node) {
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            boolean printed = job.printPage(node);
            if (printed) {
                job.endJob();
            }
        }
    }

    private String getNodeContentAsString(Node node) {
        StringBuilder content = new StringBuilder();

        if (node instanceof VBox) {
            VBox vbox = (VBox) node;
            vbox.getChildren().forEach(child -> {
                if (child instanceof Label) {
                    content.append(((Label) child).getText()).append("\n");
                } else if (child instanceof TextField) {
                    content.append(((TextField) child).getPromptText())
                            .append(": ")
                            .append(((TextField) child).getText())
                            .append("\n");
                } else if (child instanceof TextArea) {
                    content.append(((TextArea) child).getText()).append("\n");
                }
                // Add other supported UI components as needed
            });
        }

        return content.toString();
    }

    @FXML
    private void printReport(ActionEvent event) {
        // Method to handle direct printing of the report details

        // Use the showPreviewDialog method to display report details before printing
        showPreviewDialog();

        // Print the report directly
        printDirectly(reportDetailsVBox);
    }

//    private void generatePDF(File file) {
//        try {
//            PDDocument document = new PDDocument();
//            PDPage page = new PDPage();
//            document.addPage(page);
//
//            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
//                contentStream.beginText();  // <-- Add this line
//                contentStream.newLineAtOffset(20, 700);
//
//                // Get the content as a string
//                String content = getNodeContentAsString(reportDetailsVBox);
//
//                // Write content to PDF
//                contentStream.showText(content);
//                contentStream.endText();  // <-- Add this line
//            }
//            // Optionally, open the PDF file in the default PDF viewer
//            openPDFInViewer(file);
//            
//            document.save(file);
//            document.close();
//
//            // Inform the user about successful PDF creation
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("PDF Created");
//            alert.setHeaderText(null);
//            alert.setContentText("PDF file has been successfully created at:\n" + file.getAbsolutePath());
//            alert.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle exception as needed
//        }
//    }


    private void openPDFInViewer(File file) {
        // Implement code to open the generated PDF in the default PDF viewer
        // You may use java.awt.Desktop or another library for this purpose
    }

    @FXML
    private void updateReport() {
        // Get the selected status from the radio buttons
        String selectedStatus = solvedRadioButton.isSelected() ? "Solved" : "Pending";

        try (Connection connection = databaseConnector.connect()) {
            // Start a transaction
            connection.setAutoCommit(false);

            try {
                String updateQuery = "UPDATE `report` SET `status` = ? WHERE `report_id` = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, selectedStatus);
                    updateStatement.setString(2, reportIdLabel.getText());

                    // Execute the update statement
                    int rowsAffected = updateStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Report updated successfully.");
                        // Commit the transaction if the update is successful
                        connection.commit();

                        // Update the report details VBox
                        updateReportDetailsVBox(selectedStatus);

                    } else {
                        System.out.println("Failed to update report. No rows affected.");
                    }
                }
            } catch (SQLException e) {
                // Rollback the transaction in case of an error
                connection.rollback();
                e.printStackTrace();
                System.err.println("Error updating report: " + e.getMessage());
                // Optionally, log the error or show an alert to the user
            } finally {
                // Reset auto-commit to true and close the connection
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error connecting to the database: " + e.getMessage());
            // Optionally, log the error or show an alert to the user
        } finally {
            // Close the database connection (if not closed by try-with-resources)
            // Optionally, close other resources such as result sets
        }
    }

    private void updateReportDetailsVBox(String selectedStatus) {
        // Update the status label or other components in the reportDetailsVBox based on the selectedStatus
        // For example:
        Label statusLabel = (Label) reportDetailsVBox.lookup("#statusLabel"); // Assuming you have a statusLabel in the VBox
        if (statusLabel != null) {
            statusLabel.setText(selectedStatus);
        }
    }
}