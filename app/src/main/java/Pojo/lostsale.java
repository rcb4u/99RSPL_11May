package Pojo;

/**
 * Created by rspl-gourav on 28/3/17.
 */

public class lostsale {

    String Transid;
    String Salemrp;
    public Integer qty=1;
    float total;
    float GrandTotal;
    String SaleDate;
    String prodid;

    public String getTransid() {
        return Transid;
    }

    public void setTransid(String transid) {
        Transid = transid;
    }

    public String getSalemrp() {
        return Salemrp;
    }

    public void setSalemrp(String salemrp) {
        Salemrp = salemrp;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public float getSalesellingprice() {
        return Salesellingprice;
    }

    public void setSalesellingprice(float salesellingprice) {
        total= Salesellingprice  * qty;

        Salesellingprice = salesellingprice;

    }

    public String getSaleproductname() {
        return Saleproductname;
    }

    public void setSaleproductname(String saleproductname) {
        Saleproductname = saleproductname;
    }

    float Salesellingprice;
    String Saleproductname;


    public lostsale(){

        this.qty = 1;

    }



    public Integer getqty() {
        return qty;
    }

    public void setqty(Integer saleqty) {

        qty = saleqty;
        total= Salesellingprice  * qty;
    }

    public float gettotal() {
        return total;
    }

    public void settotal(float saletotal)
    {
        total = saletotal;
        try {
            total= Salesellingprice  * qty;

        }catch (Exception e){
            e.printStackTrace();
        }


    }






}