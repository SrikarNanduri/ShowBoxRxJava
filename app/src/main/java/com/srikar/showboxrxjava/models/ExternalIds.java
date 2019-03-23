package com.srikar.showboxrxjava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalIds {
    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
    @SerializedName("facebook_id")
    @Expose
    public String facebookId;
    @SerializedName("instagram_id")
    @Expose
    public String instagramId;
    @SerializedName("twitter_id")
    @Expose
    public String twitterId;
}
