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
public class Profile {

    @SerializedName("iso_639_1")
    @Expose
    public Object iso6391;
    @SerializedName("aspect_ratio")
    @Expose
    public Float aspectRatio;
    @SerializedName("vote_count")
    @Expose
    public Integer voteCount;
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("vote_average")
    @Expose
    public Float voteAverage;
    @SerializedName("file_path")
    @Expose
    public String filePath;
    @SerializedName("width")
    @Expose
    public Integer width;
}