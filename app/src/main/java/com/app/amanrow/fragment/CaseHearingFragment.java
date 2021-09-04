package com.app.amanrow.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amanrow.R;
import com.app.amanrow.adapter.AtornyCaseProgressNewAdapter;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.interfaces.FunctionSelectCallback;
import com.app.amanrow.interfaces.RequestCallback;
import com.app.amanrow.pojo.CaseDetail;
import com.app.amanrow.pojo.CaseProgresprg;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaseHearingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaseHearingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    ArrayList<CaseProgresprg> casePRGArrayList;
    RecyclerView recyclerView;
    String dayinterval_cd="";
    CaseDetail caseDetail;
    public CaseHearingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CaseHearingFragment newInstance(ArrayList<CaseProgresprg> list, String id, CaseDetail data) {
        CaseHearingFragment fragment = new CaseHearingFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, list);
        args.putString(ARG_PARAM2,id);
        args.putParcelable(ARG_PARAM3,data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            casePRGArrayList = getArguments().getParcelableArrayList(ARG_PARAM1);
            caseDetail = getArguments().getParcelable(ARG_PARAM3);
            dayinterval_cd=getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_case_hearing, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (casePRGArrayList!=null)
        {
            AtornyCaseProgressNewAdapter atornyCaseProgressAdapter=new AtornyCaseProgressNewAdapter(getContext(), recyclerView, dayinterval_cd, new FunctionSelectCallback() {
                @Override
                public void response(String position, String code, String comment)
                {
                    int index= Integer.parseInt(position);
                    CaseProgresprg caseProgresprg=casePRGArrayList.get(index);
                    //CaseProgressDetail caseProgressDetail=casePRGArrayList.get(index);
                    postJudgmentCommentData(caseProgresprg,code,comment);
                }
            });
            atornyCaseProgressAdapter.setData(casePRGArrayList);
            recyclerView.setAdapter(atornyCaseProgressAdapter);
        }


    }
    public void postJudgmentCommentData(CaseProgresprg detail, String judgement_cd, String comment) {

        //if (Utility.isInternetConnected())
        {
           // showLoading();
            new BaseAsych(Urls.CASE_PROGRESS_COM, getpostJson(detail,judgement_cd,comment), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                   // dismiss_loading();
                   // showToast(success);
                    if(getActivity()!=null) {
                        getActivity().finish();
                    }
                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    //dismiss_loading();
                    //showToast(failed);
                }

                @Override
                public void onNull(JSONObject js, String nullp) {

                }

                @Override
                public void onException(JSONObject js, String exception) {

                }
            }).execute();

        }
//        else {
//            showInternetConnectionToast();
//        }
    }

    private String getpostJson(CaseProgresprg detail, String judgement_cd, String comment) {
        JSONObject object = null;
        try {
            object = new JSONObject();

            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            objinfo.put("company", "1");

            JSONObject objinput = new JSONObject();
            objinput.put("actioncode","judgement");
            objinput.put("case_cd", detail.getCaseCd());
            objinput.put("progres_ser_no", detail.getProgresSerNo());
            objinput.put("branch_cd", detail.getBranchNo());
            objinput.put("judgement_cd", judgement_cd);
            objinput.put("comment", comment);

            object.put("info", objinfo);
            object.put("input", objinput);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
//    private String getJson() {
//        JSONObject object = null;
//        try {
//            object = new JSONObject();
//
//            JSONObject objinfo = new JSONObject();
//            objinfo.put("lang", lang);
//            objinfo.put("company", company_cd);
//
//            JSONObject objemp = new JSONObject();
//            objemp.put("user_cd", userId);
//            objemp.put("actioncode", "insert");
//            objemp.put("case_cd", caseDetail.getCase_cd());
//            objemp.put("progres_ser_no",progres_ser_no);
//            objemp.put("branch_cd", DataPrefrence.getPref(context, Constant.BRANCH_NO,""));
//            objemp.put("court_date", date);
//            objemp.put("level_cd", level_cd);
//            objemp.put("level_type_cd", level_type_cd);
//            objemp.put("court_cd", court_cd);
//            objemp.put("court_time", txt_court_time.getText().toString());
//            objemp.put("floor_no", edt_floor_number.getText().toString());
//            objemp.put("room_no", edt_room_number.getText().toString());
//            objemp.put("judgement_cd", "2");
//            objemp.put("comment", "");
//
//            object.put("info", objinfo);
//            object.put("input", objemp);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return object.toString();
//    }
}