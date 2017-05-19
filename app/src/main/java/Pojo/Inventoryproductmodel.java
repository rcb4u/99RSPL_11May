package Pojo;

import android.util.Log;

/**
 * Created by jimmy on 26-03-2016.
 */
public class Inventoryproductmodel


{
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
    String Purchaseno;
    float ProductPrice;
    String Uom;
    String VendorName;
    String Exp_Date;
    String Batch_No;
    float conversion;
    String Industery;
    float Mrp;
    public  int discountitems;
    int invpodiscount;
    float holdtotal;

    public float getHoldtotal() {
        return holdtotal;
    }

    public void setHoldtotal(float holdtotal) {
        this.holdtotal = holdtotal;
    }



    public int getInvpodiscount() {
        return invpodiscount;
    }


    public void setInvpodiscount(int invpodiscount) {
        this.invpodiscount = invpodiscount;
    }

    public String getPurchaseno() {
        return Purchaseno;
    }

    public void setPurchaseno(String purchaseno) {
        Purchaseno = purchaseno;
    }

    public float getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(float productPrice) {
        ProductPrice = productPrice;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getExp_Date() {
        return Exp_Date;
    }

    public void setExp_Date(String exp_Date) {
        Exp_Date = exp_Date;
    }

    public String getBatch_No() {
        return Batch_No;
    }

    public void setBatch_No(String batch_No) {
        Batch_No = batch_No;
    }

    public float getConversion() {
        return conversion;
    }

    public void setConversion(float conversion) {
        this.conversion = conversion;
    }

    public String getIndustery() {
        return Industery;
    }

    public void setIndustery(String industery) {
        Industery = industery;
    }

    public int getDiscountitems() {
        return discountitems;
    }

    public void setDiscountitems(int discountitems) {
        this.discountitems = discountitems;
    }

    public int getInvdiscount() {
        return invdiscount;
    }

    public void setInvdiscount(int invdiscount) {
        this.invdiscount = invdiscount;
    }

    int invdiscount;

    public float getPurchaseunitconv() {
        return purchaseunitconv;
    }

    public void setPurchaseunitconv(float purchaseunitconv) {
        this.purchaseunitconv = purchaseunitconv;
    }

    float purchaseunitconv;


    public float getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(float totalqty) {
        this.totalqty = totalqty;
        try {



            totalqty = ((productQuantity+freequantity)* convfact);        } catch( Exception e) {

        }
    }



    public int getFreequantity() {
        return freequantity;
    }

    public void setFreequantity(int freequantity) {
        this.freequantity = freequantity;
        try {
            //     productQuantity = productQuantity +freequantity;



            totalqty = ((productQuantity+freequantity)* convfact);
        } catch( Exception e) {

        }
    }




    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }


    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }


    public float getProductmargin() {
        return productmargin;
    }

    public void setProductmargin(float productmargin) {
        this.productmargin = productmargin;
        try {
            productmargin  =((mrp-pprice)/mrp)*100;
        } catch( Exception e) {

        }
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }


    public float getConvfact() {
        return convfact;
    }
    public void setConvfact(float convfact) {
        this.convfact = convfact;
        try {



            totalqty = ((productQuantity+freequantity)* convfact);        } catch( Exception e) {

        }
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
            totalqty = ((productQuantity+freequantity)* convfact);
            //   productQuantity=productQuantity+freequantity;
        } catch( Exception e) {

        }

        Log.i("&&&&&&&&&", "set productQuantity to " + productQuantity + " for " + ProductName);
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


    public Inventoryproductmodel() {
        Log.i("&&&&&&&&", "Constructor of PurchaseProductModel called ");
        this.productQuantity = 1;
        this.expdate="select date";
        this.batchno=" ";


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
 * Created by jimmy on 26-03-2016.
 *//*

public class Inventoryproductmodel
{
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
    int invdiscount;

    public int getInvdiscount() {
        return invdiscount;
    }

    public void setInvdiscount(int invdiscount) {
        this.invdiscount = invdiscount;
    }



    public float getPurchaseunitconv() {
        return purchaseunitconv;
    }

    public void setPurchaseunitconv(float purchaseunitconv) {
        this.purchaseunitconv = purchaseunitconv;
    }




    public float getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(float totalqty) {
        this.totalqty = totalqty;
        try {
            totalqty = ((productQuantity+freequantity)* convfact);        } catch( Exception e) {
        }
    }



    public int getFreequantity() {
        return freequantity;
    }

    public void setFreequantity(int freequantity) {
        this.freequantity = freequantity;
        try {
       //     productQuantity = productQuantity +freequantity;
            totalqty = ((productQuantity+freequantity)* convfact);
        } catch( Exception e) {

        }
    }




    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }


    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }


    public float getProductmargin() {
        return productmargin;
    }

    public void setProductmargin(float productmargin) {
        this.productmargin = productmargin;
        try {
            productmargin  =((mrp-pprice)/mrp)*100;
        } catch( Exception e) {

        }
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }


    public float getConvfact() {
        return convfact;
    }
    public void setConvfact(float convfact) {
        this.convfact = convfact;
        try {



            totalqty = ((productQuantity+freequantity)* convfact);        } catch( Exception e) {

        }
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
            totalqty = ((productQuantity+freequantity)* convfact);
         //   productQuantity=productQuantity+freequantity;
        } catch( Exception e) {

        }

        Log.i("&&&&&&&&&", "set productQuantity to " + productQuantity + " for " + ProductName);
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


    public Inventoryproductmodel() {
        Log.i("&&&&&&&&", "Constructor of PurchaseProductModel called ");
       this.productQuantity = 1;
        this.expdate="select date";
        this.batchno=" ";
        this.freequantity=0;


    }


    @Override
    public String toString() {
        return "Name:" + ProductName + " , Quantity:" + productQuantity   ;
    }
}

*/
