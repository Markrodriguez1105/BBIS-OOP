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

    private String id;
    private String fullName, cat, documentType, stats, rqstFname;
    private Date date;

    public document(String id, String fullName, String cat, String documentType, String stats, String rqstFname, Date date) {
        this.id = id;
        this.fullName = fullName;
        this.cat = cat;
        this.documentType = documentType;
        this.stats = stats;
        this.rqstFname = rqstFname;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getRqstFname() {
        return rqstFname;
    }

    public void setRqstFname(String rqstFname) {
        this.rqstFname = rqstFname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
}
