package com.chuross.rssmatome.infrastructure.android.logger;

public final class LoggerFactory {

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }

}
