package com.example.mymovieapp.Request;

import static com.example.mymovieapp.Contants.Contants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Services {
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit=builder.build();
    private static MovieApi movieApi=retrofit.create(MovieApi.class);
    public static MovieApi getMovieApi(){
        return movieApi;
    }
}
