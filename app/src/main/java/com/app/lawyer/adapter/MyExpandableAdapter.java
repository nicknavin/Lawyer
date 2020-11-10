package com.app.lawyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.lawyer.R;
import com.app.lawyer.activity.AtornyCaseProgressActivity;
import com.app.lawyer.customview.AnimatedExpandableListView;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.pojo.CaseClient;
import com.app.lawyer.pojo.CaseDetail;

import java.util.ArrayList;


public class MyExpandableAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    ArrayList<CaseClient> caseList = new ArrayList<>();
    Context appContext;
    String lang_cd;

    public MyExpandableAdapter(Context C, ArrayList<CaseClient> item) {
        appContext = C;
        caseList = item;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_case_layout_old, null);
        }
        final CaseDetail caseDetail = caseList.get(groupPosition).getCaseArrayList().get(childPosition);
        ((CustomTextView) convertView.findViewById(R.id.txt_case_opponent_name)).setTag(caseDetail);
        ((CustomTextView) convertView.findViewById(R.id.txt_case_code)).setText(caseDetail.getCase_cd());
        ((CustomTextView) convertView.findViewById(R.id.txt_case_subject)).setText(caseDetail.getCase_subject());
        ((CustomTextView) convertView.findViewById(R.id.txt_court_name)).setText(caseDetail.getCourt_name());
        ((LinearLayout)convertView.findViewById(R.id.lay_item)).setTag(caseDetail);
        ((LinearLayout)convertView.findViewById(R.id.lay_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaseDetail detail=(CaseDetail)v.getTag();
                Intent intent=new Intent(appContext, AtornyCaseProgressActivity.class);
                intent.putExtra("DATA",detail);
                appContext.startActivity(intent);
            }
        });



        return convertView;
    }


    @Override
    public int getGroupCount() {
        return caseList.size();
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {

        int size = caseList.get(groupPosition).getCaseArrayList().size();
        return size;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) appContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_header_layout, null);
        }

        ((CustomTextView) convertView.findViewById(R.id.txt_header)).setText(caseList.get(groupPosition).getDayinterval_name());
        if (b) {
            ((ImageView) convertView.findViewById(R.id.indicator)).setImageResource(R.mipmap.arrow_down);
        } else {
            ((ImageView) convertView.findViewById(R.id.indicator)).setImageResource(R.mipmap.arrow_up);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}