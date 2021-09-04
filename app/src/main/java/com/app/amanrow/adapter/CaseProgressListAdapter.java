package com.app.amanrow.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.amanrow.R;
import com.app.amanrow.activity.CaseDetailActivity;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.pojo.CaseProgressDetail;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class CaseProgressListAdapter extends RecyclerView.Adapter<CaseProgressListAdapter.ViewHolder> {

    Context context;
    private ArrayList<CaseProgressDetail> caseProgressDetailsList = new ArrayList<>();
String user_id="";
    public CaseProgressListAdapter(Context context, ArrayList<CaseProgressDetail> list) {
        this.context = context;
        caseProgressDetailsList = list;
       BaseActivity baseActivity= (BaseActivity)context;
       user_id=baseActivity.userId;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_caseprogress_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {

        CaseProgressDetail caseProgressDetail=caseProgressDetailsList.get(position);
        holder.txt_building.setText(caseProgressDetail.getFloor_no());
        holder.txt_court_name.setText(caseProgressDetail.getCourt_name());
        holder.txt_progres_date.setText(caseProgressDetail.getCourt_date_v());
        holder.txt_judgement.setText(caseProgressDetail.getJudgement_name());
        holder.lay_progress.setTag(caseProgressDetail);
        holder.lay_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CaseProgressDetail detail=(CaseProgressDetail)v.getTag();
                Intent intent =new Intent(context, CaseDetailActivity.class);
                intent.putExtra("DATA",detail);
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return caseProgressDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txt_progres_date,txt_court_name,txt_building,txt_judgement;
        LinearLayout lay_progress;


        public ViewHolder(View view) {
            super(view);

            txt_building = (CustomTextView) view.findViewById(R.id.txt_building);
            txt_court_name = (CustomTextView) view.findViewById(R.id.txt_court_name);
            txt_progres_date = (CustomTextView) view.findViewById(R.id.txt_progres_date);
            txt_judgement = (CustomTextView) view.findViewById(R.id.txt_judgement);
            lay_progress = (LinearLayout) view.findViewById(R.id.lay_progress);



        }

    }


}
