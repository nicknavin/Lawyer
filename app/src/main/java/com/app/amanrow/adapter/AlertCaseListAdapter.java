package com.app.amanrow.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.amanrow.R;
import com.app.amanrow.activity.ui.HomeLawyerActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.pojo.AlertTypeSub;

import java.util.ArrayList;


/**
 * Created by Navya on 28-04-2016.
 */
public class AlertCaseListAdapter extends RecyclerView.Adapter<AlertCaseListAdapter.ViewHolder> {


    private LayoutInflater inflater;
    Context context;
    ArrayList<AlertTypeSub> alertTypeSubsList;

    public AlertCaseListAdapter(Context context, ArrayList<AlertTypeSub> list)
    {
        this.context = context;
        alertTypeSubsList=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_atorny_case_list, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AlertCaseListAdapter.ViewHolder holder, int position)
    {
        AlertTypeSub detail=alertTypeSubsList.get(position);
        holder.txt_case_code.setText(detail.getCase_cd());
        holder.txt_case_subject.setText(detail.getCase_subject());
        holder.txt_court_name.setText(detail.getJudgement());
        holder.lay_item.setTag(detail);
        holder.lay_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertTypeSub data =(AlertTypeSub)v.getTag();
//                Intent intent=new Intent(context,AtornyCreateHearingSessionActivity.class);
                Intent intent=new Intent(context, HomeLawyerActivity.class);
                intent.putExtra("DATA",data);

                context.startActivity(intent);

            }
        });
    }




    @Override
    public int getItemCount() {

        return alertTypeSubsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txt_case_subject,txt_case_code,txt_court_name;
        LinearLayout lay_item;

        public ViewHolder(View view) {
            super(view);
            txt_case_subject=(CustomTextView)view.findViewById(R.id.txt_case_subject);
            txt_case_code=(CustomTextView)view.findViewById(R.id.txt_case_code);
            txt_court_name=(CustomTextView)view.findViewById(R.id.txt_court_name);
            lay_item=(LinearLayout) view.findViewById(R.id.lay_item);



        }

    }



}
