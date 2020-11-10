package com.app.lawyer.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.app.lawyer.R;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.customview.ExpandListener;
import com.app.lawyer.customview.ExpandableLinearLayout;
import com.app.lawyer.pojo.CaseClient;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class ArtornyCaseNewAdapter extends RecyclerView.Adapter<ArtornyCaseNewAdapter.ViewHolder> {



    Context context;
    ArrayList<CaseClient> casePrgsList ;
    private int lastExpandedCardPosition;
    private RecyclerView recyclerView;
    public ArtornyCaseNewAdapter(Context context, RecyclerView recyclerView)
    {
        this.recyclerView = recyclerView;
        this.context=context;

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
    public void onBindViewHolder(@NonNull ArtornyCaseNewAdapter.ViewHolder holder, int position)
    {
        CaseClient casePrg=casePrgsList.get(position);
        holder.txt_case_header.setText(casePrg.getDayinterval_name());
        holder.indicator.setChecked(casePrg.isExpanded());
        if(casePrgsList.get(position).isExpanded()){
            holder.expandableView.setVisibility(View.VISIBLE);
            holder.expandableView.setExpanded(true);
        }
        else{
            holder.expandableView.setVisibility(View.GONE);
            holder.expandableView.setExpanded(false);
        }
        holder.recyclerView_sub.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView_sub.setAdapter(new AtornyCaseListAdapter(context,casePrg.getCaseArrayList(),casePrg.getDayinterval_cd()));


    }


    public void setData(ArrayList<CaseClient> casePrgsList) {
        this.casePrgsList = casePrgsList;
    }

    public void addItem(int i) {
        casePrgsList.add(i,new CaseClient());
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
            public void onCollapseComplete() {
indicator.setChecked(false);
            }
        };
        public ViewHolder(View view) {
            super(view);
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
                        casePrgsList.get(getAdapterPosition()).setExpanded(false);
                    } else {
                        expandableView.setExpanded(true);
                        indicator.setChecked(true);
                        casePrgsList.get(getAdapterPosition()).setExpanded(true);
                        expandableView.toggle();
                    }

                }
            });
        }
    }



}
