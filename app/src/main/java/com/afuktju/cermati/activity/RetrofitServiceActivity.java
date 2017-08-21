package com.afuktju.cermati.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.afuktju.cermati.BuildConfig;
import com.afuktju.cermati.R;
import com.afuktju.cermati.controller.RetrofitController;
import com.afuktju.cermati.rest.api.ApiInterface;
import com.afuktju.cermati.rest.api.TLSSocketFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AfukTju on 21/08/2017.
 */

public class RetrofitServiceActivity extends AppCompatActivity {
    public Retrofit retrofit;
    public ApiInterface apiInterface;
    public OkHttpClient httpClient;
    public ProgressDialog alertDialog;
    public RetrofitController retrofitController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initServiceWithDialog() {
        initDialog();
        initService();
    }


    public void initService() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);


        try {
            httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(new TLSSocketFactory())
                    .addNetworkInterceptor(httpLoggingInterceptor)
                    .build();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        Retrofit.Builder retrofitBuilder = setRetrofitBuilder(BuildConfig.API_BASE_URL);
        retrofit = retrofitBuilder.build();
        onAddApiInterface(retrofit);
        bindRetrofit();
    }

    protected void onAddApiInterface(Retrofit retrofit) {
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void initDialog() {
        alertDialog = new ProgressDialog(this);
        alertDialog.setMessage(getString(R.string.label_loading));
        alertDialog.setCancelable(false);
    }



    protected Retrofit.Builder setRetrofitBuilder(String url) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofitBuilder.client(httpClient);
        retrofitBuilder.baseUrl(url);
        return retrofitBuilder;
    }



    private void bindRetrofit() {
        retrofitController = new RetrofitController(this, apiInterface, alertDialog);
    }

}