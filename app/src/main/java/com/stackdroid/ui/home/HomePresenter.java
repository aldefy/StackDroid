package com.stackdroid.ui.home;

import com.stackdroid.api.models.QItems;
import com.stackdroid.api.models.TagItem;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aditlal on 16/11/16.
 */

class HomePresenter {
    private final HomeApiService homeApiService;
    private final HomeView view;
    private CompositeSubscription compositeSubscription;

    HomePresenter(HomeApiService homeApiService, HomeView view) {
        this.homeApiService = homeApiService;
        this.view = view;
        this.compositeSubscription = new CompositeSubscription();
    }

    void getQuestions(String sort, int page) {
        if (page == 1)
            view.showProgress();

        Subscription qnSubscription = homeApiService.getQuestions(sort, page, new HomeApiService.QuestionsCallback() {
            @Override
            public void onQuestionSuccess(List<QItems> itemses) {
                view.hideProgress();
                view.questionsLoaded(itemses);
            }

            @Override
            public void onDbSaveSuccess(QItems item) {

            }


            @Override
            public void onError(Throwable error) {
                view.hideProgress();
                view.onFailure(error);
            }
        });

        compositeSubscription.add(qnSubscription);
    }


    public void saveQnInDb(QItems qItem) {

        homeApiService.saveItemToDb(qItem, new HomeApiService.QuestionsCallback() {
            @Override
            public void onQuestionSuccess(List<QItems> items) {
            }

            @Override
            public void onDbSaveSuccess(QItems item) {
                view.dbSaveSuccess();

            }

            @Override
            public void onError(Throwable retrofitError) {

            }
        });
    }


    void getTags() {

        Subscription tagSubscription = homeApiService.getTags(new HomeApiService.TagsCallback() {
            @Override
            public void onTagSuccess(List<TagItem> tagItems) {
                view.tagsLoaded(tagItems);
            }

            @Override
            public void onError(Throwable error) {
                view.onFailure(error);
            }
        });

        compositeSubscription.add(tagSubscription);
    }


    void onStop() {
        compositeSubscription.unsubscribe();
    }


}
