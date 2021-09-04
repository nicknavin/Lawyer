package com.app.amanrow.network;

import com.app.amanrow.pojo.CaseInformation;
import com.app.amanrow.pojo.ReqLoginData;
import com.app.amanrow.pojo.RootCaseDetail;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers("Content-Type: application/text")
    @POST("get_case_detail")
    public Call<RootCaseDetail> reqCaseProgressList(@Body CaseInformation caseInformation);

    @Headers("Content-Type: application/text")
    @POST("user_login")
    public Call<ResponseBody> reqLogin(@Body ReqLoginData caseInformation);



}



