
package com.app.amanrow.pojo;


import com.google.gson.annotations.Expose;

public class Result {

    @Expose
    private String rdescription;
    @Expose
    private String rstatus;

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

}
