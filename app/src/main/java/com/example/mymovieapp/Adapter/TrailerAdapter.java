package com.example.mymovieapp.Adapter;

import static com.example.mymovieapp.Contants.Contants.YOUTUBE_BASE_URL;
import static com.example.mymovieapp.Contants.Contants.YOUTUBE_THUMBNAIL_BASE_URL;
import static com.example.mymovieapp.Contants.Contants.YOUTUBE_THUMBNAIL_URL_JPG;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovieapp.Model.VideoModel;
import com.example.mymovieapp.R;
import com.example.mymovieapp.databinding.TrailerListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private List<VideoModel>videoModelList;
    private  TrailerAdapterOnClickHandler mOnClickHandler;

    public TrailerAdapter(List<VideoModel> videoModelList) {
        this.videoModelList = videoModelList;

    }

    public interface TrailerAdapterOnClickHandler {
        void onItemClick(String videoUrl);
    }
    @NonNull
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TrailerListItemBinding trailerItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.trailer_list_item, parent, false);
        return new ViewHolder(trailerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.ViewHolder holder, int position) {
        VideoModel video = videoModelList.get(position);
        holder.bind(video);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TrailerListItemBinding mTrailerItemBinding;
        public ViewHolder(TrailerListItemBinding trailerItemBinding) {
            super(trailerItemBinding.getRoot());
            mTrailerItemBinding = trailerItemBinding;
            mTrailerItemBinding.ivTrailerThumbnail.setOnClickListener(this);
        }
        void bind(VideoModel video) {
            // Get the video ID
            String videoKey = video.getmKey();
            // Get the complete the trailer thumbnail url
            String trailerThumbnailUrl = YOUTUBE_THUMBNAIL_BASE_URL + videoKey +
                    YOUTUBE_THUMBNAIL_URL_JPG;

            // Load trailer thumbnail with Picasso library
            Picasso.get()
                    .load(trailerThumbnailUrl)
                    .into(mTrailerItemBinding.ivTrailerThumbnail);

            // Get the video name and set name to the TextView to display the trailer name
            String videoName = video.getmName();
            mTrailerItemBinding.tvTrailerName.setText(videoName);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            VideoModel video = videoModelList.get(adapterPosition);
            // Get the video key
            String videoKey = video.getmKey();
            // Get the complete YouTube video url to display a trailer video
            String videoUrl = YOUTUBE_BASE_URL + videoKey;
            mOnClickHandler.onItemClick(videoUrl);
        }
    }
}
