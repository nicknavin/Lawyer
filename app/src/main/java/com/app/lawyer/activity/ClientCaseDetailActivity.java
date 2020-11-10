package com.app.lawyer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.lawyer.R;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.pojo.CasePrg;

public class ClientCaseDetailActivity extends BaseActivity {

    CasePrg casePrg;
    CustomTextView txt_court_name, txt_court_time, txt_court_date, txt_judgement, txt_court_location, txt_floor,txt_room, txt_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_case_detail);
        if (getIntent().getParcelableExtra("DATA") != null) {
            casePrg = getIntent().getParcelableExtra("DATA");
        }
        initView();
    }

    private void initView() {
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((CustomTextView)findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.hearing_detail_client));
        txt_court_name = (CustomTextView) findViewById(R.id.txt_court_name);
        txt_court_time = (CustomTextView) findViewById(R.id.txt_court_time);
        txt_court_date = (CustomTextView) findViewById(R.id.txt_court_date);
        txt_judgement = (CustomTextView) findViewById(R.id.txt_judgement);
        txt_room = (CustomTextView) findViewById(R.id.txt_room);

        txt_court_location = (CustomTextView) findViewById(R.id.txt_court_location);
        txt_floor = (CustomTextView) findViewById(R.id.txt_floor);
        txt_comment = (CustomTextView) findViewById(R.id.txt_comment);
        if(casePrg!=null)
        {
            txt_court_name.setText("Court : "+casePrg.getCourt_name());
            txt_court_location.setText("Location : "+casePrg);
            txt_court_date.setText("Date : "+casePrg.getCourt_date());
            txt_judgement.setText("Judgment : "+casePrg.getJudgement());
            txt_court_time.setText("Time : "+casePrg.getCourt_time());
            txt_room.setText("Room : "+casePrg.getRoom_no());
            txt_floor.setText("Floor : "+casePrg.getFloor_no());
            txt_comment.setText("Comment : "+casePrg.getComment());

        }


    }


}
