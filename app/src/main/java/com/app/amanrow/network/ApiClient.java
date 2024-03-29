package com.app.amanrow.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    public static final String BASE = "http://www.amanrow.com/ServiceItegrity.svc/"; //production
    public static final String BASE_ROLL="http://www.amanrow.com/ServiceLC.svc/";//
    public static final String BASE_CLIENT="http://www.amanrow.com/ServiceIIP.svc/";


    public static final String BASE_URL = "http://consultlot.com/consultantService.svc/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient(String url)
    {
        if(retrofit!=null)
        {
            retrofit=null;
        }
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
