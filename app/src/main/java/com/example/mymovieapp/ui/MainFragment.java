package com.example.mymovieapp.ui;

import static com.example.mymovieapp.Contants.Contants.API_KEY;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymovieapp.Adapter.CustomAdapter;
import com.example.mymovieapp.Model.MovieModel;
import com.example.mymovieapp.Request.MovieApi;
import com.example.mymovieapp.Request.Services;
import com.example.mymovieapp.Response.MovieResponse;
import com.example.mymovieapp.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment implements CustomAdapter.onItemClick{
    FragmentMainBinding binding;
    CustomAdapter adapter;
    List<MovieModel>movieList;
    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentMainBinding.inflate(inflater,container,false);

       getNowPlay();
       getPop();

       return binding.getRoot();
    }

    private void getPop(){

        MovieApi api= Services.getMovieApi();
        Call<MovieResponse>call=api.getPop(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieModel>movie=new ArrayList<>(response.body().getMovies());
                if(response.code()==200){
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    binding.recyclerPop.setHasFixedSize(true);
                    binding.recyclerPop.setLayoutManager(horizontalLayoutManagaer);
                    adapter=new CustomAdapter(movie,MainFragment.this);
                    binding.recyclerPop.setAdapter(adapter);
                }

                //adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void getNowPlay(){
        MovieApi api= Services.getMovieApi();
        Call<MovieResponse>call=api.getNowPlay(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieModel>movie=new ArrayList<>(response.body().getMovies());
                if(response.code()==200){
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    binding.recyclermovie.setHasFixedSize(true);
                    binding.recyclermovie.setLayoutManager(horizontalLayoutManagaer);
                    adapter=new CustomAdapter(movie,MainFragment.this::onItemClick);
                    binding.recyclermovie.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });
    }




    @Override
    public void onItemClick(int pos) {
            Intent intent=new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra("data",adapter.getSelectedMovie(pos));
            startActivity(intent);
    }
}