package Pojo;


public class SaleReportModel {

    String TransId;
    String GrnTotl;
    String CardNo;
    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }


    String CardType;

    public String getBankNm() {
        return BankNm;
    }

    public void setBankNm(String bankNm) {
        BankNm = bankNm;
    }

    String BankNm;

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }

    String Credit;

    public float getPROFIT() {
        return PROFIT;
    }

    public void setPROFIT(float profit) {
        PROFIT=profit;
        PROFIT = SPrice - PPrice;
    }

    float PROFIT;

    public String getProdNm() {
        return ProdNm;
    }

    public void setProdNm(String prodNm) {
        ProdNm = prodNm;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getExp() {
        return Exp;
    }

    public void setExp(String exp) {
        Exp = exp;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getMrp() {
        return Mrp;
    }

    public void setMrp(String mrp) {
        Mrp = mrp;
    }

    String ProdNm;
    String Batch;
    String Exp;
    String Price;
    String Qty;
    public String getTotalAfterDscnt() {
        return TotalAfterDscnt;
    }

    public void setTotalAfterDscnt(String totalAfterDscnt) {
        TotalAfterDscnt = totalAfterDscnt;
    }

    String TotalAfterDscnt;

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    String Discount;

    public Float getSPrice() {
        return SPrice;
    }

    public void setSPrice(Float SPrice) {
        this.SPrice = SPrice;
    }

    public Float getPPrice() {
        return PPrice;
    }

    public void setPPrice(Float PPrice) {
        this.PPrice = PPrice;
    }

    Float SPrice;
    Float PPrice;

    public String getUserNm() {
        return UserNm;
    }

    public void setUserNm(String userNm) {
        UserNm = userNm;
    }

    String UserNm;

    public String getSaleDate() {
        return SaleDate;
    }

    public void setSaleDate(String saleDate) {
        SaleDate = saleDate;
    }

    String SaleDate;

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    String Uom;
    String Mrp;

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public String getGrnTotl() {
        return GrnTotl;
    }

    public void setGrnTotl(String grnTotl) {
        GrnTotl = grnTotl;
    }

    @Override
    public String toString() {
        return  GrnTotl;
    }



}


/*

package Pojo;


public class SaleReportModel {

    String TransId;
    String GrnTotl;
    String CardNo;

    public String getTotalAfterDscnt() {
        return TotalAfterDscnt;
    }

    public void setTotalAfterDscnt(String totalAfterDscnt) {
        TotalAfterDscnt = totalAfterDscnt;
    }

    String TotalAfterDscnt;

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    String Discount;

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }


    String CardType;

    public String getBankNm() {
        return BankNm;
    }

    public void setBankNm(String bankNm) {
        BankNm = bankNm;
    }

    String BankNm;

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }

    String Credit;

    public float getPROFIT() {
        return PROFIT;
    }

    public void setPROFIT(float profit) {
        PROFIT=profit;
       PROFIT = SPrice - PPrice;
    }

    float PROFIT;

    public String getProdNm() {
        return ProdNm;
    }

    public void setProdNm(String prodNm) {
        ProdNm = prodNm;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getExp() {
        return Exp;
    }

    public void setExp(String exp) {
        Exp = exp;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getMrp() {
        return Mrp;
    }

    public void setMrp(String mrp) {
        Mrp = mrp;
    }

    String ProdNm;
    String Batch;
    String Exp;
    String Price;
    String Qty;

    public Float getSPrice() {
        return SPrice;
    }

    public void setSPrice(Float SPrice) {
        this.SPrice = SPrice;
    }

    public Float getPPrice() {
        return PPrice;
    }

    public void setPPrice(Float PPrice) {
        this.PPrice = PPrice;
    }

    Float SPrice;
    Float PPrice;

    public String getUserNm() {
        return UserNm;
    }

    public void setUserNm(String userNm) {
        UserNm = userNm;
    }

    String UserNm;

    public String getSaleDate() {
        return SaleDate;
    }

    public void setSaleDate(String saleDate) {
        SaleDate = saleDate;
    }

    String SaleDate;

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    String Uom;
    String Mrp;

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public String getGrnTotl() {
        return GrnTotl;
    }

    public void setGrnTotl(String grnTotl) {
        GrnTotl = grnTotl;
    }

    @Override
    public String toString() {
        return  GrnTotl;
    }



}*/
