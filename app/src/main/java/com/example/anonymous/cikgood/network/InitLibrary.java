package com.example.anonymous.cikgood.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitLibrary {
    // https://maps.googleapis.com/maps/api/directions/jsons?origin=Cirebon,ID&destination=jakarta,ID&api_key=YOUR_API_KEY
    public static String BASE_URL = "https://maps.googleapis.com/maps/api/directions/";
    public static Retrofit setInit(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }
    public static ApiServices getInstance(){
        return setInit().create(ApiServices.class);
    }
}
