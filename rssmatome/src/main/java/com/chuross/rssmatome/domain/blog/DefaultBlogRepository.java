package com.chuross.rssmatome.domain.blog;

import com.activeandroid.ActiveAndroid;
import com.chuross.rssmatome.application.Application;
import com.chuross.rssmatome.infrastructure.utils.ConcurrentUtils;
import com.chuross.rssmatome.infrastructure.android.query.AQuery;
import com.chuross.rssmatome.infrastructure.api.feedly.Feed;
import com.chuross.rssmatome.infrastructure.api.feedly.Feedly;
import com.chuross.rssmatome.infrastructure.api.feedly.SearchFeedList;
import com.chuross.rssmatome.infrastructure.database.blog.BlogHelper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.inject.Inject;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class DefaultBlogRepository implements BlogRepository {

    private Application application;

    private AQuery query;

    private Feedly feedly;

    @Inject
    public DefaultBlogRepository(Application application) {
        this.application = application;
        this.query = new AQuery(application);
        feedly = new Feedly(application);
    }

    private static Blog findByCached(BlogIdentity identity) {
        return Blog.convertFrom(BlogHelper.loadByIdentity(identity.getValue()));
    }

    private static List<Blog> findAllByCached() {
        return Lists.transform(BlogHelper.loadAll(), new Function<com.chuross.rssmatome.infrastructure.database.blog.Blog, Blog>() {
            @Override
            public Blog apply(final com.chuross.rssmatome.infrastructure.database.blog.Blog input) {
                return Blog.convertFrom(input);
            }
        });
    }

    private static Blog save(Blog blog) {
        com.chuross.rssmatome.infrastructure.database.blog.Blog b = Blog.convertTo(blog);
        long id = b.save();
        if(id <= 0) {
            return null;
        }
        return Blog.convertFrom(BlogHelper.load(id));
    }

    @Override
    public Future<Blog> find(final Executor executor, final BlogIdentity identity, final boolean cached) {
        return ConcurrentUtils.executeOrNull(executor, ListenableFutureTask.create(new Callable<Blog>() {
            @Override
            public Blog call() throws Exception {
                return find(identity, cached);
            }
        }));
    }

    @Override
    public Blog find(final BlogIdentity identity, final boolean cached) {
        if(identity == null) {
            return null;
        }
        return cached ? findByCached(identity) : findByNetwork(identity);
    }

    private Blog findByNetwork(BlogIdentity identity) {
        return Blog.convertFrom(query.syncAccess(feedly.findFeed(identity.getValue())));
    }

    @Override
    public Future<List<Blog>> findAll(final Executor executor, final boolean cached) {
        return ConcurrentUtils.executeOrNull(executor, ListenableFutureTask.create(new Callable<List<Blog>>() {
            @Override
            public List<Blog> call() throws Exception {
                return findAll(cached);
            }
        }));
    }

    @Override
    public List<Blog> findAll(final boolean cached) {
        return cached ? findAllByCached() : findAllByNetwork();
    }

    private List<Blog> findAllByNetwork() {
        final SearchFeedList feedList = query.syncAccess(feedly.findAllFeeds("2ch", application.getConfig().getFeedlySearchLimit()));
        if(feedList == null || feedList.getFeeds().size() <= 0) {
            return Lists.newArrayList();
        }
        List<Blog> blogs = Lists.transform(feedList.getFeeds(), new Function<Feed, Blog>() {
            @Override
            public Blog apply(final Feed input) {
                return Blog.convertFrom(input);
            }
        });
        ActiveAndroid.beginTransaction();
        try {
            insertAll(blogs);
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return blogs;
    }

    private void insertAll(List<Blog> blogs) {
        for(Blog blog : blogs) {
            add(blog);
        }
    }

    @Override
    public Future<Blog> add(final Executor executor, final Blog blog) {
        return ConcurrentUtils.executeOrNull(executor, ListenableFutureTask.create(new Callable<Blog>() {
            @Override
            public Blog call() throws Exception {
                return add(blog);
            }
        }));
    }

    @Override
    public Blog add(final Blog blog) {
        return blog != null ? save(blog) : null;
    }
}
