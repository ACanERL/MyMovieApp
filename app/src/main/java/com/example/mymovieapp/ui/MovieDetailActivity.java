package com.example.mymovieapp.ui;

import static com.example.mymovieapp.Contants.Contants.API_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mymovieapp.Adapter.CustomAdapter;
import com.example.mymovieapp.Model.MovieModel;
import com.example.mymovieapp.Model.VideoModel;
import com.example.mymovieapp.R;
import com.example.mymovieapp.Request.MovieApi;
import com.example.mymovieapp.Request.Services;
import com.example.mymovieapp.Response.VideoResponse;
import com.example.mymovieapp.databinding.ActivityMovieDetailBinding;
import com.example.mymovieapp.ui.BottomSheet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity  {
    ActivityMovieDetailBinding binding;
    int movie_id;
    CustomAdapter adapter;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        androidx.appcompat.widget.Toolbar toolbar;
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Movie Details");

        if(getIntent().hasExtra("data")){
            MovieModel movieModel=getIntent().getParcelableExtra("data");
            binding.movieN.setText(movieModel.getTitle());
            binding.descp.setText(movieModel.getRelease_date());
            binding.textView7.setText(movieModel.getOverview());
            Picasso.get().load("https://image.tmdb.org/t/p/w500"+movieModel.getPoster_path()).into(binding.movieIm);
            binding.ratee.setText(String.valueOf(movieModel.getVote_average()));
            System.out.println("Gelen Film İd:"+movieModel.getId());
            movie_id=movieModel.getId();
        }

        binding.videobtn.setOnClickListener(view -> {
            BottomSheet bottomSheet=BottomSheet.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(movie_id));
            bottomSheet.setArguments(bundle);
            bottomSheet.show(getSupportFragmentManager(),"add_video_frag");

        });
        MovieApi movieApi= Services.getMovieApi();
        Call<VideoResponse>call=movieApi.getVideos(movie_id,API_KEY);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if(response.isSuccessful()){
                    List<VideoModel>videoModels=new ArrayList<>(response.body().getmVideoResults());
                   /*for(VideoModel movie:videoModels){
                        System.out.println("Name : "+movie.getmName());
                        System.out.println("Name : "+movie.getmKey());
                    }*/
                    System.out.println("Birinci Key "+videoModels.get(1).getmKey());
                    String videoKey=videoModels.get(1).getmKey();//String trailerThumbnailUrl = YOUTUBE_THUMBNAIL_BASE_URL + videoKey;
                }
            }
            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });
    }
}