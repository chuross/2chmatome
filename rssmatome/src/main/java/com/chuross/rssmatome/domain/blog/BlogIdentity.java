package com.chuross.rssmatome.domain.blog;

import com.chuross.rssmatome.domain.Identity;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class BlogIdentity implements Identity<String> {

    private final String value;

    public BlogIdentity(String value) {
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
        if(!(o instanceof BlogIdentity)) {
            return false;
        }
        return ((BlogIdentity) o).getValue().equals(value);
    }

}
