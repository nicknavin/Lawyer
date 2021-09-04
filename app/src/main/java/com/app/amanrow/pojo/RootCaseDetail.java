package com.app.amanrow.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RootCaseDetail
{
    @SerializedName("case_control")
    private CaseControl caseControl;
    @SerializedName("cs")
    private CaseCS caseCS;
    @SerializedName("doc_default")
    private CaseDocDefault caseDocDefault;
    @SerializedName("docs")
    private ArrayList<CaseDocs> caseDocsArrayList;
    @SerializedName("prg")
    private ArrayList<CaseProgresprg> casePRGArrayList;
    @SerializedName("progressremark")
    private ArrayList<CaseProgressRemark> caseProgressRemarkArrayList;
    @SerializedName("result")
    private Result result;


    public CaseControl getCaseControl() {
        return caseControl;
    }

    public void setCaseControl(CaseControl caseControl) {
        this.caseControl = caseControl;
    }

    public CaseCS getCaseCS() {
        return caseCS;
    }

    public void setCaseCS(CaseCS caseCS) {
        this.caseCS = caseCS;
    }

    public CaseDocDefault getCaseDocDefault() {
        return caseDocDefault;
    }

    public void setCaseDocDefault(CaseDocDefault caseDocDefault) {
        this.caseDocDefault = caseDocDefault;
    }

    public ArrayList<CaseDocs> getCaseDocsArrayList() {
        return caseDocsArrayList;
    }

    public void setCaseDocsArrayList(ArrayList<CaseDocs> caseDocsArrayList) {
        this.caseDocsArrayList = caseDocsArrayList;
    }

    public ArrayList<CaseProgresprg> getCasePRGArrayList() {
        return casePRGArrayList;
    }

    public void setCasePRGArrayList(ArrayList<CaseProgresprg> casePRGArrayList) {
        this.casePRGArrayList = casePRGArrayList;
    }

    public ArrayList<CaseProgressRemark> getCaseProgressRemarkArrayList() {
        return caseProgressRemarkArrayList;
    }

    public void setCaseProgressRemarkArrayList(ArrayList<CaseProgressRemark> caseProgressRemarkArrayList) {
        this.caseProgressRemarkArrayList = caseProgressRemarkArrayList;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
