package com.anikinkirill.mvitutorial.api;

import com.anikinkirill.mvitutorial.util.LiveDataCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static Retrofit INSTANCE;

    public static Retrofit getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .build();
        }
        return INSTANCE;
    }

    public static ApiService getApiService() {
        return getInstance().create(ApiService.class);
    }

}
