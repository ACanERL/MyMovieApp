package com.example.mymovieapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovieapp.Model.MovieModel;
import com.example.mymovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    List<MovieModel>movieList;
    onItemClick onItemClick;
    public CustomAdapter(List<MovieModel> movieList,onItemClick onItemClick) {
        this.movieList = movieList;
        this.onItemClick=onItemClick;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.moviename.setText(movieList.get(position).getTitle());
        holder.moviedate.setText(movieList.get(position).getRelease_date());
        holder.movierate.setText(String.valueOf(movieList.get(position).getVote_average()));
        holder.descp.setText(movieList.get(position).getOverview());
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movieList.get(position).getPoster_path()).into(holder.movieimage);

        holder.itemView.setOnClickListener(view -> {
            onItemClick.onItemClick(position);
            notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView moviename,movierate,moviedate,descp;
        ImageView movieimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviename=itemView.findViewById(R.id.movieName);
            moviedate=itemView.findViewById(R.id.movieDate);
            movierate=itemView.findViewById(R.id.movieRate);
            movieimage=itemView.findViewById(R.id.movieImage);
            descp=itemView.findViewById(R.id.moviedescp);
        }
    }
    public interface onItemClick{
        void onItemClick(int pos);
    }

    public MovieModel getSelectedMovie(int position){
        if(movieList!=null){
            if(movieList.size()>0){
                return movieList.get(position);
            }
        }
        return null;
    }
}
