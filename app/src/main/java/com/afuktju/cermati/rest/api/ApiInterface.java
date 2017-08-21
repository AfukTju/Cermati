package com.afuktju.cermati.rest.api;

import com.afuktju.cermati.rest.Result.ResultGetUserList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by AfukTju on 21/08/2017.
 */

public interface ApiInterface {
    @GET("/search/users")
    Call<ResultGetUserList> getUser(@Query("q") String query, @Query("page") String page, @Query("per_page") String per_page);

}