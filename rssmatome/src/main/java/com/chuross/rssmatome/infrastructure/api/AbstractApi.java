package com.chuross.rssmatome.infrastructure.api;

import android.content.Context;

import com.chuross.rssmatome.infrastructure.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractApi<T> implements Api<T> {

    private final Context context;

    private final Map<String, Object> parameters;

    private final String baseUrl;

    public AbstractApi(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;
        parameters = new HashMap<String, Object>();
    }

    public abstract String getPath();

    public Context getContext() {
        return context;
    }

    @Override
    public String getUrl() {
        return baseUrl + "/" + getPath();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    protected void putParameter(String key, Object value) {
        parameters.put(key, UrlUtils.encode(value.toString(), "utf-8"));
    }
}
