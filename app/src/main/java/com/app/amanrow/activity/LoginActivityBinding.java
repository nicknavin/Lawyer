package com.app.amanrow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.amanrow.R;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CustomTextView;
//import com.app.lawyer.databinding.ActivityLoginBinding;
import com.app.amanrow.interfaces.RequestCallback;
import com.app.amanrow.pojo.InfoLang;
import com.app.amanrow.pojo.ReqLoginData;
import com.app.amanrow.utility.Constant;
import com.app.amanrow.utility.DataPrefrence;
import com.app.amanrow.utility.Utility;
import com.app.amanrow.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivityBinding extends BaseActivity implements View.OnClickListener {

    TextInputEditText edt_username, edt_password;
    CustomTextView btn_submit, btn_register;
    TextView btn_english, btn_arabic;
    Context context;
    CheckBox checkbox1,indicator;
//ActivityLoginBinding binding;
LoginViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
//        context = this;
//        viewModel=new ViewModelProvider(this).get(LoginViewModel.class);
//        binding.setLoginViewModel(viewModel);
//        binding.setHandler(this);
//
//        binding.btnArabic.setText(binding.getLoginViewModel().reqLoginData.getUser_name());


        //initView();
    }


    private void initView() {
        btn_english = (TextView) findViewById(R.id.btn_english);
        btn_english.setOnClickListener(this);
        btn_arabic = (TextView) findViewById(R.id.btn_arabic);
        btn_arabic.setOnClickListener(this);
        edt_password = (TextInputEditText) findViewById(R.id.edt_password);
        edt_username = (TextInputEditText) findViewById(R.id.edt_username);
//        edt_username.setText("shannu");//for user
//        edt_password.setText("12345");
        edt_username.setText("fadel");
        edt_password.setText("2");

        btn_submit = (CustomTextView) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        btn_register = (CustomTextView) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        indicator = (CheckBox) findViewById(R.id.indicator);


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


        edt_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (isInternetConnected()) {
                        if (validation()) {
                            loginApi();
                        }
                    } else {
                        showInternetConnectionToast();
                    }
                    return true;
                }
                return false;
            }
        });

//        lang=DataPrefrence.getPref(context,Constant.LANG_SELECTION,"");
//        if(lang.equals("ar"))
//        {
//            indicator.setChecked(true);
//        }
//        else
//        {
//            indicator.setChecked(false);
//        }
//
//

        indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CheckBox checkBox= (CheckBox) view;
                if(checkBox.isChecked())
                {
                    lang="ar";
//                    LocaleManager.setNewLocale(context,lang);
                    pageRefersh(lang);
//
                }
                else
                {
                    lang="en";
                    pageRefersh(lang);
                }
//
            }

        });



    }


    @Override
    protected void onResume() {
        super.onResume();
//        String lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
//        if (lang.equals("en") || lang.isEmpty())
//        {
//
//            indicator.setChecked(false);
//
//        } else {
//            indicator.setChecked(true);
//        }
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
        return true;
    }

    //https://futurestud.io/tutorials/retrofit-2-how-to-send-plain-text-request-body
//{"result":{"rdescription":"Success","rstatus":"1"},"user":{"branch_no":"1-1","company_cd":"1","identify_code":"P01112","image_name":"http:\/\/itegritys.com\/uploads\/c1\/hrs\/2.png",
// "language_cd":"EN","login_name":"asrar","name":"Asrar Tawfeeq Mohammed","user_cd":"9",
// "user_i_cd":"2","user_type":"Employee","user_type_cd":"1"}}
    public void loginApi() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.LOGIN, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    showToast(success);
                    try {
                        JSONObject object = js.getJSONObject("result");
                        String rdescription = object.getString("rdescription");
                        if (rdescription.equals("Success")) {
                            JSONObject userObj = js.getJSONObject("user");
                            String branch_no = userObj.getString("branch_cd");
                            String company_cd = userObj.getString("company_cd");
                            String image_name = userObj.getString("image_name");
                            String user_id = userObj.getString("user_cd");
                            String user_type = userObj.getString("user_type");
                            String user_type_cd = userObj.getString("user_type_cd");
                            //  String user_i_cd=userObj.getString("user_i_cd");
                            String user_name = userObj.getString("login_name");
                            String name = userObj.getString("name");


                            DataPrefrence.setPref(context, Constant.PROFILE_IMAGE, image_name);
                            DataPrefrence.setPref(context, Constant.USER_NAME, user_name);
//                            DataPrefrence.setPref(context, Constant.USER_I_CD, user_i_cd);
                            DataPrefrence.setPref(context, Constant.USER_TYPE_CD, user_type_cd);
                            DataPrefrence.setPref(context, Constant.USER_ID, user_id);
                            DataPrefrence.setPref(context, Constant.COMPANY_CD, company_cd);
                            DataPrefrence.setPref(context, Constant.LOGIN_FLAG, true);
                            DataPrefrence.setPref(context, Constant.USER_TYPE, user_type);
                            DataPrefrence.setPref(context, Constant.BRANCH_NO, branch_no);
//                           if(user_type_cd.equals("2"))
//                           {
//                               Intent intent = new Intent(context, ClientCasesListActivity.class);
//                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                               startActivity(intent);
//                               finish();
//                           }
//                           if(user_type_cd.equals("1"))
//                           {
//                               Intent intent = new Intent(context, LawyerLandingActivity.class);
//                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                               startActivity(intent);
//                               finish();
//                           }
                            Intent intent = new Intent(context, LawyerLandingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
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
    private String getJson() {
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("user_name", edt_username.getText().toString());
            object.put("user_pwd", edt_password.getText().toString());
            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", lang);
            object.put("info", objinfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("req parameter "+object.toString());
        return object.toString();
    }


    public void callLogin()
    {
        ReqLoginData reqLoginData=new ReqLoginData();
        InfoLang info=new InfoLang();
        info.setLang(lang);
        reqLoginData.setInfoLang(info);
        reqLoginData.setUser_name("");
        reqLoginData.setUser_pwd("");

    }








    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.btn_arabic:
//                initSelector();
////                setLanguage("ar");
//                LanguageHelper.setLanguage(context, "ar");
//                btn_arabic.setBackground(context.getResources().getDrawable(R.drawable.btn_gray));
//                break;
//            case R.id.btn_english:
//                initSelector();
//                LanguageHelper.setLanguage(context, "ar");
//                btn_english.setBackground(context.getResources().getDrawable(R.drawable.btn_gray));
//                break;

            case R.id.btn_submit:
//                if (isInternetConnected()) {
//                    if (validation()) {
//                        loginApi();
//                    }
//                } else {
//                    showInternetConnectionToast();
//                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent(context, OTPActivity.class);
                startActivity(intent);
                break;

        }
    }


    private void initSelector() {
        btn_arabic.setBackground(context.getResources().getDrawable(R.drawable.btn_white));
        btn_english.setBackground(context.getResources().getDrawable(R.drawable.btn_white));

    }


    private void pageRefersh(String lang) {
//        if (lang.equals("en")) {
//            checkBoxArab.setChecked(false);
//
//        } else if (lang.equals("hi")) {
//
//            checkBoxEng.setChecked(false);
//        }

        DataPrefrence.setPref(context, Constant.LANG_SELECTION, lang);
        Utility.setLang(context, lang);
        Intent refresh=null;

            refresh = new Intent(getApplicationContext(), LoginActivityBinding.class);

        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(refresh);
        finish();
    }
}
