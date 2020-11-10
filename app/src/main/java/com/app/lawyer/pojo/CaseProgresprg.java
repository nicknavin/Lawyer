
package com.app.lawyer.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class CaseProgresprg implements Parcelable {

    @SerializedName("branch_no")
    private String branchNo;
    @SerializedName("case_cd")
    private String caseCd;
    @SerializedName("comment")
    private String comment;
    @SerializedName("court_cd")
    private String courtCd;
    @SerializedName("court_date")
    private String courtDate;
    @SerializedName("court_date_v")
    private String courtDateV;
    @SerializedName("court_name")
    private String courtName;
    @SerializedName("court_time")
    private String courtTime;
    @SerializedName("defense_note_url")
    private Object defenseNoteUrl;
    @SerializedName("floor_no")
    private String floorNo;
    @SerializedName("judgement_cd")
    private String judgementCd;
    @SerializedName("judgement_name")
    private String judgementName;
    @SerializedName("next_session_date")
    private Object nextSessionDate;
    @SerializedName("progres_ser_no")
    private String progresSerNo;
    @SerializedName("room_no")
    private String roomNo;
    private boolean expanded;
    public CaseProgresprg() {

    }


    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    protected CaseProgresprg(Parcel in) {
        branchNo = in.readString();
        caseCd = in.readString();
        comment = in.readString();
        courtCd = in.readString();
        courtDate = in.readString();
        courtDateV = in.readString();
        courtName = in.readString();
        courtTime = in.readString();
        floorNo = in.readString();
        judgementCd = in.readString();
        judgementName = in.readString();
        progresSerNo = in.readString();
        roomNo = in.readString();
    }

    public static final Creator<CaseProgresprg> CREATOR = new Creator<CaseProgresprg>() {
        @Override
        public CaseProgresprg createFromParcel(Parcel in) {
            return new CaseProgresprg(in);
        }

        @Override
        public CaseProgresprg[] newArray(int size) {
            return new CaseProgresprg[size];
        }
    };

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getCaseCd() {
        return caseCd;
    }

    public void setCaseCd(String caseCd) {
        this.caseCd = caseCd;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCourtCd() {
        return courtCd;
    }

    public void setCourtCd(String courtCd) {
        this.courtCd = courtCd;
    }

    public String getCourtDate() {
        return courtDate;
    }

    public void setCourtDate(String courtDate) {
        this.courtDate = courtDate;
    }

    public String getCourtDateV() {
        return courtDateV;
    }

    public void setCourtDateV(String courtDateV) {
        this.courtDateV = courtDateV;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getCourtTime() {
        return courtTime;
    }

    public void setCourtTime(String courtTime) {
        this.courtTime = courtTime;
    }

    public Object getDefenseNoteUrl() {
        return defenseNoteUrl;
    }

    public void setDefenseNoteUrl(Object defenseNoteUrl) {
        this.defenseNoteUrl = defenseNoteUrl;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getJudgementCd() {
        return judgementCd;
    }

    public void setJudgementCd(String judgementCd) {
        this.judgementCd = judgementCd;
    }

    public String getJudgementName() {
        return judgementName;
    }

    public void setJudgementName(String judgementName) {
        this.judgementName = judgementName;
    }

    public Object getNextSessionDate() {
        return nextSessionDate;
    }

    public void setNextSessionDate(Object nextSessionDate) {
        this.nextSessionDate = nextSessionDate;
    }

    public String getProgresSerNo() {
        return progresSerNo;
    }

    public void setProgresSerNo(String progresSerNo) {
        this.progresSerNo = progresSerNo;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(branchNo);
        dest.writeString(caseCd);
        dest.writeString(comment);
        dest.writeString(courtCd);
        dest.writeString(courtDate);
        dest.writeString(courtDateV);
        dest.writeString(courtName);
        dest.writeString(courtTime);
        dest.writeString(floorNo);
        dest.writeString(judgementCd);
        dest.writeString(judgementName);
        dest.writeString(progresSerNo);
        dest.writeString(roomNo);
    }
}
