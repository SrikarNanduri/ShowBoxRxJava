package com.srikar.showboxrxjava.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.srikar.showboxrxjava.models.MovieDetails;


public class MovieDetailsDataSourceFactory extends DataSource.Factory {

    public MutableLiveData<PageKeyedDataSource<Integer, MovieDetails>> movieLiveDataSource = new MutableLiveData<>();


    @Override
    public DataSource create() {
        MovieDetailsDataSource movieDetailsDataSource = new MovieDetailsDataSource();
        movieLiveDataSource.postValue(movieDetailsDataSource);
        return movieDetailsDataSource;

    }

    public MutableLiveData<PageKeyedDataSource<Integer, MovieDetails>> getMovieLiveDataSource(){
        return movieLiveDataSource;
    }
}
