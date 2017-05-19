package Pojo;

import android.util.Log;

/**
 * Created by rspl-rahul on 19/12/16.
 */

public class InventoryTotalProductModel {
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
    int freequantity;
    float totalqty;
    float purchaseunitconv;
    float invdiscount;
    String VendorName;
    String UOM;

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }


    ////////////////////////////////////////
    float Discountamount;
    float Discountamountsalestotal;






    public InventoryTotalProductModel() {
        Log.i("&&&&&&&&", "Constructor of InventoryTotalModel called ");
        this.productQuantity = 1;
        this.expdate="select date";
        this.batchno=" ";
        this.freequantity=0;
        this.Total=0.0f;
        this.pprice=0.0f;

    }

//    public float getDiscountamount() {
//        return Discountamount;
//    }
//
//    public void setDiscountamount(float discountamount) {
//        this.Discountamount = discountamount;
//        try {
//
//            if (invdiscount == 0.00)
//            {
//                Discountamount = 0.0f;
//
//            }
//            else {
//
//                Discountamount = (Total - (Total * (invdiscount / 100)));
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    public float getDiscountamountsalestotal() {
//        return Discountamountsalestotal;
//    }
//
//    public void setDiscountamountsalestotal(float discountamountsalestotal) {
//        this.Discountamountsalestotal = discountamountsalestotal;
//        try {
//            if (invdiscount == 0.00)
//            {
//                Discountamountsalestotal = 0.0f;
//            }
//            else {
//                Discountamountsalestotal =  (Total * (invdiscount / 100));
//            }
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//
//
//
//    }

    public float getInvdiscount() {
        return invdiscount;
    }

    public void setInvdiscount(float invdiscount) {
        this.invdiscount = invdiscount;


        invdiscount = (mrp - pprice )* 100 / mrp;
        Total = (pprice * productQuantity - (pprice * productQuantity * (invdiscount / 100)));


       /* if (invdiscount == 0.00)
        {
            Total=productQuantity*pprice;


        }
        else {

            Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));
        }*/
    }



    public float getPurchaseunitconv() {
        return purchaseunitconv;
    }

    public void setPurchaseunitconv(float purchaseunitconv) {
        this.purchaseunitconv = purchaseunitconv;
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

    public float getMrp() {
        return mrp;
    }

    public void setMrp(float mrp) {
        this.mrp = mrp;
        invdiscount=(mrp-pprice)*100/mrp;
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
        invdiscount=(mrp-pprice)*100/mrp;

        Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));

    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;

        try {
            totalqty= ((productQuantity+freequantity)* convfact);
        } catch( Exception e) {

        }

        Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));

        /*if (invdiscount == 0.00)
        {
            Total=productQuantity*pprice;


        }
        else {

            Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));
        }*/
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        this.Total = total;
         Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));

    }

    public float getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(float grandTotal) {
        GrandTotal = grandTotal;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public float getConvfact() {
        return convfact;
    }

    public void setConvfact(float convfact) {
        this.convfact = convfact;
    }

    public float getProductmargin() {
        return productmargin;
    }

    public void setProductmargin(float productmargin) {
        this.productmargin = productmargin;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public int getFreequantity() {
        return freequantity;
    }

    public void setFreequantity(int freequantity) {
        this.freequantity = freequantity;
        try {
            totalqty= ((productQuantity+freequantity)* convfact);        } catch( Exception e) {

        }
    }

    public float getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(float totalqty) {

        this.totalqty = totalqty;
        try {
            totalqty= ((productQuantity+freequantity)* convfact);        } catch( Exception e) {

        }
    }


    @Override
    public String toString() {
        return "Name:" + ProductName + " , Quantity:" + productQuantity   ;
    }
}

/*
package Pojo;

import android.util.Log;

*/
/**
 * Created by rspl-rahul on 19/12/16.
 *//*


public class InventoryTotalProductModel {
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
    int freequantity;
    float totalqty;
    float purchaseunitconv;
    float invdiscount;

////////////////////////////////////////
    float Discountamount;
    float Discountamountsalestotal;


    public InventoryTotalProductModel() {
        Log.i("&&&&&&&&", "Constructor of InventoryTotalModel called ");
        this.productQuantity = 1;
        this.expdate="select date";
        this.batchno=" ";
        this.freequantity=0;
        this.Total=0.0f;
        this.pprice=0.0f;

    }

//    public float getDiscountamount() {
//        return Discountamount;
//    }
//
//    public void setDiscountamount(float discountamount) {
//        this.Discountamount = discountamount;
//        try {
//
//            if (invdiscount == 0.00)
//            {
//                Discountamount = 0.0f;
//
//            }
//            else {
//
//                Discountamount = (Total - (Total * (invdiscount / 100)));
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    public float getDiscountamountsalestotal() {
//        return Discountamountsalestotal;
//    }
//
//    public void setDiscountamountsalestotal(float discountamountsalestotal) {
//        this.Discountamountsalestotal = discountamountsalestotal;
//        try {
//            if (invdiscount == 0.00)
//            {
//                Discountamountsalestotal = 0.0f;
//            }
//            else {
//                Discountamountsalestotal =  (Total * (invdiscount / 100));
//            }
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//
//
//
//    }

    public float getInvdiscount() {
        return invdiscount;
    }

    public void setInvdiscount(float invdiscount) {
        this.invdiscount = invdiscount;


            invdiscount = (mrp - pprice )* 100 / mrp;
            Total = (pprice * productQuantity - (pprice * productQuantity * (invdiscount / 100)));


       */
/* if (invdiscount == 0.00)
        {
            Total=productQuantity*pprice;


        }
        else {

            Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));
        }*//*

    }



    public float getPurchaseunitconv() {
        return purchaseunitconv;
    }

    public void setPurchaseunitconv(float purchaseunitconv) {
        this.purchaseunitconv = purchaseunitconv;
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

    public float getMrp() {
        return mrp;
    }

    public void setMrp(float mrp) {
        this.mrp = mrp;
        invdiscount=(mrp-pprice)*100/mrp;
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
        invdiscount=(mrp-pprice)*100/mrp;

            Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));

    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;

        Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));

        */
/*if (invdiscount == 0.00)
        {
            Total=productQuantity*pprice;


        }
        else {

            Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));
        }*//*

    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
      //  Total = (pprice*productQuantity - (pprice*productQuantity * (invdiscount / 100)));

    }

    public float getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(float grandTotal) {
        GrandTotal = grandTotal;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public float getConvfact() {
        return convfact;
    }

    public void setConvfact(float convfact) {
        this.convfact = convfact;
    }

    public float getProductmargin() {
        return productmargin;
    }

    public void setProductmargin(float productmargin) {
        this.productmargin = productmargin;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public int getFreequantity() {
        return freequantity;
    }

    public void setFreequantity(int freequantity) {
        this.freequantity = freequantity;
    }

    public float getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(float totalqty) {
        this.totalqty = totalqty;
    }


    @Override
    public String toString() {
        return "Name:" + ProductName + " , Quantity:" + productQuantity   ;
    }
}
*/
