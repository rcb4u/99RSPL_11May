package Pojo;

/**
 * Created by rspl-gourav on 23/5/16.
 */
public class line_item_Product_Model {

    String ProductLineId;
    String ProductLinename;
    String discountLineitem;
    String  LineActive;




    public String getProductLinename() {
        return ProductLinename;
    }

    public void setProductLinename(String productLinename) {
        ProductLinename = productLinename;
    }

    public String getDiscountLineitem() {
        return discountLineitem;
    }

    public void setDiscountLineitem(String discountLineitem) {
        this.discountLineitem = discountLineitem;
    }




    public String getProductLineId() {
        return ProductLineId;
    }

    public void setProductLineId(String productLineId) {
        ProductLineId = productLineId;
    }



    public String getLineActive() {
        return LineActive;
    }

    public void setLineActive(String lineActive) {
        LineActive = lineActive;
    }


    @Override
    public String toString() {
        return ProductLinename;
    }
}
