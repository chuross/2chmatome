package com.chuross.rssmatome.infrastructure.Utils;

import com.chuross.rssmatome.infrastructure.android.logger.Logger;
import com.chuross.rssmatome.infrastructure.android.logger.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public final class ConcurrentUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentUtils.class);

    public static <V> FutureTask<V> executeOrNull(Executor executor, FutureTask<V> future) {
        try {
            return execute(executor, future);
        } catch(Throwable tr) {
            LOGGER.e("execute error.", tr);
            return null;
        }
    }

    public static <V> FutureTask<V> execute(Executor executor, FutureTask<V> future) {
        if(executor == null || future == null) {
            return null;
        }
        executor.execute(future);
        return future;
    }

    public static <T> T getOrNull(Future<T> future) {
        try {
            return get(future);
        } catch(Throwable tr) {
            LOGGER.e("get error.", tr);
            return null;
        }
    }

    public static <T> T get(Future<T> future) throws ExecutionException, InterruptedException {
        return future.get();
    }

}
