package com.app.lawyer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.lawyer.R;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.utility.Constant;
import com.app.lawyer.utility.DataPrefrence;
import com.app.lawyer.utility.Utility;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;

    CustomTextView tvEngish, tvArab;
    CheckBox checkBoxEng, checkBoxArab;
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this;
        if(getIntent().getStringExtra("TYPE")!=null)
        {
            type=getIntent().getStringExtra("TYPE");
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
        tvEngish = (CustomTextView) findViewById(R.id.tvEngish);
        tvArab = (CustomTextView) findViewById(R.id.tvArab);

        checkBoxEng = (CheckBox) findViewById(R.id.checkEnglish);
        checkBoxArab = (CheckBox) findViewById(R.id.checkArab);

        checkBoxEng.setOnClickListener(this);
        checkBoxArab.setOnClickListener(this);


    }


    @Override
    protected void onResume() {
        super.onResume();

        String lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
        if (lang.equals("en") || lang.isEmpty()) {

            checkBoxArab.setChecked(false);
            checkBoxEng.setChecked(true);
        } else {
            checkBoxArab.setChecked(true);
            checkBoxEng.setChecked(false);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent= new Intent(context,HomeTabActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkEnglish:
                if (checkBoxEng.isChecked()) {
                    checkBoxArab.setChecked(false);
                    pageRefersh("en");
                } else {

                    checkBoxArab.setChecked(true);
                    pageRefersh("ar");
//                    finish();
//                    startActivity(getIntent());
                }

                break;
            case R.id.checkArab:
                if (checkBoxArab.isChecked()) {


                    checkBoxEng.setChecked(false);
                    pageRefersh("ar");
                    //  finish();
                    //  startActivity(getIntent());

                } else {
                    checkBoxEng.setChecked(true);

                    pageRefersh("en");

                    // finish();
                    // startActivity(getIntent());
                }

                break;
        }

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
        refresh = new Intent(this, LawyerLandingActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(refresh);
        finish();
    }

    @Override
    public void finish() {
        super.finish();

    }

    String currentLanguage = "en", currentLang;
    Locale myLocale;

    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, SettingActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
        } else {
            Toast.makeText(SettingActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }


}
