package com.chuross.rssmatome.infrastructure.android.logger;

import android.util.Log;

public class Logger {

    private final String tag;

    Logger(Class<?> clazz) {
        tag = clazz.getSimpleName();
    }

    public int d(String message) {
        return Log.d(tag, message);
    }

    public int d(String message, Throwable tr) {
        return Log.d(tag, message, tr);
    }

    public int e(String message) {
        return Log.e(tag, message);
    }

    public int e(String message, Throwable tr) {
        return Log.e(tag, message, tr);
    }

    public int i(String message) {
        return Log.i(tag, message);
    }

    public int i(String message, Throwable tr) {
        return Log.i(tag, message, tr);
    }

    public int v(String message) {
        return Log.v(tag, message);
    }

    public int v(String message, Throwable tr) {
        return Log.v(tag, message, tr);
    }

    public int w(String message) {
        return Log.w(tag, message);
    }

    public int w(String message, Throwable tr) {
        return Log.w(tag, message, tr);
    }

}
