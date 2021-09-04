package com.app.amanrow.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.amanrow.R;
import com.app.amanrow.activity.HearingStaffDetailActivity;
import com.app.amanrow.customview.CircleImageView;
import com.app.amanrow.pojo.HearingStaff;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class HearingStaffAdapter extends RecyclerView.Adapter<HearingStaffAdapter.ViewHolder> {


    private LayoutInflater inflater;
    Context context;
    private ArrayList<HearingStaff> hearingStaffsList = new ArrayList<>();

    public HearingStaffAdapter(Context context, ArrayList<HearingStaff> list)
    {

        this.context = context;
        hearingStaffsList=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hearingstaff_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HearingStaffAdapter.ViewHolder holder, int position)
    {
        final HearingStaff hearingStaff=hearingStaffsList.get(position);
        holder.txt_user_name.setText(hearingStaff.getEmp_name());
        holder.lay_hearingstaff.setTag(hearingStaff);
        holder.lay_hearingstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HearingStaff hearingStaff1=(HearingStaff)v.getTag();
                Intent intent=new Intent(context, HearingStaffDetailActivity.class);
                intent.putExtra("DATA",hearingStaff);
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {

        return hearingStaffsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_user_name;
        CircleImageView imgProfile;
        LinearLayout lay_hearingstaff;

        public ViewHolder(View view) {
            super(view);

            txt_user_name = (TextView) view.findViewById(R.id.txt_user_name);
            imgProfile=(CircleImageView)view.findViewById(R.id.imgProfile);
            lay_hearingstaff=(LinearLayout)view.findViewById(R.id.lay_hearingstaff);

        }

    }



}
