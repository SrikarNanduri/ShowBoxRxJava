package com.srikar.showboxrxjava.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteMovieDetails {

    @SerializedName("adult")
    @Expose
    public Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("original_title")
    @Expose
    public String originalTitle;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("popularity")
    @Expose
    public Float popularity;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("revenue")
    @Expose
    private String revenue;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("vote_average")
    @Expose
    public Float voteAverage;
    @SerializedName("vote_count")
    @Expose
    public Integer voteCount;
    @SerializedName("genres")
    @Expose
    private List<GenreResults> genres;
    @SerializedName("videos")
    @Expose
    Videos videos;
    @SerializedName("credits")
    @Expose
    private Credits credits;
    @SerializedName("reviews")
    @Expose
    private Reviews reviews;
    @SerializedName("similar")
    @Expose
    private MovieResponse similar;

}