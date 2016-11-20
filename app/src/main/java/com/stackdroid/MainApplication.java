package com.stackdroid;

import android.app.Application;

import com.squareup.picasso.Picasso;
import com.stackdroid.api.models.Owner;
import com.stackdroid.api.models.QItems;
import com.stackdroid.api.models.QTags;
import com.stackdroid.api.models.QuestionsResponse;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;
import timber.log.Timber;

/**
 * Created by aditlal on 04/04/16.
 */
public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Timber.tag("StackDroid");
        }
        Picasso.Builder builder = new Picasso.Builder(this);
        Picasso built = builder.build();
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
        initializeDB();

    }

    private void initializeDB() {
        List<Class<? extends Rush>> classes = new ArrayList<>();
        // Add classes
        classes.add(QuestionsResponse.class);
        classes.add(QItems.class);
        classes.add(Owner.class);
        classes.add(QTags.class);
        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        config.setClasses(classes);
        RushCore.initialize(config);
    }


}
