package com.chuross.rssmatome.infrastructure.database.entry;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.chuross.rssmatome.infrastructure.database.blog.Blog;

import java.util.Date;

@Table(name = "entries")
public class Entry extends Model {

    @Column(name = "identity", index = true, unique = true, notNull = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String identity;

    @Column(name = "title", notNull = true)
    public String title;

    @Column(name = "blog_id", notNull = true, onDelete = Column.ForeignKeyAction.CASCADE)
    public Blog blog;

    @Column(name = "url", notNull = true)
    public String url;

    @Column(name = "image_url")
    public String imageUrl;

    @Column(name = "read_count")
    public int readCount;

    @Column(name = "published_at", notNull = true)
    public Date publishedAt;

}
