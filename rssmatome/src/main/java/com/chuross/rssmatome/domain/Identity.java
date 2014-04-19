package com.chuross.rssmatome.domain;

public interface Identity<T> {

    public T getValue();

    public boolean equals(Object o);

}
