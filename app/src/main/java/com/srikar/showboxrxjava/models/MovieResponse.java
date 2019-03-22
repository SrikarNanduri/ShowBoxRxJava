package com.srikar.showboxrxjava.models;

import android.os.Parcel;
import android.os.Parcelable;

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
public class MovieResponse implements Parcelable{

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<MovieDetails> results;
    @SerializedName("total_results")
    private long total_results;
    @SerializedName("total_pages")
    private long total_pages;

    protected MovieResponse(Parcel in) {
        page = in.readInt();
        results = in.createTypedArrayList(MovieDetails.CREATOR);
        total_results = in.readLong();
        total_pages = in.readLong();
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(page);
        parcel.writeTypedList(results);
        parcel.writeLong(total_results);
        parcel.writeLong(total_pages);
    }
}
