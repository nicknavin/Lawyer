package com.app.amanrow.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ClientCaseData implements Parcelable
{
    private String attorny_office_name;

    private String case_cd;

    private String case_subject;

    private String company_cd;

    private String court_level_name;

    private String global_client_cd;

    private String lawsuit_number;

    private String next_hearing_date;
    private String data;


    private ArrayList<CasePrg> casePrgsList;

    private String status_name;

    private String unique_number;

    public ClientCaseData()
    {

    }

    protected ClientCaseData(Parcel in) {
        attorny_office_name = in.readString();
        case_cd = in.readString();
        case_subject = in.readString();
        company_cd = in.readString();
        court_level_name = in.readString();
        global_client_cd = in.readString();
        lawsuit_number = in.readString();
        next_hearing_date = in.readString();
        data = in.readString();
        status_name = in.readString();
        unique_number = in.readString();
    }

    public static final Creator<ClientCaseData> CREATOR = new Creator<ClientCaseData>() {
        @Override
        public ClientCaseData createFromParcel(Parcel in) {
            return new ClientCaseData(in);
        }

        @Override
        public ClientCaseData[] newArray(int size) {
            return new ClientCaseData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attorny_office_name);
        dest.writeString(case_cd);
        dest.writeString(case_subject);
        dest.writeString(company_cd);
        dest.writeString(court_level_name);
        dest.writeString(global_client_cd);
        dest.writeString(lawsuit_number);
        dest.writeString(next_hearing_date);
        dest.writeString(data);
        dest.writeString(status_name);
        dest.writeString(unique_number);
    }


    public String getAttorny_office_name()
    {
        if(attorny_office_name==null)
        {
            return "";
        }
        return attorny_office_name;
    }

    public void setAttorny_office_name(String attorny_office_name) {
        this.attorny_office_name = attorny_office_name;
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

    public String getCompany_cd() {
        return company_cd;
    }

    public void setCompany_cd(String company_cd) {
        this.company_cd = company_cd;
    }

    public String getCourt_level_name() {
        if(court_level_name==null)
        {
            return "";
        }
        return court_level_name;
    }

    public void setCourt_level_name(String court_level_name) {
        this.court_level_name = court_level_name;
    }

    public String getGlobal_client_cd() {
        return global_client_cd;
    }

    public void setGlobal_client_cd(String global_client_cd) {
        this.global_client_cd = global_client_cd;
    }

    public String getLawsuit_number() {
        if(lawsuit_number==null)
        {
            return "";
        }
        return lawsuit_number;
    }

    public void setLawsuit_number(String lawsuit_number) {
        this.lawsuit_number = lawsuit_number;
    }

    public String getNext_hearing_date() {
        return next_hearing_date;
    }

    public void setNext_hearing_date(String next_hearing_date) {
        this.next_hearing_date = next_hearing_date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<CasePrg> getCasePrgsList() {
        return casePrgsList;
    }

    public void setCasePrgsList(ArrayList<CasePrg> casePrgsList) {
        this.casePrgsList = casePrgsList;
    }

    public String getStatus_name()
    {
        if(status_name==null)
        {
            return "";
        }
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getUnique_number() {
        if(unique_number==null)
        {
            return "";
        }
        return unique_number;
    }

    public void setUnique_number(String unique_number) {
        this.unique_number = unique_number;
    }

    public static Creator<ClientCaseData> getCREATOR() {
        return CREATOR;
    }
}
