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
import com.app.amanrow.activity.ui.HomeCaseActivity;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.pojo.ClientCaseData;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class ClientCaseDetailAdapter extends RecyclerView.Adapter<ClientCaseDetailAdapter.ViewHolder> {



    Context context;
    private ArrayList<ClientCaseData> clientCaseDataList;
String subjectData="";
    public ClientCaseDetailAdapter(Context context, ArrayList<ClientCaseData> list, String data)
    {

        this.context = context;
        clientCaseDataList=list;
        subjectData=data;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_client_case_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientCaseDetailAdapter.ViewHolder holder, int position)
    {
        final ClientCaseData data=clientCaseDataList.get(position);
        holder.txt_subject.setText(data.getCase_subject());
        holder.txt_status.setText(data.getStatus_name());
        holder.lay_lin.setTag(data);

        holder.lay_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ClientCaseData caseData=(ClientCaseData)v.getTag();
//                Intent intent=new Intent(context, ClientCaseSubjectDetailActivity.class);
                Intent intent=new Intent(context, HomeCaseActivity.class);
                intent.putExtra("DATA",subjectData);
                intent.putExtra("CLIENT_CASE_DATA",caseData);
                context.startActivity(intent);


                
            }
        });


    }




    @Override
    public int getItemCount() {

        return clientCaseDataList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txt_subject,txt_status;
LinearLayout lay_lin;
        public ViewHolder(View view) {
            super(view);
            lay_lin=(LinearLayout)view.findViewById(R.id.lay_lin);
            txt_subject=(CustomTextView)view.findViewById(R.id.txt_subject);
            txt_status=(CustomTextView)view.findViewById(R.id.txt_status);


        }

    }



}
