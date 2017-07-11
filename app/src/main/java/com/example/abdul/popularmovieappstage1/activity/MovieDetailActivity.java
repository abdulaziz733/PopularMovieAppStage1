package com.example.abdul.popularmovieappstage1.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdul.popularmovieappstage1.R;
import com.example.abdul.popularmovieappstage1.model.Movie;
import com.example.abdul.popularmovieappstage1.util.Constant;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private Intent intent;
    private Movie movie;
    private TextView txtMovieOriginalTitle, txtMovieReleaseDate, txtMovieRatting, txtMovieOverview, backDropTitle, popularity;
    private ImageView  backDrop, poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        txtMovieOriginalTitle = (TextView) findViewById(R.id.movie_title);
        txtMovieReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        txtMovieRatting = (TextView) findViewById(R.id.movie_ratting);
        txtMovieOverview = (TextView) findViewById(R.id.movie_overview);
        poster = (ImageView) findViewById(R.id.movie_poster);
        backDrop = (ImageView) findViewById(R.id.backdrop);
        backDropTitle = (TextView) findViewById(R.id.app_bg_title);
        popularity = (TextView) findViewById(R.id.app_bg_popularity);

        intent = getIntent();
        if (intent.hasExtra("detail_movie")){
            String resultData = intent.getStringExtra("detail_movie");
            Gson gson = new Gson();
            movie = gson.fromJson(resultData, Movie.class);

            txtMovieOriginalTitle.setText(movie.getOriginalTitle());
            backDropTitle.setText(movie.getOriginalTitle());
            popularity.setText("Popularity" + movie.getPopularity());
            txtMovieReleaseDate.setText(movie.getReleaseDate());
            txtMovieRatting.setText(movie.getVoteAverage().toString());
            txtMovieOverview.setText(movie.getOverview());

            Picasso.with(this)
                    .load(Constant.BASE_IMAGE_URL + movie.getPosterPath())
                    .placeholder(R.drawable.movie_placeholder)
                    .error(R.drawable.error_image)
                    .into(poster);

            Picasso.with(this)
                    .load(Constant.BASE_IMAGE_URL + movie.getBackdropPath())
                    .placeholder(R.drawable.movie_placeholder)
                    .error(R.drawable.error_image)
                    .into(backDrop);

        }

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(movie.getTitle());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }



}
