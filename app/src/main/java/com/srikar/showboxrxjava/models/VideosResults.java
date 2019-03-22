package com.srikar.showboxrxjava.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VideosResults {

    @SerializedName("id")
    @Expose
    private String id ;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("type")
    @Expose
    private String type;

}
