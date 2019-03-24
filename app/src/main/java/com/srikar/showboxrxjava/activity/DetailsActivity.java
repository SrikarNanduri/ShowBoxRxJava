package com.srikar.showboxrxjava.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.srikar.showboxrxjava.R;
import com.srikar.showboxrxjava.adaptors.CastListAdapter;
import com.srikar.showboxrxjava.adaptors.ReviewListAdapter;
import com.srikar.showboxrxjava.adaptors.SimilarMovieListAdapter;
import com.srikar.showboxrxjava.adaptors.VideoListAdapter;
import com.srikar.showboxrxjava.config.ConfigURL;
import com.srikar.showboxrxjava.helperClasses.CustomLinearLayout;
import com.srikar.showboxrxjava.models.Cast;
import com.srikar.showboxrxjava.models.CompleteMovieDetails;
import com.srikar.showboxrxjava.models.Credits;
import com.srikar.showboxrxjava.models.GenreResults;
import com.srikar.showboxrxjava.models.MovieDetails;
import com.srikar.showboxrxjava.models.MovieResponse;
import com.srikar.showboxrxjava.models.Reviews;
import com.srikar.showboxrxjava.models.ReviewsResults;
import com.srikar.showboxrxjava.models.Videos;
import com.srikar.showboxrxjava.models.VideosResults;
import com.srikar.showboxrxjava.viewModels.MovieDetailsViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.srikar.showboxrxjava.config.ConfigURL.POSTER_PATH;
import static com.srikar.showboxrxjava.config.ConfigURL.VIDEOS_PATH;

@SuppressLint("SimpleDateFormat")
public class DetailsActivity extends AppCompatActivity {

   // private static final String TAG = DetailsActivity.class.getSimpleName();


    MovieDetailsViewModel detailsViewModel;
    String movieId;


    List<VideosResults> videosList;
    List<Cast> castList;
    List<ReviewsResults> reviewsList;
    List<GenreResults> genreList;

    //root views
    @BindView(R.id.details_frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.poster_cv)
    CardView cardView;
    @BindView(R.id.details_activity_sv)
    ScrollView mScrollView;

    // views
    @BindView(R.id.details_scrim)
    View viewScrim;
    @BindView(R.id.view_synopsis)
    View viewSynopsis;
    @BindView(R.id.view_runtime)
    View viewRuntime;
    @BindView(R.id.view_trailer)
    View viewTrailer;
    @BindView(R.id.view_cast)
    View viewCast;
    @BindView(R.id.view_review)
    View viewReview;
    @BindView(R.id.view_similar)
    View viewSimilar;

    // Other views combination of textviews Imageviews etc.
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_poster_iv)
    ImageView poster;
    @BindView(R.id.synopsis_tv)
    TextView synopsis;
    @BindView(R.id.rating_tv)
    TextView rating;
    @BindView(R.id.release_tv)
    TextView release;
    @BindView(R.id.title_tv)
    TextView title;
    @BindView(R.id.ratingtv_tv)
    TextView ratingContinue;
    @BindView(R.id.imageView_star)
    ImageView starImage;
    @BindView(R.id.bookmark_border_iv)
    ImageView bookmark;
    @BindView(R.id.tagline_tv)
    TextView tagline;
    @BindView(R.id.runtime_tv)
    TextView runtime;
    @BindView(R.id.genres_tv)
    TextView genres;
    @BindView(R.id.genres_type_tv)
    TextView genres_types;
    @BindView(R.id.status_tv)
    TextView status;
    @BindView(R.id.trailers_tv)
    TextView trailerHeadline;
    @BindView(R.id.cast_tv)
    TextView castHeadline;
    @BindView(R.id.review_tv)
    TextView reviewHeadline;
    @BindView(R.id.similar_tv)
    TextView similarMoviesHeadline;
    @BindView(R.id.share_ib)
    ImageButton mShare;
    @BindView(R.id.backdrop_ll)
    CustomLinearLayout backdrop;
    @BindView(R.id.details_progressBar)
    ProgressBar progressBar;

    //RecycleViews
    @BindView(R.id.video_rv)
    RecyclerView mVideoRecyclerView;
    @BindView(R.id.cast_rv)
    RecyclerView mCastRecyclerView;
    @BindView(R.id.review_rv)
    RecyclerView mReviewRecyclerView;
    @BindView(R.id.similar_rv)
    RecyclerView mSimilarRecyclerView;

    //Adapters
    VideoListAdapter mVideoAdapter;
    CastListAdapter mCastAdapter;
    ReviewListAdapter mReviewAdapter;
    SimilarMovieListAdapter mSimilarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        detailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);


        if(Objects.requireNonNull(getIntent().getExtras()).getString("movieId") != null) {
            movieId = getIntent().getExtras().getString("movieId");
        }
        detailsViewModel.getDetails(movieId).observe(this, completeMovieDetails -> {
            progressBar.setVisibility(GONE);
            loadCompleteMovieDetails(completeMovieDetails);
        });
    }




    public void loadCompleteMovieDetails (CompleteMovieDetails movieDetails)
    {
        String titleValue = movieDetails.getTitle();
        String posterPath = movieDetails.getPosterPath();
        String plotSynopsis = movieDetails.getOverview();
        Float userRating = movieDetails.getVoteAverage();
        String releaseDate = movieDetails.getReleaseDate();
        String backdropPath = movieDetails.getBackdropPath();
        String runTime = movieDetails.getRuntime() + getString(R.string.mins);
        String tagLine = movieDetails.getTagline();
        String movieStatus = movieDetails.getStatus();
        Videos videos = movieDetails.getVideos();
        Credits credits = movieDetails.getCredits();
        Reviews reviews = movieDetails.getReviews();
        MovieResponse similar = movieDetails.getSimilar();
        List<GenreResults> genre = movieDetails.getGenres();


        Picasso.with(this).load( POSTER_PATH + posterPath)
                .into(poster);

        Picasso.with(this).load( ConfigURL.BACKDROP_PATH + backdropPath)
                .into(backdrop);

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String outputDate = null;
        try {
            Date date = inputFormat.parse(releaseDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        synopsis.setText(plotSynopsis);
        synopsis.setEllipsize(TextUtils.TruncateAt.END);
        synopsis.setOnClickListener(v -> {
            if(synopsis.getMaxLines() == 4){
                synopsis.setEllipsize(null);
                synopsis.setMaxLines(100);
            } else {
                if(synopsis.getMaxLines() == 100){
                    synopsis.setEllipsize(TextUtils.TruncateAt.END);
                    synopsis.setMaxLines(4);
                }
            }
        });

        setGenre(genre);
        rating.setText(String.valueOf(userRating));
        ratingContinue.setText(R.string.ratingContinue);
        release.setText(outputDate);
        title.setText(titleValue);
        runtime.setText(runTime);
        tagline.setText(tagLine);
        status.setText(movieStatus);

        mShare.setImageResource(R.drawable.ic_action_share);
        bookmark.setImageResource(R.drawable.ic_action_bookmark_white_border);
        starImage.setImageResource(R.drawable.ic_action_rating);

        trailerHeadline.setText(R.string.trailers);
        castHeadline.setText(R.string.cast);
        reviewHeadline.setText(R.string.reviews);
        similarMoviesHeadline.setText(R.string.similar_movies);

        viewSimilar.setBackgroundColor(getResources().getColor(R.color.lineSaperator));
        viewReview.setBackgroundColor(getResources().getColor(R.color.lineSaperator));
        viewCast.setBackgroundColor(getResources().getColor(R.color.lineSaperator));
        viewTrailer.setBackgroundColor(getResources().getColor(R.color.lineSaperator));
        viewSynopsis.setBackgroundColor(getResources().getColor(R.color.lineSaperator));
        viewRuntime.setBackgroundColor(getResources().getColor(R.color.lineSaperator));

        // Genre
        genres.setText(R.string.genres);
        if(genre.size() != 0) {
            StringBuilder genreList = new StringBuilder();
            for (int i = 0; i < genre.size() - 1; i++) {
                genreList.append(genre.get(i).getName()).append(", ");
            }
            genreList.append(genre.get(genre.size() - 1).getName());

            genres_types.setText(genreList.toString());
        } else {
            String genreDetails = "No Genre Available";
            genres.setText(genreDetails);
        }

        // Videos
        if(videos.getResults().size() != 0) {
            List<VideosResults> trailers = videos.getResults();
            // A recycler view to set trailers
            Log.v("Video thumbnail URL", "https://img.youtube.com/vi/" + trailers.get(0).getKey() + "/0.jpg");
            videoRV(trailers);
        } else {
            String trailerDetails = "No Trailers Available";
            trailerHeadline.setText(trailerDetails);
        }
        // Share Button
        mShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, "Show Box");
            if(videos.getResults().size() != 0) {
                String link = "Check out the trailer of " + titleValue + ": ";
                link = link + VIDEOS_PATH + videos.getResults().get(0).getKey();
                intent.putExtra(Intent.EXTRA_TEXT, link);
                startActivity(Intent.createChooser(intent, "Share Image"));
            } else {
                Toast.makeText(DetailsActivity.this, "No Links Available", Toast.LENGTH_LONG).show();
            }
        });

        // Credits (Cast and crew)
        if(credits.getCast().size() != 0) {
            List<Cast> cast = credits.getCast();
            // A recycler view to set cast
            Log.v("cast thumbnail URL", POSTER_PATH + cast.get(0).getProfilePath());
            castRV(cast);
        }

        //Reviews
        if(reviews.getResults().size() != 0) {
            List<ReviewsResults> userReviews = reviews.getResults();
            // A recycler view to set userReviews
            Log.v("reviews author", userReviews.get(0).getAuthor());
            reviewRV(userReviews);
        } else {
            String reviewDetails = "No Reviews Available";
            reviewHeadline.setText(reviewDetails);
        }

        //Similar Movies
        if(similar.getResults().size() != 0){
            List<MovieDetails> similarMovies = similar.getResults();
            Log.v("similar movies", similarMovies.get(0).getTitle());
            similarMoviesHeadline.setVisibility(VISIBLE);
            similarRV(similarMovies);
        } else {
            similarMoviesHeadline.setVisibility(GONE);
        }

    }


    // Recycler views
    private void videoRV(List<VideosResults> trailers){
        mVideoAdapter = new VideoListAdapter(this, trailers);
        mVideoRecyclerView.setLayoutManager(new GridLayoutManager(this, 1,GridLayoutManager.HORIZONTAL,false));
        mVideoRecyclerView.setHasFixedSize(true);
        mVideoRecyclerView.setAdapter(mVideoAdapter);
        setTrailers(trailers);
    }

    private void setTrailers(List<VideosResults> trailers){
        videosList = trailers;

    }
    private List<VideosResults> getTrailers(){
        return videosList;
    }

    private void castRV(List<Cast> cast){
        mCastAdapter = new CastListAdapter(this, cast);
        mCastRecyclerView.setLayoutManager(new GridLayoutManager(this, 1,GridLayoutManager.HORIZONTAL,false));
        mCastRecyclerView.setHasFixedSize(true);
        mCastRecyclerView.setAdapter(mCastAdapter);
        setCast(cast);
    }

    private void setCast(List<Cast> cast){
        castList = cast;

    }
    private List<Cast> getCast(){
        return castList;
    }


    private void reviewRV(List<ReviewsResults> reviews){
        mReviewAdapter = new ReviewListAdapter(this, reviews);
        mReviewRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mReviewRecyclerView.setHasFixedSize(true);
        mReviewRecyclerView.setNestedScrollingEnabled(false);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
        setReview(reviews);
    }

    private void setReview(List<ReviewsResults> reviews){
        reviewsList = reviews;

    }
    private List<ReviewsResults> getReview(){
        return reviewsList;
    }

    private void similarRV(List<MovieDetails> similar){
        mSimilarAdapter = new SimilarMovieListAdapter(this, similar);
        mSimilarRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL,false));
        mSimilarRecyclerView.setHasFixedSize(true);
        mSimilarRecyclerView.setAdapter(mSimilarAdapter);
    }

    private void setGenre(List<GenreResults> genre){
        genreList = genre;

    }
    private List<GenreResults> getGenre(){
        return genreList;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
