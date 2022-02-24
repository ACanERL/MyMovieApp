package com.example.mymovieapp.Response;

import com.example.mymovieapp.Model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    @SerializedName("id")
    @Expose()
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movies=" + movies +
                '}';
    }
}
