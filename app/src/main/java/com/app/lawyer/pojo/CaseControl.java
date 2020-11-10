
package com.app.lawyer.pojo;


import com.google.gson.annotations.SerializedName;

public class CaseControl {

    @SerializedName("case_cd")
    private String caseCd;
    @SerializedName("droplist_enable_status")
    private String droplistEnableStatus;
    @SerializedName("next_level")
    private String nextLevel;
    @SerializedName("prg_show_add_button")
    private String prgShowAddButton;

    public String getCaseCd() {
        return caseCd;
    }

    public void setCaseCd(String caseCd) {
        this.caseCd = caseCd;
    }

    public String getDroplistEnableStatus() {
        return droplistEnableStatus;
    }

    public void setDroplistEnableStatus(String droplistEnableStatus) {
        this.droplistEnableStatus = droplistEnableStatus;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public String getPrgShowAddButton() {
        return prgShowAddButton;
    }

    public void setPrgShowAddButton(String prgShowAddButton) {
        this.prgShowAddButton = prgShowAddButton;
    }

}
