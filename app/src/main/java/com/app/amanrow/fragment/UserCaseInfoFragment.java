package com.app.amanrow.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amanrow.R;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.databinding.FragmentUserCaseInfoBinding;
import com.app.amanrow.pojo.ClientCaseData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserCaseInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCaseInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserCaseInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserCaseInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    ClientCaseData caseDetail=null;
    FragmentUserCaseInfoBinding binding;
    public static UserCaseInfoFragment newInstance(ClientCaseData detail) {
        UserCaseInfoFragment fragment = new UserCaseInfoFragment();
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
//        View view=inflater.inflate(R.layout.fragment_user_case_info, container, false);
        binding =FragmentUserCaseInfoBinding.inflate(inflater,container,false);
//        initView(view);
        binding.setClientCaseData(caseDetail);
        return binding.getRoot();

    }

    private void initView(View view)
    {
        ((CustomTextView)view. findViewById(R.id.txt_laywer_office)).setText(caseDetail.getAttorny_office_name());
        ((CustomTextView)view. findViewById(R.id.txt_court_lavel)).setText(caseDetail.getCourt_level_name());
        ((CustomTextView) view.findViewById(R.id.txt_case_no)).setText(caseDetail.getLawsuit_number());
        ((CustomTextView) view.findViewById(R.id.txt_moj_no)).setText(caseDetail.getUnique_number());
        ((CustomTextView) view.findViewById(R.id.txt_status)).setText(caseDetail.getStatus_name());

    }


}