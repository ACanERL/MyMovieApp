package com.example.mymovieapp.ui;

import static com.example.mymovieapp.Contants.Contants.API_KEY;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymovieapp.Adapter.CustomAdapter;
import com.example.mymovieapp.Model.MovieModel;
import com.example.mymovieapp.R;
import com.example.mymovieapp.Request.MovieApi;
import com.example.mymovieapp.Request.Services;
import com.example.mymovieapp.Response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyListFragment extends Fragment implements CustomAdapter.onItemClick{

    RecyclerView recyclerView;
    CustomAdapter adapter;

    public MyListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_my_list,container,false);
        recyclerView=view.findViewById(R.id.recyclerList);

        MovieApi api= Services.getMovieApi();
        Call<MovieResponse> call=api.getUpComing(API_KEY,1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieModel>movie=new ArrayList<>(response.body().getMovies());
                if(response.isSuccessful()){
                  //  LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    adapter=new CustomAdapter(movie,MyListFragment.this);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return view;
    }
    @Override
    public void onItemClick(int pos) {

    }
}