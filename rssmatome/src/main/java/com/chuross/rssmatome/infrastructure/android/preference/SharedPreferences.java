package com.chuross.rssmatome.infrastructure.android.preference;

import android.content.Context;

import java.util.Date;

public class SharedPreferences {

    private static final String NAME = "2chmatome";
    private static final String KEY_LAST_UPDATED_AT = "last_updated_at";

    private final android.content.SharedPreferences sharedPreferences;

    public SharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public Date getLastUpdatedAt() {
        return new Date(sharedPreferences.getLong(KEY_LAST_UPDATED_AT, 0L));
    }

    public boolean putLastUpdatedAt(Date updatedAt) {
        return sharedPreferences.edit().putLong(KEY_LAST_UPDATED_AT, updatedAt.getTime()).commit();
    }

}
