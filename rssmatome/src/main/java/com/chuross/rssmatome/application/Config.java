package com.chuross.rssmatome.application;

import android.content.Context;
import android.content.res.Resources;

import com.chuross.rssmatome.R;

import java.util.concurrent.TimeUnit;

public class Config {

    private Resources resources;

    public Config(Context context) {
        this.resources = context.getResources();
    }

    public int getFeedlySearchLimit() {
        return resources.getInteger(R.integer.feedly_feed_search_limit);
    }

    public int getFeedlyEntryLimit() {
        return resources.getInteger(R.integer.feedly_feed_entry_limit);
    }

    public long getDiscCacheSize() {
        return Long.parseLong(resources.getString(R.string.disc_cache_size));
    }

    public long getRefreshInterval() {
        return TimeUnit.HOURS.toMillis(resources.getInteger(R.integer.refresh_interval_per_hour));
    }

}
