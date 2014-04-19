package com.chuross.rssmatome.application.activity;

import android.os.Bundle;

import com.chuross.rssmatome.application.Application;
import com.chuross.rssmatome.application.Config;
import com.chuross.rssmatome.domain.blog.BlogRepository;
import com.chuross.rssmatome.domain.blog.DefaultBlogRepository;
import com.chuross.rssmatome.domain.entry.DefaultEntryRepository;
import com.chuross.rssmatome.domain.entry.EntryRepository;
import com.chuross.rssmatome.infrastructure.android.preference.SharedPreferences;
import com.chuross.rssmatome.infrastructure.android.query.AQuery;
import com.chuross.rssmatome.infrastructure.deferred.NotifiableDeferredFutureTask;
import com.google.common.collect.Lists;

import org.jdeferred.DeferredFutureTask;
import org.jdeferred.Promise;
import org.jdeferred.android.AndroidDeferredManager;
import org.jdeferred.android.AndroidDeferredObject;
import org.jdeferred.android.AndroidExecutionScope;
import org.jdeferred.multiple.MasterProgress;
import org.jdeferred.multiple.MultipleResults;
import org.jdeferred.multiple.OneReject;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import roboguice.activity.RoboActivity;

public class Activity extends RoboActivity {
    private AQuery query;

    private BlogRepository blogRepository;

    private EntryRepository entryRepository;

    private List<Future<?>> futures = Lists.newArrayList();

    public static Application getApplication(Activity activity) {
        return activity != null ? (Application) activity.getApplication() : null;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query = new AQuery(this);
        blogRepository = new DefaultBlogRepository(getApplication(this));
        entryRepository = new DefaultEntryRepository(getApplication(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public AQuery getAQuery() {
        return query;
    }

    public BlogRepository getBlogRepository() {
        return blogRepository;
    }

    public EntryRepository getEntryRepository() {
        return entryRepository;
    }

    public SharedPreferences getSharedPreferences() {
        return getApplication(this).getSharedPreferences();
    }

    public Config getConfig() {
        return getApplication(this).getConfig();
    }

    public Date getNow() {
        return new Date();
    }

    public boolean needRefresh() {
        return getNow().getTime() - getSharedPreferences().getLastUpdatedAt().getTime() > getConfig().getRefreshInterval();
    }

    public <D, F, P> Promise<MultipleResults, OneReject, MasterProgress> when(ExecutorService executorService, Promise<D, F, P>... promises) {
        return new AndroidDeferredManager(executorService).when(promises);
    }

    public <D, F extends Throwable> Promise<D, F, D> promise(Executor executor, final Future<D> future, AndroidExecutionScope scope) {
        DeferredFutureTask<D, D> task = new NotifiableDeferredFutureTask<D>(future);
        futures.add(task);
        executor.execute(task);
        return new AndroidDeferredObject<D, F, D>((Promise<D, F, D>) task.promise(), scope).promise();
    }

    synchronized public void cleanFuture() {
        for(int i = 0; i < futures.size(); i++) {
            Future<?> future = futures.get(i);
            if(!future.isDone()) {
                continue;
            }
            futures.remove(i);
        }
    }

    synchronized public void cancelAll() {
        cleanFuture();
        for(Future<?> future : futures) {
            future.cancel(true);
        }
        futures.clear();
    }

}