package com.chuross.rssmatome.infrastructure.android;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class AndroidUtils {

    private AndroidUtils() {
    }

    public static String getString(Context context, int resourceId) {
        return context != null ? context.getString(resourceId) : null;
    }

    public static Integer getInteger(Context context, int resourceId) {
        return getResources(context) != null ? getResources(context).getInteger(resourceId) : null;
    }

    public static Resources getResources(Context context) {
        return context != null ? context.getResources() : null;
    }

    public static View inflate(Context context, int resourceId) {
        if(context == null) {
            return null;
        }
        return inflate(context, resourceId, null, false);
    }

    public static View inflate(Context context, int resourceId, ViewGroup parent) {
        if(context == null) {
            return null;
        }
        return inflate(context, resourceId, parent, parent != null);
    }

    public static View inflate(Context context, int resourceId, ViewGroup parent, boolean attachToRoot) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater == null) {
            return null;
        }
        return inflater.inflate(resourceId, parent, attachToRoot);
    }

}
