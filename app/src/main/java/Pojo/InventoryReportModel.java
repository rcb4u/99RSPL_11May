package Pojo;


public class InventoryReportModel {

    String Prod_Id;
    String Prod_Nm;
    String Batch;
    String Expiry;
    String Quantity;
    String Days_Left;

    public Float getTotal() {
        return Total;
    }

    public void setTotal(Float total) {
        Total = total;
        Total = Float.parseFloat(Quantity)*Float.parseFloat(PPrice);
    }

    Float Total;


    public String getPPrice() {
        return PPrice;
    }

    public void setPPrice(String PPrice) {
        this.PPrice = PPrice;
    }

    String PPrice;

    public String getDstrNm() {
        return DstrNm;
    }

    public void setDstrNm(String dstrNm) {
        DstrNm = dstrNm;
    }

    String DstrNm;

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    String InvoiceNo;

    public String getUser_Nm() {
        return User_Nm;
    }

    public void setUser_Nm(String user_Nm) {
        User_Nm = user_Nm;
    }

    String User_Nm;

    public String getDays_Left() {
        return Days_Left;
    }

    public void setDays_Left(String days_Left) {
        Days_Left = days_Left;
    }

    public String getProd_Id() {
        return Prod_Id;
    }

    public void setProd_Id(String prod_Id) {
        Prod_Id = prod_Id;
    }

    public String getProd_Nm() {
        return Prod_Nm;
    }

    public void setProd_Nm(String prod_Nm) {
        Prod_Nm = prod_Nm;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getExpiry() {
        return Expiry;
    }

    public void setExpiry(String expiry) {
        Expiry = expiry;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    @Override
    public String toString() {
        return Prod_Nm;
    }
}