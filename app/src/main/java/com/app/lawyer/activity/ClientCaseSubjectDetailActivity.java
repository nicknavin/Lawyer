package com.app.lawyer.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.lawyer.R;
import com.app.lawyer.adapter.ClientCaseNewAdapter;
import com.app.lawyer.adapter.ClientExpandableAdapter;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.AnimatedExpandableListView;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.customview.ExpandCollapseExtention;
import com.app.lawyer.pojo.CasePrg;
import com.app.lawyer.pojo.ClientCaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientCaseSubjectDetailActivity extends BaseActivity {
    ClientCaseData caseDetail=null;

    ClientExpandableAdapter adapter;
    AnimatedExpandableListView list_menu;
    String subjectData="";
    CustomTextView txt_header,txt_laywer_office,txt_court_lavel,txt_case_no,txt_moj_no,txt_status;
    ArrayList<CasePrg> casePrgsList=new ArrayList<>();
    RecyclerView recycler_view;
    LinearLayout lay_lin;
    CheckBox indicator;
     RelativeLayout lay_expand;
       static int positions=0;
       int lay_expand_count=0;
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
            caseDetail=getIntent().getParcelableExtra("CLIENT_CASE_DATA");
        }
        initView();

    }


   public void initView() {
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       txt_header=(CustomTextView)findViewById(R.id.txt_header);
       lay_expand=(RelativeLayout)findViewById(R.id.lay_expand);

        lay_lin=(LinearLayout)findViewById(R.id.lay_lin);
        indicator=(CheckBox)findViewById(R.id.indicator);
        ((CustomTextView)findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.cases_detail));
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(context));


        list_menu = (AnimatedExpandableListView) findViewById(R.id.list_menu);
        if(caseDetail!=null) {
            txt_header.setText(caseDetail.getCase_subject());
            ((CustomTextView) findViewById(R.id.txt_laywer_office)).setText(caseDetail.getAttorny_office_name());
            ((CustomTextView) findViewById(R.id.txt_court_lavel)).setText(caseDetail.getCourt_level_name());
            ((CustomTextView) findViewById(R.id.txt_case_no)).setText(caseDetail.getLawsuit_number());
            ((CustomTextView) findViewById(R.id.txt_moj_no)).setText(caseDetail.getUnique_number());
            ((CustomTextView) findViewById(R.id.txt_status)).setText(caseDetail.getStatus_name());


            try {
                JSONArray arrayPrg=new JSONArray(caseDetail.getData());
                for(int j=0;j<arrayPrg.length();j++)
                {
                    JSONObject objPrg=arrayPrg.getJSONObject(j);
                    CasePrg casePrg=new CasePrg();
                    casePrg.setCase_cd(objPrg.getString("case_cd"));
                    casePrg.setCase_prg_no(objPrg.getString("case_prg_no"));
                    casePrg.setComment(objPrg.getString("comment"));
                    casePrg.setCompany_cd(objPrg.getString("company_cd"));
                    casePrg.setCourt_date(objPrg.getString("court_date"));
                    casePrg.setCourt_level_name(objPrg.getString("court_level_name"));
                    casePrg.setCourt_name(objPrg.getString("court_name"));
                    casePrg.setCourt_time(objPrg.getString("court_time"));
                    casePrg.setFloor_no(objPrg.getString("floor_no"));
                    casePrg.setJudgement(objPrg.getString("judgement"));
                    casePrg.setRoom_no(objPrg.getString("room_no"));
                    if(j==0) {
                        casePrg.setExpanded(true);
                    }
                    casePrgsList.add(casePrg);
                }

                ClientCaseNewAdapter clientCaseNewAdapter=new ClientCaseNewAdapter(recycler_view);
                clientCaseNewAdapter.setData(casePrgsList);
                recycler_view.setAdapter(clientCaseNewAdapter);


//                if (casePrgsList.size() > 0) {
//
//                    adapter=new ClientExpandableAdapter(context,casePrgsList);
//                    list_menu.setAdapter(adapter);
//
//
//                    for (int i = 0; i < casePrgsList.size(); i++)
//                    {
//                        list_menu.expandGroupWithAnimation(0);
//                    }
//
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
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


        list_menu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group_selected
                // expansion/collapse.
                if (list_menu.isGroupExpanded(groupPosition))
                {
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



}
