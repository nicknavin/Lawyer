package com.app.amanrow.activity.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.amanrow.R;
import com.app.amanrow.activity.HearingSessionCreateFormActivity;
import com.app.amanrow.api.Urls;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.fragment.CaseAttachmentFragment;
import com.app.amanrow.fragment.CaseHearingFragment;
import com.app.amanrow.fragment.CaseInformationFragment;
import com.app.amanrow.fragment.CaseRemarkFragment;
import com.app.amanrow.network.ApiClient;
import com.app.amanrow.network.ApiInterface;
import com.app.amanrow.pojo.AlertTypeSub;
import com.app.amanrow.pojo.CaseCS;
import com.app.amanrow.pojo.CaseControl;
import com.app.amanrow.pojo.CaseDetail;
import com.app.amanrow.pojo.CaseDocs;
import com.app.amanrow.pojo.CaseInformation;
import com.app.amanrow.pojo.CaseProgresprg;
import com.app.amanrow.pojo.CaseProgressRemark;
import com.app.amanrow.pojo.Info;
import com.app.amanrow.pojo.Input;
import com.app.amanrow.pojo.Result;
import com.app.amanrow.pojo.RootCaseDetail;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeLawyerActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    AlertTypeSub alertTypeSub;
    String case_cd = "";
    ArrayList<CaseProgressRemark> caseProgressRemarkArrayList;
    ArrayList<CaseProgresprg> casePRGArrayList;
    ArrayList<CaseDocs> caseDocsArrayList;
    CaseCS caseCS;
    CaseControl caseControl;
    String dayinterval_cd = "";
    RelativeLayout fab;
    String title = "";
    CaseDetail caseDetail;
    String  judgment_cd="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_lawyer);
        if (getIntent().getParcelableExtra("DATA") != null)
        {
            alertTypeSub = getIntent().getParcelableExtra("DATA");
            case_cd = alertTypeSub.getCase_cd();
            title = alertTypeSub.getCase_subject();
        }
        if (getIntent().getParcelableExtra("HEARING_DATA") != null)
        {
            caseDetail = getIntent().getParcelableExtra("HEARING_DATA");
            case_cd = caseDetail.getCase_cd();
            title = caseDetail.getCase_subject();
        }
        if (getIntent().getStringExtra("ID") != null) {
            case_cd = getIntent().getStringExtra("ID");

        }
        if (getIntent().getStringExtra("TITLE") != null) {
            title = getIntent().getStringExtra("TITLE");

        }
        if (getIntent().getStringExtra("DAYINTERVAL_CD") != null) {
            dayinterval_cd = getIntent().getStringExtra("DAYINTERVAL_CD");

        }

        ((CustomTextView) findViewById(R.id.toolbar_header)).setText(title);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fab = (RelativeLayout) findViewById(R.id.layFab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HearingSessionCreateFormActivity.class);
                intent.putExtra("DATA", alertTypeSub);
                intent.putExtra("JUDGMENT_CD",judgment_cd);
                startActivity(intent);
            }
        });
        getAttachment();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CaseInformationFragment.newInstance(caseCS), context.getResources().getString(R.string.laywer_symbol));
        adapter.addFragment(CaseHearingFragment.newInstance(casePRGArrayList, dayinterval_cd,caseDetail), context.getResources().getString(R.string.hearings));
        adapter.addFragment(CaseRemarkFragment.newInstance(caseProgressRemarkArrayList), context.getResources().getString(R.string.remarks));
        adapter.addFragment(CaseAttachmentFragment.newInstance(caseDocsArrayList), context.getResources().getString(R.string.Attachment));

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //   LocaleManager.setNewLocale(context,"ar");

                //  showToast("position"+position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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


    public void setCustomTextView() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            if (i == 0) {
                ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab_brief_img, null);
                View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                // p.setMargins(10, 0, 10, 0);

                tab.requestLayout();
                tabLayout.getTabAt(i).setCustomView(imageView);
            } else {
                CustomTextView tv = (CustomTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_brief, null);
                View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                // p.setMargins(10, 0, 10, 0);
                tab.requestLayout();
                tabLayout.getTabAt(i).setCustomView(tv);
            }
        }
    }


    public void getAttachment() {
        showLoading();
        CaseInformation caseInformation = new CaseInformation();
        Info info = new Info();
        info.setLang("EN");
        info.setCompany(company_cd);
        caseInformation.setInfo(info);
        Input input = new Input();
        input.setCase_cd(case_cd);
        caseInformation.setInput(input);

        ApiInterface apiInterface = ApiClient.getClient(Urls.BASE_ROLL).create(ApiInterface.class);
        Call<RootCaseDetail> call = apiInterface.reqCaseProgressList(caseInformation);
        call.enqueue(new Callback<RootCaseDetail>() {
            @Override
            public void onResponse(Call<RootCaseDetail> call, Response<RootCaseDetail> response) {
                dismiss_loading();
                if (response.body() != null) {

                    Result result = response.body().getResult();
                    showLog(response.body().toString());
                    if (result.getRstatus().equals("1")) {
                        caseProgressRemarkArrayList = response.body().getCaseProgressRemarkArrayList();//Remarks
                        casePRGArrayList = response.body().getCasePRGArrayList();//Hearings
                        judgment_cd=casePRGArrayList.get(0).getJudgementCd();
                        caseDocsArrayList = response.body().getCaseDocsArrayList();//Attachments
                        caseCS = response.body().getCaseCS();//Case Detial
                        caseControl = response.body().getCaseControl();//Controll and button
                        if (caseControl.getPrgShowAddButton().equals("1"))
                        {
                            if (dayinterval_cd.isEmpty()) {
                                fab.setVisibility(View.VISIBLE);
                            }
                        } else {
                            fab.setVisibility(View.GONE);
                        }


                        setupViewPager(viewPager);
                        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
                        tabLayout.setupWithViewPager(viewPager);
                        setCustomTextView();
                    }
                }

            }

            @Override
            public void onFailure(Call<RootCaseDetail> call, Throwable t) {
                dismiss_loading();
            }
        });


    }

}