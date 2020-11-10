package com.app.lawyer.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.lawyer.R;
import com.app.lawyer.adapter.RemarkAdapter;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.customview.CustomEditText;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.pojo.CaseDetail;
import com.app.lawyer.pojo.ProgressreMark;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HearingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HearingFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String case_code = "";
    String pgsno = "";
    // TODO: Rename and change types of parameters
    private CaseDetail mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList<ProgressreMark> progressreMarksList = new ArrayList<>();
    RecyclerView recycler_view;
    LinearLayoutManager linearLayoutManager;
    CustomEditText edt_remark;
    Button btn_remark;
    View view;
    CustomTextView txt_client_name, txt_case_opponent_name, txt_case_subject, txt_court_name, txt_progres_date, txt_progres_description;
    CustomTextView txt_case,txt_case_number,txt_case_type,txt_case_lawsuit,txt_case_lavel,txt_case_defendant,txt_case_plaintiff,txt_court_location,txt_judgment;
    Context context;
    String caseData="";
    JSONObject objCaseDetail=null;
    JSONArray jsonArrayRemark=null;
    JSONObject objcs_prg=null;
    public HearingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *   * @param param1 Parameter 1.
     * @return A new instance of fragment HearingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HearingFragment newInstance(String data) {
        HearingFragment fragment = new HearingFragment();
        Bundle args = new Bundle();

        args.putString("DATA",data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }

        print("onAttach");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        if (getArguments() != null) {

            caseData=getArguments().getString("DATA");
            try {
                JSONObject jsonObject= new JSONObject(caseData);
                objCaseDetail=jsonObject.getJSONObject("cs");
                objcs_prg=jsonObject.getJSONObject("cs_prg");
                jsonArrayRemark=jsonObject.getJSONArray("progressremark");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        print("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        print("onCreateView");
        return inflater.inflate(R.layout.fragment_hearing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        print("onViewCreated");
        this.view = view;
        initView();
    }
    // TODO: Rename method, update argument and hook method into UI event

    private void initView() {

        edt_remark = (CustomEditText) view.findViewById(R.id.edt_remark);
        btn_remark = (Button) view.findViewById(R.id.btn_submit);
        btn_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRemarkData();
            }
        });
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(linearLayoutManager);

        txt_case = (CustomTextView) view.findViewById(R.id.txt_case);
        txt_judgment = (CustomTextView) view.findViewById(R.id.txt_judgment );

        txt_case_number = (CustomTextView) view.findViewById(R.id.txt_case_number);
        txt_case_type = (CustomTextView) view.findViewById(R.id.txt_case_type);
        txt_case_lawsuit = (CustomTextView) view.findViewById(R.id.txt_case_lawsuit);
        txt_case_lavel = (CustomTextView) view.findViewById(R.id.txt_case_lavel);
        txt_case_defendant = (CustomTextView) view.findViewById(R.id.txt_case_defendant);
        txt_case_plaintiff = (CustomTextView) view.findViewById(R.id.txt_case_plaintiff);
        txt_court_location = (CustomTextView) view.findViewById(R.id.txt_court_location);


        txt_case_opponent_name = (CustomTextView) view.findViewById(R.id.txt_case_opponent_name);
        txt_case_subject = (CustomTextView) view.findViewById(R.id.txt_case_subject);
        txt_client_name = (CustomTextView) view.findViewById(R.id.txt_client_name);
        txt_court_name = (CustomTextView) view.findViewById(R.id.txt_court_name);
        txt_progres_date = (CustomTextView) view.findViewById(R.id.txt_progres_date);
        txt_progres_description = (CustomTextView) view.findViewById(R.id.txt_progres_description);
//        if (caseDetail != null) {
//
//            txt_case_opponent_name.setText(context.getResources().getString(R.string.opponent_name) + " " + caseDetail.getCase_opponent_name().replace("null", ""));
//            txt_case_subject.setText(context.getResources().getString(R.string.case_subject) + " " + caseDetail.getCase_subject().replace("null", ""));
//            txt_client_name.setText(context.getResources().getString(R.string.client_name) + " " + caseDetail.getCase_client_name().replace("null", ""));
//            txt_court_name.setText(context.getResources().getString(R.string.court_name) + " " + caseDetail.getCourt_name().replace("null", ""));
//            txt_progres_date.setText(context.getResources().getString(R.string.courte_data) + " " + caseDetail.getCourt_date().replace("null", ""));
//            txt_progres_description.setText(context.getResources().getString(R.string.progress_discription) + " " + caseDetail.getProgres_description().replace("null", ""));
//        }

        if(objCaseDetail!=null)
        {
            try {

                txt_case.setText(objCaseDetail.getString("case_cd"));
                txt_case_number.setText(objCaseDetail.getString("lawsuit_no"));
                txt_case_defendant.setText(objCaseDetail.getString("defendant"));
                txt_case_lavel.setText(objCaseDetail.getString("level_name")+" "+objCaseDetail.getString("level_type_name"));
                txt_case_lawsuit.setText(objCaseDetail.getString("lawsuit_type_name"));
                txt_case_plaintiff.setText(objCaseDetail.getString("plaintiff"));
                txt_case_type.setText(objCaseDetail.getString("case_type_name"));

                txt_case_subject.setText( objCaseDetail.getString("case_subject").replace("null", ""));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(objcs_prg!=null)
        {
            try {
                txt_judgment.setText( objcs_prg.getString("judgement_name").replace("null", ""));
                txt_court_name.setText(objcs_prg.getString("court_name").replace("null", ""));
                String location="Floor: "+objcs_prg.getString("floor_no")+"|Room No: "+objcs_prg.getString("room_no")+"|Time: "+objcs_prg.getString("court_time").replace("null", "");
                txt_court_location.setText(location);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if(jsonArrayRemark!=null)
        {
          //  JSONArray jsonArray = js.getJSONArray("progressremark");
            Type type = new TypeToken<ArrayList<ProgressreMark>>() {
            }.getType();
            progressreMarksList = new Gson().fromJson(jsonArrayRemark.toString(), type);
            RemarkAdapter remarkAdapter = new RemarkAdapter(getContext(), progressreMarksList);
            recycler_view.setAdapter(remarkAdapter);
        }





        getRemarkData();
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        print("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        print("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        print("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        print("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        print("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        print("onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        print("onDetach");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void print(String msg) {
        System.out.println("Print : " + msg);
    }

    public void getRemarkData() {

        if (baseActivity.isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.GETPROGRESSREMARK, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    try {
                        JSONArray jsonArray = js.getJSONArray("progressremark");
                        Type type = new TypeToken<ArrayList<ProgressreMark>>() {
                        }.getType();
                        progressreMarksList = new Gson().fromJson(jsonArray.toString(), type);
                        RemarkAdapter remarkAdapter = new RemarkAdapter(getContext(), progressreMarksList);
                        recycler_view.setAdapter(remarkAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    //showToast(failed);
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
            objinfo.put("company", "1");
            object.put("ccd", case_code);
            object.put("pgsno", pgsno);
            object.put("info", objinfo);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public void postRemarkData() {

        if (baseActivity.isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.INSERTPROGRESSREMARK, getpostJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    getRemarkData();

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    //showToast(failed);
                    showToast("Email or Password is invalid");

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

    private String getpostJson() {
        JSONObject object = null;
        try {
            object = new JSONObject();

            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            objinfo.put("company", "1");
            object.put("info", objinfo);
            object.put("ccd", case_code);
            object.put("pgsno", pgsno);
            object.put("userid", userId);
            object.put("remark", edt_remark.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            getRemarkData();
        }
    }

}
