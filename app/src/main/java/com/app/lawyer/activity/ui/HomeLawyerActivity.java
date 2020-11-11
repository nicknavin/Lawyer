package com.app.lawyer.activity.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.lawyer.R;
import com.app.lawyer.activity.HearingSessionCreateFormActivity;
import com.app.lawyer.api.Urls;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.fragment.CaseAttachmentFragment;
import com.app.lawyer.fragment.CaseHearingFragment;
import com.app.lawyer.fragment.CaseInformationFragment;
import com.app.lawyer.fragment.CaseRemarkFragment;
import com.app.lawyer.network.ApiClient;
import com.app.lawyer.network.ApiInterface;
import com.app.lawyer.pojo.AlertTypeSub;
import com.app.lawyer.pojo.CaseCS;
import com.app.lawyer.pojo.CaseControl;
import com.app.lawyer.pojo.CaseDocs;
import com.app.lawyer.pojo.CaseInformation;
import com.app.lawyer.pojo.CaseProgresprg;
import com.app.lawyer.pojo.CaseProgressRemark;
import com.app.lawyer.pojo.Info;
import com.app.lawyer.pojo.Input;
import com.app.lawyer.pojo.Result;
import com.app.lawyer.pojo.RootCaseDetail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    AlertTypeSub caseDetail;
    String case_cd = "";
    ArrayList<CaseProgressRemark> caseProgressRemarkArrayList;
    ArrayList<CaseProgresprg> casePRGArrayList;
    ArrayList<CaseDocs> caseDocsArrayList;
    CaseCS caseCS;
    CaseControl caseControl;
String dayinterval_cd="";
FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_lawyer);
        if (getIntent().getParcelableExtra("DATA") != null) {
            caseDetail = getIntent().getParcelableExtra("DATA");
            case_cd = caseDetail.getCase_cd();
        }
        if (getIntent().getStringExtra("ID") != null) {
            case_cd = getIntent().getStringExtra("ID");

        }
        if (getIntent().getStringExtra("DAYINTERVAL_CD") != null) {
            dayinterval_cd = getIntent().getStringExtra("DAYINTERVAL_CD");

        }

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, HearingSessionCreateFormActivity.class);
                intent.putExtra("DATA",caseDetail);
                startActivity(intent);
            }
        });
        getAttachment();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CaseInformationFragment.newInstance(caseCS), context.getResources().getString(R.string.laywer_symbol));
        adapter.addFragment(CaseHearingFragment.newInstance(casePRGArrayList,dayinterval_cd), context.getResources().getString(R.string.hearings));
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

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
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
            if(i==0){
                ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab_brief_img, null);
                View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                // p.setMargins(10, 0, 10, 0);

                tab.requestLayout();
                tabLayout.getTabAt(i).setCustomView(imageView);
            }
            else {
            CustomTextView tv = (CustomTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_brief, null);
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            // p.setMargins(10, 0, 10, 0);
            tab.requestLayout();
            tabLayout.getTabAt(i).setCustomView(tv);}
        }
    }


    public void getAttachment()
    {
        CaseInformation caseInformation=new CaseInformation();
        Info info=new Info();
        info.setLang("EN");
        info.setCompany(company_cd);
        caseInformation.setInfo(info);
        Input input=new Input();
        input.setCase_cd(case_cd);
        caseInformation.setInput(input);

        ApiInterface apiInterface= ApiClient.getClient(Urls.BASE_ROLL).create(ApiInterface.class);
        Call<RootCaseDetail> call= apiInterface.reqCaseProgressList(caseInformation);
        call.enqueue(new Callback<RootCaseDetail>() {
            @Override
            public void onResponse(Call<RootCaseDetail> call, Response<RootCaseDetail> response)
            {
                if(response.body()!=null)
                {
                    Result result=response.body().getResult();
                    if(result.getRstatus().equals("1"))
                    {
                        caseProgressRemarkArrayList=response.body().getCaseProgressRemarkArrayList();//Remarks
                        casePRGArrayList=response.body().getCasePRGArrayList();//Hearings
                        caseDocsArrayList=response.body().getCaseDocsArrayList();//Attachments
                        caseCS=response.body().getCaseCS();//Case Detial
                        caseControl= response.body().getCaseControl();//Controll and button
                        if(response.body().getCaseDocDefault().getShowAddMemoButton().equals("1"))
                        {
                            if(dayinterval_cd.isEmpty()) {
                                fab.show();
                            }
                        }
                        else
                        {
                            fab.hide();
                        }


                        setupViewPager(viewPager);
                        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
                        tabLayout.setupWithViewPager(viewPager);
                        setCustomTextView();
                    }
                }

            }

            @Override
            public void onFailure(Call<RootCaseDetail> call, Throwable t) {

            }
        });



    }

}