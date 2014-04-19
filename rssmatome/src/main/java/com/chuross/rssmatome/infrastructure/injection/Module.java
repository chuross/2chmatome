package com.chuross.rssmatome.infrastructure.injection;

import com.chuross.rssmatome.domain.blog.BlogRepository;
import com.chuross.rssmatome.domain.blog.DefaultBlogRepository;
import com.chuross.rssmatome.domain.entry.DefaultEntryRepository;
import com.chuross.rssmatome.domain.entry.EntryRepository;
import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(BlogRepository.class).to(DefaultBlogRepository.class);
        bind(EntryRepository.class).to(DefaultEntryRepository.class);
    }

}
