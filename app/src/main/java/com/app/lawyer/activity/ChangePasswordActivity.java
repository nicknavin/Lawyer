package com.app.lawyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.app.lawyer.R;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CustomEditText;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends BaseActivity {
    CustomEditText edt_old_pwd, edt_new_pwd, edt_cnfm_pwd;

    CheckBox checkbox1, checkbox2, checkbox3;
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
        edt_old_pwd = (CustomEditText) findViewById(R.id.edt_old_pwd);
        edt_new_pwd = (CustomEditText) findViewById(R.id.edt_new_pwd);
        edt_cnfm_pwd = (CustomEditText) findViewById(R.id.edt_cnfm_pwd);
        btn_update = (CustomTextView) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    callPasswordUpdateApi();
                }
            }
        });
        checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox1.isChecked()) {
                    edt_old_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    edt_old_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox2.isChecked()) {
                    edt_new_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    edt_new_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox3.isChecked()) {
                    edt_cnfm_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    edt_cnfm_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                    showToast(failed);
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
            objinfo.put("lang", lang);
            jsonObject.put("info", objinfo);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
