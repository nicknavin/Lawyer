package com.app.lawyer.network;

import com.app.lawyer.pojo.CaseInformation;
import com.app.lawyer.pojo.RootCaseDetail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers("Content-Type: application/text")
    @POST("get_case_detail")
    public Call<RootCaseDetail> reqCaseProgressList(@Body CaseInformation caseInformation);

}



