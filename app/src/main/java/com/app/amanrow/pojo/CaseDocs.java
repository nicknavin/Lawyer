
package com.app.amanrow.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class CaseDocs implements Parcelable {

    @SerializedName("ccd")
    private String ccd;
    @SerializedName("doc_name")
    private String docName;
    @SerializedName("doc_url_address")
    private String docUrlAddress;
    @SerializedName("doc_url_address_full")
    private String docUrlAddressFull;
    @SerializedName("selected")
    private String selected;
    @SerializedName("serno")
    private String serno;

    protected CaseDocs(Parcel in) {
        ccd = in.readString();
        docName = in.readString();
        docUrlAddress = in.readString();
        docUrlAddressFull = in.readString();
        selected = in.readString();
        serno = in.readString();
    }

    public static final Creator<CaseDocs> CREATOR = new Creator<CaseDocs>() {
        @Override
        public CaseDocs createFromParcel(Parcel in) {
            return new CaseDocs(in);
        }

        @Override
        public CaseDocs[] newArray(int size) {
            return new CaseDocs[size];
        }
    };

    public String getCcd() {
        return ccd;
    }

    public void setCcd(String ccd) {
        this.ccd = ccd;
    }

    public String getDocName()
    {
        if(docName==null)
        {
            docName="";
        }
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocUrlAddress() {
        return docUrlAddress;
    }

    public void setDocUrlAddress(String docUrlAddress) {
        this.docUrlAddress = docUrlAddress;
    }

    public String getDocUrlAddressFull() {
        return docUrlAddressFull;
    }

    public void setDocUrlAddressFull(String docUrlAddressFull) {
        this.docUrlAddressFull = docUrlAddressFull;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getSerno() {
        return serno;
    }

    public void setSerno(String serno) {
        this.serno = serno;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ccd);
        dest.writeString(docName);
        dest.writeString(docUrlAddress);
        dest.writeString(docUrlAddressFull);
        dest.writeString(selected);
        dest.writeString(serno);
    }
}
