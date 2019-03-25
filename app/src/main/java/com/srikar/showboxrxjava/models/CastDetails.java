package com.srikar.showboxrxjava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CastDetails {

    @SerializedName("birthday")
    @Expose
    public String birthday;
    @SerializedName("known_for_department")
    @Expose
    public String knownForDepartment;
    @SerializedName("deathday")
    @Expose
    public String deathday;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("external_ids")
    @Expose
    public ExternalIds externalIds;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("also_known_as")
    @Expose
    public List<String> alsoKnownAs;
    @SerializedName("gender")
    @Expose
    public Integer gender;
    @SerializedName("biography")
    @Expose
    public String biography;
    @SerializedName("popularity")
    @Expose
    public Float popularity;
    @SerializedName("place_of_birth")
    @Expose
    public String placeOfBirth;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    @SerializedName("adult")
    @Expose
    public Boolean adult;
    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("images")
    @Expose
    public Images images;
}
