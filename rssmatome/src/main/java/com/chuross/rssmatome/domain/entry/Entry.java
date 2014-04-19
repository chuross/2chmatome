package com.chuross.rssmatome.domain.entry;

import com.chuross.rssmatome.domain.Entity;
import com.chuross.rssmatome.domain.blog.Blog;
import com.chuross.rssmatome.infrastructure.api.feedly.Mixes;
import com.chuross.rssmatome.infrastructure.api.feedly.MixesItem;
import com.chuross.rssmatome.infrastructure.database.blog.BlogHelper;
import com.chuross.rssmatome.infrastructure.database.entry.EntryHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;

public class Entry implements Entity<EntryIdentity> {

    private final EntryIdentity identity;

    private String title;

    private Blog blog;

    private String url;

    private String imageUrl;

    private int readCount;

    private Date publishedAt;

    public Entry(EntryIdentity identity) {
        this.identity = identity;
    }

    public static String getIdentityValue(Entry entry) {
        return entry != null && entry.getIdentity() != null ? entry.getIdentity().getValue() : null;
    }

    public static String getBlogName(Entry entry) {
        return entry != null && entry.getBlog() != null ? entry.getBlog().getName() : null;
    }

    public static Entry convertFrom(Mixes mixes, MixesItem item) {
        if(mixes == null || item == null) {
            return null;
        }
        final Entry entry = new Entry(new EntryIdentity(item.getId()));
        entry.setTitle(item.getTitle());
        entry.setBlog(Blog.convertFrom(BlogHelper.loadByIdentity(mixes.getId())));
        entry.setUrl(MixesItem.getUrl(item));
        entry.setImageUrl(MixesItem.getImageUrl(item));
        entry.setPublishedAt(item.getPublishedAt());
        entry.setReadCount(item.getEngagement());
        return entry;
    }

    public static Entry convertFrom(com.chuross.rssmatome.infrastructure.database.entry.Entry model) {
        if(model == null) {
            return null;
        }
        Entry entry = new Entry(new EntryIdentity(model.identity));
        entry.setTitle(model.title);
        entry.setBlog(Blog.convertFrom(model.blog));
        entry.setUrl(model.url);
        entry.setImageUrl(model.imageUrl);
        entry.setReadCount(model.readCount);
        entry.setPublishedAt(model.publishedAt);
        return entry;
    }

    public static com.chuross.rssmatome.infrastructure.database.entry.Entry convertTo(Entry entry) {
        if(entry == null) {
            return null;
        }
        com.chuross.rssmatome.infrastructure.database.entry.Entry model = getBaseModel(entry);
        model.identity = Entry.getIdentityValue(entry);
        model.title = entry.getTitle();
        model.blog = Blog.convertTo(entry.getBlog());
        model.url = entry.getUrl();
        model.imageUrl = entry.getImageUrl();
        model.readCount = entry.getReadCount();
        model.publishedAt = entry.getPublishedAt();
        return model;
    }

    private static com.chuross.rssmatome.infrastructure.database.entry.Entry getBaseModel(Entry entry) {
        com.chuross.rssmatome.infrastructure.database.entry.Entry existing = EntryHelper.loadByIdentity(Entry.getIdentityValue(entry));
        return existing != null ? existing : new com.chuross.rssmatome.infrastructure.database.entry.Entry();
    }

    @Override
    public EntryIdentity getIdentity() {
        return identity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(final Blog blog) {
        this.blog = blog;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean hasImageUrl() {
        return !StringUtils.isBlank(imageUrl) && (imageUrl.startsWith("http://") || imageUrl.startsWith("https://"));
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(final int readCount) {
        this.readCount = readCount;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(identity).toHashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if(o == null) {
            return false;
        }
        if(!(o instanceof Entry)) {
            return false;
        }
        Entry target = (Entry) o;
        return new EqualsBuilder().append(target.getIdentity(), identity).append(target.getBlog(), blog).isEquals();

    }

    @Override
    protected Object clone() {
        final Entry entry = new Entry(new EntryIdentity(identity.getValue()));
        entry.setTitle(title);
        entry.setBlog(blog);
        entry.setUrl(url);
        entry.setPublishedAt(publishedAt);
        return entry;
    }
}
