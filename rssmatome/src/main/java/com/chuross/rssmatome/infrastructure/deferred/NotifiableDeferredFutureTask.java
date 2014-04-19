package com.chuross.rssmatome.infrastructure.deferred;

import org.jdeferred.DeferredCallable;
import org.jdeferred.DeferredFutureTask;
import org.jdeferred.DeferredManager;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NotifiableDeferredFutureTask<D> extends DeferredFutureTask<D, D> {

    private Future<D> future;

    public NotifiableDeferredFutureTask(final Future<D> future) {
        super((Callable<D>) new DeferredCallable<D, D>(DeferredManager.StartPolicy.AUTO) {
            @Override
            public D call() throws Exception {
                return future.get();
            }
        });
        this.future = future;
    }

    @Override
    protected void done() {
        try {
            if(isCancelled()) {
                deferred.reject(new CancellationException());
                return;
            }
            D result = get();
            deferred.notify(result);
            deferred.resolve(result);
        } catch(InterruptedException e) {
        } catch(ExecutionException e) {
            deferred.reject(e.getCause());
        } catch(Exception e) {
            deferred.reject(e);
        }
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        future.cancel(mayInterruptIfRunning);
        return super.cancel(mayInterruptIfRunning);
    }
}
