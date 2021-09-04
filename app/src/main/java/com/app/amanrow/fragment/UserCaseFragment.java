package com.app.amanrow.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amanrow.R;
import com.app.amanrow.adapter.ClientCaseNewAdapter;
import com.app.amanrow.pojo.CasePrg;
import com.app.amanrow.pojo.ClientCaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserCaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCaseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ClientCaseData caseDetail=null;
    public UserCaseFragment() {
        // Required empty public constructor
    }
RecyclerView recyclerView;

    // TODO: Rename and change types and number of parameters
    public static UserCaseFragment newInstance(ClientCaseData detail) {
        UserCaseFragment fragment = new UserCaseFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, detail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            caseDetail = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_case, container, false);
        initView(view);
        return view;
    }
    ArrayList<CasePrg> casePrgsList=new ArrayList<>();
    private void initView(View view)
    {
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            JSONArray arrayPrg=new JSONArray(caseDetail.getData());
            for(int j=0;j<arrayPrg.length();j++)
            {
                JSONObject objPrg=arrayPrg.getJSONObject(j);
                CasePrg casePrg=new CasePrg();
                casePrg.setCase_cd(objPrg.getString("case_cd"));
                casePrg.setCase_prg_no(objPrg.getString("case_prg_no"));
                casePrg.setComment(objPrg.getString("comment"));
                casePrg.setCompany_cd(objPrg.getString("company_cd"));
                casePrg.setCourt_date(objPrg.getString("court_date"));
                casePrg.setCourt_level_name(objPrg.getString("court_level_name"));
                casePrg.setCourt_name(objPrg.getString("court_name"));
                casePrg.setCourt_time(objPrg.getString("court_time"));
                casePrg.setFloor_no(objPrg.getString("floor_no"));
                casePrg.setJudgement(objPrg.getString("judgement"));
                casePrg.setRoom_no(objPrg.getString("room_no"));
                if(j==0) {
                    casePrg.setExpanded(true);
                }
                casePrgsList.add(casePrg);
            }

            ClientCaseNewAdapter clientCaseNewAdapter=new ClientCaseNewAdapter(getContext(),recyclerView);
            clientCaseNewAdapter.setData(casePrgsList);
            recyclerView.setAdapter(clientCaseNewAdapter);


//                if (casePrgsList.size() > 0) {
//
//                    adapter=new ClientExpandableAdapter(context,casePrgsList);
//                    list_menu.setAdapter(adapter);
//
//
//                    for (int i = 0; i < casePrgsList.size(); i++)
//                    {
//                        list_menu.expandGroupWithAnimation(0);
//                    }
//
//                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}