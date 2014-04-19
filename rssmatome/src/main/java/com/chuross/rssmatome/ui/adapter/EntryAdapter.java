package com.chuross.rssmatome.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chuross.rssmatome.R;
import com.chuross.rssmatome.application.Application;
import com.chuross.rssmatome.domain.entry.Entry;
import com.chuross.rssmatome.infrastructure.android.query.AQuery;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Executors;

public class EntryAdapter extends AbstractArrayAdapter<Entry> {

    private Picasso picasso;

    public EntryAdapter(final Application application) {
        super(application);
        init(application);
    }

    public EntryAdapter(final Application application, final List<Entry> objects) {
        super(application, objects);
        init(application);
    }

    private void init(Application application) {
        picasso = new Picasso.Builder(application).executor(Executors.newFixedThreadPool(2)).downloader(new OkHttpDownloader(application, application.getConfig().getDiscCacheSize())).build();
    }

    @Override
    protected int getResourceId() {
        return R.layout.adapter_entry;
    }

    @Override
    protected void setView(final AQuery query, final int position, final View view, final Entry item) {
        query.id(R.id.entry_image_container).visible();
        final ImageView image = query.id(R.id.entry_image).getImageView();
        picasso.cancelRequest(image);
        if(item.hasImageUrl()) {
            picasso.load(item.getImageUrl()).fit().centerCrop().into(image);
        } else {
            query.id(R.id.entry_image_container).gone();
        }
        query.id(R.id.blog_text).text(Entry.getBlogName(item));
        query.id(R.id.title_text).text(item.getTitle());
    }

}
