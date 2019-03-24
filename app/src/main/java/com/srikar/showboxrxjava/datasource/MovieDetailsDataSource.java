package com.srikar.showboxrxjava.datasource;

import android.annotation.SuppressLint;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.srikar.showboxrxjava.BuildConfig;
import com.srikar.showboxrxjava.models.MovieDetails;
import com.srikar.showboxrxjava.network.ApiClient;
import com.srikar.showboxrxjava.network.MovieDataInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsDataSource extends PageKeyedDataSource<Integer, MovieDetails> {
    private static final String TAG = MovieDetailsDataSource.class.getSimpleName();
    private static final int FIRST_PAGE = 1;
    private final static String API_KEY = BuildConfig.API_KEY;
    private final MovieDataInterface apiService = ApiClient.getClient().create(MovieDataInterface.class);

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MovieDetails> callback) {

        apiService.getPopularMovies(FIRST_PAGE, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse ->  callback.onResult(movieResponse.getResults(), null, FIRST_PAGE + 1),
                        e -> Log.d("Top Rated Movies Error:" , e.getMessage()),
                         () ->  Log.d(TAG, "number of movies received in LoadInitial:" + params.requestedLoadSize));
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieDetails> callback) {

        apiService.getPopularMovies(params.key, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> callback.onResult(movieResponse.getResults(), (params.key > 1) ? params.key - 1 : null),
                        e -> Log.d("Top Rated Movies Error:" , e.getMessage()),
                        () -> Log.d(TAG, "number of movies received in loadBefore:" + params.requestedLoadSize));
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieDetails> callback) {

        apiService.getPopularMovies(params.key, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> callback.onResult(movieResponse.getResults(), params.key + 1),
                        e -> Log.d("Top Rated Movies Error:" , e.getMessage()),
                        () -> Log.d(TAG, "number of movies received in loadAfter:" + params.requestedLoadSize));
    }
}
