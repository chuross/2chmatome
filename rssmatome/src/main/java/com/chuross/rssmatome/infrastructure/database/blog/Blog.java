package com.chuross.rssmatome.infrastructure.database.blog;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.chuross.rssmatome.infrastructure.database.entry.Entry;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

@Table(name = "blogs")
public class Blog extends Model {

    @Column(name = "identity", index = true, unique = true, notNull = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String identity;

    @Column(name = "name", notNull = true)
    public String name;

    @Column(name = "site_url")
    public String siteUrl;

    @Column(name = "feed_url", notNull = true)
    public String feedUrl;

    @Column(name = "image_url")
    public String imageUrl;

    @Column(name = "last_updated_at")
    public Date lastUpdatedAt;

    public static List<Entry> getEntries(Blog blog) {
        return blog != null ? blog.getEntries() : Lists.<Entry>newArrayList();
    }

    public List<Entry> getEntries() {
        return getMany(Entry.class, "blog_id");
    }
}
