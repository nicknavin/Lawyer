package com.app.lawyer.adapter;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.app.lawyer.R;
import com.app.lawyer.customview.CustomEditText;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.customview.ExpandListener;
import com.app.lawyer.customview.ExpandableLinearLayout;
import com.app.lawyer.interfaces.FunctionSelectCallback;
import com.app.lawyer.pojo.CaseProgresprg;
import com.app.lawyer.pojo.CommanData;
import com.app.lawyer.utility.Constant;
import com.app.lawyer.utility.DataPrefrence;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class AtornyCaseProgressNewAdapter extends RecyclerView.Adapter<AtornyCaseProgressNewAdapter.ViewHolder> {
    Context context;
    private ArrayList<CaseProgresprg> casePrgsList;
    private int lastExpandedCardPosition;
    private RecyclerView recyclerView;
    Typeface tfavv;
    String dayinterval_cd;
    public final static int LAYOUT_ONE = 1;
    public final static int LAYOUT_TWO = 2;
    boolean flag = false;
    int judgment_position=0;
    ArrayList<CommanData> judgmentArrayList = new ArrayList<>();
    FunctionSelectCallback apiCallback;

    public AtornyCaseProgressNewAdapter(Context context, RecyclerView recyclerView, String dayinterval_cd, FunctionSelectCallback apiCallback) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.dayinterval_cd = dayinterval_cd;
        this.apiCallback = apiCallback;
        tfavv = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
        judgmentArrayList = DataPrefrence.getObject(context, Constant.JUDGMENT_DATA);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = null;
        if (dayinterval_cd.equals("1") || dayinterval_cd.equals("-1")) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_atorny_case_progress, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_atorny_case_progress_future, parent, false);
        }


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final AtornyCaseProgressNewAdapter.ViewHolder holder, final int position) {
        CaseProgresprg detail = casePrgsList.get(position);
        holder.txt_judgement_header.setText(detail.getJudgementName());
        holder.txt_court_name.setText(detail.getCourtName());
//        holder.txt_comment.setText(detail.getComment());
        holder.txt_court_time.setText(detail.getCourtTime());
        holder.txt_date.setText(detail.getCourtDate());
//      holder.txt_level.setText(detail.get());
        // holder.indicator.setChecked(true);

        if (!flag) {
            detail.setExpanded(true);
            flag = true;

        }


        holder.txt_floor.setText(detail.getFloorNo());
        holder.txt_room.setText(detail.getRoomNo());


        if (casePrgsList.get(position).isExpanded()) {
            holder.expandableView.setVisibility(View.VISIBLE);
            holder.expandableView.setExpanded(true);
        } else {
            holder.expandableView.setVisibility(View.GONE);
            holder.expandableView.setExpanded(false);
        }

        holder.lay_expand.setTag(detail);
        holder.lay_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CaseProgressDetail detail = (CaseProgressDetail) v.getTag();
//                Intent intent = new Intent(context, CaseDetailActivity.class);
//                intent.putExtra("DATA", detail);
//                context.startActivity(intent);
            }
        });


        if(position!=0)
        {
            holder.layCourt.setVisibility(View.GONE);
            holder.layFloor.setVisibility(View.GONE);
            holder.layRoom.setVisibility(View.GONE);
            holder.layTime.setVisibility(View.GONE);
        }


        if (dayinterval_cd.equals("1") || dayinterval_cd.equals("-1"))
        {
            holder.edt_comment.setText(detail.getComment());

            if (position == 0) {
                holder.lay_comment_judgment.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.lay_comment_judgment.setVisibility(View.GONE );

            }

            holder.spinner_judgment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String judgment = parent.getItemAtPosition(position).toString();
                    System.out.println("days "+judgment);
                    judgment_position=position+1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ArrayAdapter<CommanData> dataAdapter = new ArrayAdapter<CommanData>(context, android.R.layout.simple_spinner_item, judgmentArrayList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner_judgment.setAdapter(dataAdapter);
            holder.btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String comment = holder.edt_comment.getText().toString();
                    apiCallback.response(""+position, ""+judgment_position,comment);
                }
            });

        }

    }


    public void setData(ArrayList<CaseProgresprg> casePrgsList) {
        this.casePrgsList = casePrgsList;
    }

    public void addItem(int i) {
        casePrgsList.add(i, new CaseProgresprg());
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
        CustomTextView txt_court_name, txt_court_time, txt_floor, txt_room, txt_level, btn_save;
        Spinner spinner_judgment;
        CustomEditText edt_comment;
        CustomTextView txt_date, txt_judgement_header, txt_court_lavel;
        LinearLayout lay_expand;
        CheckBox indicator;
        LinearLayout lay_comment_judgment;
        ExpandableLinearLayout expandableView;

        LinearLayout layCourt,layTime,layFloor,layRoom;

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
            layCourt   =(LinearLayout)view.findViewById(R.id.layCourt);
            layTime   =(LinearLayout)view.findViewById(R.id.layTime);
            layFloor   =(LinearLayout)view.findViewById(R.id.layFloor);
            layRoom   =(LinearLayout)view.findViewById(R.id.layRoom);

            indicator = (CheckBox) view.findViewById(R.id.indicator);
            txt_date = (CustomTextView) view.findViewById(R.id.txt_date);
            txt_judgement_header = (CustomTextView) view.findViewById(R.id.txt_judgement_header);
            txt_court_lavel = (CustomTextView) view.findViewById(R.id.txt_court_lavel);
            txt_court_name = (CustomTextView) view.findViewById(R.id.txt_court_name);
            txt_court_time = (CustomTextView) view.findViewById(R.id.txt_court_time);
            txt_floor = (CustomTextView) view.findViewById(R.id.txt_floor);
            txt_room = (CustomTextView) view.findViewById(R.id.txt_room);
            txt_level = (CustomTextView) view.findViewById(R.id.txt_level);
            if (dayinterval_cd.equals("1") || dayinterval_cd.equals("-1")) {
                spinner_judgment = (Spinner) view.findViewById(R.id.spinner_judgment);
                edt_comment = (CustomEditText) view.findViewById(R.id.edt_comment);
                lay_comment_judgment = (LinearLayout) view.findViewById(R.id.lay_comment_judgment);
                btn_save = (CustomTextView) view.findViewById(R.id.btn_save);
            }
            expandableView = itemView.findViewById(R.id.expandableView);
            lay_expand = (LinearLayout) view.findViewById(R.id.lay_expand);
            expandableView.setExpandListener(expandListener);
            initializeClicks();
        }

        private void initializeClicks() {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flag = true;
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
