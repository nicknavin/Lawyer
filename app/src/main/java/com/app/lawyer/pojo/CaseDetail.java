package com.app.lawyer.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class CaseDetail implements Parcelable {
    private String case_cd;
    private String case_client_name;
    private String case_opponent_name;
    private String case_subject;
    private String court_name;
    private String court_time;
    private String court_date;
    private String progres_date;
    private String progres_description;
    private String progres_ser_no;

    public CaseDetail()
    {

    }


    protected CaseDetail(Parcel in) {
        case_cd = in.readString();
        case_client_name = in.readString();
        case_opponent_name = in.readString();
        case_subject = in.readString();
        court_name = in.readString();
        court_time = in.readString();
        court_date = in.readString();
        progres_date = in.readString();
        progres_description = in.readString();
        progres_ser_no = in.readString();
    }

    public static final Creator<CaseDetail> CREATOR = new Creator<CaseDetail>() {
        @Override
        public CaseDetail createFromParcel(Parcel in) {
            return new CaseDetail(in);
        }

        @Override
        public CaseDetail[] newArray(int size) {
            return new CaseDetail[size];
        }
    };

    public String getCase_cd() {
        return case_cd;
    }

    public void setCase_cd(String case_cd) {
        this.case_cd = case_cd;
    }

    public String getCase_client_name() {
        return case_client_name;
    }

    public void setCase_client_name(String case_client_name) {
        this.case_client_name = case_client_name;
    }

    public String getCase_opponent_name() {
        return case_opponent_name;
    }

    public void setCase_opponent_name(String case_opponent_name) {
        this.case_opponent_name = case_opponent_name;
    }

    public String getCase_subject() {
        return case_subject;
    }

    public void setCase_subject(String case_subject) {
        this.case_subject = case_subject;
    }

    public String getCourt_name() {
        return court_name;
    }

    public void setCourt_name(String court_name) {
        this.court_name = court_name;
    }

    public String getProgres_date() {
        return progres_date;
    }

    public void setProgres_date(String progres_date) {
        this.progres_date = progres_date;
    }

    public String getProgres_description() {
        return progres_description;
    }

    public void setProgres_description(String progres_description) {
        this.progres_description = progres_description;
    }

    public String getProgres_ser_no() {
        return progres_ser_no;
    }

    public void setProgres_ser_no(String progres_ser_no) {
        this.progres_ser_no = progres_ser_no;
    }

    public String getCourt_time() {
        return court_time;
    }

    public void setCourt_time(String court_time) {
        this.court_time = court_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCourt_date() {
        return court_date;
    }

    public void setCourt_date(String court_date) {
        this.court_date = court_date;
    }

    public static Creator<CaseDetail> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(case_cd);
        dest.writeString(case_client_name);
        dest.writeString(case_opponent_name);
        dest.writeString(case_subject);
        dest.writeString(court_name);
        dest.writeString(court_time);
        dest.writeString(court_date);
        dest.writeString(progres_date);
        dest.writeString(progres_description);
        dest.writeString(progres_ser_no);
    }
}
