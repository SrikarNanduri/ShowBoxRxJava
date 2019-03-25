package com.srikar.showboxrxjava.viewModels;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.srikar.showboxrxjava.BuildConfig;
import com.srikar.showboxrxjava.models.CastDetails;
import com.srikar.showboxrxjava.network.ApiClient;
import com.srikar.showboxrxjava.network.MovieDataInterface;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.srikar.showboxrxjava.config.ConfigURL.EXTERNALIDS;
import static com.srikar.showboxrxjava.config.ConfigURL.IMAGES;

public class CastDetailsViewModel extends ViewModel {

    private static final String TAG = CastDetailsViewModel.class.getSimpleName();

    private final static String API_KEY = BuildConfig.API_KEY;
    private final MovieDataInterface apiService = ApiClient.getClient().create(MovieDataInterface.class);

    private MutableLiveData<CastDetails> castDetails;
    private Disposable disposable;


    public LiveData<CastDetails> getDetails(String castId){
        if(castDetails == null){
            castDetails = new MutableLiveData<>();
            loadCastData(castId);
        }
        return castDetails;
    }

    @SuppressLint("CheckResult")
    private void loadCastData(String castId) {
        String query = EXTERNALIDS+","+IMAGES;
        apiService.getCastDetails(castId, API_KEY, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(castData -> castDetails.setValue(castData),
                        e -> Log.d(TAG, e.getMessage()),
                        () -> Log.d(TAG, "Name of the Cast: "+ Objects.requireNonNull(castDetails.getValue()).getName()),
                        d -> disposable = d);
    }


    @Override
    protected void onCleared () {
        disposable.dispose();
        super.onCleared ();
    }

}
