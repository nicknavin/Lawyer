package com.app.lawyer.pojo;

import com.app.lawyer.section.Section;

import java.util.List;

public class CaseClient implements Section<CaseDetail> {
    private String cnt;
    private String dayinterval_cd;
    private String dayinterval_name;
    private String emp_cd;
    private boolean expanded;
    private List<CaseDetail> caseArrayList;

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getDayinterval_cd() {
        return dayinterval_cd;
    }

    public void setDayinterval_cd(String dayinterval_cd) {
        this.dayinterval_cd = dayinterval_cd;
    }

    public String getDayinterval_name() {
        return dayinterval_name;
    }

    public void setDayinterval_name(String dayinterval_name) {
        this.dayinterval_name = dayinterval_name;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<CaseDetail> getCaseArrayList() {
        return caseArrayList;
    }



    public String getEmp_cd() {
        return emp_cd;
    }

    public void setEmp_cd(String emp_cd) {
        this.emp_cd = emp_cd;
    }


    public void setCaseArrayList(List<CaseDetail> caseArrayList) {
        this.caseArrayList = caseArrayList;
    }

    @Override
    public List<CaseDetail> getChildItems() {
        return caseArrayList;
    }
}
