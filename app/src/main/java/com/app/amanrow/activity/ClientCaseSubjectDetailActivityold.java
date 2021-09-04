package com.app.amanrow.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.app.amanrow.R;
import com.app.amanrow.adapter.ClientExpandableAdapter;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.AnimatedExpandableListView;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.pojo.ClientCaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientCaseSubjectDetailActivityold extends BaseActivity {
    ClientCaseData caseData=null;

    ClientExpandableAdapter adapter;
    AnimatedExpandableListView list_menu;
    String subjectData="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_case_subject_detail_new);
        if(getIntent().getStringExtra("DATA")!=null)
        {
            subjectData=getIntent().getStringExtra("DATA");
        }
        if(getIntent().getParcelableExtra("CLIENT_CASE_DATA")!=null)
        {
            caseData=getIntent().getParcelableExtra("CLIENT_CASE_DATA");
        }
        initView();
        initData();
    }





    public void initView() {
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list_menu = (AnimatedExpandableListView) findViewById(R.id.list_menu);
        ((CustomTextView) findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.app_name));

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

                    //.setImageResource(isExpanded ? R.drawable.dropdownicon : R.drawable.dropdownicon);
                } else {
                    ((ImageView) v.findViewById(R.id.indicator)).setImageResource(R.mipmap.minus);

                    list_menu.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
        list_menu.setGroupIndicator(null);


    }

    private void initData()
    {
        try {


           ArrayList<ClientCaseData> clientCaseDataList=new ArrayList<>();
            JSONObject jsonObject=new JSONObject(subjectData);
            JSONArray jsonArray=jsonObject.getJSONArray("gc_cases");
            for(int i=0;i<jsonArray.length();i++) {
                ClientCaseData data = new ClientCaseData();
                JSONObject object = jsonArray.getJSONObject(i);
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
                clientCaseDataList.add(data);
            }


//            if (clientCaseDataList.size() > 0) {
//
//                adapter=new ClientExpandableAdapter(context,clientCaseDataList);
//                list_menu.setAdapter(adapter);
//
//
//                for (int i = 0; i < clientCaseDataList.size(); i++) {
//                    list_menu.expandGroupWithAnimation(i);
//                }
//
//            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
