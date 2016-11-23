package com.stackdroid.ui.home;

import com.stackdroid.api.StackOverflowApi;
import com.stackdroid.api.models.QItems;
import com.stackdroid.api.models.QuestionsResponse;
import com.stackdroid.api.models.TagItem;
import com.stackdroid.api.models.TagsResponse;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by aditlal on 16/11/16.
 */

class HomeApiService {
    private final StackOverflowApi apiInterface;

    HomeApiService(StackOverflowApi apiInterface) {
        this.apiInterface = apiInterface;
    }

    Subscription getQuestions(String sort, int page, final QuestionsCallback callback) {

        return apiInterface.getQuestions("android", sort, "desc", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Observable::error)
                .map(QuestionsResponse::getItems)
                .subscribe(new Subscriber<List<QItems>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<QItems> qItemses) {
                        callback.onQuestionSuccess(qItemses);
                    }
                });
    }

    public Subscription getTags(final TagsCallback callback) {

        return apiInterface.getTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Observable::error)
                .map(TagsResponse::getItems)
                .subscribe(new Subscriber<List<TagItem>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<TagItem> tagItems) {
                        callback.onTagSuccess(tagItems);
                    }
                });
    }

    public Subscription saveItemToDb(QItems item, QuestionsCallback callback) {
        Subscription subscription = Observable.just(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<QItems, QItems>() {
                    @Override
                    public QItems call(QItems items) {
                        items.setFav(!items.isFav());
                        items.save();
                        return items;
                    }
                })
                .subscribe(new Subscriber<QItems>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(QItems items) {
//  Home fragment
                        callback.onDbSaveSuccess(items);

                    }
                });
        return subscription;
    }


    interface QuestionsCallback {
        void onQuestionSuccess(List<QItems> items);

        void onDbSaveSuccess(QItems item);

        void onError(Throwable retrofitError);
    }

    interface TagsCallback {
        void onTagSuccess(List<TagItem> tagItems);

        void onError(Throwable retrofitError);
    }
}
