
package com.app.lawyer.pojo;


import com.google.gson.annotations.SerializedName;

public class CaseDocDefault {

    @SerializedName("attach_count")
    private String attachCount;
    @SerializedName("default_file")
    private String defaultFile;
    @SerializedName("default_file_full")
    private String defaultFileFull;
    @SerializedName("default_index")
    private String defaultIndex;
    @SerializedName("show_add_memo_button")
    private String showAddMemoButton;

    public String getAttachCount() {
        return attachCount;
    }

    public void setAttachCount(String attachCount) {
        this.attachCount = attachCount;
    }

    public String getDefaultFile() {
        return defaultFile;
    }

    public void setDefaultFile(String defaultFile) {
        this.defaultFile = defaultFile;
    }

    public String getDefaultFileFull() {
        return defaultFileFull;
    }

    public void setDefaultFileFull(String defaultFileFull) {
        this.defaultFileFull = defaultFileFull;
    }

    public String getDefaultIndex() {
        return defaultIndex;
    }

    public void setDefaultIndex(String defaultIndex) {
        this.defaultIndex = defaultIndex;
    }

    public String getShowAddMemoButton() {
        return showAddMemoButton;
    }

    public void setShowAddMemoButton(String showAddMemoButton) {
        this.showAddMemoButton = showAddMemoButton;
    }

}
