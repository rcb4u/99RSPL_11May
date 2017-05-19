package Pojo;

/**
 * Created by Rahul on 3/25/2016.
 */
public class Inventorymodel {
    String VendorName;
    @Override
    public String toString() {
        return VendorName;
    }

    public Inventorymodel (String vendorName) {
        this.VendorName = vendorName;
    }

    public Inventorymodel() {
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }


}
