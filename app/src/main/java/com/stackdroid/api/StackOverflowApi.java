package com.stackdroid.api;

import com.stackdroid.api.models.QuestionsResponse;
import com.stackdroid.api.models.TagsResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by aditlal on 06/04/16.
 */
public interface StackOverflowApi {

    @GET("/search/advanced?site=stackoverflow&answers=0&accepted=false&key=gaCiJfJ1VWLinsqScu4UZw((&page_size=10")
    Observable<QuestionsResponse> getQuestions(@Query("tagged") String tag, @Query("sort") String sort, @Query("order") String order, @Query("page") int page);

    @GET("/tags?order=desc&sort=popular&site=stackoverflow&page_size=100&key=gaCiJfJ1VWLinsqScu4UZw((")
    Observable<TagsResponse> getTags();
}