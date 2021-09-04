package com.app.amanrow.pojo;

import java.util.ArrayList;

public class AlertType
{
    private String alert_type_cd;
    private String alert_type_name;
    private String cnt;
    private boolean expanded;


    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    private ArrayList<AlertTypeSub> alertTypeSubs;


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

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public ArrayList<AlertTypeSub> getAlertTypeSubs() {
        return alertTypeSubs;
    }

    public void setAlertTypeSubs(ArrayList<AlertTypeSub> alertTypeSubs) {
        this.alertTypeSubs = alertTypeSubs;
    }
}
