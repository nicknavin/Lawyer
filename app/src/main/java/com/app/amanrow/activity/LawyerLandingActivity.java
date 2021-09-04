package com.app.amanrow.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.app.amanrow.R;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CircleImageView;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.fragment.ClientCaseFragment;
import com.app.amanrow.fragment.MyRoleFragment;
import com.app.amanrow.interfaces.RequestCallback;
import com.app.amanrow.pojo.CommanData;
import com.app.amanrow.utility.Constant;
import com.app.amanrow.utility.DataPrefrence;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LawyerLandingActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    CustomTextView tv_DrawerName, tv_email, drawer_btn_client_cases, drawer_btn_hearing_staff, drawer_Profile, drawer_change_pwd, drawer_logout, drawer_btn_myRoll;
    CustomTextView drawer_customer_care, drawer_invite_friends, drawer_feedback, drawerSetting;
    ImageView imgmenu;
    CircleImageView drawer_prof_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        initView();
    }

    public void openLeft() {

        if (lang.toLowerCase().equals("ar")) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);


            } else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);

            }

        } else if (lang.toLowerCase().equals("en") || lang.toLowerCase().equals("")) {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);


            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        }

    }

    public void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_prof_img = (CircleImageView) findViewById(R.id.drawer_prof_img);
        imgmenu = (ImageView) findViewById(R.id.imgmenu);
        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLeft();
            }
        });
        tv_email = (CustomTextView) findViewById(R.id.tv_email);
        tv_DrawerName = (CustomTextView) findViewById(R.id.tv_DrawerName);
        drawer_Profile = (CustomTextView) findViewById(R.id.drawer_Profile);
        drawer_Profile.setOnClickListener(this);

        drawerSetting = (CustomTextView) findViewById(R.id.drawerSetting);
        drawerSetting.setOnClickListener(this);
        drawer_btn_client_cases = (CustomTextView) findViewById(R.id.drawer_btn_client_cases);
        drawer_btn_client_cases.setOnClickListener(this);
        drawer_btn_hearing_staff = (CustomTextView) findViewById(R.id.drawer_btn_hearing_staff);
        drawer_btn_hearing_staff.setOnClickListener(this);
        drawer_btn_myRoll = (CustomTextView) findViewById(R.id.drawer_btn_myRoll);
        drawer_btn_myRoll.setOnClickListener(this);
        drawer_change_pwd = (CustomTextView) findViewById(R.id.drawer_change_pwd);
        drawer_change_pwd.setOnClickListener(this);
        drawer_logout = (CustomTextView) findViewById(R.id.drawer_logout);
        drawer_logout.setOnClickListener(this);


        if (user_type_cd.equals("1")) {
            //  getUserData();
            drawer_btn_client_cases.setVisibility(View.GONE);
            tv_email.setText(DataPrefrence.getPref(context,Constant.EMAILID,""));
            tv_DrawerName.setText(DataPrefrence.getPref(context, Constant.USER_NAME, ""));
            ((CustomTextView) findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.menu_roll));

            displayFragment(new MyRoleFragment());
            getEmployeeData();
        }
        if (user_type_cd.equals("2"))//this for client
        {
            tv_email.setText("");

            tv_DrawerName.setText(DataPrefrence.getPref(context, Constant.USER_NAME, ""));
            ((CustomTextView) findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.cases));

           // drawer_btn_hearing_staff.setVisibility(View.GONE);
            drawer_btn_myRoll.setVisibility(View.GONE);
//            drawer_feedback.setVisibility(View.GONE);
//            drawer_customer_care.setVisibility(View.GONE);
//            drawer_invite_friends.setVisibility(View.GONE);

            displayFragment(new ClientCaseFragment());
        }


    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.drawer_Profile:
                intent = new Intent(context, ProfileUpdateActivity.class);
                startActivity(intent);
                break;
            case R.id.drawer_change_pwd:
                intent = new Intent(context, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.drawer_logout:
                clearDataBase();
                intent = new Intent(context, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.drawer_btn_myRoll:
//                intent = new Intent(context, MyRollActivity.class);
//                startActivity(intent);
                drawer_btn_client_cases.setVisibility(View.GONE);
                ((CustomTextView) findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.menu_roll));
                MyRoleFragment myRoleFragment = new MyRoleFragment();
                displayFragment(myRoleFragment);
                break;
            case R.id.drawer_btn_hearing_staff:
                intent = new Intent(context, HearingStaffActivity.class);
                startActivity(intent);
                break;
            case R.id.drawer_btn_client_cases:
                displayFragment(new ClientCaseFragment());
                break;
            case R.id.drawerSetting:
                intent = new Intent(context, SettingActivity.class);
                startActivity(intent);
                break;
        }
        openLeft();
    }

    public void displayFragment(Fragment fragment) {

        // update the main content by replacing fragments
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }

    }


    public void getUserData() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.GET_USER, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();

                    try {
                        JSONObject object = js.getJSONObject("result");
                        String rdescription = object.getString("rdescription");
                        if (rdescription.equals("Success")) {
                            JSONObject userObj = js.getJSONObject("user");
                            String email_address = userObj.getString("email_address");
                            tv_email.setText(email_address);
                            String login_name = userObj.getString("login_name");
                            tv_DrawerName.setText(login_name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();



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
            object.put("user_cd", userId);
            object.put("lang", "EN");
            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            object.put("info", objinfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!DataPrefrence.getPref(context, Constant.PROFILE_IMAGE, "").isEmpty()) {
            String image_url = DataPrefrence.getPref(context, Constant.PROFILE_IMAGE, "");
            //  Picasso.with(context).load(image_url).memoryPolicy(MemoryPolicy.NO_CACHE).into(drawer_prof_img);

            Glide.with(context)
                    .load(image_url)
                    .placeholder(R.mipmap.profile_icon)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.mipmap.profile_icon)
                    .into(drawer_prof_img);
//            Glide.with(context)
//                    .load(new File(resultUri.getPath()))
//                    .transform(new RoundedCorners(16))
//                    .placeholder(R.mipmap.place_holder)
//                    .error(R.mipmap.place_holder)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(((ItemMessageUserHolder) holder).imgChat);
        }
    }


    public void getEmployeeData() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.GET_LCS_MASTER, getJsonMaster(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    try {
                        dismiss_loading();

                        Type type = null;
                        JSONArray jsonArray = null;
                        ArrayList<CommanData> list = null;
                        jsonArray = js.getJSONArray("case_type");
                        type = new TypeToken<ArrayList<CommanData>>() {
                        }.getType();
                        list = new Gson().fromJson(jsonArray.toString(), type);
                        DataPrefrence.setObject(context, Constant.CASE_TYPE_DATA, list);
                        list = null;

                        jsonArray = js.getJSONArray("court");
                        type = new TypeToken<ArrayList<CommanData>>() {
                        }.getType();
                        list = new Gson().fromJson(jsonArray.toString(), type);
                        DataPrefrence.setObject(context, Constant.COURT_DATA, list);
                        list = null;

                        jsonArray = js.getJSONArray("court_level");
                        type = new TypeToken<ArrayList<CommanData>>() {
                        }.getType();
                        list = new Gson().fromJson(jsonArray.toString(), type);
                        DataPrefrence.setObject(context, Constant.COURT_LEVEL_DATA, list);
                        list = null;

                        jsonArray = js.getJSONArray("judgement");
                        type = new TypeToken<ArrayList<CommanData>>() {
                        }.getType();
                        list = new Gson().fromJson(jsonArray.toString(), type);
                        DataPrefrence.setObject(context, Constant.JUDGMENT_DATA, list);
                        list = null;

                        jsonArray = js.getJSONArray("lawsuit_type");
                        type = new TypeToken<ArrayList<CommanData>>() {
                        }.getType();
                        list = new Gson().fromJson(jsonArray.toString(), type);
                        DataPrefrence.setObject(context, Constant.LAWSUIT_TYPE__DATA, list);
                        list = null;

                        jsonArray = js.getJSONArray("lcemployee");
                        type = new TypeToken<ArrayList<CommanData>>() {
                        }.getType();
                        list = new Gson().fromJson(jsonArray.toString(), type);
                        DataPrefrence.setObject(context, Constant.LCEMPLOYEE_DATA, list);
                        list = null;


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();



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


    private String getJsonMaster() {
        JSONObject object = null;
        try {
            object = new JSONObject();

            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            objinfo.put("company", company_cd);
            object.put("info", objinfo);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }


}
