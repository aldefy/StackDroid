package com.stackdroid.ui.home;

import com.stackdroid.api.models.QItems;
import com.stackdroid.api.models.TagItem;

import java.util.List;

/**
 * Created by aditlal on 16/11/16.
 */

interface HomeView {

    void questionsLoaded(List<QItems> items);

    void tagsLoaded(List<TagItem> items);

    void showProgress();

    void hideProgress();

    void dbSaveSuccess();

    void onFailure(Throwable error);

    void onItemClick(QItems qItem);
}
