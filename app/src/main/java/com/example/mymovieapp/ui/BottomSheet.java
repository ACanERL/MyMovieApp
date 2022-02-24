package com.example.mymovieapp.ui;

import static com.example.mymovieapp.Contants.Contants.API_KEY;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymovieapp.Model.VideoModel;
import com.example.mymovieapp.R;
import com.example.mymovieapp.Request.MovieApi;
import com.example.mymovieapp.Request.Services;
import com.example.mymovieapp.Response.VideoResponse;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheet extends BottomSheetDialogFragment {
    Context context;

    public static  BottomSheet newInstance(){
        return new BottomSheet();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_sheet,container,false);
        String movie_id = getArguments().getString("id");
        int id=Integer.parseInt(movie_id);
        MovieApi movieApi= Services.getMovieApi();
        Call<VideoResponse> call=movieApi.getVideos(id,API_KEY);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()){
                    List<VideoModel> videoModels=new ArrayList<>(response.body().getmVideoResults());
                    String videoKey=videoModels.get(1).getmKey();
                    final YouTubePlayerView youTubePlayerView = view.findViewById(R.id.videoPlayer);
                    getLifecycle().addObserver(youTubePlayerView);
                    youTubePlayerView.enterFullScreen();
                    youTubePlayerView.isFullScreen();
                    youTubePlayerView.toggleFullScreen();
                    youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                        @Override
                        public void onYouTubePlayerEnterFullScreen() {
                            youTubePlayerView.enterFullScreen();
                        }
                        @Override
                        public void onYouTubePlayerExitFullScreen() {
                            youTubePlayerView.exitFullScreen();
                        }
                    });
                   youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                       @Override
                       public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                           youTubePlayer.loadVideo(videoKey, 0);
                       }
                   });
                }
            }
            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });
        return view;
    }
}
