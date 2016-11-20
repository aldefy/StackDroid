package com.stackdroid.ui.bookmark;

import com.stackdroid.api.models.QItems;

import java.util.List;

/**
 * Created by aditlal on 19/11/16.
 */

public interface BookmarkView {
    void questionsLoaded(List<QItems> items);

    void showProgress();

    void hideProgress();

    void onFailure(Throwable error);
}
