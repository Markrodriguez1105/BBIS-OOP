package dashboard;

import java.util.Date;

public class transaction {
    private String trans_type, fname, mname, lname;
    private Date date;

    public transaction(String trans_type, String fname, String mname, String lname, Date date) {
        this.trans_type = trans_type;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.date = date;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getFullname(){
        return lname + ", " + fname + (!mname.isEmpty() ? " " + mname.toUpperCase().charAt(0) + "." : "");
    }
    
    
    
}
