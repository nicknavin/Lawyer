package com.app.lawyer.pojo;

public class CasePrg
{
    private String case_cd;

    private String case_prg_no;

    private String comment;

    private String company_cd;

    private String court_date;

    private String court_level_name;

    private String court_name;

    private String court_time;

    private String floor_no;

    private String judgement;

    private String room_no;

    private boolean expanded;

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
    public CasePrg()
    {

    }

    public String getCase_cd() {
        return case_cd;
    }

    public void setCase_cd(String case_cd) {
        this.case_cd = case_cd;
    }

    public String getCase_prg_no() {
        return case_prg_no;
    }

    public void setCase_prg_no(String case_prg_no) {
        this.case_prg_no = case_prg_no;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCompany_cd() {
        return company_cd;
    }

    public void setCompany_cd(String company_cd) {
        this.company_cd = company_cd;
    }

    public String getCourt_date() {
        return court_date;
    }

    public void setCourt_date(String court_date) {
        this.court_date = court_date;
    }

    public String getCourt_level_name() {
        return court_level_name;
    }

    public void setCourt_level_name(String court_level_name) {
        this.court_level_name = court_level_name;
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

    public String getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(String floor_no) {
        this.floor_no = floor_no;
    }

    public String getJudgement() {
        return judgement;
    }

    public void setJudgement(String judgement) {
        this.judgement = judgement;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }
}
