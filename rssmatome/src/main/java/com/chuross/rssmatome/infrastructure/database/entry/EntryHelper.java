package com.chuross.rssmatome.infrastructure.database.entry;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

public final class EntryHelper {

    private EntryHelper() {
    }

    public static Entry load(long id) {
        return Entry.load(Entry.class, id);
    }

    public static Entry loadByIdentity(String entryIdentity) {
        return new Select().from(Entry.class).where("identity = ?", entryIdentity).executeSingle();
    }

    public static List<Entry> loadAll() {
        return new Select().all().from(Entry.class).execute();
    }

    public static void deleteAll() {
        new Delete().from(Entry.class).execute();
    }

}
