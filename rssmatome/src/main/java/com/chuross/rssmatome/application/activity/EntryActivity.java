package com.chuross.rssmatome.application.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.activeandroid.ActiveAndroid;
import com.chuross.rssmatome.R;
import com.chuross.rssmatome.domain.blog.Blog;
import com.chuross.rssmatome.domain.entry.Entry;
import com.chuross.rssmatome.infrastructure.utils.ConcurrentUtils;
import com.chuross.rssmatome.infrastructure.android.query.AQuery;
import com.chuross.rssmatome.infrastructure.database.blog.BlogHelper;
import com.chuross.rssmatome.ui.adapter.EntryAdapter;
import com.google.common.collect.Lists;

import org.jdeferred.DoneCallback;
import org.jdeferred.DonePipe;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;
import org.jdeferred.android.AndroidExecutionScope;
import org.jdeferred.multiple.MasterProgress;
import org.jdeferred.multiple.MultipleResults;
import org.jdeferred.multiple.OneProgress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class EntryActivity extends Activity {

    private AQuery query;

    private EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        query = getAQuery();
        adapter = new EntryAdapter(getApplication(this));
        query.id(R.id.grid).itemClicked(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long l) {
                EntryActivity.this.onEntryClicked(position);
            }
        }).adapter(adapter).invisible();
        executeRefresh(!needRefresh());
    }

    private void onEntryClicked(int position) {
        Entry entry = adapter.getItem(position);
        Intent intent = new Intent(this, EntryDetailActivity.class);
        intent.putExtra(EntryDetailActivity.EXTRA_KEY_URL, entry.getUrl());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.entry, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(item.getItemId() == R.id.menu_refresh) {
            return onRefreshSelected();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean onRefreshSelected() {
        executeRefresh(false);
        return true;
    }

    @Override
    protected void onDestroy() {
        cancelAll();
        super.onDestroy();
    }

    private void executeRefresh(final boolean cached) {
        adapter.clear();
        query.id(R.id.error_text).invisible();
        query.id(R.id.progress).visible();
        query.id(R.id.progress_text).text("ブログの一覧を取得しています");
        promise(AsyncTask.SERIAL_EXECUTOR, ConcurrentUtils.executeOrNull(AsyncTask.THREAD_POOL_EXECUTOR, new FutureTask<Void>(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return clearCacheIfNeeded(cached);
            }
        })), AndroidExecutionScope.UI).then(new DonePipe<Void, List<Blog>, Throwable, List<Blog>>() {
            @Override
            public Promise<List<Blog>, Throwable, List<Blog>> pipeDone(final Void result) {
                return pipeDoneClearCache(cached);
            }
        }).done(new DoneCallback<List<Blog>>() {
            @Override
            public void onDone(final List<Blog> result) {
                onDoneGetBlogs(Lists.newArrayList(result), cached);
            }
        }).fail(new FailCallback<Throwable>() {
            @Override
            public void onFail(final Throwable result) {
                displayError();
            }
        });
    }

    private Void clearCacheIfNeeded(boolean cached) {
        if(cached) {
            return null;
        }
        ActiveAndroid.clearCache();
        ActiveAndroid.beginTransaction();
        try {
            BlogHelper.deleteAll();
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return null;
    }

    private Promise<List<Blog>, Throwable, List<Blog>> pipeDoneClearCache(boolean cached) {
        return promise(AsyncTask.SERIAL_EXECUTOR, getBlogRepository().findAll(AsyncTask.THREAD_POOL_EXECUTOR, cached), AndroidExecutionScope.UI);
    }

    private void onDoneGetBlogs(List<Blog> blogs, boolean cached) {
        if(blogs == null || blogs.size() == 0) {
            displayError();
            return;
        }
        Collections.shuffle(blogs);
        query.id(R.id.progress_text).text("記事の一覧を取得しています");
        when(Executors.newSingleThreadExecutor(), promiseGetEntries(AsyncTask.SERIAL_EXECUTOR, blogs, cached)).progress(new ProgressCallback<MasterProgress>() {
            @Override
            public void onProgress(final MasterProgress progress) {
                adapter.addAll(progress instanceof OneProgress ? (List<Entry>) ((OneProgress) progress).getProgress() : new ArrayList<Entry>());
                if(progress.getTotal() == progress.getDone() && progress.getFail() <= 0) {
                    getSharedPreferences().putLastUpdatedAt(getNow());
                }
            }
        }).done(new DoneCallback<MultipleResults>() {
            @Override
            public void onDone(final MultipleResults result) {
                query.id(R.id.progress).invisible();
            }
        });
    }

    private Promise<List<Entry>, Throwable, List<Entry>>[] promiseGetEntries(Executor executor, List<Blog> blogs, boolean cached) {
        List<Promise<List<Entry>, Throwable, List<Entry>>> promises = Lists.newArrayList();
        for(Blog blog : blogs) {
            promises.add(promise(executor, getEntryRepository().findAll(executor, blog.getIdentity(), cached), AndroidExecutionScope.UI));
        }
        return promises.toArray(new Promise[promises.size()]);
    }

    private void displayError() {
        query.id(R.id.error_text).visible();
        query.id(R.id.progress).invisible();
        query.id(R.id.grid).invisible();
    }

}
