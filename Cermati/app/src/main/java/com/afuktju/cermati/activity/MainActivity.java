package com.afuktju.cermati.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afuktju.cermati.R;
import com.afuktju.cermati.adapter.UserAdapter;
import com.afuktju.cermati.controller.RetrofitController;
import com.afuktju.cermati.rest.Result.ResultDefault;
import com.afuktju.cermati.rest.Result.ResultGetUserList;
import com.afuktju.cermati.view.SearchUserView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class MainActivity extends RetrofitServiceActivity {

    @BindView(R.id.searchUserView)
    SearchUserView searchUserView;

    private UserAdapter adapter;
    private LinearLayoutManager llm;
    private int PER_PAGE = 50;
    private int page = 1;
    private String queryUser;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private List<ResultGetUserList.Item> items;
    private Handler handler;
    public final static int DELAY_SEARCH = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initServiceWithDialog();
        ButterKnife.bind(this);
        setContent();
    }

    private void setContent() {
        handler = new Handler();
        searchUserView.addSearchUserTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                queryUser = s.toString();
                handler.removeCallbacks(mSearchUser);
                handler.postDelayed(mSearchUser, DELAY_SEARCH);

            }
        });
        setupAdapter();
    }

    private void setupAdapter() {
        items = new ArrayList<>();
        adapter = new UserAdapter(this, items);
        llm = new LinearLayoutManager(this);
        searchUserView.addRvUserItemDecoration(new DividerItemDecoration(this, llm.getOrientation()));
        searchUserView.setUserAdapter(llm, adapter, new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = llm.getChildCount();
                int totalItemCount = llm.getItemCount();
                int firstVisibleItemPosition = llm.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PER_PAGE) {
                        page++;
                        loadMoreUsers();
                    }
                }
            }
        });
    }

    private void loadMoreUsers() {
        isLoading = true;
        searchUserView.showProgressVisibility(View.VISIBLE);
        retrofitController.getUser(new RetrofitController.CallbackRest<ResultGetUserList>() {
            @Override
            public void onSuccess(ResultGetUserList object) {
                items.addAll(object.items);
                adapter.notifyDataSetChanged();
                isLoading = false;
                isLastPage = items.size() == object.total_count;
                searchUserView.showProgressVisibility(View.GONE);
                searchUserView.showDataNotFoundVisibility(items.size()==0?View.VISIBLE:View.GONE);

            }

            @Override
            public void onFailed(ResponseBody responseBody) {//
                try {
                    ResultDefault result = new Gson().fromJson(responseBody.string(), ResultDefault.class);
                    Toast.makeText(getApplicationContext(), result.message, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                searchUserView.showProgressVisibility(View.GONE);
            }
        }, queryUser, page, PER_PAGE);
    }

    private Runnable mSearchUser = new Runnable() {
        @Override
        public void run() {
            page = 1;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    items.clear();
                    adapter.notifyDataSetChanged();
                    if (queryUser.trim().length() > 0) {
                        loadMoreUsers();
                    }else {
                        searchUserView.showDataNotFoundVisibility(View.GONE);
                    }
                }
            });


        }
    };
}
