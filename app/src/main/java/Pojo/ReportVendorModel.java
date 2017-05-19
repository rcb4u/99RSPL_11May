package Pojo;


public class ReportVendorModel {
    String Vend_Nm;
    String Active;

    public String getUserNm() {
        return UserNm;
    }

    public void setUserNm(String userNm) {
        UserNm = userNm;
    }

    String UserNm;


    public String getVend_Nm() {
        return Vend_Nm;
    }

    public void setVend_Nm(String vend_Nm) {
        Vend_Nm = vend_Nm;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    @Override
    public String toString() {
        return Vend_Nm;
    }
}
