package com.example.mymovieapp.Request;

import com.example.mymovieapp.Model.MovieModel;
import com.example.mymovieapp.Response.MovieResponse;
import com.example.mymovieapp.Response.VideoResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    //api sorgular

    //Vizyondaki Filmler

    @GET("/3/movie/now_playing")
    Call<MovieResponse>getNowPlay(
        @Query("api_key")String api_key
    );
    //film adi ile arama
    @GET("/3/search/movie")
    Call<MovieResponse>getSearch(
            @Query("api_key")String api_key,
            @Query("query") String movie_name

    );

    //film id ile
    @GET("/3/movie/{id}")
    Call<MovieModel>getMovieId(
            @Path("id")int id,
            @Query("api_key")String apikey
    );

    @GET("/3/movie/upcoming")
    Call<MovieResponse>getUpComing(
        @Query("api_key")String apikey,
        @Query("page")int page
    );

    //populer filmler
    @GET("/3/movie/popular")
    Call<MovieResponse>getPop(
            @Query("api_key")String api_key

    );
    @GET("/3/movie/{id}/videos")
    Call<VideoResponse>getVideos(
            @Path("id") int id,
            @Query("api_key")String api_key
    );
    @GET("/3/movie/top_rated")
    Call<MovieResponse>getTopRated(
            @Query("api_key")String apikey,
            @Query("page")int page
    );
}
