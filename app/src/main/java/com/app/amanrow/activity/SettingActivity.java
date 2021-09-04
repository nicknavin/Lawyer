package com.app.amanrow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.amanrow.R;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.utility.Constant;
import com.app.amanrow.utility.DataPrefrence;
import com.app.amanrow.utility.Utility;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    Context context;

    CustomTextView tvEngish, tvArab;
    CheckBox checkBoxEng, checkBoxArab,indicator;
    String type="";
    TextView btn_english, btn_arabic;
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
        ((CustomTextView)findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.lang_selection));

        btn_english = (TextView) findViewById(R.id.btn_english);
        btn_english.setOnClickListener(this);
        btn_arabic = (TextView) findViewById(R.id.btn_arabic);
        btn_arabic.setOnClickListener(this);
        indicator = (CheckBox) findViewById(R.id.indicator);
        tvEngish = (CustomTextView) findViewById(R.id.tvEngish);
        tvArab = (CustomTextView) findViewById(R.id.tvArab);

        checkBoxEng = (CheckBox) findViewById(R.id.checkEnglish);
        checkBoxArab = (CheckBox) findViewById(R.id.checkArab);

        checkBoxEng.setOnClickListener(this);
        checkBoxArab.setOnClickListener(this);

        indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CheckBox checkBox= (CheckBox) view;
                String lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
                if (lang.toLowerCase().equals("en") || lang.isEmpty())
                {
                    pageRefersh("ar");
                }
                else
                {
                    pageRefersh("en");
                }
//                if(checkBox.isChecked())
//                {
//
//                }
//                else
//                {
//
//                }

            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        String lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
        if (lang.toLowerCase().equals("en") || lang.isEmpty())
        {
            //indicator.setChecked(false);

        } else {
           // indicator.setChecked(true);

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

    private void pageRefersh(String lang)
    {
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



}
