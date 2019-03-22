package com.srikar.showboxrxjava.viewModels;

import android.arch.lifecycle.ViewModel;

import com.srikar.showboxrxjava.models.MovieDetails;

public class MovieDetailsViewModel extends ViewModel {

    private MovieDetails movieDetails;

    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

    public MovieDetails getMovieDetails() {
        return movieDetails;
    }





}
