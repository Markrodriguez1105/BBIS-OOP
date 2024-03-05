/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package treasury;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author HELLO MARK
 */
public class docReceipt {
    private static String ctcIssued;
    private static String ctcNo;
    private static String ctcAmount;
    private static String ctcAddress;
    private static String tax;
    private static String cost;
    private static String amount;
    private static String amountPay;

    public static String getCtcIssued() {
        return ctcIssued;
    }

    public static void setCtcIssued(String ctcIssued) {
        docReceipt.ctcIssued = ctcIssued;
    }

    public static String getCtcNo() {
        return ctcNo;
    }

    public static void setCtcNo(String ctcNo) {
        docReceipt.ctcNo = ctcNo;
    }

    public static String getCtcAmount() {
        return ctcAmount;
    }

    public static void setCtcAmount(String ctcAmount) {
        docReceipt.ctcAmount = ctcAmount;
    }

    public static String getCtcAddress() {
        return ctcAddress;
    }

    public static void setCtcAddress(String ctcAddress) {
        docReceipt.ctcAddress = ctcAddress;
    }

    public static String getTax() {
        return tax;
    }

    public static void setTax(String tax) {
        docReceipt.tax = tax;
    }

    public static String getCost() {
        return cost;
    }

    public static void setCost(String cost) {
        docReceipt.cost = cost;
    }

    public static String getAmount() {
        return amount;
    }

    public static void setAmount(String amount) {
        docReceipt.amount = amount;
    }

    public static String getAmountPay() {
        return amountPay;
    }

    public static void setAmountPay(String amountPay) {
        docReceipt.amountPay = amountPay;
    }

    public docReceipt(String ctcIssued, String ctcNo, String ctcAmount, String ctcAddress, String tax, String cost, String amount, String amountPay) {
        this.ctcIssued = ctcIssued;
        this.ctcNo = ctcNo;
        this.ctcAmount = ctcAmount;
        this.ctcAddress = ctcAddress;
        this.tax = tax;
        this.cost = cost;
        this.amount = amount;
        this.amountPay = amountPay;
    }
    
    
    
}
