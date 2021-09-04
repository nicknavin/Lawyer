package com.app.amanrow.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.app.amanrow.R;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.interfaces.RequestCallback;
import com.app.amanrow.utility.Constant;
import com.app.amanrow.utility.DataPrefrence;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class OTPActivity extends BaseActivity implements View.OnClickListener {

    CustomTextView btn_submits;
    TextInputEditText edt_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initView();
    }

    private void initView() {
        edt_otp = (TextInputEditText) findViewById(R.id.edt_otp);
        btn_submits = (CustomTextView) findViewById(R.id.btn_submit);
        btn_submits.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_submit:
                if(isInternetConnected()) {
                    if (validation())
                    {
                        callOTPVerifyApi();
                    }
                }


                break;
        }
    }




    private void setLanguage(String language) {
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        // Create a new configuration object
//        Configuration config = new Configuration();
//        // Set the locale of the new configuration
//        config.locale = locale;
//        // Update the configuration of the Accplication context
//        getResources().updateConfiguration(
//                config,
//                getResources().getDisplayMetrics()
//        );

        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }


    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

    public void setLang(String lang) {
        Locale locale;

        //lang=DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
        if (lang.equals("ar")) {
            locale = new Locale("ar");
        } else {
            locale = new Locale("en");
        }

        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, displayMetrics);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(configuration, locale);
        } else {
            setSystemLocaleLegacy(configuration, locale);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            context.getApplicationContext().getResources().updateConfiguration(configuration,
                    context.getResources().getDisplayMetrics());


    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }

    public boolean validation()
    {
        if(edt_otp.getText().toString().isEmpty())
        {
            edt_otp.setError(context.getResources().getString(R.string.error));
            return false;
        }

        return true;
    }

    public void callOTPVerifyApi()
    {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.IIP_MOB_APP_CODE_CHECK, getjsonPasswordUpdate(), new RequestCallback()
            {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    try {
                        String company_cd=   js.getString("company_cd");
                        js.getString("mob_app_cd");
                        js.getString("rdescription");
                        js.getString("rstatus");
                        String user_id=   js.getString("user_code");
                        String user_type_cd=  js.getString("user_type_cd");

                        DataPrefrence.setPref(context, Constant.USER_ID,user_id);
                        DataPrefrence.setPref(context, Constant.USER_TYPE_CD,user_type_cd);
                        DataPrefrence.setPref(context, Constant.COMPANY_CD,company_cd);
                        Intent intent=new Intent(context, RegisterActivity.class);
                        intent.putExtra("id",user_id);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                        //                  finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    showToast(success);
//                    Intent intent=new Intent(context,RegisterActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
  //                  finish();
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
    private String getjsonPasswordUpdate() {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mob_app_cd", edt_otp.getText().toString());
            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            jsonObject.put("info", objinfo);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
