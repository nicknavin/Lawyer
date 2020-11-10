package com.app.lawyer.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.lawyer.R;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.pojo.CaseClient;
import com.app.lawyer.pojo.CaseDetail;
import com.app.lawyer.section.SectionRecyclerViewAdapter;

import java.util.List;

public class HeaderChildAdapter extends SectionRecyclerViewAdapter<CaseClient, CaseDetail, HeaderChildAdapter.HeaderViewHolder, HeaderChildAdapter.ChildViewHolder>
{

    Context context;
    List<CaseClient> caseClientsList;

    public HeaderChildAdapter(Context context, List<CaseClient> sectionItemList) {
        super(context, sectionItemList);
        this.context=context;
        caseClientsList=sectionItemList;
    }

    @Override
    public HeaderViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.row_header_layout, sectionViewGroup, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_case_layout, childViewGroup, false);
        return new ChildViewHolder(view);
    }
//header layout
    @Override
    public void onBindSectionViewHolder(HeaderViewHolder headerViewHolder, int position, CaseClient section)
    {
        CaseClient caseClient=caseClientsList.get(position);
        String case_status=context.getResources().getString(R.string.case_status);
        headerViewHolder.txt_header.setText(case_status+" "+caseClient.getDayinterval_name());

    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int headerposition, int childPosition, CaseDetail child) {

        CaseDetail caseDetail= caseClientsList.get(headerposition).getCaseArrayList().get(childPosition);

        holder.txt_case_subject.setText(context.getResources().getString(R.string.client_subject)+" "+caseDetail.getCase_subject());
        holder.txt_case_opponent_name.setText(context.getResources().getString(R.string.opponent_name)+" "+caseDetail.getCase_opponent_name());
        holder.txt_court_name.setText(context.getResources().getString(R.string.court_name)+" "+caseDetail.getCourt_name());
        holder.txt_progres_date.setText(context.getResources().getString(R.string.progress_date)+" "+caseDetail.getProgres_date());
        holder.txt_progres_description.setText(context.getResources().getString(R.string.progress_discription)+" "+caseDetail.getProgres_description());
        holder.client_name.setText(context.getResources().getString(R.string.client_name)+" "+caseDetail.getCase_client_name());

    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        CustomTextView txt_header;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            txt_header = (CustomTextView) itemView.findViewById(R.id.txt_header);
        }

    }


    public class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView client_name, txt_case_opponent_name, txt_case_subject, txt_court_name,
                txt_progres_date, txt_progres_description, txt_progres_status;


        public ChildViewHolder(View view) {
            super(view);

            client_name = (TextView) view.findViewById(R.id.client_name);
            txt_case_opponent_name = (TextView) view.findViewById(R.id.txt_case_opponent_name);
            txt_case_subject = (TextView) view.findViewById(R.id.txt_case_subject);
            txt_court_name = (TextView) view.findViewById(R.id.txt_court_name);
            txt_progres_date = (TextView) view.findViewById(R.id.txt_progres_date);
            txt_progres_description = (TextView) view.findViewById(R.id.txt_progres_description);


        }

    }





}
