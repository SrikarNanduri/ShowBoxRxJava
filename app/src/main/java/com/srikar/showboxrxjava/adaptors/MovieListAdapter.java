package com.srikar.showboxrxjava.adaptors;

import android.app.Activity;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.srikar.showboxrxjava.R;
import com.srikar.showboxrxjava.activity.DetailsActivity;
import com.srikar.showboxrxjava.config.ConfigURL;
import com.srikar.showboxrxjava.models.MovieDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends PagedListAdapter<MovieDetails, MovieListAdapter.MovieViewHolder> {


    Context context;
    private LayoutInflater mInflater;


    public MovieListAdapter(Context context  ){
        super(DIFF_CALLBACK);
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = mInflater.inflate(R.layout.movie_list_item,parent,false);
    return new MovieViewHolder(mItemView, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {

        MovieDetails movieDetails = getItem(position);
        String imagePath = ConfigURL.POSTER_PATH + movieDetails.getPosterPath();
        Picasso.with(context).load(imagePath)
                .placeholder(R.drawable.ic_action_movie_placeholder)
                .into(holder.movieList);
        holder.title.setText(movieDetails.getTitle());
        holder.movieList.setTransitionName("poster");

        holder.movieList.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("movieList", movieDetails);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.movieList, "poster");
            context.startActivity(intent, optionsCompat.toBundle());
        });

    }


    private static DiffUtil.ItemCallback<MovieDetails> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieDetails>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieDetails movieDetails_, @NonNull MovieDetails t1) {
            return movieDetails_.getid() == t1.getid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieDetails movieDetails_, @NonNull MovieDetails t1) {
            return movieDetails_.equals(t1);
        }
    };

    class MovieViewHolder extends RecyclerView.ViewHolder{
  @BindView(R.id.movie_poster_iv)
  ImageView movieList;
  @BindView(R.id.home_title_tv)
  TextView title;
  MovieListAdapter mAdapter;

    public MovieViewHolder(View itemView, MovieListAdapter movieListAdapter) {
        super(itemView);
        this.mAdapter = movieListAdapter;
        ButterKnife.bind(this,itemView);
    }
}
}