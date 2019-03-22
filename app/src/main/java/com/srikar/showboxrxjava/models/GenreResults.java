package com.srikar.showboxrxjava.models;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenreResults {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;

}
