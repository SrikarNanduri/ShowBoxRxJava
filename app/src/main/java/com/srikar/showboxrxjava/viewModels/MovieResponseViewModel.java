package com.srikar.showboxrxjava.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.srikar.showboxrxjava.datasource.MovieDetailsDataSourceFactory;
import com.srikar.showboxrxjava.models.MovieDetails;

@SuppressWarnings("unchecked")
public class MovieResponseViewModel extends ViewModel {

   public LiveData<PagedList<MovieDetails>> moviePagedList;
    private MovieDetailsDataSourceFactory movieDetailsDataSourceFactory;

    public MovieResponseViewModel() {

        movieDetailsDataSourceFactory = new MovieDetailsDataSourceFactory();
        LiveData<PageKeyedDataSource<Integer, MovieDetails>> liveMovieDataSource = movieDetailsDataSourceFactory.getMovieLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20)
                .build();

        moviePagedList = (new LivePagedListBuilder(movieDetailsDataSourceFactory, config)).build();


    }

    public void refresh(){
        if(movieDetailsDataSourceFactory.movieLiveDataSource.getValue() != null) {
            movieDetailsDataSourceFactory.movieLiveDataSource.getValue().invalidate();
        }
    }
}
