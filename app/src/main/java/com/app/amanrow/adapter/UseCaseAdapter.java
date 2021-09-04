package com.app.amanrow.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amanrow.R;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.pojo.UseCase;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class UseCaseAdapter extends RecyclerView.Adapter<UseCaseAdapter.ViewHolder> {


    private LayoutInflater inflater;
    Context context;
    private ArrayList<UseCase> hearingStaffsList = new ArrayList<>();

    public UseCaseAdapter(Context context, ArrayList<UseCase> list)
    {

        this.context = context;
        hearingStaffsList=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_usecase_layout, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UseCaseAdapter.ViewHolder holder, int position)
    {
        final UseCase useCase=hearingStaffsList.get(position);
        holder.txt_client.setText(useCase.getCase_client_name());
        holder.txt_subject.setText(useCase.getCase_subject());
        holder.txt_code.setText(useCase.getCase_cd());

    }




    @Override
    public int getItemCount() {

        return hearingStaffsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txt_client,txt_subject,txt_code;

        public ViewHolder(View view) {
            super(view);

            txt_code = (CustomTextView) view.findViewById(R.id.txt_code);
            txt_client = (CustomTextView) view.findViewById(R.id.txt_client);
            txt_subject = (CustomTextView) view.findViewById(R.id.txt_subject);


        }

    }



}
