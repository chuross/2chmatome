package com.chuross.rssmatome.infrastructure.api.feedly;

import android.content.Context;

import com.chuross.rssmatome.R;
import com.chuross.rssmatome.infrastructure.api.AbstractApi;
import com.chuross.rssmatome.infrastructure.api.Method;

class SearchFeedsApi extends AbstractApi<SearchFeedList> {

    public SearchFeedsApi(Context context, String query, int limit) {
        this(context, query, limit, "ja_jp");
    }

    public SearchFeedsApi(Context context, String query, int limit, String locale) {
        super(context, context.getString(R.string.feedly_url));
        putParameter("q", query);
        putParameter("n", limit);
        putParameter("locale", locale);
    }

    @Override
    public String getPath() {
        return "v3/search/feeds";
    }

    @Override
    public Class<SearchFeedList> getResultType() {
        return SearchFeedList.class;
    }

    @Override
    public Method getMethod() {
        return Method.GET;
    }

}
