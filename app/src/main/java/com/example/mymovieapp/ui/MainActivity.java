package com.example.mymovieapp.ui;

import static android.content.ContentValues.TAG;
import static com.example.mymovieapp.Contants.Contants.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mymovieapp.Model.MovieModel;
import com.example.mymovieapp.R;
import com.example.mymovieapp.Request.MovieApi;
import com.example.mymovieapp.Request.Services;
import com.example.mymovieapp.Response.MovieResponse;
import com.example.mymovieapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);


        getSearchId();

    }
    private void getNowMovie(){
        MovieApi movieApi= Services.getMovieApi();
        Call<MovieResponse>call=movieApi.getNowPlay(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.code()==200){
                    List<MovieModel>modelList=new ArrayList<>(response.body().getMovies());
                    for(MovieModel movie:modelList){
                        Log.v(TAG,"Title : "+movie.getOverview());
                    }
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
    private void getSearchMovie(){
        MovieApi movieApi= Services.getMovieApi();
        Call<MovieResponse>call=movieApi.getSearch(API_KEY,"Terminator Genisys");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.code()==200){
                List<MovieModel>movieList=new ArrayList<>(response.body().getMovies());
                    for(MovieModel movie:movieList){
                        System.out.println("Date :"+movie.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void getSearchId(){
            MovieApi movieApi=Services.getMovieApi();
            Call<MovieModel>call=movieApi.getMovieId(550,API_KEY);
            call.enqueue(new Callback<MovieModel>() {
                @Override
                public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                    if(response.code()==200){
                        MovieModel movie=response.body();
                        Log.v("tag","The Response :"+movie.getTitle());
                        Log.v("tag","The Response :"+movie.getId());
                    }
                }
                @Override
                public void onFailure(Call<MovieModel> call, Throwable t) {

                }
            });

    }



}