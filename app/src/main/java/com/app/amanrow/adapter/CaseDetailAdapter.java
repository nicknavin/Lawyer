package com.app.amanrow.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.amanrow.R;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.pojo.CaseClient;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class CaseDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LAYOUT_HEADER = 0;
    private static final int LAYOUT_CHILD = 1;
    private LayoutInflater inflater;
    Context context;
    private ArrayList<CaseClient> caseDetailArrayList = new ArrayList<>();

    public CaseDetailAdapter(Context context, ArrayList<CaseClient> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        caseDetailArrayList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_case_layout, parent, false);
        RecyclerView.ViewHolder holder;
        if (viewType == LAYOUT_HEADER) {
            View view = inflater.inflate(R.layout.row_header_layout, parent, false);
            holder = new MyViewHolderHeader(view);
        } else {
            View view = inflater.inflate(R.layout.row_case_layout, parent, false);
            holder = new ViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
        CaseClient caseClient=caseDetailArrayList.get(position);
        if(holder.getItemViewType()== LAYOUT_HEADER)
        {
            MyViewHolderHeader vaultItemHolder = (MyViewHolderHeader) holder;
            vaultItemHolder.txt_header.setText(caseClient.getDayinterval_name());
        }
        else {

            ViewHolder vaultItemHolder = (ViewHolder) holder;
            vaultItemHolder.txt_case_subject.setText(caseClient.getCaseArrayList().get(position).getCase_subject());

        }
    }




    @Override
    public int getItemCount() {

        return caseDetailArrayList.size();
    }


    class MyViewHolderHeader extends RecyclerView.ViewHolder {

        CustomTextView txt_header;

        public MyViewHolderHeader(View itemView) {
            super(itemView);

            txt_header = (CustomTextView) itemView.findViewById(R.id.txt_header);
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView client_name, txt_case_opponent_name, txt_case_subject, txt_court_name,
                txt_progres_date, txt_progres_description, txt_progres_status;


        public ViewHolder(View view) {
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
