package com.app.amanrow.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amanrow.R;
import com.app.amanrow.adapter.CaseRemarkAdapter;
import com.app.amanrow.pojo.CaseProgressRemark;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaseRemarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaseRemarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CaseRemarkFragment() {
        // Required empty public constructor
    }

    ArrayList<CaseProgressRemark> caseProgressRemarkArrayList;
    RecyclerView recyclerView;


    public static CaseRemarkFragment newInstance(ArrayList<CaseProgressRemark> list) {
        CaseRemarkFragment fragment = new CaseRemarkFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, list);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            caseProgressRemarkArrayList = getArguments().getParcelableArrayList(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View view=inflater.inflate(R.layout.fragment_case_remark, container, false);
    initView(view);
        return view;
}

    private void initView(View view)
    {
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(caseProgressRemarkArrayList!=null) {
            CaseRemarkAdapter caseRemarkAdapter = new CaseRemarkAdapter(getContext(), caseProgressRemarkArrayList);
            recyclerView.setAdapter(caseRemarkAdapter);
        }

    }

}