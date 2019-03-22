package com.srikar.showboxrxjava.network;

import com.srikar.showboxrxjava.models.MovieResponse;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataInterface {

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRatedMovies(@Query("page") int page, @Query("api_key") String apiKey);

    @GET("movie/popular")
    Observable<MovieResponse> getPopularMovies(@Query("page") int page, @Query("api_key") String apiKey);
}
