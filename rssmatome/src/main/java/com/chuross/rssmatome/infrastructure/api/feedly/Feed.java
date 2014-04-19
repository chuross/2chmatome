package com.chuross.rssmatome.infrastructure.api.feedly;

import com.google.common.collect.Lists;

import net.arnx.jsonic.JSONHint;

import java.util.Date;
import java.util.List;

public class Feed {

    private String id;

    private String title;

    private String siteUrl;

    private String imageUrl;

    private String language;

    private String[] topics;

    private String[] deliciousTags;

    private Date lastUpdatedAt;

    private float velocity;

    private String description;

    @JSONHint(name = "feedId")
    public String getId() {
        return id;
    }

    @JSONHint(name = "feedId")
    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @JSONHint(name = "website")
    public String getSiteUrl() {
        return siteUrl;
    }

    @JSONHint(name = "website")
    public void setSiteUrl(final String siteUrl) {
        this.siteUrl = siteUrl;
    }

    @JSONHint(ignore = true)
    public String getFeedUrl() {
        return getId().substring(5);
    }

    @JSONHint(name = "visualUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    @JSONHint(name = "visualUrl")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    @JSONHint(name = "topics")
    public List<String> getTopics() {
        return Lists.newArrayList(topics);
    }

    @JSONHint(name = "topics")
    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    @JSONHint(name = "deliciousTags")
    public List<String> getDeliciousTags() {
        return Lists.newArrayList(deliciousTags);
    }

    @JSONHint(name = "deliciousTags")
    public void setDeliciousTags(String[] deliciousTags) {
        this.deliciousTags = deliciousTags;
    }

    @JSONHint(ignore = true)
    public List<String> getTags() {
        return topics != null && topics.length > 0 ? Lists.newArrayList(topics) : Lists.newArrayList(deliciousTags);
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(final Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(final float velocity) {
        this.velocity = velocity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
