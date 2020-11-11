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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Spinner;

import com.app.lawyer.R;
import com.app.lawyer.activity.ShowAttachmentActivity;
import com.app.lawyer.adapter.CaseAttachmentAdapter;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.customview.CustomImageView;
import com.app.lawyer.interfaces.ApiCallback;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.pojo.CaseDetail;
import com.app.lawyer.pojo.CaseDocs;
import com.app.lawyer.pojo.DocDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CaseAttachmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaseAttachmentFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<CaseDocs> caseDocsArrayList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList<DocDetail> docDetailArrayList = new ArrayList<>();
    RecyclerView recycler_view;
    LinearLayoutManager linearLayoutManager;
    Context context;
    private CaseDetail caseDetail;
    String case_code = "";
    String pgsno = "";
    String caseData="";
    JSONArray jsonArrayDoc=null;
    Spinner spinner;
    CustomImageView img_doc;
    WebView webView;
    public CaseAttachmentFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CaseAttachmentFragment newInstance(ArrayList<CaseDocs> list) {
        CaseAttachmentFragment fragment = new CaseAttachmentFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
//        if (getArguments() != null) {
//            caseData=getArguments().getString(ARG_PARAM1);
//            try {
//                if(caseData!=null) {
//                    JSONObject jsonObject = new JSONObject(caseData);
//                     jsonArrayDoc = jsonObject.getJSONArray("docs");
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        if (getArguments() != null)
        {
            caseDocsArrayList = getArguments().getParcelableArrayList(ARG_PARAM1);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_case_remark, container, false);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);


    }
    RecyclerView recyclerView;
    private void initView(View view)
    {
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (caseDocsArrayList!=null) {
            CaseAttachmentAdapter caseAttachmentAdapter=new CaseAttachmentAdapter(getContext(),caseDocsArrayList, new ApiCallback() {
                @Override
                public void result(String x) {
                    Intent intent=new Intent(context, ShowAttachmentActivity.class);
                    intent.putExtra("URL",x);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(caseAttachmentAdapter);
        }

    }

//    private void initView(View view)
//    {
//
//        if(jsonArrayDoc!=null)
//        {
//            //  JSONArray jsonArray = js.getJSONArray("progressremark");
//            Type type = new TypeToken<ArrayList<DocDetail>>() {
//            }.getType();
//            docDetailArrayList = new Gson().fromJson(jsonArrayDoc.toString(), type);
//
//        }
//         spinner = (Spinner)view.findViewById(R.id.spinner);
//        ArrayAdapter<DocDetail> adapter =
//                new ArrayAdapter<DocDetail>(getContext(),  android.R.layout.simple_spinner_dropdown_item, docDetailArrayList);
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        img_doc=view.findViewById(R.id.img_doc);
//        webView=view.findViewById(R.id.webview);
//        webView.setWebViewClient(new AppWebViewClients());
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUseWideViewPort(true);
//
//
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                Toast.makeText(parent.getContext(),
////                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
////                        Toast.LENGTH_SHORT).show();
//                String item_position = String.valueOf(position);
//
//                int positonInt = Integer.valueOf(item_position);
//                System.out.println("selected item "+positonInt);
//                DocDetail docDetail = docDetailArrayList.get(positonInt);
//                String image_url=docDetail.getDoc_url_address_full();
//
//
//                if(image_url.contains("doc")) {
//
//                String  myScript="<html><iframe src='http://www.amanrow.com/uploads/C1/legal/demodoc.doc'&embedded='true' style='width:600px; height:500px;'frameborder='0'></iframe></html>";
//
//
//                    webView.setVisibility(View.VISIBLE);
//                    img_doc.setVisibility(View.GONE);
////                    webView.loadUrl(image_url);
//                    webView.loadData(myScript,"text/html", "UTF-8");
//
//
//
//
//
//
//                }else
//                {
//                    webView.setVisibility(View.GONE);
//                    img_doc.setVisibility(View.VISIBLE);
//                    Picasso.with(context).load(image_url).memoryPolicy(MemoryPolicy.NO_CACHE)
//                            .networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.mipmap.placeholder).into(img_doc);
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//
//
//    }

    public class AppWebViewClients extends WebViewClient {



        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

        }
    }

    public void getAttachmentData() {

        if (baseActivity.isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.GET_CASE_PROGRESS_DETAIL, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
//                    try {
//                        JSONArray jsonArray = js.getJSONArray("progressremark");
//                        Type type = new TypeToken<ArrayList<ProgressreMark>>() {
//                        }.getType();
//                        progressreMarksList = new Gson().fromJson(jsonArray.toString(), type);
//                        RemarkAdapter remarkAdapter = new RemarkAdapter(getContext(), progressreMarksList);
//                        recycler_view.setAdapter(remarkAdapter);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
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
            JSONObject objinput=new JSONObject();
            objinput.put("case_cd", case_code);
            objinput.put("progres_ser_no", pgsno);
            object.put("info", objinfo);
            object.put("input",objinput);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
