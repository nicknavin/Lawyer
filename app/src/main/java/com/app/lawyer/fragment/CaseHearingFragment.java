package com.app.lawyer.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lawyer.R;
import com.app.lawyer.adapter.AtornyCaseProgressNewAdapter;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.interfaces.FunctionSelectCallback;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.pojo.CaseProgresprg;

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

    // TODO: Rename and change types of parameters
    ArrayList<CaseProgresprg> casePRGArrayList;
    RecyclerView recyclerView;
    String dayinterval_cd="";
    public CaseHearingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CaseHearingFragment newInstance(ArrayList<CaseProgresprg> list, String id) {
        CaseHearingFragment fragment = new CaseHearingFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, list);
        args.putString(ARG_PARAM2,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            casePRGArrayList = getArguments().getParcelableArrayList(ARG_PARAM1);
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
            objinput.put("judgement_cd  ", judgement_cd);
            objinput.put("comment  ", comment);

            object.put("info", objinfo);
            object.put("input", objinput);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}