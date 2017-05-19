package Pojo;

import android.util.Log;

/**
 * Created by rspl-abhigyan on 21/6/16.
 */
public class VendorRejectModel {



    String Reasons;

    String Saleproductname;
    String Saleexpiry;
    String conversionfactorreturn;
    String mVId;
    String mVReason;
    String active;

    public String getmVId() {
        return mVId;
    }

    public void setmVId(String mVId) {
        this.mVId = mVId;
    }

    public String getmVReason() {
        return mVReason;
    }

    public void setmVReason(String mVReason) {
        this.mVReason = mVReason;
    }


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


    public String getSaleproductname() {
        return Saleproductname;
    }

    public void setSaleproductname(String saleproductname) {
        Saleproductname = saleproductname;
    }

    public String getSaleexpiry() {
        return Saleexpiry;
    }

    public void setSaleexpiry(String saleexpiry) {
        Saleexpiry = saleexpiry;
    }


    @Override
    public String toString() {
        return mVReason;
    }

}