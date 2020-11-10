package com.app.lawyer.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.app.lawyer.R;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CustomEditText;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.pojo.AlertTypeSub;
import com.app.lawyer.pojo.CommanData;
import com.app.lawyer.utility.Constant;
import com.app.lawyer.utility.DataPrefrence;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class HearingSessionCreateFormActivity extends BaseActivity implements View.OnClickListener{
    AlertTypeSub caseDetail;
    CustomTextView txt_date,txt_court_time,btn_save;
    Spinner spinner_level,spinner_level_type,spinner_court;
    CustomEditText edt_floor_number,edt_room_number;
    private int mYear, mMonth, mDay;
    String court_cd="",level_cd="",level_type_cd="",progres_ser_no="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_session_create_form);
        if(getIntent().getParcelableExtra("DATA")!=null)
        {
            caseDetail=getIntent().getParcelableExtra("DATA");
        }
        initView();
    }

    private void initView()
    {
        ((CustomTextView) findViewById(R.id.toolbar_header)).setText(caseDetail.getCase_subject());
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save=(CustomTextView)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        txt_date=(CustomTextView)findViewById(R.id.txt_date);
        txt_court_time=(CustomTextView)findViewById(R.id.txt_court_time);
        edt_floor_number=(CustomEditText)findViewById(R.id.edt_floor_number);
        edt_room_number=(CustomEditText)findViewById(R.id.edt_room_number);
        spinner_court=(Spinner)findViewById(R.id.spinner_court);
        spinner_level=(Spinner)findViewById(R.id.spinner_level);
        spinner_level_type=(Spinner)findViewById(R.id.spinner_level_type);
        txt_date.setOnClickListener(this);
        txt_court_time.setOnClickListener(this);

        final ArrayList<CommanData> courtList= DataPrefrence.getObject(context, Constant.COURT_DATA);
        ArrayAdapter<CommanData> courtAdapter = new ArrayAdapter<CommanData>(context, R.layout.custom_spinner_layout, courtList);
        courtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_court.setAdapter(courtAdapter);

        final ArrayList<CommanData> levelList= DataPrefrence.getObject(context, Constant.COURT_LEVEL_DATA);
        ArrayAdapter<CommanData> levelAdapter = new ArrayAdapter<CommanData>(context, R.layout.custom_spinner_layout, levelList);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_level.setAdapter(levelAdapter);


        final ArrayList<CommanData> levelTypeList= DataPrefrence.getObject(context, Constant.LAWSUIT_TYPE__DATA);
        ArrayAdapter<CommanData> dataAdapter = new ArrayAdapter<CommanData>(context, R.layout.custom_spinner_layout, levelTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_level_type.setAdapter(dataAdapter);


        spinner_court.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String judgment = parent.getItemAtPosition(position).toString();
                System.out.println("days "+judgment);
                CommanData commanData=courtList.get(position);
                court_cd=commanData.getCode();
//                judgment_position=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String judgment = parent.getItemAtPosition(position).toString();
                System.out.println("days "+judgment);
              //  judgment_position=position+1;
                CommanData commanData=levelList.get(position);
                level_cd=commanData.getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_level_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String judgment = parent.getItemAtPosition(position).toString();
                System.out.println("days "+judgment);
              //  judgment_position=position+1;
                CommanData commanData=levelTypeList.get(position);
                level_type_cd=commanData.getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

    public void pickDOB() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txt_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

//        DatePickerDialog dateDialog = new DatePickerDialog(context, datePickerListener, mYear, mMonth, mDay);
//        dateDialog.getDatePicker().setMaxDate(new Date().getTime());
//        dateDialog.show();
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            String format = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
            txt_date.setText(format);


        }
    };


    private void pickTime()
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                txt_court_time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_date:
                pickDOB();
                break;
                case R.id.txt_court_time:
                    pickTime();
                break;
            case R.id.btn_save:
                callPostData();
                break;
        }
    }




    public void callPostDataold() {

        String url ="http://www.amanrow.com/ServiceLC.svc/case_progress_com";
        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(url, getParamPostData(), new RequestCallback()
            {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    showToast(success);

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    showToast(failed);


                }

                @Override
                public void onNull(JSONObject js, String nullp) {
                    dismiss_loading();
                }

                @Override
                public void onException(JSONObject js, String exception) {
                    dismiss_loading();
                }
            }).execute();

        } else {
            showInternetConnectionToast();
        }
    }
    private HashMap<String, Object> getParamPostData()
    {
        HashMap<String, Object> params = new HashMap<>();

        params.put("actioncode", "insert");
        params.put("user_cd", userId);
        params.put("case_cd", caseDetail.getCase_cd());
        params.put("progres_ser_no",progres_ser_no);
        params.put("branch_cd","1-1");
        params.put("court_date", txt_date.getText().toString());
        params.put("level_cd", level_cd);
        params.put("level_type_cd", level_type_cd);
        params.put("court_cd", court_cd);
        params.put("court_time", txt_court_time.getText().toString());
        params.put("floor_no", edt_floor_number.getText().toString());
        params.put("room_no", edt_room_number.getText().toString());
        params.put("judgement_cd", "2");
        params.put("comment", "");

        return params;
    }



    public void callPostData() {

        if (isInternetConnected())
        {
            String url ="http://www.amanrow.com/ServiceLC.svc/case_progress_com";
            showLoading();
            new BaseAsych(url, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success)
                {
                    dismiss_loading();

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    //showToast(failed);
                    showToast("Server error!! Please try after some time.");

                }

                @Override
                public void onNull(JSONObject js, String nullp) {

                }

                @Override
                public void onException(JSONObject js, String exception) {

                }
            }).execute();

        } else {
            showInternetConnectionToast();
        }
    }
    private String getJson() {
        JSONObject object = null;
        try {
            object = new JSONObject();

            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            objinfo.put("company", company_cd);

            JSONObject objemp = new JSONObject();
            objemp.put("user_cd", userId);
            objemp.put("actioncode", "insert");
            objemp.put("case_cd", caseDetail.getCase_cd());
            objemp.put("progres_ser_no",progres_ser_no);
            objemp.put("branch_cd","1-1");
            objemp.put("court_date", txt_date.getText().toString());
            objemp.put("level_cd", level_cd);
            objemp.put("level_type_cd", level_type_cd);
            objemp.put("court_cd", court_cd);
            objemp.put("court_time", txt_court_time.getText().toString());
            objemp.put("floor_no", edt_floor_number.getText().toString());
            objemp.put("room_no", edt_room_number.getText().toString());
            objemp.put("judgement_cd", "2");
            objemp.put("comment", "");

            object.put("info", objinfo);
            object.put("input", objemp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }





}
