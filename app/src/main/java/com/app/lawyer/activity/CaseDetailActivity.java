package com.app.lawyer.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.app.lawyer.R;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.fragment.AttachmentFragment;
import com.app.lawyer.fragment.HearingFragment;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.pojo.CaseProgressDetail;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CaseDetailActivity extends BaseActivity {


    CaseProgressDetail caseProgressDetail=null;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    String case_code = "";
    String pgsno = "";
    String caseData="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        if(getIntent().getParcelableExtra("DATA")!=null)
        {
            caseProgressDetail=getIntent().getParcelableExtra("DATA");
            pgsno = caseProgressDetail.getProgres_ser_no();
            case_code = caseProgressDetail.getCase_cd();
        }
        ((CustomTextView) findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.menu_roll));
        getAttachmentData();
        initTablayout();
    }


    private void initTablayout() {
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((CustomTextView)findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.menu_roll));


    }


    public void setCustomTextView() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            CustomTextView tv = (CustomTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }
    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
       adapter.addFragment(HearingFragment.newInstance(caseData), context.getResources().getString(R.string.hearing_detail));
       adapter.addFragment(AttachmentFragment.newInstance(caseData),context.getResources().getString(R.string.attachment));
      // adapter.addFragment(JudgmentFragment.newInstance(caseData), context.getResources().getString(R.string.judgment));

        viewPager.setAdapter(adapter);


    }
    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void getAttachmentData() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.GET_CASE_PROGRESS_DETAIL, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success)
                {
                    dismiss_loading();

                    caseData=js.toString();
                    viewPager = (ViewPager) findViewById(R.id.viewpager_favourite);
                    setupViewPager(viewPager);
                    tabLayout = (TabLayout) findViewById(R.id.tabLayout_favourite);
                    tabLayout.setupWithViewPager(viewPager);
                    setCustomTextView();

//                    try {
//                        JSONArray jsonArray = js.getJSONArray("progressremark");
//                        Type type = new TypeToken<ArrayList<ProgressreMark>>() {
//                        }.getType();
//                        progressreMarksList = new Gson().fromJson(jsonArray.toString(), type);
//                        RemarkAdapter remarkAdapter = new RemarkAdapter(getContext(), progressreMarksList);
//                        recycler_view.setAdapter(remarkAdapter);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
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

    private String getJson() {
        JSONObject object = null;
        try {
            object = new JSONObject();

            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            objinfo.put("company", "1");
            JSONObject objinput=new JSONObject();
            objinput.put("case_cd", case_code);
            objinput.put("progres_ser_no", pgsno);
            object.put("info", objinfo);
            object.put("input",objinput);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }





}
