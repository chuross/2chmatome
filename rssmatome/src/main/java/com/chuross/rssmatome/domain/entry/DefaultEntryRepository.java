package com.chuross.rssmatome.domain.entry;

import com.activeandroid.ActiveAndroid;
import com.chuross.rssmatome.application.Application;
import com.chuross.rssmatome.domain.blog.BlogIdentity;
import com.chuross.rssmatome.infrastructure.utils.ConcurrentUtils;
import com.chuross.rssmatome.infrastructure.android.query.AQuery;
import com.chuross.rssmatome.infrastructure.api.feedly.Feedly;
import com.chuross.rssmatome.infrastructure.api.feedly.Mixes;
import com.chuross.rssmatome.infrastructure.api.feedly.MixesItem;
import com.chuross.rssmatome.infrastructure.database.blog.Blog;
import com.chuross.rssmatome.infrastructure.database.blog.BlogHelper;
import com.chuross.rssmatome.infrastructure.database.entry.EntryHelper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.inject.Inject;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class DefaultEntryRepository implements EntryRepository {

    private Application application;

    private AQuery query;

    private Feedly feedly;

    @Inject
    public DefaultEntryRepository(Application application) {
        this.application = application;
        this.query = new AQuery(application);
        feedly = new Feedly(application);
    }

    private static Entry save(Entry entry) {
        com.chuross.rssmatome.infrastructure.database.entry.Entry e = Entry.convertTo(entry);
        long id = e.save();
        if(id <= 0) {
            return null;
        }
        return Entry.convertFrom(EntryHelper.load(id));
    }

    @Override
    public Future<Entry> find(final Executor executor, final EntryIdentity identity, final boolean cached) {
        return ConcurrentUtils.executeOrNull(executor, ListenableFutureTask.create(new Callable<Entry>() {
            @Override
            public Entry call() throws Exception {
                return find(identity, cached);
            }
        }));
    }

    @Override
    public Entry find(final EntryIdentity identity, final boolean cached) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Future<List<Entry>> findAll(final Executor executor, final boolean cached) {
        return ConcurrentUtils.executeOrNull(executor, ListenableFutureTask.create(new Callable<List<Entry>>() {
            @Override
            public List<Entry> call() throws Exception {
                return findAll(cached);
            }
        }));
    }

    @Override
    public List<Entry> findAll(final boolean cached) {
        return cached ? findAllByCached() : Lists.<Entry>newArrayList();
    }

    private List<Entry> findAllByCached() {
        return Lists.transform(EntryHelper.loadAll(), new Function<com.chuross.rssmatome.infrastructure.database.entry.Entry, Entry>() {
            @Override
            public Entry apply(final com.chuross.rssmatome.infrastructure.database.entry.Entry input) {
                return Entry.convertFrom(input);
            }
        });
    }

    @Override
    public Future<List<Entry>> findAll(final Executor executor, final BlogIdentity identity) {
        return ConcurrentUtils.executeOrNull(executor, ListenableFutureTask.create(new Callable<List<Entry>>() {
            @Override
            public List<Entry> call() throws Exception {
                return findAll(identity);
            }
        }));
    }

    @Override
    public List<Entry> findAll(final BlogIdentity blogIdentity) {
        return findAll(blogIdentity, true);
    }

    @Override
    public Future<List<Entry>> findAll(final Executor executor, final BlogIdentity identity, final boolean cached) {
        return ConcurrentUtils.executeOrNull(executor, ListenableFutureTask.create(new Callable<List<Entry>>() {
            @Override
            public List<Entry> call() throws Exception {
                return findAll(identity, cached);
            }
        }));
    }

    @Override
    public List<Entry> findAll(final BlogIdentity identity, final boolean cached) {
        return cached ? findAllByCached(identity) : findAllByNetwork(identity);
    }

    private List<Entry> findAllByCached(BlogIdentity blogIdentity) {
        if(blogIdentity == null) {
            return Lists.newArrayList();
        }
        return Lists.transform(Blog.getEntries(BlogHelper.loadByIdentity(blogIdentity.getValue())), new Function<com.chuross.rssmatome.infrastructure.database.entry.Entry, Entry>() {
            @Override
            public Entry apply(final com.chuross.rssmatome.infrastructure.database.entry.Entry input) {
                return Entry.convertFrom(input);
            }
        });
    }

    private List<Entry> findAllByNetwork(BlogIdentity blogIdentity) {
        if(blogIdentity == null) {
            return Lists.newArrayList();
        }
        final Mixes mixes = query.syncAccess(feedly.findAllEntries(blogIdentity.getValue(), application.getConfig().getFeedlyEntryLimit()));
        if(mixes == null) {
            return Lists.newArrayList();
        }
        List<Entry> entries = Lists.transform(Mixes.getEntries(mixes), new Function<MixesItem, Entry>() {
            @Override
            public Entry apply(final MixesItem input) {
                return Entry.convertFrom(mixes, input);
            }
        });
        ActiveAndroid.beginTransaction();
        try {
            insertAll(entries);
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return entries;
    }

    private void insertAll(List<Entry> entries) {
        for(Entry entry : entries) {
            add(entry);
        }
    }

    @Override
    public Future<Entry> add(final Executor executor, final Entry entry) {
        return ConcurrentUtils.executeOrNull(executor, ListenableFutureTask.create(new Callable<Entry>() {
            @Override
            public Entry call() throws Exception {
                return add(entry);
            }
        }));
    }

    @Override
    public Entry add(final Entry entry) {
        return entry != null ? save(entry) : null;
    }


}
