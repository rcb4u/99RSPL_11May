package Pojo;

/**
 * Created by shilpa on 22/9/16.
 */
public class Settlecustmodel {
    String Name;
    String Phoneno;
    String Outstanding;
    String invoiceno;
    String creditdate;
    String credititem;
    String creditmrp;
    String creditsprice;
    String creditamount;
    String credittotal;
    String paid;




    public String getCredititem() {
        return credititem;
    }

    public void setCredititem(String credititem) {
        this.credititem = credititem;
    }

    public String getCreditmrp() {
        return creditmrp;
    }

    public void setCreditmrp(String creditmrp) {
        this.creditmrp = creditmrp;
    }

    public String getCreditsprice() {
        return creditsprice;
    }

    public void setCreditsprice(String creditsprice) {
        this.creditsprice = creditsprice;
    }

    public String getCreditamount() {
        return creditamount;
    }

    public void setCreditamount(String creditamount) {
        this.creditamount = creditamount;
    }

    public String getCredittotal() {
        return credittotal;
    }

    public void setCredittotal(String credittotal) {
        this.credittotal = credittotal;
    }





    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneno() {
        return Phoneno;
    }

    public void setPhoneno(String phoneno) {
        Phoneno = phoneno;
    }

    public String getOutstanding() {
        return Outstanding;
    }

    public void setOutstanding(String outstanding) {
        Outstanding = outstanding;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getCreditdate() {
        return creditdate;
    }

    public void setCreditdate(String creditdate) {
        this.creditdate = creditdate;
    }


    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }


    @Override
    public String toString() {
        return Name;

    }



}