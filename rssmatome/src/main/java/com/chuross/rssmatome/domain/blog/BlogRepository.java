package com.chuross.rssmatome.domain.blog;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public interface BlogRepository {


    public Future<Blog> find(Executor executor, BlogIdentity identity, boolean cached);

    public Blog find(BlogIdentity identity, boolean cached);


    public Future<List<Blog>> findAll(Executor executor, boolean cached);

    public List<Blog> findAll(boolean cached);

    public Future<Blog> add(Executor executor, Blog blog);

    public Blog add(Blog blog);

}
