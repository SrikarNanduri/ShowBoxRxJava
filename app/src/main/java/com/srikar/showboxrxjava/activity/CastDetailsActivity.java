package com.srikar.showboxrxjava.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.srikar.showboxrxjava.R;
import com.srikar.showboxrxjava.models.CastDetails;
import com.srikar.showboxrxjava.models.ExternalIds;
import com.srikar.showboxrxjava.viewModels.CastDetailsViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.srikar.showboxrxjava.config.ConfigURL.FACEBOOK;
import static com.srikar.showboxrxjava.config.ConfigURL.IMDBACTOR;
import static com.srikar.showboxrxjava.config.ConfigURL.INSTAGRAM;
import static com.srikar.showboxrxjava.config.ConfigURL.POSTER_PATH;
import static com.srikar.showboxrxjava.config.ConfigURL.TWITTER;

public class CastDetailsActivity extends AppCompatActivity {

    CastDetailsViewModel castDetailsViewModel;
    String castId;


    //views
    @BindView(R.id.cast_title_tv)
    TextView castname;
    @BindView(R.id.cast_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cast_poster_iv)
    ImageView poster;
    @BindView(R.id.cast_poster_cv)
    CardView mCardView;
    @BindView(R.id.cast_synopsis_tv)
    TextView mSynopsis;
    @BindView(R.id.cast_pb)
    ProgressBar castPb;

    //images
    @BindView(R.id.cast_twitter)
    ImageView twitterIcon;
    @BindView(R.id.cast_facebook)
    ImageView facebookIcon;
    @BindView(R.id.cast_imdb)
    ImageView imdbIcon;
    @BindView(R.id.cast_instagram)
    ImageView instagramIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_details);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        if(Objects.requireNonNull(getIntent().getExtras()).getString("castId") != null) {
            castId = getIntent().getExtras().getString("castId");
        }

        castDetailsViewModel = ViewModelProviders.of(this).get(CastDetailsViewModel.class);
        castDetailsViewModel.getDetails(castId).observe(this, castDetails -> {
            castPb.setVisibility(View.GONE);
            loadCastData(castDetails);
        });

    }

    private void loadCastData(CastDetails castDetails) {
        String name = castDetails.getName();
        String biography = castDetails.getBiography();
        ExternalIds externalIds = castDetails.getExternalIds();
        String posterPath = castDetails.getProfilePath();
      //  String backdropPath = castDetails.getImages();

        Picasso.with(this).load(POSTER_PATH + posterPath)
                          .into(poster);


        /*Picasso.with(this).load(ConfigURL.BACKDROP_PATH + backdropPath)
                .into(backdrop);*/

        castname.setText(name);
        mSynopsis.setText(biography);

        Picasso.with(this).load(R.drawable.facebook)
                .into(facebookIcon);
        facebookIcon.setOnClickListener(view -> {
            String facebook = FACEBOOK + externalIds.getFacebookId();
            Uri uri = Uri.parse("fb://page/"+externalIds.getFacebookId());
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, uri);
            facebookIntent.setPackage("com.facebook.katana");
            if(isIntentAvailable(this, facebookIntent)) {
                startActivity(facebookIntent);
            } else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebook)));
            }

        });

        Picasso.with(this).load(R.drawable.twitter)
                .into(twitterIcon);
        twitterIcon.setOnClickListener(view -> {
            String twitter = TWITTER + externalIds.getTwitterId();
            Uri uri = Uri.parse("twitter://user?screen_name="+externalIds.getTwitterId());
            Intent twittwerIntent = new Intent(Intent.ACTION_VIEW, uri);
            twittwerIntent.setPackage("com.twitter.android");
            if(isIntentAvailable(this, twittwerIntent)) {
                startActivity(twittwerIntent);
            } else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitter)));
            }
        });

        Picasso.with(this).load(R.drawable.imdb)
                .into(imdbIcon);
        imdbIcon.setOnClickListener(view -> {
            String imdb = IMDBACTOR + externalIds.getImdbId();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdb));
            startActivity(intent);
        });

        Picasso.with(this).load(R.drawable.instagram)
                .into(instagramIcon);
        instagramIcon.setOnClickListener(view -> {
            String instagram = INSTAGRAM + externalIds.getInstagramId();
            Uri uri = Uri.parse("http://instagram.com/p/"+externalIds.getInstagramId());
            Intent instaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagram));
            instaIntent.setPackage("com.instagram.android");
            if (isIntentAvailable(this, instaIntent)){
                startActivity(instaIntent);
            } else{
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(instagram)));
            }
        });


    }


    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
