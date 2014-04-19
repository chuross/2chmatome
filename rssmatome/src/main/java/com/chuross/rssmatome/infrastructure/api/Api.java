package com.chuross.rssmatome.infrastructure.api;

import java.util.Map;

public interface Api<T> {

    public String getUrl();

    public Class<T> getResultType();

    public Method getMethod();

    public Map<String, Object> getParameters();

}
