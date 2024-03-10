/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businessRecord;

/**
 *
 * @author renmaee
 */
public class DataValidator {
    boolean isValidEmail(String email) {
    // Implement your email validation logic (e.g., using regular expressions)
    // For simplicity, you can use a basic regex pattern
    return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
}

boolean isValidContactNumber(String contactNumber) {
    // Implement your contact number validation logic
    // For simplicity, let's check if the number contains only digits and has at least 10 digits
    String cleanedContactNumber = contactNumber.replaceAll("\\D", ""); // Remove non-digits
    return cleanedContactNumber.length() >= 10; // Check if the cleaned number has at least 10 digits
}



boolean isValidTIN(String tin) {
    // Implement your TIN validation logic (e.g., length check)
    return tin.length() == 9 && tin.matches("\\d+");
}

boolean isValidMonthlyIncome(String income) {
    // Implement your monthly income validation logic
    try {
        int incomeValue = Integer.parseInt(income);
        return incomeValue >= 1000;
    } catch (NumberFormatException e) {
        return false; // If parsing fails, consider it invalid
    }
}
}