
package com.app.amanrow.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class CaseCS implements Parcelable {

    @SerializedName("branch_no")
    private String branchNo;
    @SerializedName("case_cd")
    private String caseCd;
    @SerializedName("case_date")
    private String caseDate;
    @SerializedName("case_subject")
    private String caseSubject;
    @SerializedName("case_type_cd")
    private String caseTypeCd;
    @SerializedName("case_type_name")
    private String caseTypeName;
    @SerializedName("case_year")
    private String caseYear;
    @SerializedName("defendant")
    private String defendant;
    @SerializedName("lawsuit_no")
    private String lawsuitNo;
    @SerializedName("lawsuit_type_cd")
    private String lawsuitTypeCd;
    @SerializedName("lawsuit_type_name")
    private String lawsuitTypeName;
    @SerializedName("level_cd")
    private String levelCd;
    @SerializedName("level_name")
    private String levelName;
    @SerializedName("level_type_cd")
    private String levelTypeCd;
    @SerializedName("level_type_name")
    private String levelTypeName;
    @SerializedName("plaintiff")
    private String plaintiff;
    @SerializedName("status_cd")
    private String statusCd;
    @SerializedName("status_hd_name")
    private String statusHdName;
    @SerializedName("status_name")
    private String statusName;
    @SerializedName("tc_next_hearing_date")
    private String tcNextHearingDate;
    @SerializedName("tc_progres_ser_no")
    private String tcProgresSerNo;
    @SerializedName("unique_number")
    private String uniqueNumber;
    @SerializedName("user_cd")
    private String userCd;

    public CaseCS() {

    }

    protected CaseCS(Parcel in) {
        branchNo = in.readString();
        caseCd = in.readString();
        caseDate = in.readString();
        caseSubject = in.readString();
        caseTypeCd = in.readString();
        caseTypeName = in.readString();
        caseYear = in.readString();
        defendant = in.readString();
        lawsuitNo = in.readString();
        lawsuitTypeCd = in.readString();
        lawsuitTypeName = in.readString();
        levelCd = in.readString();
        levelName = in.readString();
        levelTypeCd = in.readString();
        levelTypeName = in.readString();
        plaintiff = in.readString();
        statusCd = in.readString();
        statusHdName = in.readString();
        statusName = in.readString();
        tcNextHearingDate = in.readString();
        tcProgresSerNo = in.readString();
        uniqueNumber = in.readString();
        userCd = in.readString();
    }

    public static final Creator<CaseCS> CREATOR = new Creator<CaseCS>() {
        @Override
        public CaseCS createFromParcel(Parcel in) {
            return new CaseCS(in);
        }

        @Override
        public CaseCS[] newArray(int size) {
            return new CaseCS[size];
        }
    };

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getCaseCd()
    {
        if(caseCd==null)
        {
            return "";
        }
        return caseCd;
    }

    public void setCaseCd(String caseCd) {
        this.caseCd = caseCd;
    }

    public String getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(String caseDate) {
        this.caseDate = caseDate;
    }

    public String getCaseSubject() {
        return caseSubject;
    }

    public void setCaseSubject(String caseSubject) {
        this.caseSubject = caseSubject;
    }

    public String getCaseTypeCd() {
        return caseTypeCd;
    }

    public void setCaseTypeCd(String caseTypeCd) {
        this.caseTypeCd = caseTypeCd;
    }

    public String getCaseTypeName() {
        if(caseTypeName==null)
        {
            return "";
        }
        return caseTypeName;
    }

    public void setCaseTypeName(String caseTypeName) {
        this.caseTypeName = caseTypeName;
    }

    public String getCaseYear() {
        return caseYear;
    }

    public void setCaseYear(String caseYear) {
        this.caseYear = caseYear;
    }

    public String getDefendant() {
        if(defendant==null)
        {
            return "";
        }
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getLawsuitNo() {
        if(lawsuitNo==null)
        {
            return "";
        }
        return lawsuitNo;
    }

    public void setLawsuitNo(String lawsuitNo) {
        this.lawsuitNo = lawsuitNo;
    }

    public String getLawsuitTypeCd() {
        return lawsuitTypeCd;
    }

    public void setLawsuitTypeCd(String lawsuitTypeCd) {
        this.lawsuitTypeCd = lawsuitTypeCd;
    }

    public String getLawsuitTypeName() {
        return lawsuitTypeName;
    }

    public void setLawsuitTypeName(String lawsuitTypeName) {
        this.lawsuitTypeName = lawsuitTypeName;
    }

    public String getLevelCd() {
        return levelCd;
    }

    public void setLevelCd(String levelCd) {
        this.levelCd = levelCd;
    }

    public String getLevelName() {
        if(levelName==null)
        {
            return "";
        }
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelTypeCd() {
        return levelTypeCd;
    }

    public void setLevelTypeCd(String levelTypeCd) {
        this.levelTypeCd = levelTypeCd;
    }

    public String getLevelTypeName() {
        return levelTypeName;
    }

    public void setLevelTypeName(String levelTypeName) {
        this.levelTypeName = levelTypeName;
    }

    public String getPlaintiff() {
        if(plaintiff==null)
        {
            return "";
        }
        return plaintiff;
    }

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusHdName() {
        return statusHdName;
    }

    public void setStatusHdName(String statusHdName) {
        this.statusHdName = statusHdName;
    }

    public String getStatusName()
    {
        if(statusName==null)
        {
            return "";
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTcNextHearingDate() {
        return tcNextHearingDate;
    }

    public void setTcNextHearingDate(String tcNextHearingDate) {
        this.tcNextHearingDate = tcNextHearingDate;
    }

    public String getTcProgresSerNo() {
        return tcProgresSerNo;
    }

    public void setTcProgresSerNo(String tcProgresSerNo) {
        this.tcProgresSerNo = tcProgresSerNo;
    }

    public String getUniqueNumber() {
        if(uniqueNumber==null)
        {
            return "";
        }
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(branchNo);
        dest.writeString(caseCd);
        dest.writeString(caseDate);
        dest.writeString(caseSubject);
        dest.writeString(caseTypeCd);
        dest.writeString(caseTypeName);
        dest.writeString(caseYear);
        dest.writeString(defendant);
        dest.writeString(lawsuitNo);
        dest.writeString(lawsuitTypeCd);
        dest.writeString(lawsuitTypeName);
        dest.writeString(levelCd);
        dest.writeString(levelName);
        dest.writeString(levelTypeCd);
        dest.writeString(levelTypeName);
        dest.writeString(plaintiff);
        dest.writeString(statusCd);
        dest.writeString(statusHdName);
        dest.writeString(statusName);
        dest.writeString(tcNextHearingDate);
        dest.writeString(tcProgresSerNo);
        dest.writeString(uniqueNumber);
        dest.writeString(userCd);
    }
}
