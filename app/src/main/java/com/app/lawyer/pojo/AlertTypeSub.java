package com.app.lawyer.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class AlertTypeSub implements Parcelable
{
    private String alert_type_cd;
    private String alert_type_name;
    private String case_cd;
    private String case_subject;
    private String court_name;
    private String judgement;

    public AlertTypeSub() {

    }

    public String getAlert_type_cd() {
        return alert_type_cd;
    }

    public void setAlert_type_cd(String alert_type_cd) {
        this.alert_type_cd = alert_type_cd;
    }

    public String getAlert_type_name() {
        return alert_type_name;
    }

    public void setAlert_type_name(String alert_type_name) {
        this.alert_type_name = alert_type_name;
    }

    public String getCase_cd() {
        return case_cd;
    }

    public void setCase_cd(String case_cd) {
        this.case_cd = case_cd;
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

    public String getJudgement() {
        return judgement;
    }

    public void setJudgement(String judgement) {
        this.judgement = judgement;
    }

    protected AlertTypeSub(Parcel in) {
        alert_type_cd = in.readString();
        alert_type_name = in.readString();
        case_cd = in.readString();
        case_subject = in.readString();
        court_name = in.readString();
        judgement = in.readString();
    }

    public static final Creator<AlertTypeSub> CREATOR = new Creator<AlertTypeSub>() {
        @Override
        public AlertTypeSub createFromParcel(Parcel in) {
            return new AlertTypeSub(in);
        }

        @Override
        public AlertTypeSub[] newArray(int size) {
            return new AlertTypeSub[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(alert_type_cd);
        dest.writeString(alert_type_name);
        dest.writeString(case_cd);
        dest.writeString(case_subject);
        dest.writeString(court_name);
        dest.writeString(judgement);
    }
}
