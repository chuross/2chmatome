package com.chuross.rssmatome.infrastructure.api.feedly;

import android.content.Context;

import com.chuross.rssmatome.R;
import com.chuross.rssmatome.infrastructure.api.AbstractApi;
import com.chuross.rssmatome.infrastructure.api.Method;

public class MixesApi extends AbstractApi<Mixes> {

    public MixesApi(final Context context, String feedId, int limit) {
        this(context, feedId, limit, 0);
    }

    public MixesApi(final Context context, String feedId, int limit, long timestamp) {
        super(context, context.getString(R.string.feedly_url));
        putParameter("streamId", feedId);
        putParameter("count", limit);
        putParameter("newerThan", timestamp);
    }

    @Override
    public String getPath() {
        return "v3/mixes/contents";
    }

    @Override
    public Class<Mixes> getResultType() {
        return Mixes.class;
    }

    @Override
    public Method getMethod() {
        return Method.GET;
    }
}
