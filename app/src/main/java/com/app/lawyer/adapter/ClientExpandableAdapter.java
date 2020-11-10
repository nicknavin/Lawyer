package com.app.lawyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.lawyer.R;
import com.app.lawyer.customview.AnimatedExpandableListView;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.pojo.CasePrg;

import java.util.ArrayList;


public class ClientExpandableAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    ArrayList<CasePrg> caseList = new ArrayList<>();
    Context appContext;
    String lang_cd;

    public ClientExpandableAdapter(Context C, ArrayList<CasePrg> item) {
        appContext = C;
        caseList = item;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_client_case_hearingdate, null);
        }
        final CasePrg casePrg = caseList.get(groupPosition);



        if(casePrg.getCourt_level_name()!=null) {
            ((CustomTextView) convertView.findViewById(R.id.txt_court_lavel)).setText(casePrg.getCourt_level_name());
        }
        ((CustomTextView) convertView.findViewById(R.id.txt_court_name)).setText(casePrg.getCourt_name());
        ((CustomTextView) convertView.findViewById(R.id.txt_court_time)).setText(casePrg.getCourt_time());
        ((CustomTextView) convertView.findViewById(R.id.txt_court_date)).setText(casePrg.getCourt_date());
        ((CustomTextView) convertView.findViewById(R.id.txt_judgement)).setText(casePrg.getJudgement());
             ((CustomTextView) convertView.findViewById(R.id.txt_room)).setText(casePrg.getRoom_no());
        ((CustomTextView) convertView.findViewById(R.id.txt_court_location)).setText("Location");
        ((CustomTextView) convertView.findViewById(R.id.txt_floor)).setText(casePrg.getFloor_no());
        ((CustomTextView) convertView.findViewById(R.id.txt_comment)).setText(casePrg.getComment());

        return convertView;
    }


    @Override
    public int getGroupCount() {
        return caseList.size();
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {

//        int size = caseList.get(groupPosition).getCaseArrayList().size();
        return 1;

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
            convertView = infalInflater.inflate(R.layout.row_date_judgment_layout, null);
        }

        ((CustomTextView) convertView.findViewById(R.id.txt_date)).setText(caseList.get(groupPosition).getCourt_date());
        ((CustomTextView) convertView.findViewById(R.id.txt_judgement)).setText(caseList.get(groupPosition).getJudgement());
        if (b) {
            ((ImageView) convertView.findViewById(R.id.indicator)).setImageResource(R.mipmap.minus);
        } else {
            ((ImageView) convertView.findViewById(R.id.indicator)).setImageResource(R.mipmap.add);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}