package com.chuross.rssmatome.infrastructure.android.query;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.androidquery.AbstractAQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.chuross.rssmatome.infrastructure.Utils.UrlUtils;
import com.chuross.rssmatome.infrastructure.android.logger.Logger;
import com.chuross.rssmatome.infrastructure.android.logger.LoggerFactory;
import com.chuross.rssmatome.infrastructure.api.Api;
import com.google.inject.Inject;

import net.arnx.jsonic.JSON;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

public class AQuery extends AbstractAQuery<AQuery> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AQuery.class);

    @Inject
    public AQuery(final Activity act) {
        super(act);
    }

    public AQuery(final View root) {
        super(root);
    }

    public AQuery(final Activity act, final View root) {
        super(act, root);
    }

    public AQuery(final Context context) {
        super(context);
    }

    private static <T> AjaxCallback<T> getSyncCallback(final Api<T> api, final AtomicReference<T> response) {
        return new AjaxCallback<T>() {
            @Override
            protected T transform(final String url, final byte[] data, final AjaxStatus status) {
                try {
                    return JSON.decode(new String(data), api.getResultType());
                } catch(Exception e) {
                    LOGGER.e("access transform failed.", e);
                    return null;
                }
            }

            @Override
            public void callback(final String url, final T result, final AjaxStatus status) {
                response.set(result);
            }
        };
    }

    public <T> Future<T> futureAccess(final Api<T> api) {
        final AtomicReference<T> response = new AtomicReference<T>();
        final AjaxCallback<T> ajaxCallback = getSyncCallback(api, response);
        return new FutureTask<T>(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return syncAccess(api, response, ajaxCallback);
            }
        }) {
            @Override
            public boolean cancel(final boolean mayInterruptIfRunning) {
                ajaxCallback.cancel();
                return super.cancel(mayInterruptIfRunning);
            }
        };
    }

    public <T> T syncAccess(Api<T> api) {
        final AtomicReference<T> response = new AtomicReference<T>();
        return syncAccess(api, response, getSyncCallback(api, response));
    }

    public <T> T syncAccess(Api<T> api, AtomicReference<T> response, AjaxCallback<T> ajaxCallback) {
        execute(api, ajaxCallback);
        ajaxCallback.block();
        return response.get();
    }

    private <T> void execute(Api<T> api, AjaxCallback<T> ajaxCallback) {
        final String url = api.getUrl();
        final Map<String, Object> parameters = api.getParameters();
        final Class<T> resultType = api.getResultType();
        switch(api.getMethod()) {
            case GET:
                ajax(UrlUtils.appendParameterString(url, parameters), resultType, ajaxCallback);
                break;
            case POST:
                ajax(url, parameters, resultType, ajaxCallback);
                break;
            case DELETE:
                delete(url, resultType, ajaxCallback);
                break;
            case PUT:
            default:
                throw new UnsupportedOperationException();
        }
    }

}
