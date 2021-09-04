package com.app.amanrow.pojo;

import com.google.gson.annotations.SerializedName;

public class Info
{
    @SerializedName("lang")
    private String lang;
    @SerializedName("company")
    private String company;


    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
