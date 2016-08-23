package com.agriscienceapp.webservice;


import com.agriscienceapp.common.Consts;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by agile on 17-Apr-15.
 */
public class RestClientRetroFit {

    public static APICallMethods myWebServices = null;

    public static APICallMethods getWebServices() {
        if (myWebServices == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(Consts.HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setWriteTimeout(Consts.HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(Consts.HTTP_TIMEOUT, TimeUnit.MILLISECONDS);

            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(Consts.BASEURL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter(new GsonConverter(new GsonBuilder().create()))
                    .setClient(new OkClient(okHttpClient));

            RestAdapter adapter = builder.build();
            myWebServices = adapter.create(APICallMethods.class);
            return myWebServices;
        } else {
            return myWebServices;
        }
    }
}
