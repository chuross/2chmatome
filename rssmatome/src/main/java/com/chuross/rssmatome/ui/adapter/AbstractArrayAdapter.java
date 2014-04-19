package com.chuross.rssmatome.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.chuross.rssmatome.infrastructure.android.AndroidUtils;
import com.chuross.rssmatome.infrastructure.android.query.AQuery;

import java.util.List;

abstract public class AbstractArrayAdapter<T> extends ArrayAdapter<T> {

    public AbstractArrayAdapter(final Context context) {
        super(context, 0);
    }

    public AbstractArrayAdapter(final Context context, final List<T> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView != null ? convertView : AndroidUtils.inflate(getContext(), getResourceId(), parent, false);
        setView(new AQuery(view), position, view, getItem(position));
        return view;
    }

    abstract protected int getResourceId();

    abstract protected void setView(AQuery query, int position, View view, T item);

}
