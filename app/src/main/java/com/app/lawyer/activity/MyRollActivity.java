package com.app.lawyer.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.lawyer.R;
import com.app.lawyer.adapter.ArtornyCaseNewAdapter;
import com.app.lawyer.adapter.CaseDetailAdapter;
import com.app.lawyer.adapter.HeaderChildAdapter;
import com.app.lawyer.adapter.MyExpandableAdapter;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.AnimatedExpandableListView;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.pojo.AlertType;
import com.app.lawyer.pojo.AlertTypeSub;
import com.app.lawyer.pojo.CaseClient;
import com.app.lawyer.pojo.CaseDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyRollActivity extends BaseActivity {
    ArrayList<CaseClient> clientsList;
    List<CaseDetail> caseDetailList;
    CaseDetailAdapter caseDetailAdapter;
    HeaderChildAdapter headerChildAdapter;
    RecyclerView recyclerView,recycler_view_alert;
    LinearLayoutManager linearLayoutManager;
    MyExpandableAdapter adapter;
    AnimatedExpandableListView list_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_roll);
        initView();
    }

    public void initView() {
        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list_menu = (AnimatedExpandableListView) findViewById(R.id.list_menu);
        ((CustomTextView) findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.menu_roll));

        recycler_view_alert=(RecyclerView)findViewById(R.id.recycler_view_alert);
        recycler_view_alert.setLayoutManager(new LinearLayoutManager(context));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        //   headerChildAdapter=new HeaderChildAdapter(context,clientsList);
        //   caseDetailAdapter=new CaseDetailAdapter(context,clientsList);

        list_menu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group_selected
                // expansion/collapse.
                if (list_menu.isGroupExpanded(groupPosition)) {
                    list_menu.collapseGroupWithAnimation(groupPosition);
                    ((ImageView) v.findViewById(R.id.indicator)).setImageResource(R.mipmap.add);
                    ((LinearLayout) v.findViewById(R.id.lay_placehlder)).setVisibility(View.GONE);
                    //.setImageResource(isExpanded ? R.drawable.dropdownicon : R.drawable.dropdownicon);
                } else {
                    ((ImageView) v.findViewById(R.id.indicator)).setImageResource(R.mipmap.minus);
                    ((LinearLayout) v.findViewById(R.id.lay_placehlder)).setVisibility(View.VISIBLE);
                    list_menu.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
        list_menu.setGroupIndicator(null);

        getUserRole();
    }

    public void getUserRole() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.ATT_USER_ROLE, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    try {
                        JSONObject object = js.getJSONObject("result");
                        String rdescription = object.getString("rdescription");

                        JSONArray jsonArrayType = js.getJSONArray("atype1");
                        ArrayList<AlertType> alertTypesList = new ArrayList<>();
                        for (int a = 0; a < jsonArrayType.length(); a++) {
                            JSONObject alertObj = jsonArrayType.getJSONObject(a);
                            AlertType alertType = new AlertType();
                            alertType.setAlert_type_cd(alertObj.getString("alert_type_cd"));
                            alertType.setAlert_type_name(alertObj.getString("alert_type_name"));
                            alertType.setCnt(alertObj.getString("cnt"));
                            JSONArray dlistJsnArray = alertObj.getJSONArray("dlist");
                            ArrayList<AlertTypeSub> alertTypeSubsList=new ArrayList<>();
                            for (int b = 0; b < dlistJsnArray.length(); b++)
                            {
                                AlertTypeSub alertTypeSub = new AlertTypeSub();
                                JSONObject dlistObj = dlistJsnArray.getJSONObject(b);
                                alertTypeSub.setAlert_type_cd(dlistObj.getString("alert_type_cd"));
                                alertTypeSub.setAlert_type_name(dlistObj.getString("alert_type_name"));
                                alertTypeSub.setCase_cd(dlistObj.getString("case_cd"));
                                alertTypeSub.setCase_subject(dlistObj.getString("case_subject"));
                                alertTypeSub.setCourt_name(dlistObj.getString("court_name"));
                                alertTypeSub.setJudgement(dlistObj.getString("judgement"));
                                alertTypeSubsList.add(alertTypeSub);
                                alertType.setAlertTypeSubs(alertTypeSubsList);
                            }

                            alertTypesList.add(alertType);
                        }


//                        ArtornyAlertNewAdapter artornyAlertNewAdapter=new ArtornyAlertNewAdapter(recycler_view_alert);
//                        artornyAlertNewAdapter.setData(alertTypesList);
//                        recycler_view_alert.setAdapter(artornyAlertNewAdapter);



                        JSONArray array = js.getJSONArray("di");
//                        Type type = new TypeToken<ArrayList<CaseClient>>() {}.getType();
//                        clientsList = new Gson().fromJson(array.toString(), type);
                        clientsList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            CaseClient client = new CaseClient();
                            client.setCnt(obj.getString("cnt"));
                            client.setDayinterval_cd(obj.getString("dayinterval_cd"));
                            client.setDayinterval_name(obj.getString("dayinterval_name"));
                            JSONArray jsonArray = obj.getJSONArray("dlist");
                            caseDetailList = new ArrayList<>();
                            for (int j = 0; j < jsonArray.length(); j++) {
                                CaseDetail caseDetail = new CaseDetail();
                                JSONObject objCase = jsonArray.getJSONObject(j);
                                caseDetail.setCase_cd(objCase.getString("case_cd"));
                                caseDetail.setCase_client_name(objCase.getString("case_client_name"));
                                caseDetail.setCase_opponent_name(objCase.getString("case_opponent_name"));
                                caseDetail.setCase_subject(objCase.getString("case_subject"));
                                caseDetail.setCourt_name(objCase.getString("court_name"));
                                caseDetail.setCourt_time(objCase.getString("court_time"));
                                caseDetail.setCourt_date(objCase.getString("court_date"));
                                caseDetail.setProgres_description(objCase.getString("progres_description"));
                                caseDetail.setProgres_ser_no(objCase.getString("progres_ser_no"));
                                caseDetailList.add(caseDetail);
                                client.setCaseArrayList(caseDetailList);
                            }

                            clientsList.add(client);

                        }

                        ArtornyCaseNewAdapter artornyCaseNewAdapter = new ArtornyCaseNewAdapter(context,recyclerView);

                        artornyCaseNewAdapter.setData(clientsList);
                        recyclerView.setAdapter(artornyCaseNewAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    //showToast(failed);
                    showToast("Server error!! Please try after some time.");

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
            objinfo.put("company", company_cd);

            JSONObject objemp = new JSONObject();
            objemp.put("user_cd", userId);

            object.put("info", objinfo);
            object.put("input", objemp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }


}
/*
* String sourceString = "<b>" + id + "</b> " + name;
mytextview.setText(Html.fromHtml(sourceString));
* */