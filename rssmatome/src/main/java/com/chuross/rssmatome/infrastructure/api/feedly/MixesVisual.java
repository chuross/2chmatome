package com.chuross.rssmatome.infrastructure.api.feedly;

import net.arnx.jsonic.JSONHint;

import java.util.Date;

public class MixesVisual {

    private String url;

    private int width;

    private int height;

    private Date expiredAt;

    private String processor;

    private String contentType;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    @JSONHint(name = "expirationDate")
    public Date getExpiredAt() {
        return expiredAt;
    }

    @JSONHint(name = "expirationDate")
    public void setExpiredAt(final Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(final String processor) {
        this.processor = processor;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }
}
