package com.stackdroid.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aditlal on 16/04/16.
 */
public class TagsResponse {
    private String quota_max;

    @SerializedName("items")
    private List<TagItem> items;

    @SerializedName("has_more")
    private boolean has_more;

    private String quota_remaining;

    public String getQuota_max() {
        return quota_max;
    }

    public void setQuota_max(String quota_max) {
        this.quota_max = quota_max;
    }

    public List<TagItem> getItems() {
        return items;
    }

    public void setItems(List<TagItem> items) {
        this.items = items;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getQuota_remaining() {
        return quota_remaining;
    }

    public void setQuota_remaining(String quota_remaining) {
        this.quota_remaining = quota_remaining;
    }

    @Override
    public String toString() {
        return "ClassPojo [quota_max = " + quota_max + ", items = " + items + ", has_more = " + has_more + ", quota_remaining = " + quota_remaining + "]";
    }
}
