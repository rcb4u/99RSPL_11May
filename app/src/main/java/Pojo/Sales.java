package Pojo;

import java.text.DecimalFormat;

/**
 * Created by shilpa on 18/3/16.
 */
public class
Sales {
    String ProductName;
    String BatchNo;
    String Expiry;
    String PPrice;
    float SPrice;
    int  Quantity;
    String Bill_No;
    float Mrp;
    String Amount;
    String Uom;
    String Prodid;
    float Stockquant;
    float Total;
    float GrandTotal;
    String Trans_id;
    String holdtotal;
    float holdstock;
    String topproductName;
    String storeid;
    String ProductshortName;
    String ProductId;
    Float Conversionfacter;
    Float searchsprice;
    float searchmrp;
    float searchtotal;
    String Barcode;
    float Productmargin;
    String date;
    String Payment_Id;
    String Mobile_No;
    String Industry;
    String ExpDate;
    float Discountsales;
    float Discountamount;
    float Pprice;
    float Discountamountsalestotal;

 /////////////////////////////////////////

    float SaveTOTALBIllDiscount;
    float SaveTOTALBILLAMOUNT;
    float SaveFINALBILLAMOUNT;
    float SaveRECEIVEDBILLAMOUNT;

    float SaveEXPECTEDBILLAMOUNT;


    String cardHolderNumber;
    String cardGrandTotal;

    String salesTime;
    String CustomerName;


    ///////////////////////////
    public Sales() {

        this.Quantity = 1;
        this.SPrice = 1.0F;
        this.BatchNo = "Batch";
        this.ExpDate="select date";
    }

    public Sales(String productName, String batchNo, String expiry, Float Sprice, int quantity, float mrp, String amount, String Uom, Float grandtotal, Float conversionfacter) {
        ProductName = productName;
        BatchNo = batchNo;
        Expiry = expiry;
        PPrice = PPrice;
        SPrice = Sprice;
        Quantity = quantity;
        Mrp = mrp;
        Amount = amount;
        this.Uom = Uom;
        GrandTotal = grandtotal;
        Conversionfacter = conversionfacter;
    }


    public float getDiscountsales() {
        return Discountsales;
    }

    public void setDiscountsales(float discountsales) {
        Discountsales = discountsales;
        try {
            if (Discountsales == 0.00)
            {
                Discountamount = 0.0f;
                Discountamountsalestotal = 0.0f;

            }
            else {
                Discountamount = (Total - (Total * (Discountsales / 100)));
                Discountamountsalestotal =  (Total * (Discountsales / 100));

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public float getDiscountamountsalestotal() {
        return Discountamountsalestotal;
    }

    public void setDiscountamountsalestotal(float discountamountsalestotal) {

        Discountamountsalestotal = discountamountsalestotal;
        try {
            if (Discountsales == 0.00)
            {
                Discountamountsalestotal = 0.0f;
            }
            else {
                Discountamountsalestotal =  (Total * (Discountsales / 100));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public float getDiscountamount() {
        return Discountamount;
    }

    public void setDiscountamount(float discountamount) {
        Discountamount = discountamount;

        DecimalFormat f = new DecimalFormat("##.0");
        try {

            if (Discountsales == 0.00)
            {
                Discountamount = 0.0f;

            }
            else {

                Discountamount = (Total - (Total * (Discountsales / 100)));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPayment_Id() {
        return Payment_Id;
    }

    public void setPayment_Id(String payment_Id) {
        Payment_Id = payment_Id;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getProductmargin() {
        return Productmargin;
    }

    public void setProductmargin(float productmargin) {
        Productmargin = productmargin;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }



    public float getPprice() {
        return Pprice;
    }

    public void setPprice(float pprice) {
        Pprice = pprice;
    }


    public String getExpDate() {
        return ExpDate;
    }

    public void setExpDate(String expDate) {
        ExpDate = expDate;
    }



    public Float getConversionfacter() {
        return Conversionfacter;
    }

    public void setConversionfacter(Float conversionfacter) {
        Conversionfacter = conversionfacter;
        DecimalFormat f = new DecimalFormat("##.0");
        try {
            // Mrp=Mrp/ Conversionfacter;
            //SPrice=SPrice/ Conversionfacter;
            // Stockquant=Stockquant*Conversionfacter;
            Total = SPrice * Quantity;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public float getSearchsprice() {
        return searchsprice;
    }

    public void setSearchsprice(float searchsprice) {
        this.searchsprice = searchsprice;
        try {
            searchtotal = Quantity * searchsprice;
//            searchsprice = searchsprice / Conversionfacter;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public float getSearchmrp() {
        return searchmrp;
    }

    public void setSearchmrp(float searchmrp) {
        this.searchmrp = searchmrp;
        try {
//            searchmrp = searchmrp / Conversionfacter;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getSearchtotal() {
        return searchtotal;
    }

    public void setSearchtotal(float searchtotal) {
        this.searchtotal = searchtotal;
        try {
            searchtotal = Quantity * searchsprice;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getResstock() {
        return Resstock;
    }

    public void setResstock(int resstock) {
        Resstock = resstock;
    }

    int Resstock;


    public String getTopproductName() {
        return topproductName;
    }

    public void setTopproductName(String topproductName) {
        this.topproductName = topproductName;
    }


    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }


    public String getProductshortName() {
        return ProductshortName;
    }

    public void setProductshortName(String productshortName) {
        ProductshortName = productshortName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }


    public float getHoldstock() {
        return holdstock;
    }

    public void setHoldstock(float holdstock) {
        this.holdstock = holdstock;
    }

    public String getBill_No() {
        return Bill_No;
    }

    public String getTrans_id() {
        return Trans_id;
    }

    public String getHoldtotal() {
        return holdtotal;
    }

    public void setHoldtotal(String holdtotal) {
        this.holdtotal = holdtotal;
    }


    public void setTrans_id(String trans_id) {
        Trans_id = trans_id;
    }

    public void setBill_No(String bill_No) {
        Bill_No = bill_No;
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
        Total = total;
        DecimalFormat f = new DecimalFormat("##.0");
        // Mrp=Mrp/Conversionfacter;
        // SPrice=SPrice/Conversionfacter;
        Total = SPrice * Quantity;
        if (Discountsales == 0.00)
        {
            Discountamount = 0.0f;
        }
        else {
            Discountamount = (SPrice*Quantity - (SPrice*Quantity * (Discountsales / 100)));
        }
    }

    public String getProdid() {
        return Prodid;
    }

    public void setProdid(String prodid) {
        Prodid = prodid;
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getExpiry() {
        return Expiry;
    }

    public void setExpiry(String expiry) {
        Expiry = expiry;
    }

    public String getPPrice() {
        return PPrice;
    }

    public void setPPrice(String PPrice) {
        this.PPrice = PPrice;
    }

    public float getSPrice() {

        return SPrice;
    }

    public float getStockquant() {
        return Stockquant;
    }

    public void setStockquant(float stockquant) {

        Stockquant = stockquant;
        try {
            //Stockquant=Stockquant*Conversionfacter;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setSPrice(float SPrice) {
        this.SPrice = SPrice;
        try {
            DecimalFormat f = new DecimalFormat("##.0");
            // SPrice=SPrice/ Conversionfacter;
            // Mrp=Mrp/Conversionfacter;
            Total = SPrice * Quantity;
            if (Discountsales == 0.00)
            {
                Discountamount = 0.0f;

            }
            else {

                Discountamount = (SPrice*Quantity - (SPrice*Quantity * (Discountsales / 100)));
            }
        } catch (Exception e) {

        }
    }

    public int getQuantity() {

        return Quantity;
    }

    public void setQuantity(int quantity) {

        Quantity = quantity;
        Total = SPrice * Quantity;

        try {
            if (Discountsales == 0.00)
            {
                Discountamount = 0.0f;

            }
            else {

                Discountamount = (SPrice*Quantity - (SPrice*Quantity * (Discountsales / 100)));
            }
            // Mrp=Mrp/Conversionfacter;
            // SPrice=SPrice/Conversionfacter;

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public float getMrp() {
        try {
            //   Mrp=Mrp / Conversionfacter;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Mrp;
    }

    public void setMrp(float mrp) {
        Mrp = mrp;
        try {
            //Mrp= Mrp / Conversionfacter;
            // SPrice=SPrice/Conversionfacter;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    @Override
    public String toString() {
        return BatchNo;
    }




    public float getSaveEXPECTEDBILLAMOUNT() {
        return SaveEXPECTEDBILLAMOUNT;
    }

    public void setSaveEXPECTEDBILLAMOUNT(float saveEXPECTEDBILLAMOUNT) {
        SaveEXPECTEDBILLAMOUNT = saveEXPECTEDBILLAMOUNT;
    }

    public float getSaveRECEIVEDBILLAMOUNT() {
        return SaveRECEIVEDBILLAMOUNT;
    }

    public void setSaveRECEIVEDBILLAMOUNT(float saveRECEIVEDBILLAMOUNT) {
        SaveRECEIVEDBILLAMOUNT = saveRECEIVEDBILLAMOUNT;
    }

    public float getSaveFINALBILLAMOUNT() {
        return SaveFINALBILLAMOUNT;
    }

    public void setSaveFINALBILLAMOUNT(float saveFINALBILLAMOUNT) {
        SaveFINALBILLAMOUNT = saveFINALBILLAMOUNT;
    }

    public float getSaveTOTALBILLAMOUNT() {
        return SaveTOTALBILLAMOUNT;
    }

    public void setSaveTOTALBILLAMOUNT(float saveTOTALBILLAMOUNT) {
        SaveTOTALBILLAMOUNT = saveTOTALBILLAMOUNT;
    }
    public float getSaveTOTALBIllDiscount() {
        return SaveTOTALBIllDiscount;
    }

    public void setSaveTOTALBIllDiscount(float saveTOTALBIllDiscount) {
        SaveTOTALBIllDiscount = saveTOTALBIllDiscount;
    }

    public String getCardHolderNumber() {
        return cardHolderNumber;
    }

    public void setCardHolderNumber(String cardHolderNumber) {
        this.cardHolderNumber = cardHolderNumber;
    }

    public String getCardGrandTotal() {
        return cardGrandTotal;
    }

    public void setCardGrandTotal(String cardGrandTotal) {
        this.cardGrandTotal = cardGrandTotal;
    }

    ///////////////////////////////////////////

    public String getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(String salesTime) {
        this.salesTime = salesTime;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }


}