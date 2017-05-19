package Pojo;

/**
 * Created by shilpa on 13/7/16.
 */
public class CreditCustomer
{
    String creditcustname;
    String creditcustgrandtotal;
    String creditcustinvoiceno;
    String Creditdueamount;
    String MobileNo;
    String Total;


    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }



    public String getCreditdueamount() {
        return Creditdueamount;
    }

    public void setCreditdueamount(String creditdueamount) {
        Creditdueamount = creditdueamount;
    }


    public CreditCustomer(String creditcustname, String creditcustgrandtotal, String creditcustinvoiceno) {
        this.creditcustname = creditcustname;
        this.creditcustgrandtotal = creditcustgrandtotal;
        this.creditcustinvoiceno = creditcustinvoiceno;
    }

    public  CreditCustomer(){

    }

    public String getCreditcustname() {
        return creditcustname;
    }

    public void setCreditcustname(String creditcustname) {
        this.creditcustname = creditcustname;
    }

    public String getCreditcustgrandtotal() {
        return creditcustgrandtotal;
    }

    public void setCreditcustgrandtotal(String creditcustgrandtotal) {
        this.creditcustgrandtotal = creditcustgrandtotal;
    }

    public String getCreditcustinvoiceno() {
        return creditcustinvoiceno;
    }

    public void setCreditcustinvoiceno(String creditcustinvoiceno) {
        this.creditcustinvoiceno = creditcustinvoiceno;
    }




    @Override
    public String toString() {
        return creditcustname;

    }
}