package assets;

import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class DataValidator {
    //restrict textfields to accept only numbers from user

    public static void numbersOnlyTextfields(TextField... textFields) {
        for (TextField textField : textFields) {
            //add a listener to check every input of user
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) { // use regular expression for checking wether the input is a digit
                    Platform.runLater(() -> { //avoid program for triggering stackoverflow exception
                        textField.setText(oldValue);
                        textField.setStyle("-fx-border-color: red;"); //make the border of the textfield red if letter is entered
                    });
                } else {
                    textField.setStyle("-fx-border-color: blue;"); // make the border of the textfield blue if no letter is entered
                }
            });
        }
    }

    public static void numberFormatter(TextField... textfields) {
        for (TextField textfield : textfields) {
            //add a listener to check every input of user
            textfield.textProperty().addListener((observable, oldValue, newValue) -> {
                // Remove any commas from the new value and format it with commas every three digits
                String formattedValue = newValue.replaceAll(",", "").replaceAll("(?<=\\d)(?=(?:\\d{3})+$)", ",");
                textfield.setText(formattedValue);
            });
            
        }
    }
//restrict textfields to accept only letters from user

    public static void textOnlyTextfields(TextField... textFields) {
        for (TextField textField : textFields) {
            //add listener to check inputs of the user
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^[a-zA-Z]+$") && !newValue.isBlank()) { //use regular expression fro checking wether the input is a digit
                        textField.setText(oldValue); //if the input match digit, show the old input and not accept the new input
                        textField.setStyle("-fx-background-color: red, white;"); // make border of the textfield red if number is entered
                } else {
                    textField.setStyle("-fx-background-color: white, white;");
                    textField.setStyle("-fx-background-insets: 0, 1;");
                    textField.setStyle("-fx-background-radius: 3, 2;");
                    textField.setStyle("-fx-padding: 0.333333em 0.666667em 0.333333em 0.666667em;");
                    textField.setStyle("-fx-text-fill: -fx-text-inner-color;");
                    textField.setStyle("-fx-font-size: 1em;");
                }
            });
        }
    }

    //Age calculator from birthdate and date input validator
    public static void calculateAge(DatePicker birthdateDatePicker, TextField ageField) {
        LocalDate currDate = LocalDate.now();
        LocalDate birthdate = birthdateDatePicker.getValue();
        
        birthdateDatePicker.setStyle("-fx-border-color: blue;");
        
        try {
            birthdateDatePicker.getConverter().fromString(birthdateDatePicker.getEditor().getText()); //convert string into local date
            birthdateDatePicker.setStyle("-fx-border-color: blue;");
            if ((birthdate != null) && (currDate != null)) {
                //get the period from the current date to the value in the date picker and calculate it in years
                ageField.setText(String.valueOf(Period.between(birthdate, currDate).getYears()));
            }
            
        } catch (DateTimeParseException e) {
            birthdateDatePicker.setStyle("-fx-border-color: red;"); // make the border red if the value is not a date
            Toolkit.getDefaultToolkit().beep(); //make a beep sound
        }
    }

    //restrict the user to select a date is after the current date
    public static void restrictInvalidBirthDate(DatePicker bdate) {
        // Set the DayCellFactory to restrict selectable dates
        bdate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty); // update the cells in the date picker

                //Allow only past dates to be selectable
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true); //disable the cells that are after the current date
                    setStyle("-fx-background-color: #ffc0cb;"); // Change the style for disabled dates
                }
            }
        });
        
    }

    //validates phone number 
    public static void validatePhoneNumber(TextField PhoneNo) {
        TextFormatter<String> formatter = new TextFormatter<>(change -> { //add a formatter 
            String newText = change.getControlNewText();
            if (newText.matches("^$|^[0-9]{1,11}$")) { //accepts only digits from 0-9 with length of just 11
                return change; //return the change value if the conditin is true
            }
            return null; //return null if the condition is false
        });
        PhoneNo.setTextFormatter(formatter); //set the formatter 
    }

    //validate email
    public static void validateEmail(String emailAdd, TextField emailTextField) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail.com$"; //use regular expression to validate email
        Pattern pattern = Pattern.compile(emailRegex); //set the pattern
        Matcher matcher = pattern.matcher(emailAdd); // set the email address
        if (matcher.matches()) {
            emailTextField.setStyle("-fx-border-color: blue;"); //color border blue if the pattern matches
        } else {
            emailTextField.setStyle("-fx-border-color: red;"); //color border red if the pattern matches
        }
    }

    //restrict lenght of the inputs that the user can enter
    public static void restrictNumberInputlenght(TextField texfield, int rangeStart, int rangeEnd, int length) {
        //add formatter
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            //check if the entered number match the regular expression
            if (newText.matches("^$|^(" + rangeStart + "|" + rangeEnd + "|[1-9][0-9]?)$")) {
                if (newText.length() <= length) { //check if the entered value's length is less than or equals to the preffered length
                    return change;
                }
            }
            return null;
        });
        texfield.setTextFormatter(formatter); //set the textformatter
    }

    // over load  the previous method with double rangeStart and rangeEnd
    public static void restrictNumberInputLength(TextField textField, double rangeStart, double rangeEnd, int length) {
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^$|^(" + rangeStart + "|(\\d{0," + length + "}(\\.\\d{0,1})?))$") && (newText.isEmpty() || Double.parseDouble(newText) <= rangeEnd)) {
                return change;
            }
            return null;
        });
        textField.setTextFormatter(formatter);
    }    
}
