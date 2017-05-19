package Pojo;

/**
 * Created by rspl-abhigyan on 21/6/16.
 */



public class BillLevelModel {

    String mBillName;
    String mBillType;
    String lastupdate;

    public String getBActive() {
        return BActive;
    }

    public void setBActive(String BActive) {
        this.BActive = BActive;
    }

    String BActive;

    public String getmVId() {
        return mVId;
    }

    public void setmVId(String mVId) {
        this.mVId = mVId;
    }

    String mVId;

    public String getmBillName() {
        return mBillName;
    }

    public void setmBillName(String mBillName) {
        this.mBillName = mBillName;
    }

    public String getmBillType() {
        return mBillType;
    }

    public void setmBillType(String mBillType) {
        this.mBillType = mBillType;
    }

    @Override
    public String toString() {
        return mBillName;
    }


    public BillLevelModel() {
        this.mBillName = mBillName;
        this.mBillType = mBillType;
        this.lastupdate = lastupdate;
    }
}
