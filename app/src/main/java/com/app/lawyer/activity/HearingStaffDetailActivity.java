package com.app.lawyer.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lawyer.R;
import com.app.lawyer.adapter.UseCaseAdapter;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.pojo.HearingStaff;
import com.app.lawyer.pojo.UseCase;
import com.app.lawyer.utility.Constant;
import com.app.lawyer.utility.DataPrefrence;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HearingStaffDetailActivity extends BaseActivity
{
    RecyclerView recycler_view;
    LinearLayoutManager linearLayoutManager;
    HearingStaff hearingStaff;
    ArrayList<UseCase> useCasesList =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_staff_detail);
        if(getIntent().getParcelableExtra("DATA")!=null)
        {
            hearingStaff=getIntent().getParcelableExtra("DATA");

        }
        initView();
    }


    public void initView()
    {
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(linearLayoutManager);
        getUserRole();
    }

    public void getUserRole() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.ATT_CASE_USER_READ, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    try {
                        useCasesList=new ArrayList<>();
                        JSONArray jsonArray=js.getJSONArray("ucase_listed");
                        Type type = new TypeToken<ArrayList<UseCase>>() {}.getType();
                        useCasesList = new Gson().fromJson(jsonArray.toString(), type);
                        UseCaseAdapter useCaseAdapter=new UseCaseAdapter(context,useCasesList);
                        recycler_view.setAdapter(useCaseAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    //showToast(failed);
                    showToast("Email or Password is invalid");

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
        String branch_cd = DataPrefrence.getPref(context, Constant.BRANCH_NO, "");
        try {
            object = new JSONObject();

            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            objinfo.put("company", "1");

            JSONObject objinput = new JSONObject();
            objinput.put("actioncode", "read");
            objinput.put("branch_cd", branch_cd);
            objinput.put("emp_cd", "2");

            object.put("info", objinfo);
            object.put("input", objinput);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}
