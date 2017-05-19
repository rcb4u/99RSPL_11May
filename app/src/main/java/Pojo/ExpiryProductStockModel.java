package Pojo;

/**
 * Created by rspl-rahul on 21/1/17.
 */

public class ExpiryProductStockModel {
    String ProductName;
    float pprice;
    String expdate;
    String batchno;
    Double StockQty;

    public Double getStockQty() {
        return StockQty;
    }

    public void setStockQty(Double stockQty) {
        StockQty = stockQty;
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

    public float getPprice() {
        return pprice;
    }

    public void setPprice(float pprice) {
        this.pprice = pprice;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    @Override
    public String toString() {
        return  ProductName  ;
    }
}
