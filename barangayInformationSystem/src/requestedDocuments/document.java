/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestedDocuments;

import java.util.Date;

/**
 *
 * @author HELLO MARK
 */
public class document {
    
    private String id, firstname, middlename, lastname, suffix, cat, documentType, stats, Rfname, Rmname, Rlname, Rsuffix;
    private Date date;

    public document(String id, String firstname, String middlename, String lastname, String suffix, String Rfname, String Rmname, String Rlname, String Rsuffix, String cat, String documentType, Date date, String stats) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.suffix = suffix;
        this.cat = cat;
        this.documentType = documentType;
        this.stats = stats;
        this.Rfname = Rfname;
        this.Rmname = Rmname;
        this.Rlname = Rlname;
        this.Rsuffix = Rsuffix;
        this.date = date;
    }

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getRfname() {
        return Rfname;
    }

    public void setRfname(String Rfname) {
        this.Rfname = Rfname;
    }

    public String getRmname() {
        return Rmname;
    }

    public void setRmname(String Rmname) {
        this.Rmname = Rmname;
    }

    public String getRlname() {
        return Rlname;
    }

    public void setRlname(String Rlname) {
        this.Rlname = Rlname;
    }

    public String getRsuffix() {
        return Rsuffix;
    }

    public void setRsuffix(String Rsuffix) {
        this.Rsuffix = Rsuffix;
    }
    
    public String getFullname(){
        return lastname + ", " + firstname + (!middlename.isEmpty() ? " " + middlename.toUpperCase().charAt(0) + "." : "") + (!suffix.isEmpty() ? " " + suffix : "");
    }
    
    public String getRfullname(){
        return Rlname + ", " + Rfname + (!Rmname.isEmpty() ? " " + Rmname.toUpperCase().charAt(0)+ "." : "") + (!Rsuffix.isEmpty() ? " " + Rsuffix : "");
    }
}
