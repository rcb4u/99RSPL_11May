package Pojo;

/**
 * Created by Rahul on 5/20/2016.
 */
public class Topfullproductmodel {


    String ProductId;
    String Productname;
    String shortname;

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }




    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }


    public String getProductname() {
        return Productname;
    }

    public void setProductname(String productname) {
        Productname = productname;
    }




    @Override
    public String toString() {
        return Productname;
    }

}
