package com.afuktju.cermati.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afuktju.cermati.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AfukTju on 21/08/2017.
 */

public class SearchUserView extends LinearLayout {

    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.rvUser)
    RecyclerView rvUser;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tvDataNotFound)
    TextView tvDataNotFound;

    public SearchUserView(Context context) {
        super(context);
        init();
    }

    public SearchUserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.view_search_user, this));

    }

    public void addSearchUserTextChangedListener(TextWatcher textWatcher){
        edtUserName.addTextChangedListener(textWatcher);
    }


    public void setUserAdapter(LinearLayoutManager llm, RecyclerView.Adapter adapter, RecyclerView.OnScrollListener onScrollListener){
        rvUser.setLayoutManager(llm);
        rvUser.setAdapter(adapter);
        rvUser.addOnScrollListener(onScrollListener);
    }

    public void addRvUserItemDecoration(RecyclerView.ItemDecoration decoration){
        rvUser.addItemDecoration(decoration);
    }

    public void showProgressVisibility(int visibility){
        progressBar.setVisibility(visibility);
    }
    public void showDataNotFoundVisibility(int visibility){
        tvDataNotFound.setVisibility(visibility);
    }

}