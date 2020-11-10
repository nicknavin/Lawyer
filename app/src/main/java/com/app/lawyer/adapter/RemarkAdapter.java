package com.app.lawyer.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.lawyer.R;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CircleImageView;
import com.app.lawyer.pojo.ProgressreMark;
import com.app.lawyer.utility.Constant;
import com.app.lawyer.utility.DataPrefrence;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {

    Context context;
    private ArrayList<ProgressreMark> progressreMarksList = new ArrayList<>();
String user_id="";
    public RemarkAdapter(Context context, ArrayList<ProgressreMark> list) {
        this.context = context;
        progressreMarksList = list;
       BaseActivity baseActivity= (BaseActivity)context;
       user_id=baseActivity.userId;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_remark_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {

     ProgressreMark progressreMark=progressreMarksList.get(position);
     holder.txt_remark.setText(progressreMark.getRemark());
     holder.txt_user_name.setText(progressreMark.getUsername());

        if(!DataPrefrence.getPref(context, Constant.PROFILE_IMAGE,"").isEmpty())
        {
            String image_url = DataPrefrence.getPref(context, Constant.PROFILE_IMAGE, "");
            Picasso.with(context).load(image_url).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.mipmap.profile_pic).into(holder.imgProfile);
        }

    }


    @Override
    public int getItemCount() {
        return progressreMarksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_user_name, txt_remark;
        CircleImageView imgProfile;

        public ViewHolder(View view)
        {
            super(view);
            txt_user_name = (TextView) view.findViewById(R.id.txt_user_name);
            txt_remark = (TextView) view.findViewById(R.id.txt_remark);
            imgProfile=(CircleImageView)view.findViewById(R.id.imgProfile);
        }

    }


}
