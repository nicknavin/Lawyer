package com.app.lawyer.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
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
import com.app.lawyer.pojo.CasePrg;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class ClientCaseNewAdapter extends RecyclerView.Adapter<ClientCaseNewAdapter.ViewHolder> {
    Context context;
    private ArrayList<CasePrg> casePrgsList;
    private int lastExpandedCardPosition;
    private RecyclerView recyclerView;

    public ClientCaseNewAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;

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
    public void onBindViewHolder(@NonNull ClientCaseNewAdapter.ViewHolder holder, int position) {
        CasePrg casePrg = casePrgsList.get(position);
        holder.txt_court_name.setText(casePrg.getCourt_name());
        holder.txt_court_time.setText(casePrg.getCourt_time());
        String value = casePrg.getCourt_level_name();
        if (value != null && !value.equals("null")) {
            holder.txt_court_lavel.setVisibility(View.VISIBLE);
            holder.txt_court_lavel.setText(casePrg.getCourt_level_name());

        } else {
            holder.txt_court_lavel.setVisibility(View.GONE);
        }
        holder.txt_floor.setText(casePrg.getFloor_no());
        holder.txt_room.setText(casePrg.getRoom_no());
        holder.txt_judgement.setText(casePrg.getJudgement());
        holder.txt_judgement_header.setText(casePrg.getJudgement());
        holder.txt_comment.setText(casePrg.getComment());
        holder.txt_date.setText(casePrg.getCourt_date());
        holder.indicator.setChecked(casePrg.isExpanded());
        if (casePrgsList.get(position).isExpanded()) {
            holder.expandableView.setVisibility(View.VISIBLE);
            holder.expandableView.setExpanded(true);
        } else {
            holder.expandableView.setVisibility(View.GONE);
            holder.expandableView.setExpanded(false);
        }


    }


    public void setData(ArrayList<CasePrg> casePrgsList) {
        this.casePrgsList = casePrgsList;
    }

    public void addItem(int i) {
        casePrgsList.add(i, new CasePrg());
        if (i <= lastExpandedCardPosition)
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
        CustomTextView txt_court_name, txt_court_time, txt_judgement, txt_floor, txt_room, txt_comment;
        CustomTextView txt_date, txt_judgement_header, txt_court_lavel;
        LinearLayout lay_expand;
        CheckBox indicator;
        ExpandableLinearLayout expandableView;
        ExpandListener expandListener = new ExpandListener() {
            @Override
            public void onExpandComplete() {
                if (lastExpandedCardPosition != getAdapterPosition() && recyclerView.findViewHolderForAdapterPosition(lastExpandedCardPosition) != null) {
                    ((ExpandableLinearLayout) recyclerView.findViewHolderForAdapterPosition(lastExpandedCardPosition).itemView.findViewById(R.id.expandableView)).setExpanded(false);

                    casePrgsList.get(lastExpandedCardPosition).setExpanded(false);

                    ((ExpandableLinearLayout) recyclerView.findViewHolderForAdapterPosition(lastExpandedCardPosition).itemView.findViewById(R.id.expandableView)).toggle();
                } else if (lastExpandedCardPosition != getAdapterPosition() && recyclerView.findViewHolderForAdapterPosition(lastExpandedCardPosition) == null) {
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
            indicator = (CheckBox) view.findViewById(R.id.indicator);
            txt_date = (CustomTextView) view.findViewById(R.id.txt_date);
            txt_judgement_header = (CustomTextView) view.findViewById(R.id.txt_judgement_header);
            txt_court_lavel = (CustomTextView) view.findViewById(R.id.txt_court_lavel);
            txt_court_name = (CustomTextView) view.findViewById(R.id.txt_court_name);
            txt_court_time = (CustomTextView) view.findViewById(R.id.txt_court_time);
            txt_floor = (CustomTextView) view.findViewById(R.id.txt_floor);
            txt_room = (CustomTextView) view.findViewById(R.id.txt_room);
            txt_judgement = (CustomTextView) view.findViewById(R.id.txt_judgement);
            txt_comment = (CustomTextView) view.findViewById(R.id.txt_comment);
            expandableView = itemView.findViewById(R.id.expandableView);
            lay_expand = (LinearLayout) view.findViewById(R.id.lay_expand);
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
