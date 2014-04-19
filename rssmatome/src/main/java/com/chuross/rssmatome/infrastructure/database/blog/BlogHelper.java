package com.chuross.rssmatome.infrastructure.database.blog;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

public final class BlogHelper {

    private BlogHelper() {
    }

    public static Blog load(long id) {
        return Blog.load(Blog.class, id);
    }

    public static Blog loadByIdentity(String identity) {
        return new Select().from(Blog.class).where("identity = ?", identity).executeSingle();
    }

    public static List<Blog> loadAll() {
        return new Select().all().from(Blog.class).execute();
    }

    public static void delete(long id) {
        Blog.delete(Blog.class, id);
    }

    public static void deleteAll() {
        new Delete().from(Blog.class).execute();
    }

}
