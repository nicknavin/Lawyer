package com.app.amanrow.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class HearingStaff implements Parcelable
{
    private String actioncode;
    private String allow_delete;
    private String branch_cd;
    private String cr_user_cd;
    private String emp_cd;
    private String emp_name;
    private String image_name;
    private String job_desc;
    private String selected;
    private String user_cd;

    protected HearingStaff(Parcel in) {
        actioncode = in.readString();
        allow_delete = in.readString();
        branch_cd = in.readString();
        cr_user_cd = in.readString();
        emp_cd = in.readString();
        emp_name = in.readString();
        image_name = in.readString();
        job_desc = in.readString();
        selected = in.readString();
        user_cd = in.readString();
    }

    public static final Creator<HearingStaff> CREATOR = new Creator<HearingStaff>() {
        @Override
        public HearingStaff createFromParcel(Parcel in) {
            return new HearingStaff(in);
        }

        @Override
        public HearingStaff[] newArray(int size) {
            return new HearingStaff[size];
        }
    };

    public String getActioncode() {
        return actioncode;
    }

    public void setActioncode(String actioncode) {
        this.actioncode = actioncode;
    }

    public String getAllow_delete() {
        return allow_delete;
    }

    public void setAllow_delete(String allow_delete) {
        this.allow_delete = allow_delete;
    }

    public String getBranch_cd() {
        return branch_cd;
    }

    public void setBranch_cd(String branch_cd) {
        this.branch_cd = branch_cd;
    }

    public String getCr_user_cd() {
        return cr_user_cd;
    }

    public void setCr_user_cd(String cr_user_cd) {
        this.cr_user_cd = cr_user_cd;
    }

    public String getEmp_cd() {
        return emp_cd;
    }

    public void setEmp_cd(String emp_cd) {
        this.emp_cd = emp_cd;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getUser_cd() {
        return user_cd;
    }

    public void setUser_cd(String user_cd) {
        this.user_cd = user_cd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(actioncode);
        dest.writeString(allow_delete);
        dest.writeString(branch_cd);
        dest.writeString(cr_user_cd);
        dest.writeString(emp_cd);
        dest.writeString(emp_name);
        dest.writeString(image_name);
        dest.writeString(job_desc);
        dest.writeString(selected);
        dest.writeString(user_cd);
    }
}
