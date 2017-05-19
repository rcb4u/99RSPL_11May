package Pojo;

/**
 * Created by w7 on 1/26/2016.
 */
public class Customer {


    String customermobileno;
    String customername;
    String customeremail;
    String Customercredit;

    public String getCustomeradress() {
        return Customeradress;
    }

    public void setCustomeradress(String customeradress) {
        Customeradress = customeradress;
    }

    String Customeradress;
//    String credtinvoic;
//
//
//    public String getCredtinvoic() {
//        return credtinvoic;
//    }
//
//    public void setCredtinvoic(String credtinvoic) {
//        this.credtinvoic = credtinvoic;
//    }



    public String getCustomercredit() {
        return Customercredit;
    }

    public void setCustomercredit(String customercredit) {
        Customercredit = customercredit;
    }




    public Customer(String customermobileno, String customername,String customeremail,String Customercredit,String  customeradress) {
        this.customermobileno = customermobileno;
        this.customername = customername;
        this.customeremail=customeremail;
        this.Customercredit=Customercredit;
        this.Customeradress=customeradress;


    }

    public Customer() {

    }

    public String getCustomermobileno() {
        return customermobileno;
    }

    public void setCustomermobileno(String customermobileno) {
        this.customermobileno = customermobileno;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }


    public String getCustomeremail() {
        return customeremail;
    }

    public void setCustomeremail(String customeremail) {
        this.customeremail = customeremail;
    }
    @Override
    public String toString() {
        return customername;

    }

}


