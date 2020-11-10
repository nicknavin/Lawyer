package com.app.lawyer.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.app.lawyer.R;
import com.app.lawyer.activity.ClientCaseSubjectDetailActivity;
import com.app.lawyer.adapter.ClientCaseDetailAdapter;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.pojo.CasePrg;
import com.app.lawyer.pojo.ClientCaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.app.lawyer.utility.Methods.isInternetConnected;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientCaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientCaseFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recycler_view;
    ArrayList<ClientCaseData> clientCaseDataList=new ArrayList<>();
    String subjectData="";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClientCaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientCaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientCaseFragment newInstance(String param1, String param2) {
        ClientCaseFragment fragment = new ClientCaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_case, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view)
    {

        recycler_view=(RecyclerView)view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recycler_view.setLayoutManager(linearLayoutManager);

        getClientCaseRead();

    }

    public void getClientCaseRead() {

        if (isInternetConnected(getContext())) {
            showLoading();
            new BaseAsych(Urls.GCLIENT_CASE_READ, getjsonClientCaseRegister(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    try {
                        subjectData=js.toString();
                        clientCaseDataList=new ArrayList<>();
                        JSONArray jsonArray=js.getJSONArray("gc_cases");

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            ClientCaseData data =new ClientCaseData();
                            JSONObject object=jsonArray.getJSONObject(i);
                            data.setAttorny_office_name(object.getString("attorny_office_name"));
                            data.setCase_cd(object.getString("case_cd"));
                            data.setCase_subject(object.getString("case_subject"));
                            data.setCompany_cd(object.getString("company_cd"));
                            data.setCourt_level_name(object.getString("court_level_name"));
                            data.setGlobal_client_cd(object.getString("global_client_cd"));
                            data.setLawsuit_number(object.getString("lawsuit_number"));
                            data.setNext_hearing_date(object.getString("next_hearing_date"));
                            data.setStatus_name(object.getString("status_name"));
                            data.setUnique_number(object.getString("unique_number"));
                            data.setData(object.getJSONArray("prg").toString());
                            ArrayList<CasePrg> casePrgsList=new ArrayList<>();
                            clientCaseDataList.add(data);

                        }




                            ClientCaseDetailAdapter clientCaseDetailAdapter = new ClientCaseDetailAdapter(context, clientCaseDataList, subjectData);
                            recycler_view.setAdapter(clientCaseDetailAdapter);
                            if(clientCaseDataList.size()<2) {
                                ClientCaseData caseData = clientCaseDataList.get(0);
                                Intent intent = new Intent(context, ClientCaseSubjectDetailActivity.class);
                                intent.putExtra("DATA", subjectData);
                                intent.putExtra("CLIENT_CASE_DATA", caseData);
                                context.startActivity(intent);
                            }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    //{"info":{"lang":"EN"},"user_name":"shafi", "user_pwd":"2"}
    private String getjsonClientCaseRegister() {
        try {
            JSONObject object = new JSONObject();
            JSONObject jsonObjinput = new JSONObject();

            jsonObjinput.put("global_client_cd", userId);


            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");

            object.put("info", objinfo);
            object.put("input", jsonObjinput);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
