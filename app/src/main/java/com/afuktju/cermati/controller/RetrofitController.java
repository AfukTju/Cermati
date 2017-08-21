package com.afuktju.cermati.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.afuktju.cermati.rest.Result.ResultGetUserList;
import com.afuktju.cermati.rest.api.ApiInterface;

import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AfukTju on 21/08/2017.
 */

public class RetrofitController {
    public ApiInterface apiInterface;
    public ProgressDialog alertDialog;
    public Context context;

    public RetrofitController(Context context, ApiInterface apiInterface, ProgressDialog alertDialog) {
        this.apiInterface = apiInterface;
        this.alertDialog = alertDialog;
        this.context = context;
    }

    public void getUser(final CallbackRest<ResultGetUserList> mListener, String query, int page, int per_page) {
        Call<ResultGetUserList> getUsers = apiInterface.getUser(query,""+page, ""+per_page);
        getUsers.enqueue(new Callback<ResultGetUserList>() {
            @Override
            public void onResponse(Call<ResultGetUserList> call, Response<ResultGetUserList> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    mListener.onSuccess(response.body());
                } else {
                    mListener.onFailed(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResultGetUserList> call, Throwable t) {
                mListener.onFailed(null);
            }
        });
    }

    public interface CallbackRest<T> {
        void onSuccess(T object);

        void onFailed(ResponseBody responseBody);

    }
}