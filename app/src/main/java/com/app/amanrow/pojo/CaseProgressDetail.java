package com.app.amanrow.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class CaseProgressDetail implements Parcelable
{
    private String branch_no;
    private String case_cd;
    private String comment;
    private String court_cd;
    private String court_date;
    private String court_date_v;
    private String court_name;
    private String court_time;
    private String defense_note_url;
    private String floor_no;
    private String judgement_cd;
    private String judgement_name;
    private String progres_ser_no;
    private String room_no;
    private String next_session_date;
    private boolean expanded;

    public CaseProgressDetail()
    {

    }

    protected CaseProgressDetail(Parcel in) {
        branch_no = in.readString();
        case_cd = in.readString();
        comment = in.readString();
        court_cd = in.readString();
        court_date = in.readString();
        court_date_v = in.readString();
        court_name = in.readString();
        court_time = in.readString();
        defense_note_url = in.readString();
        floor_no = in.readString();
        judgement_cd = in.readString();
        judgement_name = in.readString();
        progres_ser_no = in.readString();
        room_no = in.readString();  next_session_date = in.readString();
        expanded = in.readByte() != 0;
    }

    public static final Creator<CaseProgressDetail> CREATOR = new Creator<CaseProgressDetail>() {
        @Override
        public CaseProgressDetail createFromParcel(Parcel in) {
            return new CaseProgressDetail(in);
        }

        @Override
        public CaseProgressDetail[] newArray(int size) {
            return new CaseProgressDetail[size];
        }
    };

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getCase_cd() {
        return case_cd;
    }

    public void setCase_cd(String case_cd) {
        this.case_cd = case_cd;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCourt_cd() {
        return court_cd;
    }

    public void setCourt_cd(String court_cd) {
        this.court_cd = court_cd;
    }

    public String getCourt_date() {
        return court_date;
    }

    public void setCourt_date(String court_date) {
        this.court_date = court_date;
    }

    public String getCourt_date_v() {
        return court_date_v;
    }

    public void setCourt_date_v(String court_date_v) {
        this.court_date_v = court_date_v;
    }

    public String getCourt_name() {
        return court_name;
    }

    public void setCourt_name(String court_name) {
        this.court_name = court_name;
    }

    public String getCourt_time() {
        return court_time;
    }

    public void setCourt_time(String court_time) {
        this.court_time = court_time;
    }

    public String getDefense_note_url() {
        return defense_note_url;
    }

    public void setDefense_note_url(String defense_note_url) {
        this.defense_note_url = defense_note_url;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(String floor_no) {
        this.floor_no = floor_no;
    }

    public String getJudgement_cd() {
        return judgement_cd;
    }

    public void setJudgement_cd(String judgement_cd) {
        this.judgement_cd = judgement_cd;
    }

    public String getJudgement_name() {
        return judgement_name;
    }

    public void setJudgement_name(String judgement_name) {
        this.judgement_name = judgement_name;
    }

    public String getProgres_ser_no() {
        return progres_ser_no;
    }

    public void setProgres_ser_no(String progres_ser_no) {
        this.progres_ser_no = progres_ser_no;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getNext_session_date() {
        return next_session_date;
    }

    public void setNext_session_date(String next_session_date) {
        this.next_session_date = next_session_date;
    }



    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(branch_no);
        dest.writeString(case_cd);
        dest.writeString(comment);
        dest.writeString(court_cd);
        dest.writeString(court_date);
        dest.writeString(court_date_v);
        dest.writeString(court_name);
        dest.writeString(court_time);
        dest.writeString(defense_note_url);
        dest.writeString(floor_no);
        dest.writeString(judgement_cd);
        dest.writeString(judgement_name);
        dest.writeString(progres_ser_no);
        dest.writeString(room_no);
        dest.writeString(next_session_date);
        dest.writeByte((byte) (expanded ? 1 : 0));
    }
}
