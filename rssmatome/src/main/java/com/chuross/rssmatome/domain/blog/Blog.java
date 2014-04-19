package com.chuross.rssmatome.domain.blog;

import com.chuross.rssmatome.domain.Entity;
import com.chuross.rssmatome.infrastructure.api.feedly.Feed;
import com.chuross.rssmatome.infrastructure.database.blog.BlogHelper;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;

public class Blog implements Entity<BlogIdentity> {

    private final BlogIdentity identity;

    private String name;

    private String siteUrl;

    private String feedUrl;

    private String imageUrl;

    private Date lastUpdatedAt;

    public Blog(BlogIdentity identity) {
        this.identity = identity;
    }

    public static String getIdentityValue(Blog blog) {
        if(blog == null) {
            return null;
        }
        return blog.getIdentity() != null ? blog.getIdentity().getValue() : null;
    }

    public static Blog convertFrom(Feed feed) {
        final Blog blog = new Blog(new BlogIdentity(feed.getId()));
        blog.setName(feed.getTitle());
        blog.setSiteUrl(feed.getSiteUrl());
        blog.setFeedUrl(feed.getFeedUrl());
        blog.setImageUrl(feed.getImageUrl());
        blog.setLastUpdatedAt(feed.getLastUpdatedAt());
        return blog;
    }

    public static Blog convertFrom(com.chuross.rssmatome.infrastructure.database.blog.Blog model) {
        if(model == null) {
            return null;
        }
        Blog blog = new Blog(new BlogIdentity(model.identity));
        blog.setName(model.name);
        blog.setSiteUrl(model.siteUrl);
        blog.setFeedUrl(model.feedUrl);
        blog.setImageUrl(model.imageUrl);
        blog.setLastUpdatedAt(model.lastUpdatedAt);
        return blog;
    }

    public static com.chuross.rssmatome.infrastructure.database.blog.Blog convertTo(Blog blog) {
        if(blog == null) {
            return null;
        }
        com.chuross.rssmatome.infrastructure.database.blog.Blog model = getBaseModel(blog);
        model.identity = Blog.getIdentityValue(blog);
        model.name = blog.getName();
        model.siteUrl = blog.getSiteUrl();
        model.feedUrl = blog.getFeedUrl();
        model.imageUrl = blog.getImageUrl();
        model.lastUpdatedAt = blog.getLastUpdatedAt();
        return model;
    }

    private static com.chuross.rssmatome.infrastructure.database.blog.Blog getBaseModel(Blog blog) {
        com.chuross.rssmatome.infrastructure.database.blog.Blog existing = BlogHelper.loadByIdentity(Blog.getIdentityValue(blog));
        return existing != null ? existing : new com.chuross.rssmatome.infrastructure.database.blog.Blog();
    }

    @Override
    public BlogIdentity getIdentity() {
        return identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(final Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(identity).append(siteUrl).append(feedUrl).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(!(o instanceof Blog)) {
            return false;
        }
        Blog blog = (Blog) o;
        return new EqualsBuilder().append(blog.getIdentity(), identity).append(blog.getSiteUrl(), siteUrl).append(blog.getFeedUrl(), feedUrl).isEquals();
    }

    @Override
    protected Blog clone() {
        final Blog blog = new Blog(new BlogIdentity(identity.getValue()));
        blog.setName(name);
        blog.setSiteUrl(siteUrl);
        blog.setFeedUrl(feedUrl);
        blog.setImageUrl(imageUrl);
        blog.setLastUpdatedAt(lastUpdatedAt);
        return blog;
    }

}
