package com.example.abdul.popularmovieappstage1.api;


import com.example.abdul.popularmovieappstage1.model.api.ListPopularMovie;
import com.example.abdul.popularmovieappstage1.util.Constant;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by abdul on 6/13/2017.
 */

public interface ApiInterface {

    @GET(Constant.POPULAR_MOVIE)
    Call<ListPopularMovie> getListPopularMovie(@Path("type_sort") String typeSort,
                                               @Query("language") String lang,
                                               @Query("page") int page);

}
