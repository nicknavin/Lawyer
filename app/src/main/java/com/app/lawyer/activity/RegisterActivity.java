package com.app.lawyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.app.lawyer.R;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.utility.Constant;
import com.app.lawyer.utility.DataPrefrence;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity {
    EditText edt_username, edt_password, edt_password_cnfm;
    CustomTextView btn_submit, btn_login;
    CheckBox checkbox1, checkbox2;
    String global_client_cd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getIntent().getStringExtra("id") != null) {
            global_client_cd = getIntent().getStringExtra("id");
        }
        initView();
    }

    private void initView() {


        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_password_cnfm = (EditText) findViewById(R.id.edt_password_cnfm);
        edt_username = (EditText) findViewById(R.id.edt_username);

        btn_submit = (CustomTextView) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected()) {
                    if (validation()) {
                        callClientRegisterApi();
                    }
                } else {
                    showInternetConnectionToast();
                }
            }
        });
        btn_login = (CustomTextView) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox1.isChecked()) {
                    edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox2.isChecked()) {
                    edt_password_cnfm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    edt_password_cnfm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }


    private boolean validation() {
        if (edt_username.getText().toString().isEmpty()) {
            edt_username.setError(context.getResources().getString(R.string.error_username));
            return false;
        }
        if (edt_password.getText().toString().isEmpty()) {
            edt_password.setError(context.getResources().getString(R.string.error_pwd));
            return false;
        }
        if (edt_password_cnfm.getText().toString().isEmpty()) {
            edt_password_cnfm.setError(context.getResources().getString(R.string.error_pwd));
            return false;
        }
        if (!edt_password_cnfm.getText().toString().equals(edt_password.getText().toString())) {
            edt_password_cnfm.setError(context.getResources().getString(R.string.pwd_match));
        }


        return true;
    }


    public void callClientRegisterApi() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.GLOBAL_CLIENT_CREDENTIAL, getjsonClientRegister(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();

                    try {

                        JSONObject jsonObject = js.getJSONObject("result");
                        if (jsonObject.getString("rdescription").equals("success")) {
                            JSONObject userObj = js.getJSONObject("user");
                            String branch_no = userObj.getString("branch_cd");
                            String company_cd = userObj.getString("company_cd");
                            String image_name = userObj.getString("image_name");
                            String user_name = userObj.getString("login_name");
                            String language_cd = userObj.getString("language_cd");
                            String name = userObj.getString("name");
                            String user_id = userObj.getString("user_cd");
                            String user_type = userObj.getString("user_type");
                            String user_type_cd = userObj.getString("user_type_cd");
                            DataPrefrence.setPref(context, Constant.BRANCH_NO, branch_no);
                            DataPrefrence.setPref(context, Constant.COMPANY_CD, company_cd);
                            DataPrefrence.setPref(context, Constant.PROFILE_IMAGE, image_name);
                            DataPrefrence.setPref(context, Constant.USER_NAME, user_name);
                            DataPrefrence.setPref(context, Constant.LANGUAGE_CD, language_cd);
//                            DataPrefrence.setPref(context, Constant.USER_I_CD, user_i_cd);
                            DataPrefrence.setPref(context, Constant.USER_TYPE_CD, user_type_cd);
                            DataPrefrence.setPref(context, Constant.USER_ID, user_id);
                            DataPrefrence.setPref(context, Constant.LOGIN_FLAG, true);
                            DataPrefrence.setPref(context, Constant.USER_TYPE, user_type);
                            Intent intent = new Intent(context, LawyerLandingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    try {
//
////                        Intent intent=new Intent(context,RegisterActivity.class);
//////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                        startActivity(intent);
//                        //                  finish();
//                    }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }

//                    showToast(success);

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

    private String getjsonClientRegister() {
        try {
            JSONObject object = new JSONObject();
            JSONObject jsonObjinput = new JSONObject();

            jsonObjinput.put("global_client_cd", global_client_cd);
            jsonObjinput.put("user_name", edt_username.getText().toString());
            jsonObjinput.put("user_pwd", edt_password.getText().toString());

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
