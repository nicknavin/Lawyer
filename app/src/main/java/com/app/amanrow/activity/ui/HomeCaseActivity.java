package com.app.amanrow.activity.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.amanrow.R;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.fragment.UserCaseFragment;
import com.app.amanrow.fragment.UserCaseInfoFragment;
import com.app.amanrow.pojo.ClientCaseData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeCaseActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String title = "";
    String subjectData="";
    ClientCaseData caseDetail=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_case);
        if(getIntent().getStringExtra("DATA")!=null)
        {
            subjectData=getIntent().getStringExtra("DATA");
        }
        if(getIntent().getParcelableExtra("CLIENT_CASE_DATA")!=null)
        {
            caseDetail=getIntent().getParcelableExtra("CLIENT_CASE_DATA");

        }
        initView();
    }

    private void initView()
    {
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ((CustomTextView) findViewById(R.id.toolbar_header)).setText(caseDetail.getCase_subject());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setCustomTextView();
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(UserCaseInfoFragment.newInstance(caseDetail), context.getResources().getString(R.string.laywer_symbol));
        adapter.addFragment(UserCaseFragment.newInstance(caseDetail), context.getResources().getString(R.string.hearings));
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

}