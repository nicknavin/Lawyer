package com.app.lawyer.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.app.lawyer.R;
import com.app.lawyer.adapter.ClientCaseAdapter;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.pojo.CasePrg;
import com.app.lawyer.pojo.ClientCaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientCaseHearingDateActivity extends BaseActivity {

    ClientCaseData caseData=null;
    ArrayList<CasePrg> casePrgsList=new ArrayList<>();
    RecyclerView recycler_view;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_case_hearing_date);
        if(getIntent().getParcelableExtra("DATA")!=null)
        {
            caseData=getIntent().getParcelableExtra("DATA");
//            casePrgsList=caseData.getCasePrgsList();
            data=caseData.getData();
        }
        calll();
        initView();
    }
public void calll()
{
    try {
        JSONArray arrayPrg=new JSONArray(data);
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
            casePrgsList.add(casePrg);
        }

    } catch (JSONException e) {
        e.printStackTrace();
    }
}




    private void initView()
    {
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((CustomTextView)findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.hearing_detail));
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recycler_view.setLayoutManager(linearLayoutManager);
        ClientCaseAdapter clientCaseAdapter=new ClientCaseAdapter(context,casePrgsList);
        recycler_view.setAdapter(clientCaseAdapter);


    }

}
