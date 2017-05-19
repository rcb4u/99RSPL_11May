package Pojo;

/**
 * Created by rspl-gourav on 10/9/16.
 */
public class Decimal {

    String StoreId;
    String decimalmrp;
    String decimalpprice;
    String decimalsprice;
    String storeid;
    String holdpo;
    String holdinv;
    String holdsales;

    public String getColorbackround() {
        return colorbackround;
    }

    public void setColorbackround(String colorbackround) {
        this.colorbackround = colorbackround;
    }

    String colorbackround;

    public String getPurchaseholdno() {
        return purchaseholdno;
    }

    public void setPurchaseholdno(String purchaseholdno) {
        this.purchaseholdno = purchaseholdno;
    }

    public String getInventoryholdno() {
        return inventoryholdno;
    }

    public void setInventoryholdno(String inventoryholdno) {
        this.inventoryholdno = inventoryholdno;
    }


    String purchaseholdno;
    String inventoryholdno;


    public String getRoundofff() {
        return roundofff;
    }

    public void setRoundofff(String roundofff) {
        this.roundofff = roundofff;
    }

    String roundofff;

    public String getHoldpo() {
        return holdpo;
    }

    public void setHoldpo(String holdpo) {
        this.holdpo = holdpo;
    }

    public String getHoldinv() {
        return holdinv;
    }

    public void setHoldinv(String holdinv) {
        this.holdinv = holdinv;
    }

    public String getHoldsales() {
        return holdsales;
    }

    public void setHoldsales(String holdsales) {
        this.holdsales = holdsales;
    }






    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }



    public String getDecimalpprice() {
        return decimalpprice;
    }

    public void setDecimalpprice(String decimalpprice) {
        this.decimalpprice = decimalpprice;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getDecimalmrp() {
        return decimalmrp;
    }

    public void setDecimalmrp(String decimalmrp) {
        this.decimalmrp = decimalmrp;
    }

    public String getDecimalsprice() {
        return decimalsprice;
    }

    public void setDecimalsprice(String decimalsprice) {
        this.decimalsprice = decimalsprice;
    }





    public Decimal(){}

    public Decimal(String storeId, String mrp, String ppice, String sprice) {
        StoreId = storeId;
        decimalmrp = mrp;
        decimalpprice = ppice;
        decimalsprice = sprice;

    }



}