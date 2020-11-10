package com.app.lawyer.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.lawyer.R;
import com.app.lawyer.activity.ui.HomeLawyerActivity;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.pojo.CaseDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navya on 28-04-2016.
 */
public class AtornyCaseListAdapter extends RecyclerView.Adapter<AtornyCaseListAdapter.ViewHolder> {


    private LayoutInflater inflater;
    Context context;
    private ArrayList<String> hearingStaffsList = new ArrayList<>();
    List<CaseDetail> caseDetailsList;
String dayinterval_cd="";
    public AtornyCaseListAdapter(Context context, List<CaseDetail> list, String dayinterval_cd)
    {
        this.context = context;
        caseDetailsList=list;
        this.dayinterval_cd=dayinterval_cd;
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
    public void onBindViewHolder(@NonNull AtornyCaseListAdapter.ViewHolder holder, int position)
    {
        CaseDetail detail=caseDetailsList.get(position);
        holder.txt_case_code.setText(detail.getCase_cd());
        holder.txt_case_subject.setText(detail.getCase_subject());
        holder.txt_court_name.setText(detail.getCourt_name());
        holder.lay_item.setTag(detail);
        holder.lay_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaseDetail detail=(CaseDetail)v.getTag();
//                Intent intent=new Intent(context, AtornyCaseProgressActivity.class);
//                intent.putExtra("DATA",detail);
//                intent.putExtra("FILTER",dayinterval_cd);
                Intent intent=new Intent(context, HomeLawyerActivity.class);
                intent.putExtra("ID",detail.getCase_cd());
                intent.putExtra("DAYINTERVAL_CD",dayinterval_cd);
                context.startActivity(intent);

            }
        });
    }




    @Override
    public int getItemCount() {

        return caseDetailsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txt_case_subject,txt_case_code,txt_court_name;
LinearLayout lay_item;
        public ViewHolder(View view) {
            super(view);
            txt_case_subject=(CustomTextView)view.findViewById(R.id.txt_case_subject);
            txt_case_code=(CustomTextView)view.findViewById(R.id.txt_case_code);
            txt_court_name=(CustomTextView)view.findViewById(R.id.txt_court_name);
            lay_item=(LinearLayout)view.findViewById(R.id.lay_item);


        }

    }



}
