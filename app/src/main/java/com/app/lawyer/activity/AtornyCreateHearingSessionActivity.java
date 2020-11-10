package com.app.lawyer.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.lawyer.R;
import com.app.lawyer.adapter.AtornyCaseProgressAdapter;
import com.app.lawyer.adapter.CaseProgressListAdapter;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.customview.ExpandCollapseExtention;
import com.app.lawyer.interfaces.FunctionSelectCallback;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.network.ApiClient;
import com.app.lawyer.network.ApiInterface;
import com.app.lawyer.pojo.AlertTypeSub;
import com.app.lawyer.pojo.CaseCS;
import com.app.lawyer.pojo.CaseControl;
import com.app.lawyer.pojo.CaseDocs;
import com.app.lawyer.pojo.CaseInformation;
import com.app.lawyer.pojo.CaseProgresprg;
import com.app.lawyer.pojo.CaseProgressDetail;
import com.app.lawyer.pojo.CaseProgressRemark;
import com.app.lawyer.pojo.Info;
import com.app.lawyer.pojo.Input;
import com.app.lawyer.pojo.Result;
import com.app.lawyer.pojo.RootCaseDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtornyCreateHearingSessionActivity extends BaseActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CaseProgressListAdapter caseProgressListAdapter;
    ArrayList<CaseProgressDetail> caseProgressDetailsList = new ArrayList<>();
    AlertTypeSub caseDetail;
    String case_cd = "";
    LinearLayout lay_lin;
    RelativeLayout lay_expand;
    static int positions=0;
    int lay_expand_count=0;
    CheckBox indicator;
    CustomTextView txt_case_code, txt_case_number, txt_moj_no, txt_case_type, txt_case_lawsuit,txt_case_lavel,txt_case_defendant,txt_case_plaintiff,txt_case_status,txt_header;
    String dayinterval_cd="";
    ImageView img_add_more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atorny_create_hearing_session  );
        if (getIntent().getParcelableExtra("DATA") != null) {
            caseDetail = getIntent().getParcelableExtra("DATA");
            case_cd = caseDetail.getCase_cd();
        }

        initView();
//        getAttachmentData();
        getAttachment();
    }


    private void initView() {

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
        txt_header.setText(caseDetail.getCase_cd()+","+caseDetail.getCase_subject());
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
        img_add_more=(ImageView)findViewById(R.id.img_add_more);
        img_add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, HearingSessionCreateFormActivity.class);
                intent.putExtra("DATA",caseDetail);
                startActivity(intent);

            }
        });
        getAttachmentData();
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

                        ((CustomTextView) findViewById(R.id.toolbar_header)).setText(cssObj.getString("case_subject"));


                        JSONArray jsonArray = js.getJSONArray("prg");
                        Type type = new TypeToken<ArrayList<CaseProgressDetail>>() {
                        }.getType();
                        caseProgressDetailsList = new Gson().fromJson(jsonArray.toString(), type);

                        AtornyCaseProgressAdapter atornyCaseProgressAdapter=new AtornyCaseProgressAdapter(context, recyclerView, dayinterval_cd, new FunctionSelectCallback() {
                            @Override
                            public void response(String position, String code, String comment)
                            {
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
        System.out.println("object "+object.toString());
        return object.toString();
    }

    ArrayList<CaseProgressRemark> caseProgressRemarkArrayList;
    ArrayList<CaseProgresprg> casePRGArrayList;
    ArrayList<CaseDocs> caseDocsArrayList;
    CaseCS caseCS;
    CaseControl caseControl;
    public void getAttachment()
    {
        CaseInformation caseInformation=new CaseInformation();
        Info info=new Info();
        info.setLang("EN");
        info.setCompany(company_cd);
        caseInformation.setInfo(info);
        Input input=new Input();
        input.setCase_cd(case_cd);
        caseInformation.setInput(input);

        ApiInterface apiInterface= ApiClient.getClient(Urls.BASE_ROLL).create(ApiInterface.class);
       Call<RootCaseDetail> call= apiInterface.reqCaseProgressList(caseInformation);
       call.enqueue(new Callback<RootCaseDetail>() {
           @Override
           public void onResponse(Call<RootCaseDetail> call, Response<RootCaseDetail> response)
           {
               if(response.body()!=null)
               {
                   Result result=response.body().getResult();
                   if(result.getRstatus().equals("1"))
                   {
                       caseProgressRemarkArrayList=response.body().getCaseProgressRemarkArrayList();//Remarks
                       casePRGArrayList=response.body().getCasePRGArrayList();//Hearings
                       caseDocsArrayList=response.body().getCaseDocsArrayList();//Attachments
                       caseCS=response.body().getCaseCS();//Case Detial
                       caseControl= response.body().getCaseControl();//Controll and button

                   }
               }

           }

           @Override
           public void onFailure(Call<RootCaseDetail> call, Throwable t) {

           }
       });



    }




}