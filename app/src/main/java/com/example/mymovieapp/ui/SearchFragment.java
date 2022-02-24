package com.example.mymovieapp.ui;

import static com.example.mymovieapp.Contants.Contants.API_KEY;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymovieapp.Adapter.CustomAdapter;
import com.example.mymovieapp.Model.MovieModel;
import com.example.mymovieapp.R;
import com.example.mymovieapp.Request.MovieApi;
import com.example.mymovieapp.Request.Services;
import com.example.mymovieapp.Response.MovieResponse;
import com.example.mymovieapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment implements CustomAdapter.onItemClick{
    FragmentSearchBinding binding;
    String movie;
    CustomAdapter adapter;
    RecyclerView recyclerView;
    TextView txt;
    public SearchFragment() {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=getLayoutInflater().inflate(R.layout.fragment_search,container,false);
        recyclerView=view.findViewById(R.id.searchRecycler);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Search Movie");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu2, menu);
        MenuItem menuItem= menu.findItem(R.id.ara);
        androidx.appcompat.widget.SearchView searchView= (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MovieApi movieApi= Services.getMovieApi();
                Call<MovieResponse>call=movieApi.getSearch(API_KEY,query);
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if(response.isSuccessful()){
                            List<MovieModel> movies=new ArrayList<>(response.body().getMovies());
                            for(MovieModel movie:movies){
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                                adapter=new CustomAdapter(movies,SearchFragment.this);
                                recyclerView.setAdapter(adapter);
                            }
                            adapter.notifyDataSetChanged();

                        }
                        else{
                            try {
                                Log.v("tag","Error"+ response.errorBody().string());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent=new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("data",adapter.getSelectedMovie(pos));
        startActivity(intent);
    }
}