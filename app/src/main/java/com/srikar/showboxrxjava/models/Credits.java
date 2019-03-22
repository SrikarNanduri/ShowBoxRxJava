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
public class Credits  {
    @SerializedName("cast")
    @Expose
    private List<Cast> cast;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew;

}
