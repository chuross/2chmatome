package com.chuross.rssmatome.domain;

public interface Entity<T extends Identity<?>> extends Cloneable {

    public T getIdentity();

    public boolean equals(Object o);

}
