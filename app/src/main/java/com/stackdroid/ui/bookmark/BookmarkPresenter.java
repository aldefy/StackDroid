package com.stackdroid.ui.bookmark;

import com.stackdroid.api.models.QItems;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aditlal on 16/11/16.
 */

class BookmarkPresenter {
    private final BookmarkService bookmarkService;
    private final BookmarkView view;
    private CompositeSubscription compositeSubscription;

    BookmarkPresenter(BookmarkService bookmarkService, BookmarkView view) {
        this.bookmarkService = bookmarkService;
        this.view = view;
        this.compositeSubscription = new CompositeSubscription();
    }

    void getQuestions() {
        view.showProgress();

        Subscription qnSubscription = bookmarkService.getQuestionsFromDB(new BookmarkService.DataCallback() {
            @Override
            public void onSuccess(List<QItems> itemses) {
                view.hideProgress();
                view.questionsLoaded(itemses);
            }


            @Override
            public void onError(Throwable error) {
                view.hideProgress();
                view.onFailure(error);
            }
        });

        compositeSubscription.add(qnSubscription);
    }




    void onStop() {
        compositeSubscription.unsubscribe();
    }
}
