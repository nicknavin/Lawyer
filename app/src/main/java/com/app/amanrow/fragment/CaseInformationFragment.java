package com.app.amanrow.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amanrow.R;
import com.app.amanrow.databinding.FragmentCaseInformationBinding;
import com.app.amanrow.pojo.CaseCS;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaseInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaseInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private CaseCS caseDetail;
    private String mParam2;
    FragmentCaseInformationBinding binding;
    TextInputEditText txt_case_code, txt_case_number, txt_moj_no, txt_case_type, txt_case_lawsuit,txt_case_lavel,txt_case_defendant,txt_case_plaintiff,txt_case_status,txt_header;

    public CaseInformationFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment CaseInformationFragment.
//     */
    // TODO: Rename and change types and number of parameters
    public static CaseInformationFragment newInstance(CaseCS casedetail)
    {
        CaseInformationFragment fragment = new CaseInformationFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, casedetail);
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
//        View view=inflater.inflate(R.layout.fragment_case_information, container, false);
        binding =FragmentCaseInformationBinding.inflate(inflater,container,false);
//        initView(view);
       binding.setCases(caseDetail);
        return binding.getRoot();
    }

    private void initView(View view)
    {
        txt_case_status=(TextInputEditText)view.findViewById(R.id.txt_case_status);
        txt_case_status.setText(caseDetail.getStatusName());
        txt_case_code=(TextInputEditText)view.findViewById(R.id.txt_case_code);
        txt_case_code.setText(caseDetail.getCaseCd());
        txt_case_number=(TextInputEditText)view.findViewById(R.id.txt_case_number);
        txt_case_number.setText(caseDetail.getLawsuitNo());
        txt_moj_no=(TextInputEditText)view.findViewById(R.id.txt_moj_no);
        txt_moj_no.setText(caseDetail.getUniqueNumber());
        txt_case_type=(TextInputEditText)view.findViewById(R.id.txt_case_type);
        txt_case_type.setText(caseDetail.getCaseTypeName());
        txt_case_lawsuit=(TextInputEditText)view.findViewById(R.id.txt_case_lawsuit);
        txt_case_lawsuit.setText(caseDetail.getLawsuitNo());
        txt_case_lavel=(TextInputEditText)view.findViewById(R.id.txt_case_lavel);
        txt_case_lavel.setText(caseDetail.getLevelName());
        txt_case_defendant=(TextInputEditText)view.findViewById(R.id.txt_case_defendant);
        txt_case_defendant.setText(caseDetail.getDefendant());
        txt_case_plaintiff=(TextInputEditText)view.findViewById(R.id.txt_case_plaintiff);
        txt_case_plaintiff.setText(caseDetail.getPlaintiff());





    }



}