package com.srikar.showboxrxjava.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.srikar.showboxrxjava.BuildConfig;
import com.srikar.showboxrxjava.R;
import com.srikar.showboxrxjava.adaptors.MovieListAdapter;
import com.srikar.showboxrxjava.network.ConnectivityReceiver;
import com.srikar.showboxrxjava.network.MyApplication;
import com.srikar.showboxrxjava.viewModels.MovieResponseViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    // Binding all the views here
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    MovieListAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.API_Key)
    TextView apiTV;
    @BindView(R.id.home_imageholder_iv)
    ImageView mImageView;
    int spanCount;

    // This is the layout manager.
    GridLayoutManager manager;

    // We use pagination
    MovieResponseViewModel viewModel;
    private final static String API_KEY = BuildConfig.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Popular Movies");
        mToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_sort));

        viewModel = ViewModelProviders.of(this).get(MovieResponseViewModel.class);

        Configuration newConfig = getResources().getConfiguration();
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 5;
        } else {
            if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                spanCount = 3;
            }
        }
    checkConnection();

    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        connection(isConnected);

    }

    // Checking the status
    private void connection(boolean isConnected){
        if(isConnected){
            if(API_KEY.equals("Enter your API key here")){
                mImageView.setVisibility(VISIBLE);
                mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_api_key));
                apiTV.setVisibility(VISIBLE);
                apiTV.setText(R.string.API_key_error);
            } else {
                viewModel.refresh();
                movieFeed();
                mImageView.setVisibility(GONE);
                apiTV.setVisibility(GONE);
            }
        } else {
            mImageView.setVisibility(VISIBLE);
            viewModel.refresh();
            mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_network_check));
            apiTV.setVisibility(VISIBLE);
            apiTV.setText(R.string.NetworkNotAvailable);
            apiTV.setTextColor(getResources().getColor(R.color.white));
            Toast.makeText(MainActivity.this, R.string.NetworkNotAvailable, Toast.LENGTH_LONG).show();
            swipeToRefresh();
        }
    }


    private void swipeToRefresh(){
        mSwipeRefreshLayout.setOnRefreshListener(() -> viewModel.refresh());
    }


    public void movieFeed(){
        manager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieListAdapter(this);

        viewModel.moviePagedList.observe(this, movieDetails -> {
            mAdapter.submitList(movieDetails);
            mSwipeRefreshLayout.setRefreshing(false);
        });
        mRecyclerView.setAdapter(mAdapter);
        swipeToRefresh();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        connection(isConnected);
    }
}
