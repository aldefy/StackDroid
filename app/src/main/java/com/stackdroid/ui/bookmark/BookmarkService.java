package com.stackdroid.ui.bookmark;

import com.stackdroid.api.models.QItems;

import java.util.List;

import co.uk.rushorm.core.RushSearch;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by aditlal on 19/11/16.
 */

 class BookmarkService {

    Subscription getQuestionsFromDB(final DataCallback dataCallback) {
        return Observable.create(
                new Observable.OnSubscribe<List<QItems>>() {
                    @Override
                    public void call(Subscriber<? super List<QItems>> sub) {
                        List<QItems> list = new RushSearch().find(QItems.class);
                        sub.onNext(list);
                        sub.onCompleted();
                    }
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<QItems>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dataCallback.onError(e);
                    }

                    @Override
                    public void onNext(List<QItems> qItemses) {
                        Timber.tag("DbQuery").d(qItemses.toString());
                        dataCallback.onSuccess(qItemses);
                    }
                });
    }

    interface DataCallback {
        void onSuccess(List<QItems> tagItems);

        void onError(Throwable retrofitError);
    }
}
