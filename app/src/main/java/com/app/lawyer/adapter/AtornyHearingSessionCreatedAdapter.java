package com.app.lawyer.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.lawyer.R;
import com.app.lawyer.customview.CustomTextView;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class AtornyHearingSessionCreatedAdapter extends RecyclerView.Adapter<AtornyHearingSessionCreatedAdapter.ViewHolder> {


    private LayoutInflater inflater;
    Context context;
    private ArrayList<String> hearingStaffsList = new ArrayList<>();

    public AtornyHearingSessionCreatedAdapter(Context context)
    {

        this.context = context;

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
    public void onBindViewHolder(@NonNull AtornyHearingSessionCreatedAdapter.ViewHolder holder, int position)
    {
        holder.lay_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                
            }
        });


    }




    @Override
    public int getItemCount() {

        return 10;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txt_client,txt_subject,txt_code;
LinearLayout lay_lin;
        public ViewHolder(View view) {
            super(view);
            lay_lin=(LinearLayout)view.findViewById(R.id.lay_lin);



        }

    }



}
