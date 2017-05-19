package Pojo;

import android.util.Log;

public class InventoryStockadjustmentmodel {
    String ProductName;
    String ProductId;
    float mrp;
    float Sprice;
    float pprice;
    String tax;
    String expdate;
    String batchno;
    public int productQuantity=1;
    String bill;
    float Total;
    float GrandTotal;
    float stock;
    String Barcode;
    float convfact;
    float productmargin;
    String Industry;
    float ConvMrp;
    float EditQty;
    Double StockQty;
    String TotalADj;

    public String getTotalADj() {
        return TotalADj;
    }

    public void setTotalADj(String totalADj) {
        TotalADj = totalADj;
    }

    public Double getStockQty() {
        return StockQty;
    }

    public void setStockQty(Double stockQty) {
        StockQty = stockQty;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    String MRP;

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    String Days;

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }



    public void setMrp(String mrp) {
        Mrp = mrp;
    }

    String Qty;
    String Mrp;

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    String Active;


    public float getConvMrp() {
        return ConvMrp;
    }

    public void setConvMrp(float convMrp) {
        ConvMrp = convMrp;
    }



    public float getEditQty() {
        return EditQty;
    }

    public void setEditQty(float editQty) {
        EditQty = editQty;
    }

    public float getPurchseunitconv() {
        return purchseunitconv;
    }

    public void setPurchseunitconv(float purchseunitconv) {
        this.purchseunitconv = purchseunitconv;
    }

    float purchseunitconv;


    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public float getProductmargin() {
        return productmargin;
    }
    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }



    public float getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(float grandTotal) {
        GrandTotal = grandTotal;
    }

    public float getTotal() {
        return Total;
    }
    public void setTotal(float total) {
        Total=total;
        Total = productQuantity* mrp;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {

        this.batchno = batchno;
    }


    public String getExpdate() {
        return expdate;
    }
    public void setExpdate(String expdate) {
        this.expdate = expdate;


    }


    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;

        try {
            Total = productQuantity * pprice;
        } catch( Exception e) {

        }

        Log.i("QUANTITY", "set productQuantity to " + productQuantity + " for " + ProductName);
    }




    public float getMrp() {
        return mrp;
    }

    public void setMrp(float Mrp) {

        mrp=Mrp;
        try {
            productmargin  =((mrp-pprice)/mrp)*100;
        } catch( Exception e) {

        }

    }

    public float getSprice() {
        return Sprice;
    }

    public void setSprice(float sprice) {
        Sprice = sprice;
    }


    public float getPprice() {
        return pprice;
    }

    public void setPprice(float pprice) {
        this.pprice = pprice;
        try {
            Total = productQuantity * pprice;
            productmargin  =((mrp-pprice)/mrp)*100;
        } catch( Exception e) {

        }
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }


    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public InventoryStockadjustmentmodel() {
        this.productQuantity = 1;
        this.EditQty=0;
        this.expdate="";
        this.batchno="";
        this.Total=0.0f;

    }

    @Override
    public String toString() {
        return  ProductName  ;
    }
}