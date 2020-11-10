package com.app.lawyer.pojo;

import com.google.gson.annotations.SerializedName;

public class LoginData
{
    @SerializedName("rdescription")
    private String rdescription;
    @SerializedName("rstatus")
    private String rstatus;

    @SerializedName("user")
    private User user;

    public String getRdescription() {
        return rdescription;
    }

    public void setRdescription(String rdescription) {
        this.rdescription = rdescription;
    }

    public String getRstatus() {
        return rstatus;
    }

    public void setRstatus(String rstatus) {
        this.rstatus = rstatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
