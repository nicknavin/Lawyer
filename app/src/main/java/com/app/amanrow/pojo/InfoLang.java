package com.app.amanrow.pojo;

import com.google.gson.annotations.SerializedName;

public class InfoLang
{
    @SerializedName("lang")
    private String lang;



    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
