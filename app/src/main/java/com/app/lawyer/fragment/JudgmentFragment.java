package com.app.lawyer.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lawyer.R;
import com.app.lawyer.customview.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JudgmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JudgmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String caseDetail;
    View view;
    Context context;
    private OnFragmentInteractionListener mListener;
CustomTextView txt_client_name,txt_case_opponent_name,txt_case_subject,txt_court_name,txt_progres_date,txt_progres_description;
    public JudgmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment JudgmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JudgmentFragment newInstance(String param1) {
        JudgmentFragment fragment = new JudgmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
   //     args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     context=getContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_judgment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        initView();
    }




    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void initView()
    {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

//        txt_case_opponent_name=(CustomTextView)view.findViewById(R.id.txt_case_opponent_name);
//        txt_case_subject=(CustomTextView)view.findViewById(R.id.txt_case_subject);
//        txt_client_name=(CustomTextView)view.findViewById(R.id.txt_client_name);
//        txt_court_name=(CustomTextView)view.findViewById(R.id.txt_court_name);
//        txt_progres_date=(CustomTextView)view.findViewById(R.id.txt_progres_date);
//        txt_progres_description=(CustomTextView)view.findViewById(R.id.txt_progres_description);
//        if(caseDetail!=null)
//        {
//            txt_case_opponent_name.setText(context.getResources().getString(R.string.opponent_name)+" "+caseDetail.getCase_opponent_name().replace("null",""));
//            txt_case_subject.setText(context.getResources().getString(R.string.case_subject)+" "+caseDetail.getCase_subject().replace("null",""));
//            txt_client_name.setText(context.getResources().getString(R.string.client_name)+" "+caseDetail.getCase_client_name().replace("null",""));
//            txt_court_name.setText(context.getResources().getString(R.string.court_name)+" "+caseDetail.getCourt_name().replace("null",""));
//            txt_progres_date.setText(context.getResources().getString(R.string.courte_data)+" "+caseDetail.getCourt_date().replace("null",""));
//            txt_progres_description.setText(context.getResources().getString(R.string.progress_discription)+" "+caseDetail.getProgres_description().replace("null",""));
//        }

    }















    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
