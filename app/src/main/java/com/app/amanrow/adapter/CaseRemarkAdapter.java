package com.app.amanrow.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amanrow.R;
import com.app.amanrow.customview.CircleImageView;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.pojo.CaseProgressRemark;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CaseRemarkAdapter extends RecyclerView.Adapter<CaseRemarkAdapter.ViewHolder>
{
    ArrayList<CaseProgressRemark> caseProgressRemarkArrayList;
    Context context;
   public CaseRemarkAdapter(Context context, ArrayList<CaseProgressRemark> list)
    {
        caseProgressRemarkArrayList=list;
        this.context=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_remark_layout, parent, false);
             return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        CaseProgressRemark progressreMark=caseProgressRemarkArrayList.get(position);
        holder.txt_remark.setText(progressreMark.getRemark());
        holder.txt_user_name.setText(progressreMark.getUsername());
            Picasso.with(context).load(progressreMark.getUserimage()).error(R.mipmap.profile_icon).placeholder(R.mipmap.profile_icon).into(holder.imgProfile);
            holder.textMSgTime.setText(progressreMark.getTimeDiff());
    }

    @Override
    public int getItemCount() {
        return caseProgressRemarkArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CustomTextView txt_user_name,txt_remark,textMSgTime;
        CircleImageView imgProfile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_remark=(CustomTextView)itemView.findViewById(R.id.txt_remark);
            textMSgTime=(CustomTextView)itemView.findViewById(R.id.textMSgTime);
            txt_user_name=(CustomTextView)itemView.findViewById(R.id.txt_user_name);
            imgProfile=(CircleImageView)itemView.findViewById(R.id.imgProfile);

        }
    }
}
