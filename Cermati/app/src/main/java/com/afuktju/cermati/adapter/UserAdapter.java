package com.afuktju.cermati.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afuktju.cermati.R;
import com.afuktju.cermati.rest.Result.ResultGetUserList;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AfukTju on 21/08/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    private List<ResultGetUserList.Item> userList;
    private Context context;

    public UserAdapter(Context context,List<ResultGetUserList.Item> userList) {
        this.context=context;
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_ch, parent, false);
        return new UserViewHolder(v);
    }


    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        ResultGetUserList.Item user = userList.get(position);
        Glide.with(context).load(user.avatar_url).into(holder.ivPhoto);
        holder.tvUsername.setText(user.login);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvUsername)
        TextView tvUsername;
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}