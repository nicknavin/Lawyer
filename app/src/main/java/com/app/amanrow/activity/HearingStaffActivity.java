package com.app.amanrow.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.amanrow.R;
import com.app.amanrow.adapter.HearingStaffAdapter;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.interfaces.RequestCallback;
import com.app.amanrow.pojo.HearingStaff;
import com.app.amanrow.utility.Constant;
import com.app.amanrow.utility.DataPrefrence;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HearingStaffActivity extends BaseActivity {
    RecyclerView recycler_view;
    LinearLayoutManager linearLayoutManager;
    HearingStaffAdapter hearingStaffAdapter;
    ArrayList<HearingStaff> hearingStaffsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing);
        initView();
        getUserRole();

    }

    private void initView() {
        ((CustomTextView)findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.menu_hearing_staff));
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(linearLayoutManager);
        hearingStaffAdapter = new HearingStaffAdapter(context,hearingStaffsList);
        recycler_view.setAdapter(hearingStaffAdapter);

    }

    public void getUserRole() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.ATT_HEARING_STAF_READ, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    try {
                        hearingStaffsList=new ArrayList<>();
                        JSONArray jsonArray=js.getJSONArray("hslist_notadded");
                        Type type = new TypeToken<ArrayList<HearingStaff>>() {}.getType();
                        hearingStaffsList = new Gson().fromJson(jsonArray.toString(), type);
                        hearingStaffAdapter = new HearingStaffAdapter(context,hearingStaffsList);
                        recycler_view.setAdapter(hearingStaffAdapter);

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
            //objinput.put("emp_cd", "2");

            object.put("info", objinfo);
            object.put("input", objinput);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}
