package com.afuktju.cermati.rest.Result;

import java.util.List;

/**
 * Created by AfukTju on 21/08/2017.
 */

public class ResultGetUserList extends ResultDefault{

    public Integer total_count;
    public Boolean incomplete_results;
    public List<Item> items = null;

    public class Item {

        public String login;
        public Integer id;
        public String avatar_url;
        public String gravatar_id;
        public String url;
        public String html_url;
        public String followers_url;
        public String following_url;
        public String gists_url;
        public String starred_url;
        public String subscriptions_url;
        public String organizations_url;
        public String repos_url;
        public String events_url;
        public String received_events_url;
        public String type;
        public Boolean site_admin;
        public Float score;

    }
}