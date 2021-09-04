package com.app.amanrow.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.app.amanrow.R;
import com.app.amanrow.adapter.ClientCaseDetailAdapter;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.interfaces.RequestCallback;
import com.app.amanrow.pojo.CasePrg;
import com.app.amanrow.pojo.ClientCaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientCasesListActivity extends BaseActivity
{
RecyclerView recycler_view;
ArrayList<ClientCaseData> clientCaseDataList=new ArrayList<>();
String subjectData="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_cases_list);
        initView();
    }

    private void initView()
    {
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recycler_view.setLayoutManager(linearLayoutManager);

        getClientCaseRead();

    }
    public void getClientCaseRead() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.GCLIENT_CASE_READ, getjsonClientCaseRegister(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    try {
                        subjectData=js.toString();
                        clientCaseDataList=new ArrayList<>();
                        JSONArray jsonArray=js.getJSONArray("gc_cases");

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            ClientCaseData data =new ClientCaseData();
                            JSONObject object=jsonArray.getJSONObject(i);
                            data.setAttorny_office_name(object.getString("attorny_office_name"));
                            data.setCase_cd(object.getString("case_cd"));
                            data.setCase_subject(object.getString("case_subject"));
                            data.setCompany_cd(object.getString("company_cd"));
                            data.setCourt_level_name(object.getString("court_level_name"));
                            data.setGlobal_client_cd(object.getString("global_client_cd"));
                            data.setLawsuit_number(object.getString("lawsuit_number"));
                            data.setNext_hearing_date(object.getString("next_hearing_date"));
                            data.setStatus_name(object.getString("status_name"));
                            data.setUnique_number(object.getString("unique_number"));
                            data.setData(object.getJSONArray("prg").toString());
                            ArrayList<CasePrg> casePrgsList=new ArrayList<>();

//                            JSONArray arrayPrg=object.getJSONArray("prg");
//                            for(int j=0;j<arrayPrg.length();j++)
//                            {
//                                JSONObject objPrg=arrayPrg.getJSONObject(j);
//                                CasePrg casePrg=new CasePrg();
//                                casePrg.setCase_cd(objPrg.getString("case_cd"));
//                                casePrg.setCase_prg_no(objPrg.getString("case_prg_no"));
//                                casePrg.setComment(objPrg.getString("comment"));
//                                casePrg.setCompany_cd(objPrg.getString("company_cd"));
//                                casePrg.setCourt_date(objPrg.getString("court_date"));
//                                casePrg.setCourt_level_name(objPrg.getString("court_level_name"));
//                                casePrg.setCourt_name(objPrg.getString("court_name"));
//                                casePrg.setCourt_time(objPrg.getString("court_time"));
//                                casePrg.setFloor_no(objPrg.getString("floor_no"));
//                                casePrg.setJudgement(objPrg.getString("judgement"));
//                                casePrg.setRoom_no(objPrg.getString("room_no"));
//                                casePrgsList.add(casePrg);
//                            }
//                            data.setCasePrgsList(casePrgsList);

                            clientCaseDataList.add(data);

                        }


                        ClientCaseDetailAdapter clientCaseDetailAdapter=new ClientCaseDetailAdapter(context,clientCaseDataList,subjectData);
                        recycler_view.setAdapter(clientCaseDetailAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showToast(success);

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    showToast(failed);


                }

                @Override
                public void onNull(JSONObject js, String nullp) {
                    dismiss_loading();
                }

                @Override
                public void onException(JSONObject js, String exception) {
                    dismiss_loading();
                }
            }).execute();

        } else {
            showInternetConnectionToast();
        }
    }
    //{"info":{"lang":"EN"},"user_name":"shafi", "user_pwd":"2"}
    private String getjsonClientCaseRegister() {
        try {
            JSONObject object = new JSONObject();
            JSONObject jsonObjinput = new JSONObject();

            jsonObjinput.put("global_client_cd", "200100001");


            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");

            object.put("info", objinfo);
            object.put("input", jsonObjinput);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
