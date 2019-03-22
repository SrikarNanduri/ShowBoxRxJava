package com.srikar.showboxrxjava.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.srikar.showboxrxjava.R;
import com.srikar.showboxrxjava.config.ConfigURL;
import com.srikar.showboxrxjava.models.MovieDetails;
import com.srikar.showboxrxjava.viewModels.MovieDetailsViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    MovieDetails movieDetails;
    MovieDetailsViewModel detailsViewModel;


    @BindView(R.id.similar_tv)
    TextView similarMoviesHeadline;

    @BindView(R.id.details_activity_sv)
    ScrollView mScrollView;
    @BindView(R.id.movie_poster_iv)
    ImageView poster;
    @BindView(R.id.bookmark_border_iv) ImageView bookmark;
    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.synopsis_tv) TextView synopsis;
    @BindView(R.id.rating_tv) TextView rating;
    @BindView(R.id.release_tv) TextView release;
    @BindView(R.id.runtime_tv) TextView runtime;
    @BindView(R.id.tagline_tv) TextView tagline;
    @BindView(R.id.status_tv) TextView status;
    @BindView(R.id.genres_tv) TextView genres;
    @BindView(R.id.genres_type_tv) TextView genres_types;
    @BindView(R.id.backdrop_ll)
    LinearLayout backdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.review_tv)
    TextView mReviewTV;
    @BindView(R.id.share_ib)
    ImageButton mShare;
    @BindView(R.id.trailers_tv)
    TextView mTrailersTV;


    @BindView(R.id.video_rv)
    RecyclerView mVideoRecyclerView;
    @BindView(R.id.cast_rv)
    RecyclerView mCastRecyclerView;
    @BindView(R.id.review_rv)
    RecyclerView mReviewRecyclerView;
    @BindView(R.id.similar_rv)
    RecyclerView mSimilarRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        detailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);


        if(getIntent().getParcelableExtra("movieList") != null) {
             detailsViewModel.setMovieDetails(getIntent().getParcelableExtra("movieList"));
        }


        movieDetails = detailsViewModel.getMovieDetails();
        movieListDetails_helper();
    }




    public void movieListDetails_helper ()
    {
        String title_value = movieDetails.getTitle();
        String poster_path = movieDetails.getPosterPath();
        String plot_synopsis = movieDetails.getOverview();
        String user_rating = movieDetails.getVoteAverage();
        String release_date = movieDetails.getReleaseDate();
        final String backdrop_path = movieDetails.getBackdrop_path();


        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transation));
        poster.setTransitionName("poster");

        Picasso.with(this).load( ConfigURL.POSTER_PATH + poster_path)
                .into(poster);

        Picasso.with(this).load( ConfigURL.BACKDROP_PATH + backdrop_path)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        backdrop.setBackground(new BitmapDrawable(getApplication().getResources(),bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        backdrop.setBackgroundDrawable(errorDrawable);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String outputDate = null;
        try {
            Date date = inputFormat.parse(release_date);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        synopsis.setText(plot_synopsis);
        synopsis.setEllipsize(TextUtils.TruncateAt.END);
        synopsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(synopsis.getMaxLines() == 4){
                    synopsis.setEllipsize(null);
                    synopsis.setMaxLines(100);
                } else {
                    if(synopsis.getMaxLines() == 100){
                        synopsis.setEllipsize(TextUtils.TruncateAt.END);
                        synopsis.setMaxLines(4);
                    }
                }
            }
        });
        rating.setText(user_rating);
        release.setText(outputDate);
        title.setText(title_value);



    }

}
