package com.srikar.showboxrxjava.datasource;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.srikar.showboxrxjava.BuildConfig;
import com.srikar.showboxrxjava.models.MovieDetails;
import com.srikar.showboxrxjava.models.MovieResponse;
import com.srikar.showboxrxjava.network.ApiClient;
import com.srikar.showboxrxjava.network.MovieDataInterface;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsDataSource extends PageKeyedDataSource<Integer, MovieDetails> {
    private static final String TAG = MovieDetailsDataSource.class.getSimpleName();
    private static final int FIRST_PAGE = 1;
    private final static String API_KEY = BuildConfig.API_KEY;
    private final MovieDataInterface apiService = ApiClient.getClient().create(MovieDataInterface.class);

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MovieDetails> callback) {

        apiService.getPopularMovies(FIRST_PAGE, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        callback.onResult(movieResponse.getResults(), null, FIRST_PAGE + 1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Top Rated Movies Error:" , e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "number of movies received:" + params.requestedLoadSize);
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieDetails> callback) {


        apiService.getPopularMovies(params.key, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        callback.onResult(movieResponse.getResults(), (params.key > 1) ? params.key - 1 : null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Top Rated Movies Error:" , e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "number of movies received:" + params.requestedLoadSize);
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieDetails> callback) {
        apiService.getPopularMovies(params.key, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        callback.onResult(movieResponse.getResults(), params.key + 1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Top Rated Movies Error:" , e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "number of movies received:" + params.requestedLoadSize);
                    }
                });
    }
}
