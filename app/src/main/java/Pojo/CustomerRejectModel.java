package Pojo;

/**
 * Created by rspl-abhigyan on 21/6/16.
 */
public class CustomerRejectModel {
    String mVId;
    String mVReason;
    String lastupdate;
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

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return mVReason;
    }

}
