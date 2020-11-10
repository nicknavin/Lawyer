package com.app.lawyer.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.lawyer.R;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.pojo.CaseDocs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CaseAttachmentAdapter extends RecyclerView.Adapter<CaseAttachmentAdapter.ViewHolder>
{
    ArrayList<CaseDocs> caseDocsArrayList;
    Context context;
   public CaseAttachmentAdapter(Context context, ArrayList<CaseDocs> list)
    {
        caseDocsArrayList=list;
        this.context=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attachment_layout, parent, false);
             return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        CaseDocs caseDocs=caseDocsArrayList.get(position);
        holder.txtDocumentName.setText(caseDocs.getDocName());

        if(caseDocs.getDocUrlAddress().toLowerCase().contains(".jpg")||caseDocs.getDocUrlAddress().toLowerCase().contains(".png"))
        {
            holder.imgAttachment.setVisibility(View.VISIBLE);

            Picasso.with(context)
                    .load(caseDocs.getDocUrlAddressFull())
                    .error(R.mipmap.placeholder)
                    .placeholder(R.mipmap.placeholder)
                    .into(holder.imgAttachment);

//            Glide.with(context)
//                    .load(caseDocs.getDocUrlAddressFull())
//                    .placeholder(R.mipmap.place_holder)
//                    .error(R.mipmap.place_holder)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(holder.imgAttachment);
        }
        else
        {
            holder.imgAttachment.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return caseDocsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CustomTextView txtDocumentName;
        ImageView imgAttachment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDocumentName=(CustomTextView)itemView.findViewById(R.id.txtDocumentName);
            imgAttachment=(ImageView)itemView.findViewById(R.id.imgAttachment);

        }
    }
}
