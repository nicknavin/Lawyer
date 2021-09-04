package com.app.amanrow.pojo;

public class DocDetail
{
   private String ccd;
   private String doc_name;
   private String doc_url_address;
   private String doc_url_address_full;
   private String selected;
   private String serno;

    public String getCcd() {
        return ccd;
    }

    public void setCcd(String ccd) {
        this.ccd = ccd;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_url_address() {
        return doc_url_address;
    }

    public void setDoc_url_address(String doc_url_address) {
        this.doc_url_address = doc_url_address;
    }

    public String getDoc_url_address_full() {
        return doc_url_address_full;
    }

    public void setDoc_url_address_full(String doc_url_address_full) {
        this.doc_url_address_full = doc_url_address_full;
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
    public String toString() {
        return doc_name;
    }

}
