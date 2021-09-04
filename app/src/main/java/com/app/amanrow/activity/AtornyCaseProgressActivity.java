package com.app.amanrow.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.amanrow.R;
import com.app.amanrow.adapter.AtornyCaseProgressAdapter;
import com.app.amanrow.adapter.CaseProgressListAdapter;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.customview.ExpandCollapseExtention;
import com.app.amanrow.interfaces.FunctionSelectCallback;
import com.app.amanrow.interfaces.RequestCallback;
import com.app.amanrow.pojo.CaseDetail;
import com.app.amanrow.pojo.CaseProgressDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AtornyCaseProgressActivity extends BaseActivity {


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CaseProgressListAdapter caseProgressListAdapter;
    ArrayList<CaseProgressDetail> caseProgressDetailsList = new ArrayList<>();
    CaseDetail caseDetail;
    String case_cd = "";
    LinearLayout lay_lin;
    RelativeLayout lay_expand;
    static int positions=0;
    int lay_expand_count=0;
    CheckBox indicator;
    CustomTextView txt_case_code, txt_case_number, txt_moj_no, txt_case_type, txt_case_lawsuit,txt_case_lavel,txt_case_defendant,txt_case_plaintiff,txt_case_status,txt_header;
    String dayinterval_cd="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_progress);
        if (getIntent().getParcelableExtra("DATA") != null) {
            caseDetail = getIntent().getParcelableExtra("DATA");
            case_cd = caseDetail.getCase_cd();
        }
        if(getIntent().getStringExtra("FILTER")!=null)
        {
            dayinterval_cd=getIntent().getStringExtra("FILTER");
        }
        initView();
        getAttachmentData();
    }


    private void initView() {
        ((CustomTextView) findViewById(R.id.toolbar_header)).setText(caseDetail.getCourt_date());
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        txt_case_status=(CustomTextView)findViewById(R.id.txt_case_status);
        txt_case_code=(CustomTextView)findViewById(R.id.txt_case_code);
        txt_case_number=(CustomTextView)findViewById(R.id.txt_case_number);
        txt_moj_no=(CustomTextView)findViewById(R.id.txt_moj_no);
        txt_case_type=(CustomTextView)findViewById(R.id.txt_case_type);
        txt_case_lawsuit=(CustomTextView)findViewById(R.id.txt_case_lawsuit);
        txt_case_lavel=(CustomTextView)findViewById(R.id.txt_case_lavel);
        txt_case_defendant=(CustomTextView)findViewById(R.id.txt_case_defendant);
        txt_case_plaintiff=(CustomTextView)findViewById(R.id.txt_case_plaintiff);
        txt_header=(CustomTextView)findViewById(R.id.txt_header);
        txt_header.setText(caseDetail.getCase_subject());
        lay_expand=(RelativeLayout)findViewById(R.id.lay_expand);
        lay_lin=(LinearLayout)findViewById(R.id.lay_lin);
        indicator=(CheckBox)findViewById(R.id.indicator);
        ExpandCollapseExtention.collapse(lay_lin);
        indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indicator.isChecked())
                {
                    ExpandCollapseExtention.expand(lay_lin);
                }
                else
                {
                    ExpandCollapseExtention.collapse(lay_lin);
                }
            }
        });


        lay_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lay_expand_count==0)
                {
                    indicator.setChecked(true);
                    ExpandCollapseExtention.expand(lay_lin);
                    lay_expand_count=1;
                }
                else
                {
                    indicator.setChecked(false);
                    ExpandCollapseExtention.collapse(lay_lin);
                    lay_expand_count=0;
                }
            }
        });


            }


    public void getAttachmentData() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.GET_CASE_PROGRESS_LIST, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    try {
                        JSONObject cssObj=js.getJSONObject("cs");
                        txt_case_code.setText(cssObj.getString("case_cd"));
                        txt_case_defendant.setText(cssObj.getString("defendant"));
                        txt_case_lawsuit.setText(cssObj.getString("lawsuit_no"));
                        txt_case_lavel.setText(cssObj.getString("level_name"));
                        txt_case_number.setText(cssObj.getString("lawsuit_no"));
                        txt_case_plaintiff.setText(cssObj.getString("plaintiff"));
                        txt_case_status.setText(cssObj.getString("status_name"));
                        txt_case_type.setText(cssObj.getString("case_type_name"));
                        txt_moj_no.setText(cssObj.getString("unique_number"));
                        JSONArray jsonArray = js.getJSONArray("prg");
                        Type type = new TypeToken<ArrayList<CaseProgressDetail>>() {
                        }.getType();
                        caseProgressDetailsList = new Gson().fromJson(jsonArray.toString(), type);


//                        caseProgressListAdapter = new CaseProgressListAdapter(context, caseProgressDetailsList);
//                        recyclerView.setAdapter(caseProgressListAdapter);
                        AtornyCaseProgressAdapter atornyCaseProgressAdapter=new AtornyCaseProgressAdapter(context, recyclerView, dayinterval_cd, new FunctionSelectCallback() {
                            @Override
                            public void response(String position, String code, String comment)
                            {
                                System.out.println("code "+code+"comment"+comment);
                                int index= Integer.parseInt(position);
                                CaseProgressDetail caseProgressDetail=caseProgressDetailsList.get(index);
                                postJudgmentCommentData(caseProgressDetail,code,comment);

                            }
                        });
                        atornyCaseProgressAdapter.setData(caseProgressDetailsList);
                        recyclerView.setAdapter(atornyCaseProgressAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    //showToast(failed);
                }

                @Override
                public void onNull(JSONObject js, String nullp) {

                }

                @Override
                public void onException(JSONObject js, String exception) {

                }
            }).execute();

        } else {
            showInternetConnectionToast();
        }
    }

    private String getJson() {
        JSONObject object = null;
        try {
            object = new JSONObject();

            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            objinfo.put("company", company_cd   );

            JSONObject objinput = new JSONObject();
            objinput.put("case_cd", case_cd);

            object.put("info", objinfo);
            object.put("input", objinput);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }


    public void postJudgmentCommentData(CaseProgressDetail detail, String judgement_cd, String comment) {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.CASE_PROGRESS_COM, getpostJson(detail,judgement_cd,comment), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    showToast(success);

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    //showToast(failed);
                }

                @Override
                public void onNull(JSONObject js, String nullp) {

                }

                @Override
                public void onException(JSONObject js, String exception) {

                }
            }).execute();

        } else {
            showInternetConnectionToast();
        }
    }

    private String getpostJson(CaseProgressDetail detail, String judgement_cd, String comment) {
        JSONObject object = null;
        try {
            object = new JSONObject();

            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            objinfo.put("company", "1");

            JSONObject objinput = new JSONObject();
            objinput.put("actioncode","judgement");
            objinput.put("case_cd", detail.getCase_cd());
            objinput.put("progres_ser_no", detail.getProgres_ser_no());
            objinput.put("branch_cd", detail.getBranch_no());
            objinput.put("judgement_cd  ", judgement_cd);
            objinput.put("comment  ", comment);

            object.put("info", objinfo);
            object.put("input", objinput);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}
