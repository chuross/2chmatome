package com.chuross.rssmatome.infrastructure.api.feedly;

import net.arnx.jsonic.JSONHint;

import java.util.Date;
import java.util.List;

public class MixesItem {

    private String id;

    private String fingerprint;

    private String originId;

    private MixesContent content;

    private List<MixesAlternate> alternate;

    private String title;

    private Date publishedAt;

    private MixesVisual visual;

    private boolean unread;

    private int engagement;

    private float engagementRate;

    public static String getUrl(MixesItem item) {
        if(item == null) {
            return null;
        }
        if(item.getAlternate().size() > 0) {
            return item.getAlternate().get(0).getHref();
        } else {
            return item.originId;
        }
    }

    public static String getContent(MixesItem item) {
        return item != null && item.getContent() != null ? item.getContent().getContent() : null;
    }

    public static String getImageUrl(MixesItem item) {
        return item != null && item.getVisual() != null ? item.getVisual().getUrl() : null;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(final String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(final String originId) {
        this.originId = originId;
    }

    public MixesContent getContent() {
        return content;
    }

    public void setContent(final MixesContent content) {
        this.content = content;
    }

    public List<MixesAlternate> getAlternate() {
        return alternate;
    }

    public void setAlternate(final List<MixesAlternate> alternate) {
        this.alternate = alternate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @JSONHint(name = "published")
    public Date getPublishedAt() {
        return publishedAt;
    }

    @JSONHint(name = "published")
    public void setPublishedAt(final Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public MixesVisual getVisual() {
        return visual;
    }

    public void setVisual(final MixesVisual visual) {
        this.visual = visual;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(final boolean unread) {
        this.unread = unread;
    }

    public int getEngagement() {
        return engagement;
    }

    public void setEngagement(final int engagement) {
        this.engagement = engagement;
    }

    public float getEngagementRate() {
        return engagementRate;
    }

    public void setEngagementRate(final float engagementRate) {
        this.engagementRate = engagementRate;
    }

}
