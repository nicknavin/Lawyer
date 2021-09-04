package com.app.amanrow.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.amanrow.R;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.interfaces.ApiCallback;
import com.app.amanrow.pojo.CaseDocs;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

public class CaseAttachmentAdapter extends RecyclerView.Adapter<CaseAttachmentAdapter.ViewHolder>
{
    ArrayList<CaseDocs> caseDocsArrayList;
    Context context;
    ApiCallback apiCallback;
   public CaseAttachmentAdapter(Context context, ArrayList<CaseDocs> list, ApiCallback callback)
    {
        caseDocsArrayList=list;
        this.context=context;
        apiCallback=callback;
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

//            Picasso.with(context)
//                    .load(caseDocs.getDocUrlAddressFull())
//                    .error(R.mipmap.placeholder)
//                    .placeholder(R.mipmap.placeholder)
//                    .into(holder.imgAttachment);

            Glide.with(context)
                    .load(caseDocs.getDocUrlAddressFull())
                    .placeholder(R.mipmap.place_holder)
                    .error(R.mipmap.place_holder)
                    .transform(new RoundedCorners(50))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imgAttachment);
        }
        else if(caseDocs.getDocUrlAddress().toLowerCase().contains(".doc"))
        {
            holder.imgAttachment.setImageResource(R.mipmap.docs_story);
        }

        holder.layout2.setTag(caseDocs);
        holder.layout2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CaseDocs docs=(CaseDocs)view.getTag();
                apiCallback.result(docs.getDocUrlAddressFull());
            }
        });


    }

    @Override
    public int getItemCount() {
        return caseDocsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CustomTextView txtDocumentName;
        ImageView imgAttachment;
        ConstraintLayout layout2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDocumentName=(CustomTextView)itemView.findViewById(R.id.txtDocumentName);
            imgAttachment=(ImageView)itemView.findViewById(R.id.imgAttachment);
            layout2=(ConstraintLayout)itemView.findViewById(R.id.layout2);

        }
    }
}
