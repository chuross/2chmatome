package com.chuross.rssmatome.domain.entry;

import com.chuross.rssmatome.domain.blog.BlogIdentity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public interface EntryRepository {

    public Future<Entry> find(Executor executor, EntryIdentity identity, boolean cached);

    public Entry find(EntryIdentity identity, boolean cached);

    public Future<List<Entry>> findAll(Executor executor, boolean cached);

    public List<Entry> findAll(boolean cached);

    public Future<List<Entry>> findAll(Executor executor, BlogIdentity identity);

    public List<Entry> findAll(BlogIdentity blogIdentity);

    public Future<List<Entry>> findAll(Executor executor, BlogIdentity identity, boolean cached);

    public List<Entry> findAll(BlogIdentity identity, boolean cached);

    public Future<Entry> add(Executor executor, Entry entry);

    public Entry add(Entry entry);

}
