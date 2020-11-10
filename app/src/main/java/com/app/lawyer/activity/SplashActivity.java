package com.app.lawyer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.app.lawyer.R;
import com.app.lawyer.utility.Constant;
import com.app.lawyer.utility.DataPrefrence;
import com.app.lawyer.utility.Utility;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    Context context;
    private static int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        context = this;
//        Intent intent = new Intent(context, LoginActivity.class);
//        startActivity(intent);
//        finish();
        String lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION,"");
//        Utility.setSystemLang(getApplicationContext());
        Utility.setLang(context, lang);
//        DataPrefrence.setPref(context,Constant.PREFERED_LANGUAGE,"EN");
            new Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    if(DataPrefrence.getPref(context, Constant.LOGIN_FLAG,false)) {
                        Intent intent = new Intent(context, LawyerLandingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, SPLASH_TIME_OUT);
        }






}
