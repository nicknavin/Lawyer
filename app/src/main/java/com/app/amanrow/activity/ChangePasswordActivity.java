package com.app.amanrow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.amanrow.R;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.interfaces.RequestCallback;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends BaseActivity {
    TextInputEditText edt_old_pwd, edt_new_pwd, edt_cnfm_pwd;


    CustomTextView btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }

    private void initView() {
        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((CustomTextView)findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.change_password));
        edt_old_pwd = (TextInputEditText) findViewById(R.id.edt_old_pwd);
        edt_new_pwd = (TextInputEditText) findViewById(R.id.edt_new_pwd);
        edt_cnfm_pwd = (TextInputEditText) findViewById(R.id.edt_cnfm_pwd);
        btn_update = (CustomTextView) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    callPasswordUpdateApi();
                }
            }
        });

    }

    private boolean validation() {
        if (edt_old_pwd.getText().toString().isEmpty()) {
            edt_old_pwd.setError(context.getResources().getString(R.string.error));
            return false;
        }
        if (edt_new_pwd.getText().toString().isEmpty()) {
            edt_new_pwd.setError(context.getResources().getString(R.string.error));
            return false;
        }
        if (edt_cnfm_pwd.getText().toString().isEmpty()) {
            edt_cnfm_pwd.setError(context.getResources().getString(R.string.error));
            return false;
        }
        if (!edt_cnfm_pwd.getText().toString().equals(edt_new_pwd.getText().toString())) {
            edt_cnfm_pwd.setError(context.getResources().getString(R.string.error_pwd));
            return false;
        }

        return true;
    }

    public void callPasswordUpdateApi() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.CHANGEPASSWORD, getjsonPasswordUpdate(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    showToast(success);
                    Intent intent = new Intent(context, LawyerLandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
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

    ///{ login_user_id: uid, user_password: oldpwd, user_newpassword: newpwd }
    private String getjsonPasswordUpdate() {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_cd", userId);
            jsonObject.put("old_password", edt_old_pwd.getText().toString());
            jsonObject.put("new_password", edt_new_pwd.getText().toString());
            jsonObject.put("user_type_cd",user_type_cd);
            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", lang.toUpperCase());
            jsonObject.put("info", objinfo);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
