
package com.app.amanrow.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;


public class CaseProgressRemark implements Parcelable {

    @SerializedName("ccd")
    private String ccd;
    @Nullable
    @SerializedName("pgsno")
    private String pgsno;
    @SerializedName("rdate")
    private String rdate;
    @SerializedName("remark")
    private String remark;
    @SerializedName("rmno")
    private String rmno;
    @SerializedName("time_diff")
    private String timeDiff;
    @SerializedName("userid")
    private String userid;
    @SerializedName("userimage")
    private String userimage;
    @SerializedName("username")
    private String username;

    protected CaseProgressRemark(Parcel in) {
        ccd = in.readString();
        pgsno = in.readString();
        rdate = in.readString();
        remark = in.readString();
        rmno = in.readString();
        timeDiff = in.readString();
        userid = in.readString();
        userimage = in.readString();
        username = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ccd);
        dest.writeString(pgsno);
        dest.writeString(rdate);
        dest.writeString(remark);
        dest.writeString(rmno);
        dest.writeString(timeDiff);
        dest.writeString(userid);
        dest.writeString(userimage);
        dest.writeString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CaseProgressRemark> CREATOR = new Creator<CaseProgressRemark>() {
        @Override
        public CaseProgressRemark createFromParcel(Parcel in) {
            return new CaseProgressRemark(in);
        }

        @Override
        public CaseProgressRemark[] newArray(int size) {
            return new CaseProgressRemark[size];
        }
    };

    public String getCcd() {
        return ccd;
    }

    public void setCcd(String ccd) {
        this.ccd = ccd;
    }

    public String getPgsno() {
        return pgsno;
    }

    public void setPgsno(String pgsno) {
        this.pgsno = pgsno;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getRemark()
    {
        if(remark==null)
        {
            return "";
        }
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRmno() {
        return rmno;
    }

    public void setRmno(String rmno) {
        this.rmno = rmno;
    }

    public String getTimeDiff()
    {
        if(timeDiff==null)
        {
            return "";
        }
        return timeDiff;
    }

    public void setTimeDiff(String timeDiff) {
        this.timeDiff = timeDiff;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getUsername() {
        if(username==null)
        {
            return "";
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
