package com.app.amanrow.pojo;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("branch_no")
    private String branch_no;

    @SerializedName("code")
    private String code;

    @SerializedName("company_cd")
    private String company_cd;

    @SerializedName("identify_code")
    private String identify_code;

    @SerializedName("image_name")
    private String image_name;

    @SerializedName("language_cd")
    private String language_cd;

    @SerializedName("login_name")
    private String login_name;

    @SerializedName("name")
    private String name;

    @SerializedName("user_cd")
    private String user_cd;

    @SerializedName("user_type")
    private String user_type;

    @SerializedName("user_type_cd")
    private String user_type_cd;

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompany_cd() {
        return company_cd;
    }

    public void setCompany_cd(String company_cd) {
        this.company_cd = company_cd;
    }

    public String getIdentify_code() {
        return identify_code;
    }

    public void setIdentify_code(String identify_code) {
        this.identify_code = identify_code;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getLanguage_cd() {
        return language_cd;
    }

    public void setLanguage_cd(String language_cd) {
        this.language_cd = language_cd;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_cd() {
        return user_cd;
    }

    public void setUser_cd(String user_cd) {
        this.user_cd = user_cd;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_type_cd() {
        return user_type_cd;
    }

    public void setUser_type_cd(String user_type_cd) {
        this.user_type_cd = user_type_cd;
    }
}
