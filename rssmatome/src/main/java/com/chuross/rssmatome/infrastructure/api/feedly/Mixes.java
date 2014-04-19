package com.chuross.rssmatome.infrastructure.api.feedly;

import com.google.common.collect.Lists;

import net.arnx.jsonic.JSONHint;

import java.util.Date;
import java.util.List;

public class Mixes {

    private String id;

    private List<MixesAlternate> alternate;

    private String title;

    private String direction;

    private Date updatedAt;

    private List<MixesItem> items;

    public static List<MixesItem> getEntries(Mixes mixes) {
        return mixes != null ? mixes.getItems() : Lists.<MixesItem>newArrayList();
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public List<MixesAlternate> getAlternate() {
        return alternate;
    }

    public void setAlternate(final List<MixesAlternate> alternate) {
        this.alternate = alternate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    @JSONHint(name = "updated")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @JSONHint(name = "updated")
    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<MixesItem> getItems() {
        return items;
    }

    public void setItems(final List<MixesItem> items) {
        this.items = items;
    }

}
