package com.example.twisterfragments.WebApiServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServices {
    private static Retrofit retrofit =null;

    public static Retrofit getComments(String baseUrl){
        if(retrofit ==null){
            retrofit= new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
