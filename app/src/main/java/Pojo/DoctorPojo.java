package Pojo;

/**
 * Created by rspl-nishant on 28/5/16.
 */
public class DoctorPojo {

    String DoctorName;
    String DoctorSpeciality;
    String DoctorQualification;
    String Doctid;
    String Active;


    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }


    public DoctorPojo(String doctid,String doctorName, String doctorQualification, String doctorSpeciality,String active) {

        Doctid = doctid;
        DoctorName = doctorName;
        DoctorQualification = doctorQualification;
        DoctorSpeciality = doctorSpeciality;
        Active=active;

    }



    public DoctorPojo() {

    }

    public String getDoctid() {
        return Doctid;
    }

    public void setDoctid(String doctid) {
        Doctid = doctid;
    }


    public String getDoctorQualification() {
        return DoctorQualification;
    }

    public void setDoctorQualification(String doctorQualification) {
        DoctorQualification = doctorQualification;
    }

    public String getDoctorSpeciality() {
        return DoctorSpeciality;
    }

    public void setDoctorSpeciality(String doctorSpeciality) {
        DoctorSpeciality = doctorSpeciality;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }






}
