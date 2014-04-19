package com.chuross.rssmatome.infrastructure.api.feedly;

import android.content.Context;

import com.chuross.rssmatome.R;
import com.chuross.rssmatome.infrastructure.Utils.UrlUtils;
import com.chuross.rssmatome.infrastructure.api.AbstractApi;
import com.chuross.rssmatome.infrastructure.api.Method;

public class FindFeedApi extends AbstractApi<Feed> {

    private String feedId;

    public FindFeedApi(final Context context, String feedId) {
        super(context, context.getString(R.string.feedly_url));
        this.feedId = feedId;
    }

    @Override
    public String getPath() {
        return "v3/feeds/" + UrlUtils.encode(feedId, "utf-8");
    }

    @Override
    public Class<Feed> getResultType() {
        return Feed.class;
    }

    @Override
    public Method getMethod() {
        return Method.GET;
    }

}
