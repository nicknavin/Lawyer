package com.app.amanrow.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.amanrow.R;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.customview.ExpandListener;
import com.app.amanrow.customview.ExpandableLinearLayout;
import com.app.amanrow.pojo.AlertType;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class ArtornyAlertNewAdapter extends RecyclerView.Adapter<ArtornyAlertNewAdapter.ViewHolder> {



    Context context;

    ArrayList<AlertType> casePrgsList;
    private int lastExpandedCardPosition;
    private RecyclerView recyclerView;

    public ArtornyAlertNewAdapter(Context context, RecyclerView recyclerView)
    {
        this.context=context;
        this.recyclerView = recyclerView;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_atorny_cases, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtornyAlertNewAdapter.ViewHolder holder, int position)
    {
        AlertType casePrg=casePrgsList.get(position);
        holder.txt_case_header.setText(casePrg.getAlert_type_name());
        holder.indicator.setChecked(casePrg.isExpanded());
        if(casePrgsList.get(position).isExpanded()){
            holder.expandableView.setVisibility(View.VISIBLE);
            holder.expandableView.setExpanded(true);
            holder.layCase.setBackground(context.getResources().getDrawable(R.drawable.case_box));
        }
        else{
            holder.expandableView.setVisibility(View.GONE);
            holder.expandableView.setExpanded(false);
            holder.layCase.setBackground(context.getResources().getDrawable(R.drawable.case_box_new));
        }
        holder.recyclerView_sub.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView_sub.setAdapter(new AlertCaseListAdapter(context,casePrg.getAlertTypeSubs()));
if(position!=0)
{
    holder.layCase.setBackground(context.getResources().getDrawable(R.drawable.case_box));
}

    }


    public void setData(ArrayList<AlertType> casePrgsList) {
        this.casePrgsList = casePrgsList;
    }

    public void addItem(int i) {
        casePrgsList.add(i,new AlertType());
        if(i<=lastExpandedCardPosition)
            lastExpandedCardPosition++;
        notifyDataSetChanged();
    }

    public void deleteItem(int i) {
        casePrgsList.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return casePrgsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txt_case_header;
        LinearLayout lay_expand;
        CheckBox indicator;
        LinearLayout layCase;
        RelativeLayout layHeader;
        RecyclerView recyclerView_sub;
        ExpandableLinearLayout expandableView;
        ExpandListener expandListener = new ExpandListener()
        {
            @Override
            public void onExpandComplete() {
                if(lastExpandedCardPosition!=getAdapterPosition() && recyclerView.findViewHolderForAdapterPosition(lastExpandedCardPosition)!=null){
                    ((ExpandableLinearLayout)recyclerView.findViewHolderForAdapterPosition(lastExpandedCardPosition).itemView.findViewById(R.id.expandableView)).setExpanded(false);

                    casePrgsList.get(lastExpandedCardPosition).setExpanded(false);

                    ((ExpandableLinearLayout)recyclerView.findViewHolderForAdapterPosition(lastExpandedCardPosition).itemView.findViewById(R.id.expandableView)).toggle();
                }
                else if(lastExpandedCardPosition!=getAdapterPosition() && recyclerView.findViewHolderForAdapterPosition(lastExpandedCardPosition)==null){
                    casePrgsList.get(lastExpandedCardPosition).setExpanded(false);
                }
                lastExpandedCardPosition = getAdapterPosition();
            }

            @Override
            public void onCollapseComplete()
            {
               indicator.setChecked(false);
                layCase.setBackground(context.getResources().getDrawable(R.drawable.case_box_new));
                layHeader.setBackground(context.getResources().getDrawable(R.color.white));
            }
        };
        public ViewHolder(View view) {
            super(view);
            layHeader=(RelativeLayout) view.findViewById(R.id.layHeader);
            layCase=(LinearLayout)view.findViewById(R.id.layCase);
            indicator=(CheckBox)view.findViewById(R.id.indicator);
            recyclerView_sub=(RecyclerView)view.findViewById(R.id.recycler_view);
            txt_case_header=(CustomTextView)view.findViewById(R.id.txt_case_header);
            expandableView = itemView.findViewById(R.id.expandableView);
            lay_expand=(LinearLayout)view.findViewById(R.id.lay_expand);
            expandableView.setExpandListener(expandListener);

            initializeClicks();
        }
        private void initializeClicks() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expandableView.isExpanded()) {
                        expandableView.setExpanded(false);
                        indicator.setChecked(false);
                        expandableView.toggle();
                        layCase.setBackground(context.getResources().getDrawable(R.drawable.case_box_new));
                        layHeader.setBackground(context.getResources().getDrawable(R.color.white));
                        casePrgsList.get(getAdapterPosition()).setExpanded(false);
                    } else {
                        expandableView.setExpanded(true);
                        indicator.setChecked(true);
                        layHeader.setBackground(context.getResources().getDrawable(R.color.header_bg));
                        layCase.setBackground(context.getResources().getDrawable(R.drawable.case_box));
                        casePrgsList.get(getAdapterPosition()).setExpanded(true);
                        expandableView.toggle();
                    }

                }
            });
        }
    }



}
