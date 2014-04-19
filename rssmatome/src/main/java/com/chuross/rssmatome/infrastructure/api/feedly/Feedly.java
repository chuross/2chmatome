package com.chuross.rssmatome.infrastructure.api.feedly;

import android.content.Context;

import com.chuross.rssmatome.infrastructure.api.Api;

public class Feedly {

    private Context context;

    public Feedly(Context context) {
        this.context = context;
    }

    public Api<Feed> findFeed(String feedId) {
        return new FindFeedApi(context, feedId);
    }

    public Api<SearchFeedList> findAllFeeds(String query, int limit) {
        return new SearchFeedsApi(context, query, limit);
    }

    public Api<Mixes> findAllEntries(String feedId, int limit) {
        return new MixesApi(context, feedId, limit);
    }

}
