package com.app.amanrow.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.amanrow.R;
import com.app.amanrow.adapter.ArtornyAlertNewAdapter;
import com.app.amanrow.adapter.ArtornyCaseNewAdapter;
import com.app.amanrow.adapter.CaseDetailAdapter;
import com.app.amanrow.adapter.HeaderChildAdapter;
import com.app.amanrow.adapter.MyExpandableAdapter;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.customview.AnimatedExpandableListView;
import com.app.amanrow.interfaces.RequestCallback;
import com.app.amanrow.pojo.AlertType;
import com.app.amanrow.pojo.AlertTypeSub;
import com.app.amanrow.pojo.CaseClient;
import com.app.amanrow.pojo.CaseDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyRoleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRoleFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<CaseClient> clientsList;
    List<CaseDetail> caseDetailList;
    CaseDetailAdapter caseDetailAdapter;
    HeaderChildAdapter headerChildAdapter;
    RecyclerView recyclerView,recycler_view_alert;
    LinearLayoutManager linearLayoutManager;
    MyExpandableAdapter adapter;
    AnimatedExpandableListView list_menu;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyRoleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyRoleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyRoleFragment newInstance(String param1, String param2) {
        MyRoleFragment fragment = new MyRoleFragment();
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
        View view =inflater.inflate(R.layout.fragment_my_role, container, false);
        initView(view);
        return view;
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



    public void initView(View view) {

        list_menu = (AnimatedExpandableListView) view.findViewById(R.id.list_menu);
//        ((CustomTextView)view. findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.menu_roll));

        recycler_view_alert=(RecyclerView)view.findViewById(R.id.recycler_view_alert);
        recycler_view_alert.setLayoutManager(new LinearLayoutManager(context));

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        //   headerChildAdapter=new HeaderChildAdapter(context,clientsList);
        //   caseDetailAdapter=new CaseDetailAdapter(context,clientsList);

        list_menu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group_selected
                // expansion/collapse.
                if (list_menu.isGroupExpanded(groupPosition)) {
                    list_menu.collapseGroupWithAnimation(groupPosition);
                    ((ImageView) v.findViewById(R.id.indicator)).setImageResource(R.mipmap.add);
                    ((LinearLayout) v.findViewById(R.id.lay_placehlder)).setVisibility(View.GONE);
                    //.setImageResource(isExpanded ? R.drawable.dropdownicon : R.drawable.dropdownicon);
                } else {
                    ((ImageView) v.findViewById(R.id.indicator)).setImageResource(R.mipmap.minus);
                    ((LinearLayout) v.findViewById(R.id.lay_placehlder)).setVisibility(View.VISIBLE);
                    list_menu.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
        list_menu.setGroupIndicator(null);


    }

    @Override
    public void onResume() {
        super.onResume();
        getUserRole();
    }

    public void getUserRole() {

        if (baseActivity.isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.ATT_USER_ROLE, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    try {
                        JSONObject object = js.getJSONObject("result");
                        String rdescription = object.getString("rdescription");

                        JSONArray jsonArrayType = js.getJSONArray("atype1");
                        ArrayList<AlertType> alertTypesList = new ArrayList<>();
                        for (int a = 0; a < jsonArrayType.length(); a++)
                        {

                            JSONObject alertObj = jsonArrayType.getJSONObject(a);
                            AlertType alertType = new AlertType();
                            alertType.setAlert_type_cd(alertObj.getString("alert_type_cd"));
                            alertType.setAlert_type_name(alertObj.getString("alert_type_name"));
                            alertType.setCnt(alertObj.getString("cnt"));
                            JSONArray dlistJsnArray = alertObj.getJSONArray("dlist");
                            ArrayList<AlertTypeSub> alertTypeSubsList=new ArrayList<>();

                            for (int b = 0; b < dlistJsnArray.length(); b++)
                            {
                                AlertTypeSub alertTypeSub = new AlertTypeSub();
                                JSONObject dlistObj = dlistJsnArray.getJSONObject(b);
                                alertTypeSub.setAlert_type_cd(dlistObj.getString("alert_type_cd"));
                                alertTypeSub.setAlert_type_name(dlistObj.getString("alert_type_name"));
                                alertTypeSub.setCase_cd(dlistObj.getString("case_cd"));
                                alertTypeSub.setCase_subject(dlistObj.getString("case_subject"));
                                alertTypeSub.setCourt_name(dlistObj.getString("court_name"));
                                alertTypeSub.setJudgement(dlistObj.getString("judgement"));
                                alertTypeSubsList.add(alertTypeSub);
                                alertType.setAlertTypeSubs(alertTypeSubsList);
                            }

                            alertTypesList.add(alertType);
                        }


                        ArtornyAlertNewAdapter artornyAlertNewAdapter=new ArtornyAlertNewAdapter(context,recycler_view_alert);
                        artornyAlertNewAdapter.setData(alertTypesList);
                        recycler_view_alert.setAdapter(artornyAlertNewAdapter);



                        JSONArray array = js.getJSONArray("di");
//                        Type type = new TypeToken<ArrayList<CaseClient>>() {}.getType();
//                        clientsList = new Gson().fromJson(array.toString(), type);
                        clientsList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            CaseClient client = new CaseClient();
                            client.setCnt(obj.getString("cnt"));
                            client.setDayinterval_cd(obj.getString("dayinterval_cd"));
                            client.setDayinterval_name(obj.getString("dayinterval_name"));
                            JSONArray jsonArray = obj.getJSONArray("dlist");
                            caseDetailList = new ArrayList<>();
                            for (int j = 0; j < jsonArray.length(); j++) {
                                CaseDetail caseDetail = new CaseDetail();
                                JSONObject objCase = jsonArray.getJSONObject(j);
                                caseDetail.setCase_cd(objCase.getString("case_cd"));
                                caseDetail.setCase_client_name(objCase.getString("case_client_name"));
                                caseDetail.setCase_opponent_name(objCase.getString("case_opponent_name"));
                                caseDetail.setCase_subject(objCase.getString("case_subject"));
                                caseDetail.setCourt_name(objCase.getString("court_name"));
                                caseDetail.setCourt_time(objCase.getString("court_time"));
                                caseDetail.setCourt_date(objCase.getString("court_date"));
                                caseDetail.setProgres_description(objCase.getString("progres_description"));
                                caseDetail.setProgres_ser_no(objCase.getString("progres_ser_no"));
                                caseDetailList.add(caseDetail);
                                client.setCaseArrayList(caseDetailList);
                            }

                            clientsList.add(client);

                        }

                        ArtornyCaseNewAdapter artornyCaseNewAdapter = new ArtornyCaseNewAdapter(context,recyclerView);
                        artornyCaseNewAdapter.setData(clientsList);
                        recyclerView.setAdapter(artornyCaseNewAdapter);


//                        if (clientsList.size() > 0) {
//                            adapter = new MyExpandableAdapter(context, clientsList);
//                            list_menu.setAdapter(adapter);
//
//
//                            for (int i = 0; i < clientsList.size(); i++) {
//                                list_menu.expandGroupWithAnimation(i);
//                            }
//
//                        }
//                        headerChildAdapter=new HeaderChildAdapter(context,clientsList);
//                        recyclerView.setAdapter(headerChildAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    //showToast(failed);
                    showToast("Server error!! Please try after some time.");

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

    private String getJson() {
        JSONObject object = null;
        try {
            object = new JSONObject();

            JSONObject objinfo = new JSONObject();
             objinfo.put("lang", lang);
            objinfo.put("company", baseActivity.company_cd);

            JSONObject objemp = new JSONObject();
            objemp.put("user_cd", userId);

            object.put("info", objinfo);
            object.put("input", objemp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }









}
