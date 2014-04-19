package com.chuross.rssmatome.infrastructure.api.feedly;

import com.google.common.collect.Lists;

import net.arnx.jsonic.JSONHint;

import java.util.List;

public class SearchFeedList {

    private List<Feed> feeds;

    @JSONHint(name = "results")
    public List<Feed> getFeeds() {
        return feeds != null ? feeds : Lists.<Feed>newArrayList();
    }

    @JSONHint(name = "results")
    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

}
