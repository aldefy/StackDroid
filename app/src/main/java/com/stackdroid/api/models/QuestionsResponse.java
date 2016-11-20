package com.stackdroid.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.annotations.RushList;
import timber.log.Timber;

/**
 * Created by aditlal on 16/04/16.
 */
public class QuestionsResponse implements Rush {
    private String quota_max;

    @RushList(classType = QItems.class)
    @SerializedName("items")
    private List<QItems> items;

    @SerializedName("has_more")
    private boolean has_more;

    private String quota_remaining;

    public String getQuota_max() {
        return quota_max;
    }

    public void setQuota_max(String quota_max) {
        this.quota_max = quota_max;
    }

    public List<QItems> getItems() {
        return items;
    }

    public void setItems(List<QItems> items) {
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

    @Override
    public void save() {
        Timber.tag("RushSave").d(toString());
        RushCore.getInstance().save(this);
    }

    @Override
    public void save(RushCallback callback) {
        RushCore.getInstance().save(this, callback);
    }

    @Override
    public void delete() {
        RushCore.getInstance().delete(this);
    }

    @Override
    public void delete(RushCallback callback) {
        RushCore.getInstance().delete(this, callback);
    }

    @Override
    public String getId() {
        return RushCore.getInstance().getId(this);
    }
}
