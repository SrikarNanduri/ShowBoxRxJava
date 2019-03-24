package com.srikar.showboxrxjava.viewModels;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.srikar.showboxrxjava.BuildConfig;
import com.srikar.showboxrxjava.models.CompleteMovieDetails;
import com.srikar.showboxrxjava.network.ApiClient;
import com.srikar.showboxrxjava.network.MovieDataInterface;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.srikar.showboxrxjava.config.ConfigURL.CREDITS;
import static com.srikar.showboxrxjava.config.ConfigURL.EXTERNALIDS;
import static com.srikar.showboxrxjava.config.ConfigURL.REVIEWS;
import static com.srikar.showboxrxjava.config.ConfigURL.SIMILAR;
import static com.srikar.showboxrxjava.config.ConfigURL.VIDEOS;

public class MovieDetailsViewModel extends ViewModel {
    private static final String TAG = MovieDetailsViewModel.class.getSimpleName();


    private final static String API_KEY = BuildConfig.API_KEY;
    private final MovieDataInterface apiService = ApiClient.getClient().create(MovieDataInterface.class);

    private MutableLiveData<CompleteMovieDetails> movieDetails;

    private Disposable disposable;
    public LiveData<CompleteMovieDetails> getDetails(String movieId){
        if(movieDetails == null){
            movieDetails = new MutableLiveData<>();
            loadData(movieId);
        }

        return movieDetails;
    }

    @SuppressLint("CheckResult")
    private void loadData(String movieId){


        String queries = VIDEOS+","+CREDITS+","+REVIEWS+","+EXTERNALIDS+","+SIMILAR;
        apiService.getMoreDetails(String.valueOf(movieId), API_KEY,queries)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeMovieDetails -> movieDetails.setValue(completeMovieDetails),
                        e -> Log.d(TAG, e.getMessage()),
                        () -> Log.d(TAG, "Title of movie received: "+ Objects.requireNonNull(movieDetails.getValue()).getTitle()),
                        d -> disposable = d );
    }

    @Override
    protected void onCleared () {
        disposable.dispose();
        super.onCleared ();
    }


}
