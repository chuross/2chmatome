package com.chuross.rssmatome.infrastructure.Utils;

import com.chuross.rssmatome.infrastructure.android.logger.Logger;
import com.chuross.rssmatome.infrastructure.android.logger.LoggerFactory;

import java.net.URLEncoder;
import java.util.Map;

public final class UrlUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    private UrlUtils() {
    }

    public static String appendParameterString(String basrUrl, Map<String, Object> parameters) {
        StringBuilder parameterStringBuilder = new StringBuilder();
        for(Map.Entry<String, Object> parameter : parameters.entrySet()) {
            parameterStringBuilder.append("&" + parameter.getKey() + "=" + parameter.getValue());
        }
        return parameterStringBuilder.length() > 0 ? basrUrl + "?" + parameterStringBuilder.toString().substring(1) : basrUrl;
    }

    public static String encode(String value, String charset) {
        try {
            return URLEncoder.encode(value, charset);
        } catch(Exception e) {
            LOGGER.e("encode failed.", e);
            return null;
        }
    }

}
