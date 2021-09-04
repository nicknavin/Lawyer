package com.app.amanrow.pojo;

import com.app.amanrow.BR;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ReqLoginData extends BaseObservable {


    @SerializedName("info")
    private InfoLang infoLang;

    @SerializedName("user_name")
    private String user_name;

    @SerializedName("user_pwd")
    private String user_pwd;

    @Bindable
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
        notifyPropertyChanged(BR.user_name);
    }
    @Bindable
    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
        notifyPropertyChanged(BR.user_pwd);
    }

   @Bindable
    public InfoLang getInfoLang() {
        return infoLang;

    }

    public void setInfoLang(InfoLang infoLang) {
        this.infoLang = infoLang;
        notifyPropertyChanged(BR.infoLang);
    }
}
