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
import com.app.lawyer.pojo.CasePrg;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class ClientCaseAdapter extends RecyclerView.Adapter<ClientCaseAdapter.ViewHolder> {



    Context context;
    private ArrayList<CasePrg> casePrgsList =new ArrayList<>();

    public ClientCaseAdapter(Context context, ArrayList<CasePrg> list )
    {
        casePrgsList=list;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_client_case_hearing_judgment, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientCaseAdapter.ViewHolder holder, int position)
    {
      CasePrg casePrg=casePrgsList.get(position);
      holder.txt_date.setText(casePrg.getCourt_date());
      holder.txt_judgement.setText(casePrg.getJudgement());
        holder.lay_lin.setTag(casePrg);
        holder.lay_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                CasePrg data=(CasePrg)v.getTag();
//                Intent intent=new Intent(context, ClientCaseDetailActivity.class);
//                intent.putExtra("DATA",data);
//                context.startActivity(intent);
            }
        });



    }




    @Override
    public int getItemCount() {

        return casePrgsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
//        CustomTextView txt_court_name,txt_court_time,txt_court_date,txt_judgement,txt_room_no,txt_court_location,txt_floor,txt_comment;
        CustomTextView txt_date,txt_judgement;
        LinearLayout lay_lin;
        public ViewHolder(View view) {
            super(view);
            txt_date=(CustomTextView)view.findViewById(R.id.txt_date);
            txt_judgement=(CustomTextView)view.findViewById(R.id.txt_judgement);
            lay_lin=(LinearLayout)view.findViewById(R.id.lay_lin);
        }

    }



}
