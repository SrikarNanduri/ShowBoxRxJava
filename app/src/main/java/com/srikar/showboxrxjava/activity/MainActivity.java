package com.srikar.showboxrxjava.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


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
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.API_Key)
    TextView apiTV;
    @BindView(R.id.home_imageholder_iv)
    ImageView mImageView;
    int spanCount;

    // This is the layout manager.
    GridLayoutManager manager;

    // We use pagination
    MovieResponseViewModel viewModel;

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
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else {
            if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                spanCount = 3;
                Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
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
            movieFeed();
            mProgressBar.setVisibility(GONE);
            mImageView.setVisibility(GONE);
            apiTV.setVisibility(GONE);
        } else {
            mImageView.setVisibility(VISIBLE);
            mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_network_check));
            apiTV.setVisibility(VISIBLE);
            apiTV.setText(R.string.NetworkNotAvailable);
            apiTV.setTextColor(getResources().getColor(R.color.white));
            Toast.makeText(MainActivity.this, R.string.NetworkNotAvailable, Toast.LENGTH_LONG).show();
            mProgressBar.setVisibility(GONE);
            swipeToRefresh();
        }
    }


    private void swipeToRefresh(){
        mSwipeRefreshLayout.setOnRefreshListener(() -> viewModel.refresh());
    }


    public void movieFeed(){
        swipeToRefresh();
        mProgressBar.setVisibility(GONE);
        manager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieListAdapter(this);

        viewModel.moviePagedList.observe(this, movieDetails -> {
            mAdapter.submitList(movieDetails);
            mSwipeRefreshLayout.setRefreshing(false);
        });
        mRecyclerView.setAdapter(mAdapter);
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
