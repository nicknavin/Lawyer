package com.app.lawyer.base;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.lawyer.R;
import com.app.lawyer.utility.Constant;
import com.app.lawyer.utility.DataPrefrence;
import com.app.lawyer.utility.Methods;
import com.app.lawyer.utility.MyDialog;
import com.app.lawyer.utility.Utility;
import com.rey.material.widget.ProgressView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog mProgressDialog;


    public Context context;
    public Activity activity;

    public String accessToken = "", userId = "",user_name="",company_cd="",user_type_cd="",user_i_cd="";

    public String loginType = "";
    public String lang="";
    public String android_id="";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        context = this;
        activity = this;
        user_type_cd= DataPrefrence.getPref(context, Constant.USER_TYPE_CD,"");
        user_i_cd= DataPrefrence.getPref(context, Constant.USER_I_CD,"");
        userId= DataPrefrence.getPref(context, Constant.USER_ID,"");
        user_name= DataPrefrence.getPref(context, Constant.USER_NAME,"");
        company_cd= DataPrefrence.getPref(context, Constant.COMPANY_CD,"");
       // lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
        lang="EN";
        startTransition();

         }


    @Override
    protected void onResume() {
        super.onResume();


    }

    public void log(String msg) {
        System.out.println(msg);
    }

    public static android.app.Dialog dd;
   static ProgressView progressDialog;

    public void showLoading() {

        if (dd != null) {
            dd.dismiss();
        }
        dd = new android.app.Dialog(context);
        try {
            dd.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dd.setContentView(R.layout.custom_loading);
            progressDialog = (ProgressView) dd.findViewById(R.id.rey_loading);
            progressDialog.start();
            dd.getWindow().setLayout(-1, -2);
            dd.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            dd.setCancelable(false);
            dd.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void dismiss_loading() {
        try {
            if (dd != null) {
                if (dd.isShowing() || dd != null) {
                    if (progressDialog != null)
                        progressDialog.stop();
                }
                dd.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Boolean isInternetConnected() {
        return Methods.isInternetConnected(BaseActivity.this);
    }

    public void showToast(String x) {
        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
    }

    public void showInternetConnectionToast() {
        Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
    }


    public void startTransition() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void finishTransition() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }


    public void msg(String msg) {
//           System.out.println(msg);
    }


    public int getHightWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }




    public void showInternetPop(Context context) {
        MyDialog.iPhone(context.getResources().getString(R.string.connection), context);
    }
    public void showAlert(Context context, String msg) {
        MyDialog.iPhone(msg, context);
    }


    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

//    public void unAuthorized() {
//        clearDataBase();
//
//        Intent intent = new Intent(context, SplashActivity.class);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }


    @Override
    public void finish() {
        super.finish();
        finishTransition();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                Utility.hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }


    public void clearDataBase()
    {
        DataPrefrence.setPref(context, Constant.USER_ID, "");
        DataPrefrence.setPref(context, Constant.USER_I_CD, "");
        DataPrefrence.setPref(context, Constant.USER_TYPE_CD, "");
        DataPrefrence.setPref(context, Constant.USER_ID, "");
        DataPrefrence.setPref(context, Constant.EMAILID, "");
        DataPrefrence.setPref(context, Constant.PROFILE_IMAGE, "");
        DataPrefrence.setPref(context, Constant.USER_NAME, "");
        DataPrefrence.setPref(context, Constant.MOBILE_NO, "");
        DataPrefrence.setPref(context, Constant.FULLNAME, "");
        DataPrefrence.setPref(context, Constant.COUNTRY_ID, "");
        DataPrefrence.setPref(context, Constant.ACCESS_TOKEN, "");
        DataPrefrence.setPref(context, Constant.LOGIN_FLAG, false);
        DataPrefrence.setPref(context, Constant.LOGIN_TYPE,"");
        DataPrefrence.setPref(context, Constant.IS_CONSULTANT,"");
        DataPrefrence.setPref(context, Constant.IS_COMPANY,"");
        DataPrefrence.setPref(context, Constant.LANG_SELECTION,"");
        DataPrefrence.setPref(context, Constant.LANGUAGE_SELECTED,false);
    }







}
