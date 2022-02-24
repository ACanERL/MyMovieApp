package com.example.mymovieapp.Response;

import android.provider.MediaStore;

import com.example.mymovieapp.Model.VideoModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    @SerializedName("id")
    private int mId;

    @SerializedName("results")
    private List<VideoModel> mVideoResults = null;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public List<VideoModel> getmVideoResults() {
        return mVideoResults;
    }

    public void setmVideoResults(List<VideoModel> mVideoResults) {
        this.mVideoResults = mVideoResults;
    }
}
