package com.chuross.rssmatome.domain.entry;

import com.chuross.rssmatome.domain.Identity;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class EntryIdentity implements Identity<String> {

    private final String value;

    public EntryIdentity(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if(o == null) {
            return false;
        }
        if(!(o instanceof EntryIdentity)) {
            return false;
        }
        return ((EntryIdentity) o).getValue().equals(value);
    }

}
